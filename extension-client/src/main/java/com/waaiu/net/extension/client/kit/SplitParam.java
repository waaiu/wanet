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
package com.waaiu.net.extension.client.kit;

import com.waaiu.net.common.kit.*;
import lombok.*;
import lombok.experimental.*;

/**
 * @author
 * @date 2023-08-06
 */
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SplitParam {
    String[] split;

    public SplitParam(String text) {
        this(text, "-");
    }

    public SplitParam(String text, String regex) {
        if (StrKit.isEmpty(text)) {
            text = "";
        }

        this.split = text.split(regex);
    }

    public int getInt(int index, int defaultValue) {
        if (index >= split.length) {
            return defaultValue;
        }

        return SafeKit.getInt(split[index], defaultValue);
    }

    public String getString(int index, String defaultValue) {
        if (index >= split.length) {
            return defaultValue;
        }

        return split[index];
    }

    public String getString(int index) {
        if (index >= split.length) {
            return null;
        }

        return split[index];
    }

}
