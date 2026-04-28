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
package com.waaiu.net.server.cmd;

/**
 * Command-region routing bucket that tracks logic-server ids for one command
 * merge.
 *
 * @author
 * @date 2023-05-01
 */
public interface CmdRegion {

    void addServerId(int serverId);

    void removeByServerId(int serverId);

    int endpointLogicServerId(int[] logicServerIds);

    boolean hasServerId();

    int getAnyServerId();
}
