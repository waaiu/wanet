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

import com.waaiu.net.common.kit.*;
import com.waaiu.net.common.kit.source.*;
import java.util.*;
import java.util.stream.*;

/**
 * Parsed Java class metadata used to render a proto message/enum block.
 *
 * @author
 * @date 2022-01-24
 */
public class ProtoJava {
    Class<?> clazz;
    String className;

    String comment;

    String fileName;
    String filePackage;

    SourceClass sourceClass;

    List<ProtoJavaField> protoJavaFieldList = new ArrayList<>(16);

    public void addProtoJavaFiled(ProtoJavaField protoJavaField) {
        this.protoJavaFieldList.add(protoJavaField);
    }

    public boolean inThisFile(ProtoJava protoJava) {
        return Objects.equals(this.fileName, protoJava.fileName)
                && Objects.equals(this.filePackage, protoJava.filePackage);
    }

    public ProtoJavaRegionKey getProtoJavaRegionKey() {
        return new ProtoJavaRegionKey(this.fileName, this.filePackage);

    }

    public String toProtoMessage() {

        String fieldsString = protoJavaFieldList
                .stream()
                .map(ProtoJavaField::toProtoFieldLine)
                .collect(Collectors.joining("\n"));

        Map<String, String> messageMap = new HashMap<>();
        messageMap.put("className", this.className);
        messageMap.put("fieldsString", fieldsString);
        messageMap.put("classComment", this.comment);
        messageMap.put("classOrEnum", clazz.isEnum() ? "enum" : "message");

        String template = """
                // {classComment}
                {classOrEnum} {className} {
                {fieldsString}
                }

                """;

        return StrKit.format(template, messageMap);
    }
}
