package com.waaiu.net.server.logic;

import com.waaiu.net.common.*;
import com.waaiu.net.framework.core.*;
import com.waaiu.net.framework.protocol.*;
import com.waaiu.net.server.logic.codec.*;
import com.waaiu.net.server.logic.fragment.*;
import java.util.concurrent.atomic.*;

/**
 * Registers logic-server SBE encoders and fragment handlers into shared
 * registries.
 *
 * @author
 * @date 2025-09-04
 * @since 25.1
 */
public class LogicServerCoreConfigLoader implements CoreConfigLoader {
    AtomicBoolean load = new AtomicBoolean(false);

    @Override
    public void config() {
        if (load.get()) {
            return;
        }

        if (load.compareAndSet(false, true)) {
            configSBE();
            configOnFragment();
        }
    }

    private void configSBE() {
        SbeMessageManager.register(BroadcastMulticastMessage.class, new BroadcastMulticastMessageSbe());
        SbeMessageManager.register(BroadcastUserMessage.class, new BroadcastUserMessageSbe());
        SbeMessageManager.register(BroadcastUserListMessage.class, new BroadcastUserListMessageSbe());

        SbeMessageManager.register(RequestMessage.class, new RequestMessageSbe());
        SbeMessageManager.register(SendMessage.class, new SendMessageSbe());
        SbeMessageManager.register(ResponseMessage.class, new ResponseMessageSbe());

        SbeMessageManager.register(ExternalRequestMessage.class, new ExternalRequestMessageSbe());

        SbeMessageManager.register(UserResponseMessage.class, new UserResponseMessageSbe());
    }

    private void configOnFragment() {
        OnFragmentManager.register(new UserRequestMessageOnFragment());
        OnFragmentManager.register(new RequestMessageOnFragment());
        OnFragmentManager.register(new SendMessageOnFragment());

        OnFragmentManager.register(new ResponseMessageOnFragment());

        OnFragmentManager.register(new ExternalResponseMessageOnFragment());
        OnFragmentManager.register(new EmptyExternalResponseMessageOnFragment());
    }
}
