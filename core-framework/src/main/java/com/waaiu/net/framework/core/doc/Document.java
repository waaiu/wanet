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
package com.waaiu.net.framework.core.doc;

import java.util.*;
import lombok.*;
import lombok.experimental.*;

/**
 * Document-related information, such as action-related, broadcast-related, and
 * error code-related details.
 *
 * @author
 * @date 2024-06-25
 * @since 21.11
 */
@FieldDefaults(level = AccessLevel.PUBLIC)
public final class Document {
    /** Parsed broadcast document list */
    List<BroadcastDocument> broadcastDocumentList;
    /** Parsed error code document list */
    List<ErrorCodeDocument> errorCodeDocumentList;
    /** Parsed action document list */
    List<ActionDoc> actionDocList;
}
