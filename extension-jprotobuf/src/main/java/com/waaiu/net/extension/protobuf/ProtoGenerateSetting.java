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

import java.util.function.*;
import lombok.experimental.*;

/**
 * Global settings for proto file generation behavior and field-name formatting.
 *
 * @author
 * @date 2024-10-31
 * @since 21.20
 */
@UtilityClass
public final class ProtoGenerateSetting {

    public boolean enableLog;

    public Function<FieldNameGenerate, String> fieldNameFunction = fieldNameGenerate -> {
        if (fieldNameGenerate.isEnum()) {
            return fieldNameGenerate.fieldName;
        }

        // default UnderScoreCase
        StringBuilder result = new StringBuilder();
        String fieldName = fieldNameGenerate.fieldName;

        for (int i = 0; i < fieldName.length(); i++) {
            char c = fieldName.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i > 0) {
                    result.append('_');
                }

                result.append(Character.toLowerCase(c));
            } else {
                result.append(c);
            }
        }

        return result.toString();
    };
}
