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
package com.waaiu.net.framework.core.doc;

import lombok.*;
import lombok.experimental.*;

/**
 * Documentation model for a single error code entry, carrying the enum constant
 * name,
 * numeric value, and human-readable description.
 *
 * @author
 * @date 2024-06-26
 */
@Getter
@ToString
@FieldDefaults(level = AccessLevel.PUBLIC)
public final class ErrorCodeDocument {
    /** Error Code - Variable name */
    String name;
    /** Error Code - Value */
    int value;
    /** Error Code - Description */
    String description;
}
