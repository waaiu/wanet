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
import com.waaiu.net.common.kit.*;
import com.waaiu.net.framework.communication.*;
import com.waaiu.net.framework.protocol.*;
import com.waaiu.net.sbe.*;
import com.waaiu.net.server.*;
import io.aeron.logbuffer.*;
import lombok.extern.slf4j.*;
import org.agrona.*;

/**
 * Internal ResponseMessageOnFragment
 *
 * @author
 * @date 2025-08-30
 * @since 25.1
 */
@Slf4j
public final class ResponseMessageOnFragment implements OnFragment, NetServerSettingAware {
    final ResponseMessageDecoder decoder = new ResponseMessageDecoder();
    FutureManager futureManager;

    @Override
    public void setNetServerSetting(NetServerSetting setting) {
        this.futureManager = setting.futureManager();
    }

    @Override
    public void process(DirectBuffer buffer, int offset, int actingBlockLength, int actingVersion, Header header) {
        decoder.wrap(buffer, offset, actingBlockLength, actingVersion);

        var message = new ResponseMessage();
        SbeKit.decoderMessageCommon(message, decoder.common());
        SbeKit.decoderUserIdentity(message, decoder.userIdentity());

        message.setErrorCode(decoder.errorCode());
        message.setErrorMessage(decoder.errorMessage());

        var dataLength = decoder.dataLength();
        var dataBytes = ByteKit.ofBytes(dataLength);
        decoder.getData(dataBytes, 0, dataLength);
        message.setData(dataBytes);

        this.futureManager.complete(message);
    }

    @Override
    public int getTemplateId() {
        return ResponseMessageDecoder.TEMPLATE_ID;
    }
}
