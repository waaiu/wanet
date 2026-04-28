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
package com.waaiu.net.extension.domain;

import com.waaiu.net.extension.domain.user.UserLogin;
import com.waaiu.net.extension.domain.user.UserLoginEmailEventHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author
 * @date 2021-12-26
 */
public class UserLoginDomainEventTest {
    DomainEventApplication domainEventApplication;

    @AfterEach
    public void tearDown() {
        // -
        domainEventApplication.stop();
    }

    @BeforeEach
    public void setUp() {
        // ======（）======

        //
        var setting = new DomainEventSetting();
        // email
        setting.addEventHandler(new UserLoginEmailEventHandler());

        //
        domainEventApplication = new DomainEventApplication();
        domainEventApplication.startup(setting);
    }

    @Test
    public void testEventSend() {
        //
        UserLogin userLogin = new UserLogin(101, "Michael Jackson");
        /*
         * 、。
         * ,。（）
         * ， （） （）
         * email，，。（）
         */
        DomainEventPublish.send(userLogin);
    }
}
