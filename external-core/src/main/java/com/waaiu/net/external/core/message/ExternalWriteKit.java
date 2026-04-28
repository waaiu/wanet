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

import com.waaiu.net.external.core.session.*;
import com.waaiu.net.framework.protocol.*;
import lombok.experimental.*;

/**
 * Writes outbound messages to a user session resolved from
 * {@link UserSessions}.
 *
 * @author
 * @date 2025-11-12
 * @since 25.1
 */
@UtilityClass
public final class ExternalWriteKit {
    /**
     * Resolve the target user session and flush the message if the session exists.
     *
     * @param message      outbound communication message
     * @param userSessions session container used to resolve the user channel
     */
    public void writeAndFlush(CommunicationMessage message, UserSessions<?, ?> userSessions) {
        if (userSessions == null) {
            return;
        }

        var userId = message.getUserId();
        // Identity-verified requests are indexed by business userId; otherwise use the
        // channel-scoped id.
        var userSession = message.isVerifyIdentity()
                ? userSessions.getUserSession(userId)
                : userSessions.getUserSessionByUserChannelId(userId);

        if (userSession != null) {
            userSession.writeAndFlush(message);
        }
    }
}
