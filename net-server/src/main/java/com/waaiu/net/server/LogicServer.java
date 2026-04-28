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

import com.waaiu.net.framework.core.*;
import com.waaiu.net.framework.protocol.*;

/**
 * <a href=
 * "https://waaiu.github.io/wanet/docs/manual/logic_intro">LogicServer</a>
 *
 * @author
 * @date 2025-09-04
 * @since 25.1
 */
public interface LogicServer {
    /**
     * Configures the bar-skeleton builder for this logic server.
     *
     * @param builder skeleton builder
     */
    void settingBarSkeletonBuilder(BarSkeletonBuilder builder);

    /**
     * Configures the server metadata builder used for registration.
     *
     * @param builder server builder
     */
    void settingServerBuilder(ServerBuilder builder);

    /**
     * Callback invoked after startup finishes successfully.
     *
     * @param barSkeleton started skeleton
     */
    default void startupSuccess(BarSkeleton barSkeleton) {
    }
}
