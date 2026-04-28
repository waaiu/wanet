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
package com.waaiu.net.extension.protobuf;

import com.waaiu.net.common.kit.*;
import java.lang.reflect.*;
import java.util.*;
import lombok.*;
import lombok.experimental.*;

/**
 * Parsed Java field metadata used to render a proto field line.
 *
 * @author
 * @date 2022-01-24
 */
@FieldDefaults(level = AccessLevel.PUBLIC)
public class ProtoJavaField {
    boolean repeated;
    String fieldName;
    String comment;
    int order;
    Class<?> fieldTypeClass;
    String fieldProtoType;
    Field field;

    ProtoJava protoJavaParent;

    boolean isMap() {
        return Map.class.equals(fieldTypeClass);
    }

    boolean isList() {
        return List.class.equals(fieldTypeClass);
    }

    private Map<String, String> createParam() {
        Map<String, String> messageMap = new HashMap<>(8);

        messageMap.put("comment", this.comment);
        messageMap.put("repeated", "");
        messageMap.put("fieldProtoType", this.fieldProtoType);
        messageMap.put("order", String.valueOf(this.order));

        FieldNameGenerate fieldNameGenerate = new FieldNameGenerate();
        fieldNameGenerate.enumType = this.protoJavaParent.clazz.isEnum();
        fieldNameGenerate.fieldName = this.fieldName;
        messageMap.put("fieldName", ProtoGenerateSetting.fieldNameFunction.apply(fieldNameGenerate));

        if (this.repeated) {
            messageMap.put("repeated", "repeated ");
        }

        return messageMap;
    }

    public String toProtoFieldLine() {
        Map<String, String> messageMap = this.createParam();
        String templateFiled = getTemplateFiled(this.protoJavaParent.clazz.isEnum());
        return StrKit.format(templateFiled, messageMap);
    }

    private String getTemplateFiled(boolean fieldIsInEnum) {
        StringBuilder templateFiled = new StringBuilder();

        if (this.comment != null) {
            templateFiled.append("""
                      // {comment}
                    """);
        }

        if (fieldIsInEnum) {
            templateFiled.append("  {repeated}{fieldName} = {order};");
        } else {
            templateFiled.append("  {repeated}{fieldProtoType} {fieldName} = {order};");
        }

        return templateFiled.toString();
    }
}
