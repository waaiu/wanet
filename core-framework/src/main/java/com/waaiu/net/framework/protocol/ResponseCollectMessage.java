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

import java.util.*;
import lombok.*;
import lombok.experimental.*;

/**
 * Default implementation of {@link ResponseCollect} that stores aggregated
 * responses from multiple logic servers.
 * <p>
 * Holds the list of individual {@link Response} objects along with a
 * top-level error code and message representing the overall collect outcome.
 *
 * @author
 * @date 2022-05-22
 */
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResponseCollectMessage implements ResponseCollect {
    /** Individual responses collected from logic servers. */
    List<Response> responseList;
    /** Overall error code; 0 indicates success. */
    int errorCode;
    /** Human-readable error message; {@code null} on success. */
    String errorMessage;
}
