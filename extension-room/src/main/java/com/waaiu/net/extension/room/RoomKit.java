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
package com.waaiu.net.extension.room;

import com.waaiu.net.framework.core.exception.*;
import com.waaiu.net.framework.core.flow.*;
import com.waaiu.net.framework.protocol.*;
import java.util.*;
import lombok.experimental.*;
import lombok.extern.slf4j.*;

/**
 * Room related utilities
 *
 * @author
 * @date 2024-04-30
 * @since 21.7
 */
@Slf4j
@UtilityClass
public class RoomKit {
    /**
     * Get an empty seat number from the room
     *
     * @param room The room
     * @return Empty seat number. When the value is -1, it means there are no empty
     *         seats (the room is full).
     */
    public int getEmptySeatNo(Room room) {
        // Player seat map
        var playerSeatMap = room.getPlayerSeatMap();

        for (int i = 0; i < room.getSpaceSize(); i++) {
            if (!playerSeatMap.containsKey(i)) {
                return i;
            }
        }

        return -1;
    }

    FlowContext ofFlowContext(long userId) {
        var requestMessage = new UserRequestMessage();
        requestMessage.setUserId(userId);

        var flowContext = new DefaultFlowContext();
        flowContext.setRequest(requestMessage);

        return flowContext;
    }

    /**
     * Send exception to the current user
     *
     * @param e           The exception
     * @param flowContext The flowContext of the current user
     */
    public void onException(Throwable e, FlowContext flowContext) {
        Objects.requireNonNull(flowContext);

        var broadcastUserMessage = new BroadcastUserMessage();

        if (e instanceof MessageException messageException) {
            broadcastUserMessage.setError(messageException.getErrorInformation());
        } else {
            broadcastUserMessage.setError(ActionErrorEnum.systemOtherErrCode);

            log.error(e.getMessage(), e);
        }

        flowContext.broadcastMe(broadcastUserMessage);
    }
}
