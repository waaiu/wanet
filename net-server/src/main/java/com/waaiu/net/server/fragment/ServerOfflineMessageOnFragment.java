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
package com.waaiu.net.server.fragment;

import com.waaiu.net.common.*;
import com.waaiu.net.sbe.*;
import com.waaiu.net.server.*;
import io.aeron.logbuffer.*;
import org.agrona.*;

/**
 * Handles server-offline notifications and triggers peer offline cleanup.
 *
 * @author
 * @date 2025-09-26
 * @since 25.1
 */
public final class ServerOfflineMessageOnFragment implements OnFragment, NetServerSettingAware {
    final ServerOfflineMessageDecoder decoder = new ServerOfflineMessageDecoder();
    NetServerSetting setting;

    @Override
    public void setNetServerSetting(NetServerSetting setting) {
        this.setting = setting;
    }

    @Override
    public void process(DirectBuffer buffer, int offset, int actingBlockLength, int actingVersion, Header header) {
        decoder.wrap(buffer, offset, actingBlockLength, actingVersion);

        int serverId = decoder.serverId();
        var server = ServerManager.getServerById(serverId);
        if (server == null) {
            return;
        }

        ServerLineKit.offlineProcess(server, setting);
    }

    @Override
    public int getTemplateId() {
        return ServerOfflineMessageDecoder.TEMPLATE_ID;
    }
}
