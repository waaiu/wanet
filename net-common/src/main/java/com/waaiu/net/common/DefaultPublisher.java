/*
 * wanet
 * Copyright (C) 2021 - present   () . All Rights Reserved.
 * # waaiu.com . 
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package com.waaiu.net.common;

import com.waaiu.net.common.kit.*;
import com.waaiu.net.common.kit.concurrent.*;
import com.waaiu.net.framework.*;
import com.waaiu.net.sbe.*;
import io.aeron.*;
import java.nio.*;
import java.util.*;
import java.util.concurrent.*;
import lombok.extern.slf4j.*;
import org.agrona.concurrent.*;

/**
 * Default single-threaded {@link Publisher} implementation backed by
 * per-publication queues.
 *
 * <p>
 * Messages are queued by publication name and encoded on the publisher thread
 * before
 * being offered to Aeron.
 * </p>
 *
 * @author
 * @date 2025-09-27
 * @since 25.1
 */
@Slf4j
public final class DefaultPublisher implements Publisher {
    final Map<String, Publication> publicationMap = CollKit.ofConcurrentHashMap();
    final Map<String, Queue<Object>> messageQueueMap = CollKit.ofConcurrentHashMap();
    volatile boolean running = true;
    ExecutorService executorService;

    @Override
    public void addPublication(String name, Publication publication) {
        publicationMap.put(name, publication);
        messageQueueMap.putIfAbsent(name, new LinkedBlockingQueue<>());
    }

    @Override
    public void publishMessage(String name, Object message) {
        var queue = messageQueueMap.get(name);
        if (queue != null) {
            queue.offer(message);
        }
    }

    @Override
    public void startup() {
        executorService = ExecutorKit.newSingleScheduled("Publisher");
        executorService.submit(new DefaultPublisherRunnable());
    }

    @Override
    public void shutdown() {
        running = false;
        if (executorService != null) {
            executorService.shutdown();
        }
    }

    private class DefaultPublisherRunnable implements PublisherRunnable {
        private final UnsafeBuffer buffer = new UnsafeBuffer(
                ByteBuffer.allocateDirect(CoreGlobalConfig.publisherBufferSize));
        private final MessageHeaderEncoder headerEncoder = new MessageHeaderEncoder();

        @Override
        public void run() {
            try {
                while (running) {
                    extracted();
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            } finally {
                publicationMap.values().forEach(Publication::close);
            }
        }

        private void extracted() throws Exception {
            boolean messagesPublished = false;
            for (String key : messageQueueMap.keySet()) {
                var queue = messageQueueMap.get(key);
                var publication = publicationMap.get(key);

                if (queue == null || publication == null) {
                    continue;
                }

                Object message;
                while ((message = queue.poll()) != null) {
                    messagesPublished = true;
                    MessageSbe<Object> encoder = SbeMessageManager.getMessageEncoder(message.getClass());

                    if (encoder == null) {
                        log.error("MessageSbe Error: {} not exist!", message.getClass().getSimpleName());
                        continue;
                    }

                    encoder.encoder(message, headerEncoder, buffer);
                    int limit = encoder.limit();
                    // The encoder writes into the shared direct buffer and reports the message
                    // boundary via limit().
                    publication.offer(buffer, 0, limit);
                }
            }

            if (!messagesPublished) {
                // Back off briefly when all queues are empty to avoid a busy-spin loop.
                TimeUnit.MILLISECONDS.sleep(1);
            }
        }
    }
}
