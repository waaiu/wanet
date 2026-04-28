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
package com.waaiu.net.common.kit;

import java.util.function.*;
import lombok.experimental.*;

/**
 * Null-presence conditional execution utilities.
 *
 * @author
 * @date 2023-06-01
 */
@UtilityClass
public class PresentKit {

    /**
     * If a value is null, performs the given action with the value,
     * otherwise does nothing.
     *
     * @param value  value
     * @param action the action to be performed, if a value is null
     */
    public void ifNull(Object value, Runnable action) {
        if (value == null) {
            action.run();
        }
    }

    /**
     * If a value is present, performs the given action with the value,
     * otherwise does nothing.
     *
     * @param value  value
     * @param action the action to be performed, if a value is present
     * @since 21.8
     */
    public <T> void ifPresent(T value, Consumer<T> action) {
        if (value != null) {
            action.accept(value);
        }
    }
}
