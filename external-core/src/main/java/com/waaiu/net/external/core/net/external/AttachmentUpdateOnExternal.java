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

import com.waaiu.net.common.kit.*;
import com.waaiu.net.framework.core.exception.*;
import com.waaiu.net.framework.protocol.*;

/**
 * Handles template requests that update a user's session attachment.
 *
 * @author
 * @date 2025-09-14
 * @since 25.1
 */
public final class AttachmentUpdateOnExternal implements OnExternal {
    @Override
    public void process(byte[] payload, int payloadLength, OnExternalContext context) {
        var userSession = context.getUserSession();
        if (userSession != null) {
            userSession.setAttachment(ByteKit.getPayload(payload, payloadLength));
        } else {
            context.response().setError(ActionErrorEnum.dataNotExist);
        }
    }

    @Override
    public int getTemplateId() {
        return OnExternalTemplateId.attachmentUpdate;
    }
}
