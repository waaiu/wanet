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
package com.waaiu.net.framework.communication.eventbus;

import lombok.experimental.*;

/**
 * Utility class providing static access to the global {@link EventBusSetting}
 * and {@link EventBusRegion}.
 *
 * @author
 * @date 2025-10-10
 * @since 25.1
 */
@UtilityClass
public final class EventBusSettingKit {
    /** The global EventBus setting instance. */
    public EventBusSetting setting = new FreeEventBusSetting();

    /**
     * Get the global EventBus region from the current setting.
     *
     * @return the EventBus region
     */
    public EventBusRegion getEventBusRegion() {
        return setting.getEventBusRegion();
    }
}
