/*
 * wanet
 * Copyright (C) 2021 - present   () . All Rights Reserved.
 * # waaiu.com . 
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

import com.waaiu.net.common.kit.*;
import com.waaiu.net.common.kit.concurrent.executor.*;
import com.waaiu.net.framework.protocol.*;
import com.waaiu.net.sbe.*;
import com.waaiu.net.server.*;
import com.waaiu.net.server.balanced.*;
import com.waaiu.net.server.listener.*;
import java.util.*;
import lombok.experimental.*;
import lombok.extern.slf4j.*;

/**
 * Shared online/offline processing helpers for peer state transitions.
 *
 * @author
 * @date 2025-09-20
 * @since 25.1
 */
@Slf4j
@UtilityClass
public final class ServerLineKit {
    private final Set<Integer> idRecordSet = CollKit.ofConcurrentSet();
    private final int threadIndex = ConnectResponseMessageDecoder.TEMPLATE_ID;

    public void onlineProcess(Server otherServer, NetServerSetting setting) {
        // Atomically check and add: if already present, skip duplicate processing
        if (!idRecordSet.add(otherServer.id())) {
            return;
        }

        ServerManager.addServer(otherServer);

        ExecutorRegionKit.getSimpleThreadExecutor(threadIndex).executeTry(() -> {

            for (ServerListener listener : setting.listenerList()) {
                listener.onlineServer(otherServer, setting);
            }

            BalancedManager balancedManager = setting.balancedManager();
            balancedManager.register(otherServer);
        });
    }

    public void offlineProcess(Server otherServer, NetServerSetting setting) {
        idRecordSet.remove(otherServer.id());

        ServerManager.removeServer(otherServer);

        ExecutorRegionKit.getSimpleThreadExecutor(threadIndex).executeTry(() -> {
            for (ServerListener listener : setting.listenerList()) {
                listener.offlineServer(otherServer, setting);
            }

            BalancedManager balancedManager = setting.balancedManager();
            balancedManager.unregister(otherServer);
        });
    }
}
