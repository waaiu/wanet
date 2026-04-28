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
package com.waaiu.net.external.core;

import com.waaiu.net.common.kit.attr.*;
import com.waaiu.net.external.core.hook.internal.*;
import com.waaiu.net.external.core.session.*;
import com.waaiu.net.framework.protocol.*;
import com.waaiu.net.server.*;
import lombok.*;

/**
 * Immutable runtime settings for one external server instance.
 *
 * @param port               port exposed to real players
 * @param server             server metadata descriptor
 * @param userSessions       user session manager
 * @param idleProcessSetting heartbeat/idle processing settings
 * @param options            extensible attribute options
 * @author
 * @date 2025-10-15
 * @since 25.1
 */
@Builder(setterPrefix = "set", builderClassName = "RecordBuilder")
public record ExternalSetting(
        int port, Server server, UserSessions<?, ?> userSessions, IdleProcessSetting idleProcessSetting,
        AttrOptions options) implements AttrOptionDynamic {

    /** Attribute key storing the resolved {@link NetServerSetting}. */
    public static final AttrOption<NetServerSetting> netServerSetting = AttrOption.valueOf("NetServerSetting");

    /**
     * Get the net server setting attached to this external setting.
     *
     * @return net server setting, or {@code null} if not attached
     */
    public NetServerSetting netServerSetting() {
        return this.option(netServerSetting);
    }

    /**
     * Get the convenient communication facade from the attached net server setting.
     *
     * @return convenient communication facade
     */
    public ConvenientCommunication convenientCommunication() {
        return netServerSetting().convenientCommunication();
    }

    @Override
    public AttrOptions getOptions() {
        return options;
    }

    /**
     * Inject this setting into supported aware components.
     *
     * @param o target component
     */
    public void inject(Object o) {
        if (o == null) {
            return;
        }

        if (o instanceof ExternalSettingAware aware) {
            aware.setExternalSetting(this);
        }

        if (o instanceof NetServerSettingAware aware) {
            aware.setNetServerSetting(netServerSetting());
        }
    }
}
