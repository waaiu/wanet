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

import com.waaiu.net.external.core.config.*;
import com.waaiu.net.framework.protocol.*;
import io.netty.channel.*;

/**
 * CmdCacheHandler, externalServer data cache
 *
 * @author
 * @date 2023-07-02
 */
@ChannelHandler.Sharable
public final class CmdCacheHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if (ExternalGlobalConfig.externalCmdCache == null) {
            // remove self
            ctx.pipeline().remove(this);
        }

        super.channelActive(ctx);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof CommunicationMessage message) {
            var cache = ExternalGlobalConfig.externalCmdCache.getCache(message);
            if (cache != null) {
                ctx.writeAndFlush(cache);
                return;
            }

            ctx.fireChannelRead(message);
        } else {
            ctx.fireChannelRead(msg);

        }
    }

    public CmdCacheHandler() {
    }

    public static CmdCacheHandler me() {
        return Holder.ME;
    }

    private static class Holder {
        static final CmdCacheHandler ME = new CmdCacheHandler();
    }
}
