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

import com.waaiu.net.common.*;
import com.waaiu.net.framework.communication.*;
import com.waaiu.net.framework.core.*;
import com.waaiu.net.server.balanced.*;
import com.waaiu.net.server.cmd.*;
import com.waaiu.net.server.connection.*;
import com.waaiu.net.server.listener.*;
import io.aeron.*;
import java.util.*;
import lombok.*;

/**
 * Immutable runtime setting assembled by {@link NetServerBuilder}.
 *
 * @author
 * @date 2025-09-08
 * @since 25.1
 */
@Builder(setterPrefix = "set")
public record NetServerSetting(
        int netId, Aeron aeron, CmdRegions cmdRegions, ConnectionManager connectionManager,
        SkeletonThreadPipeline skeletonThreadPipeline, CommunicationAggregation communicationAggregation,
        FindServer findServer, BalancedManager balancedManager, FutureManager futureManager,
        List<ServerShutdownHook> serverShutdownHookList, List<ServerListener> listenerList, Publisher publisher,
        ConvenientCommunication convenientCommunication) {
}
