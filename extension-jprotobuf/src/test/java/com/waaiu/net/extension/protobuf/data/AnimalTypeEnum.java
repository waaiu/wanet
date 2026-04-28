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
package com.waaiu.net.extension.protobuf.data;

import com.baidu.bjf.remoting.protobuf.EnumReadable;
import com.baidu.bjf.remoting.protobuf.annotation.ProtobufClass;
import com.waaiu.net.extension.protobuf.ProtoFileMerge;
import lombok.ToString;

/**
 * TestAnimalTypeEnum
 *
 * @author
 * @date 2024-12-16
 * @since 21.23
 */
@ToString
@ProtobufClass
@ProtoFileMerge(fileName = TempProtoFile.fileName, filePackage = TempProtoFile.filePackage)
public enum AnimalTypeEnum implements EnumReadable {
    /** the cat */
    cat(0),
    /** the tiger */
    tiger(10),
    ;

    final int value;

    AnimalTypeEnum(int value) {
        this.value = value;
    }

    @Override
    public int value() {
        return this.value;
    }
}
