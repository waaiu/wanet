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
import com.waaiu.net.external.core.hook.*;
import com.waaiu.net.external.core.netty.session.*;
import com.waaiu.net.external.core.session.*;
import com.waaiu.net.framework.protocol.*;
import io.netty.channel.*;
import io.netty.handler.timeout.*;

/**
 * Netty idle handler that processes heartbeat frames and idle timeout events.
 *
 * @author
 * @date 2023-02-18
 */
@ChannelHandler.Sharable
public final class SocketIdleHandler extends ChannelInboundHandlerAdapter implements ExternalSettingAware, IdleHandler {
    /** Heartbeat event callback */
    IdleHook<IdleStateEvent> idleHook;
    /** true : Respond with a heartbeat to the client */
    boolean pong;
    UserSessions<ChannelHandlerContext, SocketUserSession> userSessions;

    @Override
    @SuppressWarnings("unchecked")
    public void setExternalSetting(ExternalSetting setting) {
        if (this.userSessions != null) {
            return;
        }

        var idleProcessSetting = setting.idleProcessSetting();
        this.idleHook = (IdleHook<IdleStateEvent>) idleProcessSetting.idleHook();
        this.pong = idleProcessSetting.pong();
        this.userSessions = (UserSessions<ChannelHandlerContext, SocketUserSession>) setting.userSessions();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        var message = (CommunicationMessage) msg;
        int cmdCode = message.getCmdCode();
        if (cmdCode != CmdCodeConst.IDLE) {
            ctx.fireChannelRead(msg);
            return;
        }

        if (this.pong) {
            if (this.idleHook != null) {
                // Allow custom logic to enrich the pong payload before writing it back.
                this.idleHook.pongBefore(message);
            }

            ctx.writeAndFlush(message);
        }
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent event) {

            boolean close = true;

            var userSession = userSessions.getUserSession(ctx);

            if (this.idleHook != null) {
                close = idleHook.callback(userSession, event);
            }

            // Remove and close the user session when the idle policy requests it.
            if (close) {
                this.userSessions.removeUserSession(userSession);
            }

        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}
