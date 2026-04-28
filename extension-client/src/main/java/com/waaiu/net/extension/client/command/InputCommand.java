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
package com.waaiu.net.extension.client.command;

import com.waaiu.net.common.kit.*;
import com.waaiu.net.framework.core.*;
import com.waaiu.net.framework.core.kit.*;
import lombok.*;
import lombok.experimental.*;

/**
 * Simulated client input command configuration.
 * example:
 * 
 * <pre>{@code
 *         ofCommand(DemoCmd.here).setTitle("here").setRequestData(() -> {
 *             YourMsg msg = ...
 *             return msg;
 *         }).callback(result -> {
 *              HelloReq value = result.getValue(HelloReq.class);
 *              log.info("value : {}", value);
 *          });
 *
 *         ofCommand(DemoCmd.list).setTitle("list").callback(result -> {
 *             // get list data
 *             List<HelloReq> list = result.listValue(HelloReq.class);
 *             log.info("list : {}", list);
 *         });
 * }
 * </pre>
 *
 * @author
 * @date 2023-07-08
 */
@Getter
@Setter
@Accessors(chain = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class InputCommand {

    final String inputName;
    @Getter
    final CmdInfo cmdInfo;

    /** Display description for the simulated request command. */
    String title = "... ...";
    /** Prefix label displayed before the command title. */
    String cmdName = "";

    /** Request payload supplier. */
    RequestDataDelegate requestData;
    /** Response callback. */
    @Setter(AccessLevel.PRIVATE)
    CallbackDelegate callback;

    public InputCommand(CmdInfo cmdInfo) {
        this.inputName = CmdKit.toSimpleString(cmdInfo);
        this.cmdInfo = cmdInfo;
    }

    public InputCommand setRequestData(RequestDataDelegate requestData) {
        this.requestData = requestData;
        return this;
    }

    public InputCommand callback(CallbackDelegate callback) {
        this.callback = callback;
        return this;
    }

    @Override
    public String toString() {
        int width = 7;
        String inputNameFormat = "%-" + width + "s";

        if (StrKit.isEmpty(cmdName)) {
            return (inputNameFormat + ": %s").formatted(inputName, title);
        }

        return (inputNameFormat + ": [%s] - %s").formatted(inputName, cmdName, title);
    }
}
