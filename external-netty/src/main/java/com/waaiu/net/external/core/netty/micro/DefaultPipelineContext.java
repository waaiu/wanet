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
package com.waaiu.net.external.core.netty.micro;

import com.waaiu.net.external.core.*;
import com.waaiu.net.external.core.micro.*;
import io.netty.channel.*;

/**
 * PipelineContext is mainly used to wrap the SocketChannel channel, aiming to
 * enhance the Handler aware capability
 *
 * @author
 * @date 2023-04-26
 */
public record DefaultPipelineContext(Channel channel, ExternalSetting setting)
        implements PipelineContext {

    @Override
    public void addFirst(String name, Object handler) {
        this.setting.inject(handler);

        if (handler instanceof ChannelHandler channelHandler) {
            ChannelPipeline pipeline = channel.pipeline();
            pipeline.addFirst(name, channelHandler);
        }
    }

    @Override
    public void addLast(String name, Object handler) {
        if (handler instanceof ChannelHandler channelHandler) {
            this.setting.inject(handler);
            ChannelPipeline pipeline = channel.pipeline();
            pipeline.addLast(name, channelHandler);
        }
    }

    @Override
    public void remove(String name) {
        ChannelPipeline pipeline = channel.pipeline();
        pipeline.remove(name);
    }
}
