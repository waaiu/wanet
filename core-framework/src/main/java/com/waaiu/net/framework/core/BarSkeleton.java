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
import com.waaiu.net.framework.communication.*;
import com.waaiu.net.framework.communication.eventbus.*;
import com.waaiu.net.framework.core.flow.*;
import com.waaiu.net.framework.core.runner.*;
import com.waaiu.net.framework.protocol.*;
import lombok.*;
import lombok.experimental.*;

/**
 * Central execution engine of the wanet business framework.
 * <p>
 * Holds action commands, flow executor, interceptors, and communication
 * aggregation.
 * Built via {@link BarSkeletonBuilder}.
 *
 * @author
 * @date 2021-12-12
 */
@FieldDefaults(level = AccessLevel.PUBLIC)
@Builder(access = AccessLevel.PACKAGE, builderClassName = "InternalBuilder", builderMethodName = "internalBuilder", setterPrefix = "set")
public final class BarSkeleton {
    final Runners runners;
    final ActionAfter actionAfter;
    final FlowExecutor flowExecutor;
    final ActionMethodInOut[] inOuts;
    final ExecutorRegion executorRegion;
    final ActionCommand[][] actionCommands;
    final ActionMethodInvoke actionMethodInvoke;
    final FlowContextFactory flowContextFactory;
    final ActionCommandRegions actionCommandRegions;
    final ActionFactoryBean<Object> actionFactoryBean;
    final ActionMethodExceptionProcess actionMethodExceptionProcess;

    Server server;
    EventBus eventBus;
    CommunicationAggregation communicationAggregation;

    /**
     * Create a new {@link BarSkeletonBuilder} instance.
     *
     * @return a new builder
     */
    public static BarSkeletonBuilder builder() {
        return new BarSkeletonBuilder();
    }

    /**
     * Handle an incoming request by executing the flow pipeline.
     *
     * @param flowContext the per-request flow context
     */
    public void handle(final FlowContext flowContext) {
        flowContext.setCommunicationAggregation(communicationAggregation);
        flowContext.setBarSkeleton(this);

        this.flowExecutor.execute(flowContext, this);
    }
}
