/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.auth;

import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.AuthStatus;
import javax.security.auth.message.MessageInfo;
import javax.security.auth.message.MessagePolicy;
import javax.security.auth.message.callback.CallerPrincipalCallback;
import javax.security.auth.message.callback.GroupPrincipalCallback;
import javax.security.auth.message.module.ServerAuthModule;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExampleAuthModule implements ServerAuthModule {
     
    /**
     * Supported message types. For our case we only need to deal with HTTP
     * servlet request and responses. On Java EE 7 this will handle WebSockets
     * as well.
     */
    private static final Class<?>[] SUPPORTED_MESSAGE_TYPES = new Class<?>[] {
        HttpServletRequest.class, HttpServletResponse.class };
 
    /**
     * Callback handler that is passed in initialize by the container. This
     * processes the callbacks which are objects that populate the "subject".
     */
    private CallbackHandler handler;
 
    /**
     * Does nothing of note for what we need.
     */
    @Override
    public void cleanSubject(final MessageInfo messageInfo,
            final Subject subject) throws AuthException {
    }
 
    @SuppressWarnings("rawtypes")
    @Override
    public Class[] getSupportedMessageTypes() {
        return SUPPORTED_MESSAGE_TYPES;
    }
 
    /**
     * Initializes the module.  Allows you to pass in options.
     * @param requestPolicy
     *            request policy, ignored
     * @param responsePolicy
     *            response policy, ignored
     * @param h
     *            callback handler
     * @param options
     *            options
     */
    @Override
    public void initialize(final MessagePolicy requestPolicy,
            MessagePolicy responsePolicy, CallbackHandler h, Map options)
                    throws AuthException {
        handler = h;
    }
 
    /**
     * @return AuthStatus.SEND_SUCCESS
     */
    @Override
    public AuthStatus secureResponse(final MessageInfo paramMessageInfo,
            final Subject subject) throws AuthException {
        return AuthStatus.SEND_SUCCESS;
    }
 
    /**
     * Validation occurs here.
     */
    @Override
    public AuthStatus validateRequest(final MessageInfo messageInfo,
            final Subject client, final Subject serviceSubject)
                    throws AuthException {
 
        // Take the request from the messageInfo structure.
        final HttpServletRequest req = (HttpServletRequest) messageInfo
                .getRequestMessage();
        try {
            // Get the user name from the header.  If not there then fail authentication.
            final String userName = req.getHeader("X-Forwarded-User");
            if (userName == null) {
                return AuthStatus.FAILURE;
            }
 
            // Store the user name that was in the header and also set a group.
            handler.handle(new Callback[] {
                    new CallerPrincipalCallback(client, userName),
                    new GroupPrincipalCallback(client, new String[] { "users" }) });
            return AuthStatus.SUCCESS;
        } catch (final Exception e) {
            throw new AuthException(e.getMessage());
        }
    }
}