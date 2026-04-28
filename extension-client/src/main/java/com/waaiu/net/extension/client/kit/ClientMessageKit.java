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
package com.waaiu.net.extension.client.kit;

import com.waaiu.net.external.core.message.*;
import com.waaiu.net.framework.core.*;
import com.waaiu.net.framework.protocol.*;
import lombok.experimental.*;

/**
 * Factory helpers for client communication and idle messages.
 *
 * @author
 * @date 2025-09-11
 * @since 25.1
 */
@UtilityClass
public final class ClientMessageKit {
    public ExternalMessage ofCommunicationMessage(CmdInfo cmdInfo) {
        var message = CommunicationMessageKit.createCommunicationMessage();
        message.setCmdMerge(cmdInfo.cmdMerge());
        return (ExternalMessage) message;
    }

    public ExternalMessage ofIdleMessage() {
        var message = CommunicationMessageKit.createCommunicationMessage();
        message.setCmdCode(CmdCodeConst.IDLE);
        return (ExternalMessage) message;
    }
}
