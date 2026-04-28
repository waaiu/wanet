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
package com.waaiu.net.server.logic.codec;

import com.waaiu.net.common.*;
import com.waaiu.net.common.kit.*;
import com.waaiu.net.framework.protocol.*;
import com.waaiu.net.sbe.*;
import org.agrona.*;

/**
 * Base SBE encoder for internal send messages.
 *
 * @author
 * @date 2025-09-14
 * @since 25.1
 */
@SuppressWarnings("all")
public class SendMessageSbe implements MessageSbe<SendMessage> {
    protected final SendMessageEncoder encoder = new SendMessageEncoder();

    @Override
    public void encoder(SendMessage message, MessageHeaderEncoder headerEncoder, MutableDirectBuffer buffer) {
        encoder.wrapAndApplyHeader(buffer, 0, headerEncoder);

        SbeKit.encoderMessageCommon(message, encoder.common());
        encoder.hopCount((byte) message.getHopCount());

        extracted(message);

        var data = ByteKit.getBytes(message.getData());
        encoder.putData(data, 0, data.length);

        var attachment = ByteKit.getBytes(message.getAttachment());
        encoder.putAttachment(attachment, 0, attachment.length);
    }

    protected void extracted(SendMessage message) {
        encoder.bindingLogicServerIdsCount(0);
    }

    @Override
    public int limit() {
        return encoder.limit();
    }
}
