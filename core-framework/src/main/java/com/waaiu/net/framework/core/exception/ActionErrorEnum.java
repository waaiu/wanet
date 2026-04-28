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
package com.waaiu.net.framework.core.exception;

import com.waaiu.net.common.kit.*;
import lombok.*;
import lombok.experimental.*;

/**
 * Built-in error codes used by the framework's action processing pipeline.
 * <p>
 * Each constant carries a numeric {@link #code} and a human-readable
 * {@link #message}.
 * When the locale is Chinese the original message is returned; otherwise the
 * enum
 * constant
 *
 * @author
 * @date 2022-01-14
 */
@Getter
@FieldDefaults(level = AccessLevel.PRIVATE)
public enum ActionErrorEnum implements ErrorInformation {
    /** Generic system error. */
    systemOtherErrCode(-1000, ""),
    /** Parameter validation error. */
    validateErrCode(-1001, ""),
    /** Routing / command info error. */
    cmdInfoErrorCode(-1002, ""),
    /** Heartbeat idle timeout. */
    idleErrorCode(-1003, ""),
    /** User not logged in. */
    verifyIdentity(-1004, ""),
    /** Class does not exist. */
    classNotExist(-1005, "class "),
    /** Requested data does not exist. */
    dataNotExist(-1006, ""),
    /** Force user offline. */
    forcedOffline(-1007, ""),
    /** Bound logic server not found. */
    findBindingLogicServerNotExist(-1008, ""),
    /** Internal inter-server communication error. */
    internalCommunicationError(-1009, "internalCommunicationError"),
    /** Enterprise-only feature. */
    enterpriseFunction(-1010, "enterpriseFunction"),
    ;

    final int code;
    final String message;

    ActionErrorEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getMessage() {
        return LocaleKit.isChina() ? message : name();
    }
}
