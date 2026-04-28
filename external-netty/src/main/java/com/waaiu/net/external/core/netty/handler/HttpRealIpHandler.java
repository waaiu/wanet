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
package com.waaiu.net.external.core.netty.handler;

import com.waaiu.net.common.kit.*;
import com.waaiu.net.external.core.*;
import com.waaiu.net.external.core.netty.session.*;
import com.waaiu.net.external.core.session.*;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;

/**
 * HttpRealIpHandler
 * 
 * <pre>
 * nginx config
 *
 * server {
 *     location /websocket {
 *         proxy_http_version 1.1;
 *         proxy_set_header Upgrade $http_upgrade;
 *         proxy_set_header Connection "Upgrade";
 *         proxy_set_header X-Real-IP $remote_addr;
 *         proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
 *         proxy_set_header Host $host;
 *     }
 * }
 * </pre>
 *
 * @author
 * @date 2023-08-16
 */
public final class HttpRealIpHandler extends ChannelInboundHandlerAdapter implements ExternalSettingAware {
    SocketUserSessions userSessions;

    @Override
    public void setExternalSetting(ExternalSetting setting) {
        this.userSessions = (SocketUserSessions) setting.userSessions();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest request) {
            HttpHeaders headers = request.headers();
            String realIp = headers.get("X-Real-IP");

            PresentKit.ifPresent(userSessions.getUserSession(ctx), userSession -> {
                userSession.option(UserSessionOption.realIp, realIp);
            });

            ctx.pipeline().remove(this);
        }

        super.channelRead(ctx, msg);
    }
}
