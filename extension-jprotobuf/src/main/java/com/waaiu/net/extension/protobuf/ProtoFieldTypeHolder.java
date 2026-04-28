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
package com.waaiu.net.extension.protobuf;

import java.util.*;
import lombok.experimental.*;

/**
 * Type mapping table from Java field types to proto scalar types.
 *
 * @author
 * @date 2022-01-24
 */
@UtilityClass
public class ProtoFieldTypeHolder {

    final Map<Class<?>, String> filedTypeMap = new HashMap<>();

    public String getProtoType(Class<?> filedTypeClass) {
        return filedTypeMap.get(filedTypeClass);
    }

    static {
        filedTypeMap.put(Double.class, "double");
        filedTypeMap.put(double.class, "double");

        filedTypeMap.put(Float.class, "float");
        filedTypeMap.put(float.class, "float");

        filedTypeMap.put(Long.class, "int64");
        filedTypeMap.put(long.class, "int64");

        filedTypeMap.put(Integer.class, "int32");
        filedTypeMap.put(int.class, "int32");

        filedTypeMap.put(Boolean.class, "bool");
        filedTypeMap.put(boolean.class, "bool");

        filedTypeMap.put(String.class, "string");

        filedTypeMap.put(byte[].class, "bytes");

    }
}
