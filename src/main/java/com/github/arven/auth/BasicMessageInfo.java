/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.arven.auth;

import java.util.HashMap;
import java.util.Map;
import javax.security.auth.message.MessageInfo;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author brian.becker
 */
public class BasicMessageInfo implements MessageInfo {

    private Object request;
    private Object response;
    private Map info;
    
    public BasicMessageInfo() {
        this.info = new HashMap();
    }
    
    @Override
    public Object getRequestMessage() {
        return this.request;
    }

    @Override
    public Object getResponseMessage() {
        return this.response;
    }

    @Override
    public void setRequestMessage(Object request) {
        this.request = request;
    }

    @Override
    public void setResponseMessage(Object response) {
        this.response = response;
    }

    @Override
    public Map getMap() {
        return this.info;
    }
    
}
