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
package com.waaiu.net.server;

import com.waaiu.net.common.kit.*;
import com.waaiu.net.framework.protocol.*;
import java.util.*;
import java.util.function.*;
import lombok.*;
import lombok.experimental.*;

/**
 * Global registry for server metadata known to the current net-server process.
 *
 * @author
 * @date 2025-09-25
 * @since 25.1
 */
@UtilityClass
public final class ServerManager {
    @Getter
    final Map<Integer, Server> serverMap = CollKit.ofConcurrentHashMap();

    public void addServer(Server server) {
        serverMap.put(server.id(), server);
    }

    public void removeServer(Server server) {
        serverMap.remove(server.id());
    }

    public Server getServerById(int serverId) {
        return serverMap.get(serverId);
    }

    public void forEach(BiConsumer<Integer, Server> action) {
        serverMap.forEach(action);
    }

    public Server ofServer(ServerMessage server) {
        return Server.recordBuilder()
                .setId(server.getId())
                .setName(server.getName())
                .setTag(server.getTag())
                .setServerType(server.getServerType())
                .setNetId(server.getNetId())
                .setIp(server.getIp())
                .setPubName(String.valueOf(server.getNetId()))
                .setCmdMerges(server.getCmdMerges())
                .setPayloadMap(server.getPayloadMap())
                .build();
    }
}
