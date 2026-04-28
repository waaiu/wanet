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
 * Framework network communication aggregation interface.
 * <p>
 * Combines all low-level communication contracts: logic server communication,
 * external server communication, broadcast, logic collect, external collect,
 * and event bus messaging.
 *
 * @author
 * @date 2022-07-27
 */
public interface CommunicationAggregation extends
                LogicCommunication, ExternalCommunication, BroadcastCommunication
                // Enterprise
                , LogicCollectCommunication, ExternalCollectCommunication, EventBusMessageCommunication {
}
