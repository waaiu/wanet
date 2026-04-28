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
package com.waaiu.net.server.codec;

import com.waaiu.net.common.*;
import com.waaiu.net.common.kit.*;
import com.waaiu.net.framework.communication.eventbus.*;
import com.waaiu.net.sbe.*;
import org.agrona.*;

/**
 * SBE encoder for cross-server event-bus messages.
 *
 * @author
 * @date 2025-09-21
 * @since 25.1
 */
@SuppressWarnings("all")
public final class EventBusMessageSbe implements MessageSbe<EventBusMessage> {
    final EventBusMessageEncoder encoder = new EventBusMessageEncoder();

    @Override
    public void encoder(EventBusMessage message, MessageHeaderEncoder headerEncoder, MutableDirectBuffer buffer) {
        encoder.wrapAndApplyHeader(buffer, 0, headerEncoder);

        encoder.serverId(message.serverId)
                .threadIndex(message.threadIndex)
                .traceId(message.traceId)
                .topic(message.topic);

        var data = ByteKit.getBytes(message.data);
        encoder.putData(data, 0, data.length);
    }

    @Override
    public int limit() {
        return encoder.limit();
    }
}
