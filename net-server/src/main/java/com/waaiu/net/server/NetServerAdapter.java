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
package com.waaiu.net.server;

import com.waaiu.net.common.*;
import com.waaiu.net.sbe.*;
import io.aeron.logbuffer.*;
import lombok.*;
import lombok.experimental.*;
import lombok.extern.slf4j.*;
import org.agrona.*;

/**
 * Aeron fragment adapter that dispatches messages to registered
 * {@link com.waaiu.net.common.OnFragment} handlers.
 *
 * @author
 * @date 2025-08-24
 * @since 25.1
 */
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE)
final class NetServerAdapter implements FragmentHandler {
    final MessageHeaderDecoder headerDecoder = new MessageHeaderDecoder();

    @Override
    public void onFragment(DirectBuffer buffer, int offset, int length, Header header) {
        headerDecoder.wrap(buffer, offset);
        final int headerLength = headerDecoder.encodedLength();
        final int actingBlockLength = headerDecoder.blockLength();
        final int actingVersion = headerDecoder.version();
        var messageOffset = offset + headerLength;

        try {
            var on = OnFragmentManager.onFragments[headerDecoder.templateId()];
            on.process(buffer, messageOffset, actingBlockLength, actingVersion, header);
        } catch (Exception e) {
            log.error("templateId: {}", headerDecoder.templateId());
            log.error(e.getMessage(), e);
        }
    }
}
