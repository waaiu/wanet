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

import com.waaiu.net.framework.protocol.*;
import io.netty.channel.*;

/**
 * Filters out heartbeat messages when no idle handler is configured.
 *
 * @author
 * @date 2024-11-09
 * @since 21.20
 */
@ChannelHandler.Sharable
public final class SocketIdleExcludeHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        var message = (CommunicationMessage) msg;
        if (message.getCmdCode() == CmdCodeConst.IDLE) {
            return;
        }

        ctx.fireChannelRead(msg);
    }

    private SocketIdleExcludeHandler() {
    }

    public static SocketIdleExcludeHandler me() {
        return Holder.ME;
    }

    private static class Holder {
        static final SocketIdleExcludeHandler ME = new SocketIdleExcludeHandler();
    }
}
