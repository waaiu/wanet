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
package com.waaiu.net.common.kit.source;

/**
 * Javadoc tag extracted from source, such as {@code @param} or {@code @return}.
 *
 * @param name  tag name (e.g. "param", "return")
 * @param value tag value text
 * @author
 * @date 2025-02-27
 */
public record SourceDocTag(String name, String value) {
}
