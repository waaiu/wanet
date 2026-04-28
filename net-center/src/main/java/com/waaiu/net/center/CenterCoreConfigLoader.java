package com.waaiu.net.center;

import com.waaiu.net.center.codec.*;
import com.waaiu.net.center.fragment.*;
import com.waaiu.net.common.*;
import com.waaiu.net.framework.core.*;
import com.waaiu.net.framework.protocol.*;

/**
 * Registers center-server SBE encoders and fragment handlers into shared registries.
 */
public class CenterCoreConfigLoader implements CoreConfigLoader {
    @Override
    public void config() {
        configSBE();
        configOnFragment();
    }

    private void configSBE() {
        SbeMessageManager.register(ConnectResponseMessage.class, new ConnectResponseMessageSbe());
    }

    private void configOnFragment() {
        OnFragmentManager.register(new ConnectRequestMessageOnFragment());
    }
}

