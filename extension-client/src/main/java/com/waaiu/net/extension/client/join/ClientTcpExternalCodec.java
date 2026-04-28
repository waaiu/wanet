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

import com.waaiu.net.external.core.message.*;
import com.waaiu.net.framework.core.codec.*;
import io.netty.buffer.*;
import io.netty.channel.*;
import io.netty.handler.codec.*;
import java.util.*;

/**
 * @author
 * @date 2023-12-15
 */
public class ClientTcpExternalCodec extends MessageToMessageCodec<ByteBuf, ExternalMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ExternalMessage message, List<Object> out) {
        var codec = DataCodecManager.getDataCodec();
        byte[] bytes = codec.encode(message);
        ByteBuf buffer = ctx.alloc().buffer(bytes.length + 4);
        buffer.writeInt(bytes.length);
        buffer.writeBytes(bytes);
        out.add(buffer);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) {
        byte[] msgBytes = new byte[msg.readInt()];
        msg.readBytes(msgBytes);

        ExternalMessage message = DataCodecManager.decode(msgBytes, ExternalMessage.class);
        out.add(message);
    }
}
