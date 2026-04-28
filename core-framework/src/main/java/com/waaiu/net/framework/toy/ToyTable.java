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
package com.waaiu.net.framework.toy;

import java.util.*;

/**
 * A simple table model composed of named {@link ToyTableRegion} columns, used
 * to
 * render structured key-value information in the startup banner.
 *
 * @author
 * @date 2023-01-30
 */
final class ToyTable {
    AnsiColor.Color color = BannerColorStrategy.anyColor();
    Map<String, ToyTableRegion> regionMap = new LinkedHashMap<>();
    ToyTableRender tableRender;
    int bodyMaxNum;

    void render() {

        this.bodyMaxNum = this.countRegionMaxLine();
        this.tableRender = new ToyTableRender(bodyMaxNum);

        int size = this.regionMap.size();
        int eleNum = 0;

        for (ToyTableRegion region : regionMap.values()) {
            region.render();
            eleNum++;

            boolean lastEle = size == eleNum;
            if (!lastEle) {
                tableRender.next();
            }
        }

        this.tableRender.render();
    }

    int countRegionMaxLine() {
        int lineMax = 0;

        for (ToyTableRegion region : this.regionMap.values()) {

            int size = region.lineMap.size();
            if (size > lineMax) {
                lineMax = size;
            }
        }

        return lineMax;
    }

    ToyTableRegion getRegion(String regionName) {

        ToyTableRegion region = this.regionMap.get(regionName);

        if (region == null) {

            region = new ToyTableRegion();
            region.head = regionName;
            region.table = this;

            this.regionMap.putIfAbsent(regionName, region);
            region = this.regionMap.get(regionName);
        }

        return region;
    }
}
