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
package com.waaiu.net.external.core.message;

import com.waaiu.net.framework.core.codec.*;
import com.waaiu.net.framework.protocol.*;

/**
 * Strategy for creating and serializing external communication messages.
 *
 * @author
 * @date 2023-12-15
 */
public interface CommunicationMessageCodec {
    /**
     * Create a new mutable message instance for outgoing writes.
     *
     * @return communication message implementation
     */
    CommunicationMessage createCommunicationMessage();

    /**
     * Encode a communication message with the currently configured data codec.
     *
     * @param message message to encode
     * @return serialized bytes
     */
    default byte[] encode(CommunicationMessage message) {
        var codec = DataCodecManager.getDataCodec();
        return codec.encode(message);
    }

    /**
     * Decode a serialized external message.
     *
     * @param bytes serialized bytes
     * @return decoded communication message
     */
    default CommunicationMessage decode(byte[] bytes) {
        var codec = DataCodecManager.getDataCodec();
        return codec.decode(bytes, ExternalMessage.class);
    }
}
