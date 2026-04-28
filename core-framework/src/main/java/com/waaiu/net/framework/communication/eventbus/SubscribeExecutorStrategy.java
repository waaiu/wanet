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
package com.waaiu.net.framework.communication.eventbus;

import com.waaiu.net.common.kit.concurrent.executor.*;

/**
 * Strategy interface for selecting the thread executor used to run a subscriber
 * method.
 * Implement this interface to provide custom executor selection logic when the
 * built-in
 * {@link Er} options are insufficient.
 *
 * @author
 * @date 2023-12-24
 * @see ExecutorSelector#customExecutor
 * @since 21
 */
public interface SubscribeExecutorStrategy {
    /**
     * Get the corresponding thread executor
     *
     * @param subscriber      Subscriber
     * @param eventBusMessage Event message
     * @param executorRegion  The thread executor management domain associated with
     *                        the business framework
     * @return Thread executor
     */
    ThreadExecutor select(Subscriber subscriber, EventBusMessage eventBusMessage, ExecutorRegion executorRegion);
}
