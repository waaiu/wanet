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
 * SBE encoder for external request messages.
 *
 * @author
 * @date 2025-09-11
 * @since 25.1
 */
@SuppressWarnings("all")
public final class ExternalRequestMessageSbe implements MessageSbe<ExternalRequestMessage> {
    final ExternalRequestMessageEncoder encoder = new ExternalRequestMessageEncoder();

    @Override
    public void encoder(ExternalRequestMessage message, MessageHeaderEncoder headerEncoder,
            MutableDirectBuffer buffer) {
        encoder.wrapAndApplyHeader(buffer, 0, headerEncoder);

        SbeKit.encoderUserIdentity(message, encoder.userIdentity());

        encoder
                .futureId(message.getFutureId())
                .templateId((byte) message.getTemplateId())
                .externalServerId(message.getExternalServerId())
                .netId(message.getNetId())
                .traceId(message.getTraceId());

        var payload = ByteKit.getBytes(message.getPayload());
        encoder.putPayload(payload, 0, payload.length);
    }

    @Override
    public int limit() {
        return encoder.limit();
    }
}
