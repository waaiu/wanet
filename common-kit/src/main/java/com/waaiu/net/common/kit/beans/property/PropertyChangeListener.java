/*
 * wanet
 * Copyright (C) 2021 - present   (
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
package com.waaiu.net.common.kit.beans.property;

/**
 * Listener interface for property value change events.
 *
 * @param <T> the type of the observed property value
 * @author
 * @date 2024-04-17
 */
@FunctionalInterface
public interface PropertyChangeListener<T> {
    /**
     * Invoke when the observed property value changes.
     *
     * @param observable the property whose value changed
     * @param oldValue   the previous value before the change
     * @param newValue   the new value after the change
     */
    void changed(PropertyValueObservable<? extends T> observable, T oldValue, T newValue);
}
