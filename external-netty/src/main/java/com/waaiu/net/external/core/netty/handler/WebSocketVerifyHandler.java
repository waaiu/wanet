/*
 * wanet
 * Copyright (C) 2021 - present   () . All Rights Reserved.
 * 
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
package com.waaiu.net.external.core.netty.handler;

import com.waaiu.net.external.core.*;
import com.waaiu.net.external.core.netty.session.*;
import com.waaiu.net.server.*;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import java.util.*;
import java.util.stream.*;

/**
 * Optional WebSocket handshake verification handler executed before protocol
 * upgrade completion.
 *
 * @author
 * @date 2023-08-03
 */
@ChannelHandler.Sharable
public class WebSocketVerifyHandler extends ChannelInboundHandlerAdapter implements ExternalSettingAware {

    protected SocketUserSessions userSessions;
    protected ConvenientCommunication convenientCommunication;

    @Override
    public void setExternalSetting(ExternalSetting setting) {
        this.userSessions = (SocketUserSessions) setting.userSessions();
        this.convenientCommunication = setting.convenientCommunication();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest request) {
            String uri = request.uri();
            var params = getParams(uri);

            // Developers can override the verify method for extension.
            var userSession = userSessions.getUserSession(ctx);
            boolean verify = verify(userSession, params);

            if (verify) {
                ctx.pipeline().remove(this);
            } else {
                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1,
                        HttpResponseStatus.UNAUTHORIZED);
                ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
                return;
            }
        }

        super.channelRead(ctx, msg);
    }

    /**
     * Verify the WebSocket handshake request.
     *
     * @param userSession user session associated with the Netty channel
     * @param params      handshake query parameters
     * @return false to reject the handshake and close the connection
     */
    protected boolean verify(SocketUserSession userSession, Map<String, String> params) {
        return true;
    }

    /**
     * Parse query parameters from the WebSocket request URI.
     *
     * @param uri request URI
     * @return parameter map
     */
    protected Map<String, String> getParams(String uri) {
        return new QueryStringDecoder(uri)
                .parameters()
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getFirst()));
    }
}
