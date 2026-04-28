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
package com.waaiu.net.extension.protobuf.data;

import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.waaiu.net.extension.protobuf.ProtoFileMerge;
import lombok.AccessLevel;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

/**
 * teacher
 *
 * @author
 * @date 2022-01-07
 */
@ToString
@ProtobufClass
@FieldDefaults(level = AccessLevel.PUBLIC)
@ProtoFileMerge(fileName = TempProtoFile.fileName, filePackage = TempProtoFile.filePackage)
public class ProtoTeacher {
    /**  */
    String name;
    int id;
    long age;
    /**  */
    String email;

    Double doubleF;
    Float floatF;
    byte[] bytesF;

    Boolean boolF;
}
