/*
 * wanet
 * Copyright (C) 2021 - present   (
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
package com.waaiu.net.common.kit.concurrent;

import java.util.concurrent.*;
import org.jspecify.annotations.*;

/**
 * A {@link ThreadFactory} that creates daemon threads with auto-incrementing
 * names.
 *
 * @author
 * @date 2023-04-02
 */
public final class DaemonThreadFactory extends ThreadCreator implements ThreadFactory {

    public DaemonThreadFactory(String threadNamePrefix) {
        super(threadNamePrefix);
        this.daemon = true;
    }

    @Override
    public Thread newThread(@NonNull Runnable runnable) {
        return createThread(runnable);
    }
}
