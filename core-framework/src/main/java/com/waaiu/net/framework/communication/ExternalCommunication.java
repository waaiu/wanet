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
package com.waaiu.net.framework.communication;

import com.waaiu.net.framework.protocol.*;
import java.util.concurrent.*;

/**
 * Interface for communicating with external (client-facing) servers.
 * <p>
 * Provides both asynchronous and blocking methods to send a request to an
 * external server and receive a response.
 *
 * @author
 * @date 2025-09-07
 * @since 25.1
 */
public interface ExternalCommunication {
    /**
     * Send a request to an external server asynchronously.
     *
     * @param message the external request message
     * @return a {@link CompletableFuture} that completes with the external response
     */
    CompletableFuture<ExternalResponse> callExternalFuture(ExternalRequestMessage message);

    /**
     * Send a request to an external server and block until a response is received.
     *
     * @param message the external request message
     * @return the external response
     */
    ExternalResponse callExternal(ExternalRequestMessage message);
}
