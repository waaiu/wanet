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
package com.waaiu.net.external.core.session;

import com.waaiu.net.common.kit.attr.*;
import com.waaiu.net.external.core.config.*;

/**
 * Dynamic attribute names for UserSession
 *
 * @author
 * @date 2023-02-21
 */
public interface UserSessionOption {
    /** false : not verified identity */
    AttrOption<Boolean> verifyIdentity = AttrOption.valueOf("verifyIdentity");
    /** Player's real ip */
    AttrOption<String> realIp = AttrOption.valueOf("realIp", "");
}
