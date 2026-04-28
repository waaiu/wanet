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
package com.waaiu.net.framework.protocol;

/**
 * Constants for command code types used in the external protocol layer.
 * <p>
 * Distinguishes heartbeat (idle) frames from business request frames.
 *
 * @author
 * @date 2023-02-21
 */
public interface CmdCodeConst {
    /** Request command type: 0 Heartbeat */
    int IDLE = 0;
    /** Request command type: 1 Business */
    int BIZ = 1;
}
