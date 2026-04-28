package com.waaiu.net.framework.core;

/**
 * SPI interface for loading framework configuration at startup.
 * <p>
 * Implementations are discovered via {@link java.util.ServiceLoader} during
 * the {@link com.waaiu.net.framework.Preloading} phase.
 */
public interface CoreConfigLoader {
    /** Apply configuration settings to the framework. */
    void config();
}

