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
package com.waaiu.net.server.connection;

import com.waaiu.net.framework.protocol.*;
import io.aeron.*;
import lombok.*;

/**
 * Manage all client connections
 *
 * @author
 * @date 2025-08-26
 * @since 25.1
 */
@Getter
public final class ConnectionItem {
    final Server server;
    final Publication publication;
    final String pubName;
    final int serverId;
    final int netId;

    public ConnectionItem(Server server, Publication publication) {
        this.server = server;
        this.publication = publication;
        this.netId = server.netId();
        this.pubName = server.pubName();
        this.serverId = server.id();
    }
}
