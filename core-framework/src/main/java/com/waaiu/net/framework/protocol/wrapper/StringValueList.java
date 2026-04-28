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
package com.waaiu.net.framework.protocol.wrapper;

import com.baidu.bjf.remoting.protobuf.*;
import com.baidu.bjf.remoting.protobuf.annotation.*;
import java.util.*;
import lombok.*;

/**
 * Protocol wrapper for a list of String values.
 * <p>
 * Wraps a {@link List} of {@link String} for protobuf serialization, allowing
 * it to be used
 * as a parameter or return type in {@code @ActionMethod} handlers.
 *
 * @author
 * @date 2023-02-05
 */
@ToString
@ProtobufClass
public final class StringValueList {
    /** the wrapped list of string values */
    @Protobuf(fieldType = FieldType.STRING, order = 1)
    public List<String> values;

    /**
     * Create a StringValueList wrapping the given list of strings.
     *
     * @param values the list of string values to wrap
     * @return a new StringValueList instance
     */
    public static StringValueList of(List<String> values) {
        var theValue = new StringValueList();
        theValue.values = values;
        return theValue;
    }
}
