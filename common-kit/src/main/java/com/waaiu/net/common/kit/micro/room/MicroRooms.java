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
package com.waaiu.net.common.kit.micro.room;

import com.waaiu.net.common.kit.*;
import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import lombok.*;
import lombok.experimental.*;

/**
 * Thread-safe room registry that manages {@link MicroRoom} instances by ID.
 *
 * @author
 * @date 2023-07-12
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public class MicroRooms<Room extends MicroRoom> {
    @Getter
    final Map<Long, Room> roomMap = CollKit.ofConcurrentHashMap();

    @Setter
    Supplier<Room> roomSupplier;

    /**
     * Check if a room with the given ID exists.
     *
     * @param id the room ID
     * @return {@code true} if the room exists
     */
    public boolean contains(long id) {
        return roomMap.containsKey(id);
    }

    /**
     * Remove the room with the given ID.
     *
     * @param id the room ID
     * @return the removed room, or {@code null} if not found
     */
    public Room remove(long id) {
        return this.roomMap.remove(id);
    }

    /**
     * Get the room with the given ID.
     *
     * @param id the room ID
     * @return the room, or {@code null} if not found
     */
    public Room getRoom(long id) {
        return roomMap.get(id);
    }

    /**
     * Add a room to the registry. If a room with the same ID already exists,
     * the existing room is returned instead.
     *
     * @param room the room to add
     * @return the room now associated with the ID
     */
    public Room add(Room room) {
        long id = room.getId();
        var anyRegion = roomMap.putIfAbsent(id, room);

        if (anyRegion == null) {
            anyRegion = roomMap.get(id);
        }

        return anyRegion;
    }

    /**
     * Get the room with the given ID as an {@link Optional}.
     *
     * @param id the room ID
     * @return an Optional containing the room, or empty if not found
     */
    public Optional<Room> optionalRoom(long id) {
        return Optional.ofNullable(roomMap.get(id));
    }

    /**
     * Get the room by ID, creating it if absent.
     *
     * @param id roomId
     * @return the room instance
     */
    public Room ofRoom(long id) {

        Room region = roomMap.get(id);

        if (region == null) {
            region = roomSupplier.get();
            region.setId(id);

            region = add(region);
        }

        return region;
    }

    /**
     * Return a stream of all rooms in the registry.
     *
     * @return a stream of rooms
     */
    public Stream<Room> stream() {
        return this.roomMap.values().stream();
    }
}
