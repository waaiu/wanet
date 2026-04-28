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
package com.waaiu.net.framework.core.enhance;

import com.waaiu.net.common.kit.*;
import com.waaiu.net.framework.core.*;
import java.util.*;
import lombok.experimental.*;

/**
 * Registry and executor for {@link BarSkeletonBuilderEnhance} extensions.
 * <p>
 * Implementations are auto-discovered via {@link ServiceLoader} at class-load
 * time.
 * Call {@link #enhance(BarSkeletonBuilder)} to apply all registered
 * enhancem
 *
 * @author
 * @date 2023-06-16
 */
@UtilityClass
public class BarSkeletonBuilderEnhances {

    final Set<BarSkeletonBuilderEnhance> enhanceSet = CollKit.ofConcurrentSet();

    static {
        ServiceLoader.load(BarSkeletonBuilderEnhance.class).forEach(BarSkeletonBuilderEnhances::add);
    }

    /**
     * Register an additional enhance instance.
     *
     * @param enhance the enhance to add
     */
    void add(BarSkeletonBuilderEnhance enhance) {
        enhanceSet.add(enhance);
    }

    /**
     * Apply all registered enhancements to the given builder.
     *
     * @param builder the skeleton builder to enhance
     */
    public void enhance(BarSkeletonBuilder builder) {
        for (BarSkeletonBuilderEnhance enhance : enhanceSet) {
            enhance.enhance(builder);
        }
    }
}
