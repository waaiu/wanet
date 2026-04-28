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
package com.waaiu.net.framework.protocol;

/**
 * Message that carries a future correlation id for asynchronous
 * request/response matching.
 * <p>
 * Implemented by request and response messages that participate in
 * asynchronous communication, allowing the caller to correlate a response
 * back to its originating request.
 *
 * @author
 * @date 2025-11-07
 * @since 25.1
 */
public interface FutureMessage {
    /**
     * Get the future correlation id.
     *
     * @return the future id
     */
    long getFutureId();

    /**
     * Set the future correlation id.
     *
     * @param futureId the future id
     */
    void setFutureId(long futureId);
}
