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
package com.waaiu.net.external.core.netty;

import com.waaiu.net.common.kit.concurrent.*;
import com.waaiu.net.external.core.*;
import com.waaiu.net.external.core.message.*;
import com.waaiu.net.external.core.micro.*;
import com.waaiu.net.server.*;
import java.util.*;

/**
 * Default {@link ExternalServer} implementation backed by a Netty
 * {@link MicroBootstrap}.
 *
 * @author
 * @date 2023-02-19
 */
public class DefaultExternalServer implements ExternalServer {
    ExternalSetting setting;
    MicroBootstrap microBootstrap;
    MicroBootstrapFlow<?> microBootstrapFlow;
    Set<Object> injectSet;

    /**
     * Create the external server from a transport-independent parameter bundle.
     *
     * @param parameter assembled external server bootstrap parameters
     */
    public DefaultExternalServer(ExternalServerCreatorParameter parameter) {
        this.setting = parameter.setting();
        this.microBootstrap = parameter.microBootstrap();
        this.microBootstrapFlow = parameter.microBootstrapFlow();
        this.injectSet = parameter.injectSet();

        ExternalServerSingle.userSessions = setting.userSessions();
    }

    @Override
    public void startup(NetServer netServer) {

        var server = this.setting.server();
        netServer.addServer(server);

        this.setting.option(ExternalSetting.netServerSetting, netServer.getNetServerSetting());
        this.injectSet.forEach(setting::inject);
        this.injectSet = null;

        // Start the external transport asynchronously so net-server startup can
        // continue.
        TaskKit.executeVirtual(() -> microBootstrap.startup(setting.port(), microBootstrapFlow));
    }
}
