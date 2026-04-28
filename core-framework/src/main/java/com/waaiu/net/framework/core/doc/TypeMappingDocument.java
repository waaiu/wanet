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
package com.waaiu.net.framework.core.doc;

import com.waaiu.net.framework.protocol.wrapper.*;
import java.util.*;

/**
 * Strategy interface for mapping Java/protobuf types to their client-side type
 * names
 * used during document and SDK code generation.
 *
 * @author
 * @date 2024-06-26
 */
public interface TypeMappingDocument {
    List<Class<?>> intClassList = List.of(int.class, Integer.class, IntValue.class);
    List<Class<?>> longClassList = List.of(long.class, Long.class, LongValue.class);
    List<Class<?>> boolClassList = List.of(boolean.class, Boolean.class, BoolValue.class);
    List<Class<?>> stringClassList = List.of(String.class, StringValue.class);

    /**
     * Return the underlying type mapping table.
     *
     * @return map from Java class to its type mapping record
     */
    Map<Class<?>, TypeMappingRecord> getMap();

    /**
     * Look up the type mapping record for the given class.
     *
     * @param protoTypeClazz the class to look up
     * @return the corresponding type mapping record
     */
    TypeMappingRecord getTypeMappingRecord(Class<?> protoTypeClazz);

    /**
     * Register a type mapping record for all classes in the given list.
     *
     * @param record    the type mapping record
     * @param clazzList the classes to map
     */
    default void mapping(TypeMappingRecord record, List<Class<?>> clazzList) {
        for (Class<?> clazz : clazzList) {
            this.getMap().put(clazz, record);
        }
    }
}
