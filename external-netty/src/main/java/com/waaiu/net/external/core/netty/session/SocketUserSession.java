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
package com.waaiu.net.external.core.netty.session;

import com.waaiu.net.external.core.session.*;
import io.netty.channel.*;
import java.net.*;
import java.util.*;

/**
 * Netty channel-backed implementation of an external user session.
 *
 * @author
 * @date 2023-02-18
 */
public final class SocketUserSession extends AbstractUserSession {

    SocketUserSession(Channel channel, long userChannelId) {
        this.channel = channel;
        this.userChannelId = userChannelId;
    }

    @Override
    public boolean isActive() {
        return Objects.nonNull(this.channel) && this.channel.isActive();
    }

    @Override
    @SuppressWarnings("unchecked")
    public ChannelFuture writeAndFlush(Object message) {
        return this.channel.writeAndFlush(message);
    }

    @Override
    public String getIp() {
        String realIp = this.option(UserSessionOption.realIp);

        if (realIp.isEmpty()) {
            InetSocketAddress inetSocketAddress = (InetSocketAddress) channel.remoteAddress();
            return inetSocketAddress.getHostString();
        }

        return realIp;
    }
}
