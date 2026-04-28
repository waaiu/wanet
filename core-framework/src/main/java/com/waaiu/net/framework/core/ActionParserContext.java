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
package com.waaiu.net.framework.core;

import lombok.*;
import lombok.experimental.*;

/**
 * Mutable context passed to {@link ActionParserListener} callbacks during
 * action parsing.
 * <p>
 * Carries the current {@link BarSkeleton} and the {@link ActionCommand} being
 * processed
 * so that inspect or enrich them.
 *
 * @author
 * @date 2024-04-30
 * @since 21.7
 */
@FieldDefaults(level = AccessLevel.PUBLIC)
public final class ActionParserContext {
    /** The business framework skeleton that owns the action being parsed. */
    BarSkeleton barSkeleton;
    /** The action command currently being parsed. */
    ActionCommand actionCommand;
}
