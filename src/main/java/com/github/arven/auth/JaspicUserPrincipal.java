package com.github.arven.auth;

import java.security.Principal;

/**
 *
 * @author brian.becker
 */
public class JaspicUserPrincipal implements Principal {
    
    private final String name;
    
    public JaspicUserPrincipal(String name) {
        this.name = name;
    }
    
    @Override
    public String getName() {
        return this.name;
    }

}
