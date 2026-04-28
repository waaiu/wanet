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
import lombok.*;

/**
 * Protocol wrapper for a single String value.
 * <p>
 * Wraps a {@link String} for protobuf serialization, allowing it to be used
 * as a parameter or return type in {@code @ActionMethod} handlers.
 *
 * @author
 * @date 2023-02-05
 */
@ToString
@ProtobufClass
public final class StringValue {
    /** the wrapped string value */
    @Protobuf(fieldType = FieldType.STRING, order = 1)
    public String value;

    /**
     * Create a StringValue wrapping the given string.
     *
     * @param value the string value to wrap
     * @return a new StringValue instance
     */
    public static StringValue of(String value) {
        var theValue = new StringValue();
        theValue.value = value;
        return theValue;
    }
}
