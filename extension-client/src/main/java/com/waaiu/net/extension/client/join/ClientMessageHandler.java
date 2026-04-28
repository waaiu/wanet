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

import com.waaiu.net.extension.client.user.*;
import com.waaiu.net.external.core.message.*;
import io.netty.channel.*;

/**
 * @author
 * @date 2023-05-31
 */
@ChannelHandler.Sharable
public class ClientMessageHandler extends SimpleChannelInboundHandler<ExternalMessage> {

    final ClientUserChannel clientUserChannel;

    public ClientMessageHandler(ClientUser clientUser) {
        this.clientUserChannel = clientUser.getClientUserChannel();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ExternalMessage message) {
        clientUserChannel.readMessage(message);
    }
}
