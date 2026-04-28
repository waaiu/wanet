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
package com.waaiu.net.framework.core;

import com.waaiu.net.common.kit.concurrent.executor.*;
import com.waaiu.net.framework.*;
import com.waaiu.net.framework.core.flow.*;
import com.waaiu.net.framework.protocol.*;
import java.util.*;

/**
 * Lightweight {@link DefaultFlowContext} used when no real client request is
 * available,
 * for example during runner execution or internal framework operations.
 * <p>
 * Initializes with a dummy {@link UserRequestMessage}, a zero-valued
 * {@link CmdInfo},
 * and a vixecutor.
 *
 * @author
 * @date 2025-10-05
 * @since 25.1
 */
class EmptyFlowContext extends DefaultFlowContext {
    Server server;

    EmptyFlowContext() {
        Request request = new UserRequestMessage();
        this.setRequest(request);
        this.setCmdInfo(CmdInfo.of(0));

        var threadExecutor = ExecutorRegionKit.getUserVirtualThreadExecutor(0);
        this.setCurrentThreadExecutor(threadExecutor);

        this.server = Server.recordBuilder()
                .setNetId(CoreGlobalConfig.getNetId())
                .setPayloadMap(Collections.emptyMap())
                .build();
    }

    @Override
    public Server getServer() {
        return this.server;
    }
}
