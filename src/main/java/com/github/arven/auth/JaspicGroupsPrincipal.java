/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.auth;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author brian.becker
 */
public class JaspicGroupsPrincipal implements Principal {
    
    private final String name;
    private final List<String> groups;
    
    public JaspicGroupsPrincipal(String name, String[] groups) {
        this.name = name;
        this.groups = Arrays.asList(groups);
    }
    
    @Override
    public String getName() {
        return this.name;
    }
    
    public List<String> getGroups() {
        return this.groups;
    }

}
