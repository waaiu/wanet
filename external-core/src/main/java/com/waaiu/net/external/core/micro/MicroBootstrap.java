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
package com.waaiu.net.external.core.micro;

/**
 * Bootstrap contract for starting the external transport server.
 *
 * @author
 * @date 2023-05-28
 */
public interface MicroBootstrap {
    /**
     * Start the server that accepts real-user connections.
     *
     * @param port               bind port
     * @param microBootstrapFlow transport bootstrap/pipeline orchestration flow
     */
    void startup(int port, MicroBootstrapFlow<?> microBootstrapFlow);
}
