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

import com.waaiu.net.framework.core.kit.*;
import lombok.*;
import lombok.experimental.*;

/**
 * Document model for a route member constant generated from an action method,
 * carrying the command pair, a generated member name, and the Javadoc comment.
 *
 * @author
 * @date 2024-06-26
 * @since 21.11
 */
@Getter
@FieldDefaults(level = AccessLevel.PUBLIC)
public final class ActionMemberCmdDocument {
    final int cmd;
    final int subCmd;
    final int cmdMerge;
    String comment;
    String memberName;

    ActionMemberCmdDocument(int cmd, int subCmd, String memberName, String comment) {
        this.cmd = cmd;
        this.subCmd = subCmd;
        this.cmdMerge = CmdKit.merge(cmd, subCmd);
        this.comment = comment;
        this.memberName = memberName;
    }
}
