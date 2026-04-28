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
package com.waaiu.net.framework.core.flow.parser;

import com.waaiu.net.framework.core.*;
import com.waaiu.net.framework.core.codec.*;
import com.waaiu.net.framework.protocol.wrapper.*;
import java.util.*;

/**
 * Method parser for {@code boolean}/{@link Boolean} parameters and return
 * types.
 * <p>
 * Converts between Java {@code boolean}/{@link Boolean} values and the protocol
 * wrapper types
 * {@link B{@link BoolValueList}.
 *
 * @author
 * @date 2023-02-07
 */
final class BoolValueMethodParser implements MethodParser {
    /** {@inheritDoc} */
    @Override
    public Class<?> getActualClazz(ActualParameter parameterReturn) {
        return parameterReturn.isList() ? BoolValueList.class : BoolValue.class;
    }

    /** {@inheritDoc} */
    @Override
    public Object parseParam(byte[] data, ActionMethodParameter actionMethodParameter, DataCodec codec) {

        if (actionMethodParameter.isList()) {
            if (data == null) {
                return Collections.emptyList();
            }

            return codec.decode(data, BoolValueList.class).values;
        }

        if (data == null) {
            return false;
        }

        return codec.decode(data, BoolValue.class).value;
    }

    /** {@inheritDoc} */
    @Override
    @SuppressWarnings("unchecked")
    public Object parseDataList(Object data, DataCodec codec) {
        var valueList = new BoolValueList();
        valueList.values = (List<Boolean>) data;
        return valueList;
    }

    /** {@inheritDoc} */
    @Override
    public Object parseData(Object data) {
        return BoolValue.of((boolean) data);
    }

    private BoolValueMethodParser() {
    }

    /**
     * Return the singleton instance.
     *
     * @return the singleton {@code BoolValueMethodParser}
     */
    public static BoolValueMethodParser me() {
        return Holder.ME;
    }

    private static class Holder {
        static final BoolValueMethodParser ME = new BoolValueMethodParser();
    }
}
