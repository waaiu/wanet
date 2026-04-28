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
package com.waaiu.net.external.core.hook.cache;

import com.waaiu.net.framework.annotations.*;
import com.waaiu.net.framework.protocol.*;

/**
 * Interfaces for external server cache data query and addition.
 *
 * @author
 * @date 2023-07-02
 */
@Enterprise
public interface ExternalCmdCache extends ExternalCmdCacheSetting {
    /**
     * Gets data from the cache.
     *
     * @param message message
     * @return null if there is no data in the cache
     */
    CommunicationMessage getCache(CommunicationMessage message);

    /**
     * Adds the response data to the cache.
     *
     * @param responseMessage responseMessage
     */
    void addCacheData(CommunicationMessage responseMessage);
}
