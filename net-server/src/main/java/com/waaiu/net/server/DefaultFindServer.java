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

import com.waaiu.net.framework.core.exception.*;
import com.waaiu.net.framework.protocol.*;
import com.waaiu.net.server.balanced.*;
import com.waaiu.net.server.cmd.*;
import lombok.*;
import lombok.experimental.*;

/**
 * Default request router that resolves logic servers from load-balancing state.
 *
 * @author
 * @date 2025-10-11
 * @since 25.1
 */
@FieldDefaults(level = AccessLevel.PROTECTED)
final class DefaultFindServer implements FindServer {
    CmdRegions cmdRegions;
    LogicServerLoadBalanced logicServerLoadBalanced;

    @Override
    public void setNetServerSetting(NetServerSetting setting) {
        this.logicServerLoadBalanced = setting.balancedManager().getLogicBalanced();
        this.cmdRegions = setting.cmdRegions();
    }

    @Override
    public Server getServer(Request message) {
        int cmdMerge = message.getCmdMerge();
        Server server = this.logicServerLoadBalanced.getServerByCmdMerge(cmdMerge);
        if (server == null) {
            message.setOutputError(ActionErrorEnum.cmdInfoErrorCode);
        } else {
            message.setLogicServerId(server.id());
        }

        return server;
    }
}
