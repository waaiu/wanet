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
package com.waaiu.net.framework.communication.eventbus;

/**
 * Strategy interface for invoking a subscriber method when an event is
 * dispatched.
 *
 * @author
 * @date 2023-12-24
 * @since 21
 */
public interface SubscriberInvoke {
    /**
     * Execute subscriber method
     *
     * @param eventBusMessage Event message
     * @see EventSubscribe
     */
    void invoke(EventBusMessage eventBusMessage);
}
