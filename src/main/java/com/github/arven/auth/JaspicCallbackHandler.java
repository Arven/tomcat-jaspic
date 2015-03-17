/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.auth;

import java.io.IOException;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.message.callback.CallerPrincipalCallback;
import javax.security.auth.message.callback.GroupPrincipalCallback;

public class JaspicCallbackHandler implements CallbackHandler {
    
    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        for(Callback c : callbacks) {
            if (c instanceof CallerPrincipalCallback) {
                CallerPrincipalCallback cb = (CallerPrincipalCallback) c;
                if(cb.getName() != null) {
                    cb.getSubject().getPrincipals().add(new JaspicUserPrincipal(cb.getName()));
                }
            } else if (c instanceof GroupPrincipalCallback) {
                GroupPrincipalCallback cb = (GroupPrincipalCallback) c;
                if(cb.getGroups() != null) {
                    cb.getSubject().getPrincipals().add(new JaspicGroupsPrincipal("groups", cb.getGroups()));
                }
            }
        }
    }

}