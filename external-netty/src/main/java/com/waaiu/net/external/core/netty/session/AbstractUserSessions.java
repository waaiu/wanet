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
package com.waaiu.net.external.core.netty.session;

import com.waaiu.net.common.kit.attr.*;
import com.waaiu.net.external.core.config.*;
import com.waaiu.net.external.core.hook.*;
import com.waaiu.net.external.core.session.*;
import io.netty.channel.*;
import java.util.*;
import lombok.*;
import lombok.experimental.*;

/**
 * Base implementation of Netty-backed user session registries.
 *
 * @author
 * @date 2023-05-28
 */
@FieldDefaults(level = AccessLevel.PROTECTED)
abstract class AbstractUserSessions<ChannelHandlerContext, Session extends UserSession>
        implements UserSessions<ChannelHandlerContext, Session> {

    @Getter
    final AttrOptions options = new AttrOptions();
    /** key : userId */
    final Long2ObjectConcurrentHashMap<Session> userIdMap = new Long2ObjectConcurrentHashMap<>();
    /** key : userChannelId */
    final Long2ObjectConcurrentHashMap<Session> userChannelIdMap = new Long2ObjectConcurrentHashMap<>();

    ExternalJoinEnum joinEnum;
    UserHook userHook;

    @Override
    public void setJoinEnum(ExternalJoinEnum joinEnum) {
        this.joinEnum = joinEnum;
    }

    @Override
    public void setUserHook(UserHook userHook) {
        this.userHook = userHook;
    }

    @Override
    public UserHook getUserHook() {
        return this.userHook;
    }

    @Override
    public boolean existUserSession(long userId) {
        return this.userIdMap.containsKey(userId);
    }

    @Override
    public Session getUserSession(long userId) {
        return this.userIdMap.get(userId);
    }

    @Override
    public Session getUserSessionByUserChannelId(long userChannelId) {
        return this.userChannelIdMap.get(userChannelId);
    }

    @Override
    public void removeUserSession(long userId, Object msg) {
        this.ifPresent(userId, userSession -> {
            ChannelFuture channelFuture = userSession.writeAndFlush(msg);
            channelFuture.addListener((ChannelFutureListener) future -> {
                // Remove the session only after the forced-offline message has been flushed.
                this.removeUserSession(userSession);
            });
        });
    }

    @Override
    public List<Session> listUserSession() {
        return this.userChannelIdMap.values();
    }

    /**
     * Online notification.
     *
     * @param userSession user session
     */
    void userHookInto(UserSession userSession) {
        if (this.userHook == null) {
            return;
        }

        this.userHook.into(userSession);
    }

    /**
     * Offline notification.
     *
     * @param userSession user session
     */
    void userHookQuit(UserSession userSession) {
        if (userHook == null) {
            return;
        }

        this.userHook.quit(userSession);
    }

    /**
     * Apply transport defaults (for example, join type) to the new session.
     *
     * @param userSession user session
     */
    void settingDefault(UserSession userSession) {
        userSession.setExternalJoin(this.joinEnum);
    }
}
