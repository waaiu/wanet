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
package com.waaiu.net.server.listener;

import com.waaiu.net.framework.protocol.*;
import com.waaiu.net.server.*;
import lombok.extern.slf4j.*;

/**
 * Development listener that logs net-server peer lifecycle events.
 *
 * @author
 * @date 2025-09-26
 * @since 25.1
 */
@Slf4j
public final class DebugServerListener implements ServerListener {
    @Override
    public void connectBefore(Server server, NetServerSetting setting) {
        log.info("connectBefore {}", server.name());
    }

    @Override
    public void onlineServer(Server server, NetServerSetting setting) {
        log.info("onlineServer {}", server.name());
    }

    @Override
    public void offlineServer(Server server, NetServerSetting setting) {
        log.info("offlineServer {}", server.name());
    }
}
