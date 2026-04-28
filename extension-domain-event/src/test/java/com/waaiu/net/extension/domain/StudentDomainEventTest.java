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
package com.waaiu.net.extension.domain;

import com.waaiu.net.extension.domain.student.StudentEmailEventHandler1;
import com.waaiu.net.extension.domain.student.StudentEo;
import com.waaiu.net.extension.domain.student.StudentGoHomeEventHandler2;
import com.waaiu.net.extension.domain.student.StudentSleepEventHandler3;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *
 * @author
 * @date 2021-12-26
 */
public class StudentDomainEventTest {
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
        // -
        setting.addEventHandler(new StudentEmailEventHandler1());
        // -
        setting.addEventHandler(new StudentGoHomeEventHandler2());
        // -
        setting.addEventHandler(new StudentSleepEventHandler3());

        //
        domainEventApplication = new DomainEventApplication();
        domainEventApplication.startup(setting);
    }

    @Test
    public void testEventSend() {
        //
        var studentEo = new StudentEo(1);
        /*
         * 、。
         * email,。（）
         * ， （） （）
         * email，，。（）
         */
        studentEo.send();
    }
}
