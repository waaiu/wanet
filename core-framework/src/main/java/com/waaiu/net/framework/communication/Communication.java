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
package com.waaiu.net.framework.communication;

/**
 * Aggregate communication interface combining broadcast, logic call, external,
 * and send capabilities.
 * <p>
 * Composes all decorator interfaces: external communication, logic call/send,
 * broadcast (user, user-list, multicast), and enterprise logic call collect.
 *
 * @author
 * @date 2025-09-28
 * @since 25.1
 */
public interface Communication extends
                // External
                ExternalCommunicationDecorator
                // Logic
                , LogicCallCommunicationDecorator, LogicSendCommunicationDecorator
                // Broadcast
                , BroadcastUserCommunicationDecorator, BroadcastUserListCommunicationDecorator,
                BroadcastMulticastCommunicationDecorator
                // Enterprise
                , LogicCallCollectCommunicationDecorator {

}
