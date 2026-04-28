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
package com.waaiu.net.framework.protocol;

import com.waaiu.net.framework.core.exception.*;

/**
 * Result of a user id binding (setting) operation on the external server.
 * <p>
 * An error code of 0 indicates the user id was successfully bound to the
 * session.
 * Pre-defined constants {@link #SUCCESS} and {@link #ERROR} cover the most
 * common outcomes.
 *
 * @param errorCode    error code; 0 indicates success
 * @param errorMessage human-readable error description; {@code null} on success
 * @author
 * @date 2024-10-18
 * @since 21.19
 */
public record SettingUserIdResult(int errorCode, String errorMessage) {

    /** Successful result singleton. */
    public static final SettingUserIdResult SUCCESS = new SettingUserIdResult(0, null);
    /** Generic internal communication error result. */
    public static final SettingUserIdResult ERROR = ofError(ActionErrorEnum.internalCommunicationError);

    /**
     * Check whether the operation completed successfully.
     *
     * @return {@code true} if the error code is 0
     */
    public boolean success() {
        return errorCode == 0;
    }

    /**
     * Create an error result with a validation error code and the given message.
     *
     * @param errorMessage the error description
     * @return a new error result
     */
    public static SettingUserIdResult ofError(String errorMessage) {
        return new SettingUserIdResult(ActionErrorEnum.validateErrCode.getCode(), errorMessage);
    }

    /**
     * Create an error result from the given {@link ErrorInformation}.
     *
     * @param errorInformation the error information source
     * @return a new error result
     */
    public static SettingUserIdResult ofError(ErrorInformation errorInformation) {
        return new SettingUserIdResult(errorInformation.getCode(), errorInformation.getMessage());
    }
}
