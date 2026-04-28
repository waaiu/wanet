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
package com.waaiu.net.framework.protocol.wrapper;

import com.baidu.bjf.remoting.protobuf.*;
import com.baidu.bjf.remoting.protobuf.annotation.*;
import java.util.*;
import lombok.*;

/**
 * Protocol wrapper for a list of int values.
 * <p>
 * Wraps a {@link List} of {@link Integer} for protobuf serialization, allowing
 * it to be used
 * as a parameter or return type in {@code @ActionMethod} handlers.
 *
 * @author
 * @date 2023-02-10
 */
@ToString
@ProtobufClass
public final class IntValueList {
    /** the wrapped list of int values */
    @Protobuf(fieldType = FieldType.SINT32, order = 1)
    public List<Integer> values;

    /**
     * Create an IntValueList wrapping the given list of integers.
     *
     * @param values the list of integer values to wrap
     * @return a new IntValueList instance
     */
    public static IntValueList of(List<Integer> values) {
        var theValue = new IntValueList();
        theValue.values = values;
        return theValue;
    }
}
