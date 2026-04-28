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
package com.waaiu.net.server.logic.fragment;

import com.waaiu.net.common.*;
import com.waaiu.net.framework.communication.*;
import com.waaiu.net.framework.protocol.*;
import com.waaiu.net.sbe.*;
import com.waaiu.net.server.*;
import io.aeron.logbuffer.*;
import org.agrona.*;

/**
 * Handles empty external-response messages and completes pending futures.
 *
 * @author
 * @date 2025-09-18
 * @since 25.1
 */
public final class EmptyExternalResponseMessageOnFragment implements OnFragment, NetServerSettingAware {
    final EmptyExternalResponseMessageDecoder decoder = new EmptyExternalResponseMessageDecoder();
    FutureManager futureManager;

    @Override
    public void setNetServerSetting(NetServerSetting setting) {
        this.futureManager = setting.futureManager();
    }

    static final EmptyExternalResponseMessage success = new EmptyExternalResponseMessage();

    @Override
    public void process(DirectBuffer buffer, int offset, int actingBlockLength, int actingVersion, Header header) {
        decoder.wrap(buffer, offset, actingBlockLength, actingVersion);

        EmptyExternalResponseMessage message;

        int errorCode = decoder.errorCode();
        if (errorCode == 0) {
            message = success;
        } else {
            message = new EmptyExternalResponseMessage();
            message.setErrorCode(errorCode);
            message.setErrorMessage(decoder.errorMessage());
        }

        var future = this.futureManager.remove(decoder.futureId());
        if (future != null) {
            future.complete(message);
        }
    }

    @Override
    public int getTemplateId() {
        return EmptyExternalResponseMessageDecoder.TEMPLATE_ID;
    }
}
