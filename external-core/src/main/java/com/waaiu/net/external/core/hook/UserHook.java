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
package com.waaiu.net.external.core.hook;

import com.waaiu.net.external.core.session.*;

/**
 * User session lifecycle callbacks for online/offline events.
 *
 * @author
 * @date 2023-02-20
 */
public interface UserHook {
    /**
     * User is online.
     *
     * @param userSession user session entering the external server
     */
    void into(UserSession userSession);

    /**
     * User is offline.
     *
     * @param userSession user session leaving the external server
     */
    void quit(UserSession userSession);
}
