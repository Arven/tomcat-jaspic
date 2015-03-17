/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.auth;

import java.security.Principal;
import java.util.Arrays;
import javax.security.auth.Subject;
import javax.security.auth.message.module.ServerAuthModule;
import org.apache.catalina.realm.GenericPrincipal;
import org.apache.catalina.realm.RealmBase;

/**
 *
 * @author Brian Becker
 */
public class JaspicRealm extends RealmBase {
    
    private ServerAuthModule sam;
    private Subject client, server;
    
    @Override
    protected String getName() {
        return "JASPIC";
    }

    @Override
    protected String getPassword(String string) {
        return string;
    }

    @Override
    protected Principal getPrincipal(String string) {
        return new GenericPrincipal(string, string, Arrays.asList("user"));
    }
    
}
