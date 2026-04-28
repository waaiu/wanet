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
package com.waaiu.net.extension.client.join;

import com.waaiu.net.common.kit.*;
import com.waaiu.net.extension.client.*;
import com.waaiu.net.extension.client.user.*;
import com.waaiu.net.external.core.message.*;
import com.waaiu.net.framework.core.codec.*;
import java.net.*;
import java.nio.*;
import java.util.*;
import lombok.extern.slf4j.*;
import org.java_websocket.client.*;
import org.java_websocket.drafts.*;
import org.java_websocket.handshake.*;

/**
 * WebSocket client connector startup implementation.
 *
 * @author
 * @date 2023-07-04
 */
@Slf4j(topic = IonetLogName.CommonStdout)
class WebSocketClientStartup implements ClientConnect {
    @Override
    public void connect(ClientConnectOption option) {
        ClientUser clientUser = option.getClientUser();
        ClientUserChannel clientUserChannel = clientUser.getClientUserChannel();

        String wsUrl = option.getWsUrl();

        URI uri = null;
        try {
            uri = new URI(wsUrl);
        } catch (URISyntaxException e) {
            log.error(e.getMessage(), e);
        }

        WebSocketClient webSocketClient = new WebSocketClient(Objects.requireNonNull(uri), new Draft_6455()) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
                if (option.getConnectedCallback() != null) {
                    option.getConnectedCallback().run();
                }
            }

            @Override
            public void onMessage(String s) {
                log.info("onMessage : {}", s);
            }

            @Override
            public void onClose(int i, String s, boolean b) {
                log.info("onClose : {}", s);
            }

            @Override
            public void onError(Exception e) {
                log.error(e.getMessage(), e);
            }

            @Override
            public void onMessage(ByteBuffer byteBuffer) {
                byte[] msgBytes = byteBuffer.array();
                var message = DataCodecManager.decode(msgBytes, ExternalMessage.class);

                clientUserChannel.readMessage(message);
            }
        };

        clientUserChannel.setChannelAccept(message -> {
            var codec = DataCodecManager.getDataCodec();
            byte[] bytes = codec.encode(message);
            webSocketClient.send(bytes);
        });

        clientUserChannel.setCloseChannel(webSocketClient::close);

        clientUser.getClientUserInputCommands().start();

        // Start connecting to the server.
        webSocketClient.connect();
    }
}
