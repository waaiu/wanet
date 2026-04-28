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
package com.waaiu.net.extension.room.operation;

import com.waaiu.net.extension.domain.*;
import com.waaiu.net.extension.room.*;
import com.waaiu.net.framework.core.flow.*;
import lombok.extern.slf4j.*;

/**
 * Domain-event handler that executes room operation contexts and reports
 * user-facing exceptions.
 *
 * @author
 * @date 2024-05-12
 * @since 21.8
 */
@Slf4j
public final class OperationContextEventHandler implements DomainEventHandler<OperationContext> {
    @Override
    public void onEvent(OperationContext operationContext, boolean endOfBatch) {
        try {
            operationContext.execute();
        } catch (Throwable e) {
            FlowContext flowContext = operationContext.getFlowContext();
            if (flowContext == null) {
                log.error(e.getMessage(), e);
                return;
            }

            RoomKit.onException(e, flowContext);
        }
    }
}
