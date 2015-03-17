/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.auth;

import java.util.Collections;
import javax.security.auth.Subject;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.AuthStatus;
import javax.security.auth.message.MessageInfo;
import javax.security.auth.message.config.ServerAuthContext;
import javax.security.auth.message.module.ServerAuthModule;

public class BasicServerAuthContext implements ServerAuthContext {
 
    private ServerAuthModule serverAuthModule;
 
    public BasicServerAuthContext(CallbackHandler handler) throws AuthException {
        serverAuthModule = new ExampleAuthModule();
        serverAuthModule.initialize(null, null, handler,
                Collections.<String, String> emptyMap());
    }
 
    @Override
    public AuthStatus validateRequest(MessageInfo messageInfo,
            Subject clientSubject, Subject serviceSubject) throws AuthException {
        return serverAuthModule.validateRequest(messageInfo, clientSubject,
                serviceSubject);
    }
 
    @Override
    public AuthStatus secureResponse(MessageInfo messageInfo,
            Subject serviceSubject) throws AuthException {
        return serverAuthModule.secureResponse(messageInfo, serviceSubject);
    }
 
    @Override
    public void cleanSubject(MessageInfo messageInfo, Subject subject)
            throws AuthException {
        serverAuthModule.cleanSubject(messageInfo, subject);
    }
 
}