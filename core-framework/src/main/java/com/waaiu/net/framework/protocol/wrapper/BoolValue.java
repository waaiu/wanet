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
import lombok.*;

/**
 * Protocol wrapper for a single boolean value.
 * <p>
 * Wraps a primitive {@code boolean} for protobuf serialization, allowing it to
 * be used
 * as a parameter or return type in {@code @ActionMethod} handlers. Instances
 * for
 * {@code true} and {@code false} are cached and reused via the factory methods.
 *
 * @author
 * @date 2023-02-07
 */
@ToString
@ProtobufClass
public final class BoolValue {
    @Protobuf(fieldType = FieldType.BOOL, order = 1)
    public boolean value;

    @Ignore
    private static final BoolValue trueValue = create(true);
    @Ignore
    private static final BoolValue falseValue = create(false);

    /**
     * Return a cached BoolValue for the given boolean.
     *
     * @param value the boolean value
     * @return the cached BoolValue instance
     */
    public static BoolValue of(boolean value) {
        return value ? trueValue : falseValue;
    }

    /**
     * Return the cached BoolValue for {@code true}.
     *
     * @return the true BoolValue instance
     */
    public static BoolValue ofTrue() {
        return trueValue;
    }

    /**
     * Return the cached BoolValue for {@code false}.
     *
     * @return the false BoolValue instance
     */
    public static BoolValue ofFalse() {
        return falseValue;
    }

    private static BoolValue create(boolean value) {
        var theValue = new BoolValue();
        theValue.value = value;
        return theValue;
    }
}
