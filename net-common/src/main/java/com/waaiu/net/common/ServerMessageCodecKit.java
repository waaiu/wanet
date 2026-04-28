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

import com.waaiu.net.framework.protocol.*;
import com.waaiu.net.sbe.*;
import lombok.experimental.*;

/**
 * Encodes and decodes {@link ServerMessage} common fields with SBE generated
 * codecs.
 *
 * @author
 * @date 2025-09-05
 * @since 25.1
 */
@UtilityClass
public final class ServerMessageCodecKit {
    /**
     * Encodes framework server-message fields into an SBE encoder.
     *
     * @param message source model
     * @param common  target SBE encoder
     */
    public void encoder(ServerMessage message, ServerMessageCommonEncoder common) {
        common.id(message.getId())
                .serverType(message.getServerType().getValue())
                .netId(message.getNetId())
                .ip(message.getIp())
                .name(message.getName())
                .tag(message.getTag());
    }

    /**
     * Decodes framework server-message fields from an SBE decoder.
     *
     * @param message target model
     * @param common  source SBE decoder
     */
    public void decoder(ServerMessage message, ServerMessageCommonDecoder common) {
        var serverType = ServerTypeEnum.valueOf(common.serverType());

        message.setId(common.id());
        message.setServerType(serverType);
        message.setNetId(common.netId());
        message.setIp(common.ip());
        message.setName(common.name());
        message.setTag(common.tag());
    }
}
