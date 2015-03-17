/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.auth;

import java.util.Map;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.MessageInfo;
import javax.security.auth.message.config.ServerAuthConfig;
import javax.security.auth.message.config.ServerAuthContext;

/**
 * This class functions as a kind of factory for {@link ServerAuthContext}
 * instances, which are delegates for the actual {@link ServerAuthModule} (SAM)
 * that we're after.
 *
 */
public class BasicServerAuthConfig implements ServerAuthConfig {
 
    private String layer;
    private String appContext;
    private CallbackHandler handler;
    private Map<String, String> providerProperties;
 
    public BasicServerAuthConfig(String layer, String appContext,
            CallbackHandler handler, Map<String, String> providerProperties) {
        this.layer = layer;
        this.appContext = appContext;
        this.handler = handler;
        this.providerProperties = providerProperties;
    }
 
    /**
     * WebLogic 12c, JBoss EAP 6 and GlassFish 3.1.2.2 call this only once per
     * request, Geronimo V3 calls this before sam.validateRequest and again
     * before sam.secureRequest in the same request.
     *
     */
    @Override
    public ServerAuthContext getAuthContext(String authContextID,
            Subject serviceSubject, @SuppressWarnings("rawtypes") Map properties)
            throws AuthException {
         
        return new BasicServerAuthContext(handler);
    }
 
    @Override
    public String getMessageLayer() {
        return layer;
    }
 
    @Override
    public String getAuthContextID(MessageInfo messageInfo) {
        return appContext;
    }
 
    @Override
    public String getAppContext() {
        return appContext;
    }
 
    @Override
    public void refresh() {
    }
 
    @Override
    public boolean isProtected() {
        return false;
    }
 
    public Map<String, String> getProviderProperties() {
        return providerProperties;
    }
 
}