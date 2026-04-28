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
package com.waaiu.net.framework;

/**
 * Provides the current version string of the wanet framework.
 * <p>
 * The version is extracted at class-load time from an XML-style tag embedded in
 * the source,
 * making it easy to keep in sync with the Maven POM version.
 *
 * @author
 * @date 2022-12-23
 */
public final class IonetVersion {
    public static final String VERSION;

    static {
        VERSION = "<version>25.4</version>"
                .replace("<version>", "")
                .replace("</version>", "");
    }
}
