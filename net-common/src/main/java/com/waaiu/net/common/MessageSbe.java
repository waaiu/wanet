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
package com.waaiu.net.common;

import com.waaiu.net.sbe.*;
import org.agrona.*;

/**
 * Encodes a specific message type into an SBE buffer.
 *
 * @param <T> message type
 * @author
 * @date 2025-09-06
 * @since 25.1
 */
public interface MessageSbe<T> {
    /**
     * Encodes the given message into the supplied direct buffer.
     *
     * @param message       message instance
     * @param headerEncoder SBE message header encoder
     * @param buffer        target buffer
     */
    void encoder(T message, MessageHeaderEncoder headerEncoder, MutableDirectBuffer buffer);

    /**
     * Returns the encoded buffer limit after the last encode call.
     *
     * @return encoded message limit
     */
    int limit();
}
