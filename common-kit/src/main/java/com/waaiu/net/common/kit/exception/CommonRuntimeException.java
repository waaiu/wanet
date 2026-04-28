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
 * Common runtime exception used throughout the wanet framework.
 *
 * @author
 * @date 2024-08-02
 * @since 21.14
 */
public class CommonRuntimeException extends RuntimeException {
    /**
     * Create a new instance with the given detail message.
     *
     * @param message the detail message
     */
    public CommonRuntimeException(String message) {
        super(message);
    }

    /**
     * Create a new instance with the given detail message and cause.
     *
     * @param message the detail message
     * @param cause   the cause
     */
    public CommonRuntimeException(String message, Throwable cause) {
        super(message, cause);
    }
}
