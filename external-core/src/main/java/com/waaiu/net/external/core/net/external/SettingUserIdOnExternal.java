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
package com.waaiu.net.external.core.net.external;

import com.waaiu.net.framework.core.exception.*;
import com.waaiu.net.framework.protocol.*;

/**
 * Handles template requests that bind a business userId to a user channel.
 *
 * @author
 * @date 2025-09-10
 * @since 25.1
 */
public final class SettingUserIdOnExternal implements OnExternal {

    @Override
    public void process(byte[] payload, int payloadLength, OnExternalContext context) {
        if (context.verifyIdentity()) {
            context.response().setError(ActionErrorEnum.dataNotExist);
            return;
        }

        long userChannelId = context.userId();
        var userId = context.getPayloadAsLong();
        var userSessions = context.userSessions();

        boolean result = userSessions.settingUserId(userChannelId, userId);
        if (!result) {
            context.response().setError(ActionErrorEnum.dataNotExist);
        }
    }

    @Override
    public int getTemplateId() {
        return OnExternalTemplateId.settingUserId;
    }
}
