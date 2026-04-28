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
package com.waaiu.net.external.core.net.fragment;

import com.waaiu.net.common.*;
import com.waaiu.net.common.kit.*;
import com.waaiu.net.external.core.message.*;
import com.waaiu.net.framework.protocol.*;
import com.waaiu.net.sbe.*;
import io.aeron.logbuffer.*;
import org.agrona.*;

/**
 * Aeron fragment consumer that broadcasts a decoded message to all connected
 * users.
 *
 * @author
 * @date 2025-09-03
 * @since 25.1
 */
public class BroadcastMulticastMessageOnFragment implements OnFragment {
    final BroadcastMulticastMessageDecoder decoder = new BroadcastMulticastMessageDecoder();

    @Override
    public void process(DirectBuffer buffer, int offset, int actingBlockLength, int actingVersion, Header header) {
        decoder.wrap(buffer, offset, actingBlockLength, actingVersion);

        var message = CommunicationMessageKit.createCommunicationMessage();
        message.setCmdMerge(decoder.cmdMerge());

        var dataLength = decoder.dataLength();
        var dataBytes = ByteKit.ofBytes(dataLength);
        decoder.getData(dataBytes, 0, dataLength);
        message.setData(dataBytes);

        this.writeAndFlush(message);
    }

    /**
     * Broadcast the decoded message to all user sessions in the current external
     * server.
     *
     * @param message decoded outbound message
     */
    protected void writeAndFlush(CommunicationMessage message) {
        ExternalServerSingle.userSessions.broadcast(message);
    }

    @Override
    public int getTemplateId() {
        return BroadcastMulticastMessageDecoder.TEMPLATE_ID;
    }
}
