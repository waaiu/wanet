/*
 * wanet
 * Copyright (C) 2021 - present   (
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
package com.waaiu.net.common.kit.concurrent.timer.delay;

import com.waaiu.net.common.kit.concurrent.*;
import java.util.*;

/**
 * Lightweight controllable delayed task region interface, responsible for
 * operations such as creation, retrieval, cancellation, and counting of
 * lightweile delayed tasks.
 *
 * @author
 * @date 2024-09-01
 * @since 21.16
 */
public interface DelayTaskRegion {

    /**
     * Get an Optional controllable delayed task by taskId
     *
     * @param taskId taskId
     * @return DelayTask Optional
     */
    Optional<DelayTask> optional(String taskId);

    /**
     * Cancel the execution of the controllable delayed task based on taskId.
     *
     * @param taskId taskId
     */
    void cancel(String taskId);

    /**
     * Count the number of current delayed tasks
     *
     * @return the number of current delayed tasks
     */
    int count();

    /**
     * Create a controllable delayed task and set the task listener callback.
     * 
     * <pre>{@code
     * DelayTask delayTask = of(taskListener);
     * // Start the delayed task
     * delayTask.task();
     * }
     * </pre>
     *
     * @param taskListener task listener callback
     * @return the controllable delayed task
     */
    DelayTask of(TaskListener taskListener);

    /**
     * Create a controllable delayed task, and set the taskId and task listener
     * callback
     * 
     * <pre>{@code
     * DelayTask delayTask = of(taskId, taskListener);
     * // Start the delayed task
     * delayTask.task();
     * }
     * </pre>
     *
     * @param taskId       taskId
     * @param taskListener task listener callback
     * @return the controllable delayed task
     */
    DelayTask of(String taskId, TaskListener taskListener);
}
