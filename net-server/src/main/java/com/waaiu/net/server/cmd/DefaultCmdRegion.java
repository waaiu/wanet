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

import com.waaiu.net.common.kit.*;
import com.waaiu.net.framework.core.kit.*;
import java.util.*;
import lombok.*;
import lombok.experimental.*;

/**
 * Default {@link CmdRegion} implementation backed by a compact int array.
 *
 * @author
 * @date 2023-04-30
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class DefaultCmdRegion implements CmdRegion {
    final int cmdMerge;
    int[] serverIds;
    int size = 0;

    public DefaultCmdRegion(int cmdMerge) {
        this.cmdMerge = cmdMerge;
        this.serverIds = new int[1];
    }

    @Override
    public void addServerId(int serverId) {
        if (contains(serverId)) {
            return;
        }

        if (size == serverIds.length) {
            serverIds = Arrays.copyOf(serverIds, serverIds.length * 2);
        }

        serverIds[size++] = serverId;
    }

    @Override
    public void removeByServerId(int serverId) {
        for (int i = 0; i < size; i++) {
            if (serverIds[i] == serverId) {
                serverIds[i] = serverIds[size - 1];
                size--;
                return;
            }
        }
    }

    private boolean contains(int serverId) {
        for (int i = 0; i < size; i++) {
            if (serverIds[i] == serverId) {
                return true;
            }
        }

        return false;
    }

    @Override
    public int endpointLogicServerId(int[] logicServerIds) {
        for (int logicServerId : logicServerIds) {
            for (int i = 0; i < size; i++) {
                if (serverIds[i] == logicServerId) {
                    return logicServerId;
                }
            }
        }

        return 0;
    }

    @Override
    public boolean hasServerId() {
        return size > 0;
    }

    @Override
    public int getAnyServerId() {
        return RandomKit.randomEle(serverIds);
    }

    @Override
    public String toString() {
        return "CmdRegion {" +
                CmdKit.toString(cmdMerge) +
                " -- serverIds : " + Arrays.toString(Arrays.copyOf(serverIds, size)) +
                '}';
    }
}
