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
package com.waaiu.net.common.kit;

import java.util.concurrent.atomic.*;
import lombok.experimental.*;

/**
 * Internal helper holding the global atomic counter for {@link OperationCode}
 * assignment.
 */
@UtilityClass
class OperationCodeKit {
    /** Atomic counter that provides unique, auto-incrementing operation codes. */
    final AtomicInteger codeAtomic = new AtomicInteger(1);
}
