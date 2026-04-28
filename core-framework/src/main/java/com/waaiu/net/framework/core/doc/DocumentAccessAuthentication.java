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

/**
 * Document access permission generation
 *
 * @author
 * @date 2024-09-02
 * @since 21.16
 */
public interface DocumentAccessAuthentication {
    /**
     * Routes documentation to be rejected from generation. When the return value is
     * true, the documentation corresponding to this route will not be generated.
     *
     * @param cmdMerge Route
     * @return true means the documentation corresponding to this route will not be
     *         generated
     */
    boolean reject(int cmdMerge);
}
