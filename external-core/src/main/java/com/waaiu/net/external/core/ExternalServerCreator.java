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
package com.waaiu.net.external.core;

/**
 * Factory for creating transport-specific external server instances.
 *
 * @author
 * @date 2025-10-16
 * @since 25.1
 */
public interface ExternalServerCreator {
    /**
     * Create an external server using prepared bootstrap parameters.
     *
     * @param parameter bootstrap parameter bundle
     * @return external server instance
     */
    ExternalServer of(ExternalServerCreatorParameter parameter);
}
