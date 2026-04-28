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
package com.waaiu.net.center;

import com.waaiu.net.center.creator.*;
import com.waaiu.net.common.*;
import io.aeron.*;
import java.util.*;
import lombok.*;
import lombok.experimental.*;

/**
 * Builder for assembling center-server runtime dependencies.
 *
 * @author
 * @date 2025-10-23
 * @since 25.1
 */
@Setter
@Accessors(chain = true)
public final class CenterServerBuilder {
    CenterServerCreator centerServerCreator = DefaultCenterServer::new;
    CenterConnectionManagerCreator centerConnectionManagerCreator = DefaultCenterConnectionManager::new;

    Aeron aeron;
    Publisher publisher;

    /**
     * Builds a center server with the configured Aeron instance and publisher.
     *
     * @return center server instance
     */
    public CenterServer builder() {
        Objects.requireNonNull(aeron);
        Objects.requireNonNull(publisher);

        var connectionManager = centerConnectionManagerCreator
                .of(new CenterConnectionManagerCreatorParameter(aeron, publisher));

        var setting = CenterServerSetting.builder()
                .setAeron(aeron)
                .setPublisher(publisher)
                .setConnectionManager(connectionManager)
                .build();

        return centerServerCreator.of(setting);
    }
}
