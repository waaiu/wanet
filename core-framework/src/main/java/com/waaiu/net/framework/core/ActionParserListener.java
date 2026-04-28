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
package com.waaiu.net.framework.core;

/**
 * Listener (hook) during action construction
 *
 * @author
 * @date 2024-04-30
 * @since 21.7
 */
public interface ActionParserListener {
    /**
     * subCmd action callback. Each action will be called once
     *
     * @param context Context during action construction
     */
    void onActionCommand(ActionParserContext context);

    /**
     * Executed after
     * {@link ActionParserListener#onActionCommand(ActionParserContext)}
     *
     * @param barSkeleton Business framework
     */
    default void onAfter(BarSkeleton barSkeleton) {
    }
}
