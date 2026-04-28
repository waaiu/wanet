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
package com.waaiu.net.framework.communication;

import com.waaiu.net.framework.protocol.*;

/**
 * Aggregate broadcast communication interface combining multicast, unicast, and
 * user-list broadcast capabilities.
 *
 * @author
 * @date 2025-09-07
 * @since 25.1
 */
public interface BroadcastCommunication {
    /**
     * Broadcast a message to a single user (unicast).
     *
     * @param message the unicast broadcast message
     */
    void broadcast(BroadcastUserMessage message);

    /**
     * Broadcast a message to a specific list of users.
     *
     * @param message the user-list broadcast message
     */
    void broadcast(BroadcastUserListMessage message);

    /**
     * Broadcast a message to multiple recipients via multicast.
     *
     * @param message the multicast broadcast message
     */
    void broadcast(BroadcastMulticastMessage message);
}
