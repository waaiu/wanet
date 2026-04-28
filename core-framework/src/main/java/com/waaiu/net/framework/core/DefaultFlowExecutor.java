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

import com.waaiu.net.framework.core.flow.*;
import com.waaiu.net.framework.core.kit.*;
import lombok.extern.slf4j.*;

/**
 * Default {@link FlowExecutor} implementation that executes the flow pipeline
 * within a {@link ScopedValue} context for thread-scoped FlowContext
 * propagation.
 *
 * @author
 * @date 2021-12-17
 */
@Slf4j
final class DefaultFlowExecutor implements FlowExecutor {
    @Override
    public void execute(final FlowContext flowContext, final BarSkeleton barSkeleton) {
        ScopedValue.where(FlowContextKeys.FLOW_CONTEXT, flowContext).run(() -> {
            try {
                FlowExecutorKit.execute(flowContext, barSkeleton);
            } catch (Throwable e) {
                log.error(e.getMessage(), e);
            }
        });
    }
}
