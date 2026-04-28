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
package com.waaiu.net.extension.client.user;

import com.waaiu.net.common.kit.attr.*;
import com.waaiu.net.extension.client.*;
import java.util.*;
import lombok.*;
import lombok.experimental.*;

/**
 * Default client-side user model.
 * 
 * <pre>
 *     Developers can extend business data through dynamic attributes,
 *     such as currency, battle power, HP, and more.
 *
 *     {@link ClientUser} can also be extended via inheritance.
 * </pre>
 *
 * @author
 * @date 2023-07-09
 */
@Setter
@Getter
@FieldDefaults(level = AccessLevel.PROTECTED)
public class DefaultClientUser implements ClientUser {
    final AttrOptions options = new AttrOptions();
    /** Communication channel used for reading and writing. */
    final ClientUserChannel clientUserChannel = new ClientUserChannel(this);
    final ClientUserInputCommands clientUserInputCommands = new ClientUserInputCommands(clientUserChannel);
    List<InputCommandRegion> inputCommandRegions;

    /** True after login succeeds. */
    boolean loginSuccess;

    long userId;
    /** Nickname. */
    String nickname;
    String jwt;

    boolean active = true;

    @Override
    public void callbackInputCommandRegion() {
        if (this.inputCommandRegions == null) {
            return;
        }

        this.inputCommandRegions.forEach(InputCommandRegion::loginSuccessCallback);
    }
}
