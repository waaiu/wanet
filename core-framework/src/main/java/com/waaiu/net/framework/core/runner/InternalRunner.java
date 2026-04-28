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
package com.waaiu.net.framework.core.runner;

import com.waaiu.net.framework.communication.eventbus.*;

/**
 * Framework-internal runner that registers the EventBus validation runner (if
 * configured)
 * into the {@link Runners#runnerBeforeList} so it executes before user-defined
 * runners.
 *
 * @author
 * @date 2023-04-23
 */
final class InternalRunner {

    InternalRunner(Runners runners) {
        Runner checkedEventBusRunner = EventBusSettingKit.setting.getCheckedEventBusRunner();
        if (checkedEventBusRunner != null) {
            runners.runnerBeforeList.add(checkedEventBusRunner);
        }
    }
}
