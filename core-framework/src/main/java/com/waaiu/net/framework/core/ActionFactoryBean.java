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
 * Factory interface for creating or retrieving action controller instances.
 * Implementations may delegate to DI containers like Spring.
 *
 * @param <T> the type of the action controller instance
 * @author
 * @date 2021-12-20
 */
public interface ActionFactoryBean<T> {
    /**
     * Get or create the action controller instance for the given action command.
     *
     * @param actionCommand the action command describing the target controller
     * @return the action controller instance
     */
    T getBean(ActionCommand actionCommand);

    /**
     * Get or create an action controller instance by class. Returns null by
     * default.
     *
     * @param actionControllerClazz the action controller class
     * @return the action controller instance, or null if not supported
     */
    default T getBean(Class<?> actionControllerClazz) {
        return null;
    }
}
