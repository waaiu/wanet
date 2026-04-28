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
package com.waaiu.net.common.kit.exception;

/**
 * Common null pointer exception used throughout the wanet framework.
 *
 * @author
 * @date 2024-08-16
 * @since 21.15
 */
public class CommonNullPointerException extends NullPointerException {
    /**
     * Create a new instance with no detail message.
     */
    public CommonNullPointerException() {
    }

    /**
     * Create a new instance with the given detail message.
     *
     * @param s the detail message
     */
    public CommonNullPointerException(String s) {
        super(s);
    }
}
