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

import com.waaiu.net.server.*;

/**
 * External-facing transport server (WebSocket/TCP/UDP/custom) bootstrap
 * contract.
 *
 * @author
 * @date 2023-02-18
 */
public interface ExternalServer {
    /**
     * Start the external transport and bind it to the shared net server runtime.
     *
     * @param netServer core net server runtime
     */
    void startup(NetServer netServer);
}
