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

/**
 * Executes the task {@link Runnable}. The task must be placed in
 * {@link OperationContext#setCommand(Object)}.
 *
 * @author
 * @date 2024-12-09
 * @since 21.23
 */
public final class SimpleOperationHandler implements OperationHandler {
    @Override
    public void process(PlayerOperationContext context) {
        if (context.getCommand() instanceof Runnable runnable) {
            runnable.run();
        }
    }

    private SimpleOperationHandler() {
    }

    public static SimpleOperationHandler me() {
        return Holder.ME;
    }

    private static class Holder {
        static final SimpleOperationHandler ME = new SimpleOperationHandler();
    }
}
