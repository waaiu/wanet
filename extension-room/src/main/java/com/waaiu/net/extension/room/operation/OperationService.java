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
package com.waaiu.net.extension.room.operation;

import com.waaiu.net.common.kit.*;

/**
 * Gameplay operation related service. Get user's gameplay operations, all
 * gameplay operations, and the gameplay operation factory.
 *
 * @author
 * @date 2024-05-12
 * @since 21.8
 */
public interface OperationService {
    /**
     * @return Gameplay operation factory
     */
    OperationFactory getOperationFactory();

    /**
     * Get OperationHandler (Gameplay Operation Business Class)
     *
     * @param operation Operation code
     * @return Business logic handler corresponding to the operation code
     */
    default OperationHandler getOperationHandler(int operation) {
        return this.getOperationFactory().getOperationHandler(operation);
    }

    /**
     * Get OperationHandler that the user can operate (Gameplay Operation Business
     * Class)
     *
     * @param operation Operation code
     * @return Gameplay Operation Business Class
     */
    default OperationHandler getUserOperationHandler(int operation) {
        return this.getOperationFactory().getUserOperationHandler(operation);
    }

    default OperationHandler getOperationHandler(OperationCode operationCode) {
        return this.getOperationHandler(operationCode.getOperationCode());
    }

    default OperationHandler getUserOperationHandler(OperationCode operationCode) {
        return this.getUserOperationHandler(operationCode.getOperationCode());
    }
}
