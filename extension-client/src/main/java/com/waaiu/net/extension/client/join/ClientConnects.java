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
package com.waaiu.net.extension.client.join;

import com.waaiu.net.external.core.config.*;
import java.util.*;
import lombok.experimental.*;

/**
 * @author
 * @date 2023-07-07
 */
@UtilityClass
public class ClientConnects {
    Map<ExternalJoinEnum, ClientConnect> clientConnectMap = new HashMap<>();

    static {
        clientConnectMap.put(ExternalJoinEnum.TCP, new TcpClientStartup());
        clientConnectMap.put(ExternalJoinEnum.WEBSOCKET, new WebSocketClientStartup());
    }

    public void put(ExternalJoinEnum joinEnum, ClientConnect connect) {
        clientConnectMap.put(joinEnum, connect);
    }

    ClientConnect getClientConnect(ExternalJoinEnum joinEnum) {
        return clientConnectMap.get(joinEnum);
    }
}
