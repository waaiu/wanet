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
package com.waaiu.net.center;

import lombok.extern.slf4j.*;
import org.agrona.concurrent.*;

/**
 * Default center-server runtime that starts the center polling agent.
 *
 * @author
 * @date 2025-09-23
 * @since 25.1
 */
@Slf4j
final class DefaultCenterServer implements CenterServer {
    CenterAgent serverAgent;

    public DefaultCenterServer(CenterServerSetting setting) {
        this.serverAgent = new CenterAgent(setting);
    }

    public void onStart() {
        final IdleStrategy idleStrategy = new SleepingMillisIdleStrategy();
        var serverAgentRunner = new AgentRunner(idleStrategy, Throwable::printStackTrace,
                null, serverAgent);

        AgentRunner.startOnThread(serverAgentRunner);
    }
}
