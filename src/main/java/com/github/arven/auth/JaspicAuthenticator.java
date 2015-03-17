package com.github.arven.auth;

import java.io.IOException;
import java.security.Principal;
import java.util.Collections;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.message.AuthException;
import javax.security.auth.message.AuthStatus;
import javax.security.auth.message.MessageInfo;
import javax.security.auth.message.MessagePolicy;
import javax.security.auth.message.MessagePolicy.TargetPolicy;
import javax.security.auth.message.config.AuthConfigFactory;
import javax.security.auth.message.config.AuthConfigProvider;
import javax.security.auth.message.config.ClientAuthConfig;
import javax.security.auth.message.config.ClientAuthContext;
import javax.security.auth.message.config.ServerAuthConfig;
import javax.security.auth.message.module.ServerAuthModule;

import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.authenticator.AuthenticatorBase;
import org.apache.catalina.authenticator.Constants;
import org.apache.catalina.connector.Request;
import org.apache.catalina.deploy.LoginConfig;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

public class JaspicAuthenticator extends AuthenticatorBase {
       
    private ServerAuthModule sam;
    private Subject client, server;
    
    private static final Log log = LogFactory.getLog(JaspicAuthenticator.class);
    
    public JaspicAuthenticator() throws AuthException {
        super();

        AuthConfigFactory factory = AuthConfigFactory.getFactory();
        factory.registerConfigProvider(new BasicAuthConfigProvider(), "HttpServlet", null, "Test Registration");
    }

    /**
     * Authenticate the user making this request, based on the specified
     * login configuration.  Return <code>true</code> if any specified
     * constraint has been satisfied, or <code>false</code> if we have
     * created a response challenge already.
     *
     * @param request Request we are processing
     * @param response Response we are creating
     * @param config    Login configuration describing how authentication
     *              should be performed
     * @return 
     *
     * @exception IOException if an input/output error occurs
     */
    @Override
    public boolean authenticate(Request request, HttpServletResponse response, LoginConfig lconfig) throws IOException {
        try {
            // Have we already authenticated someone?
            Principal principal = request.getUserPrincipal();
            String ssoId = (String) request.getNote(Constants.REQ_SSOID_NOTE);
            if (principal != null) {
                if (log.isDebugEnabled())
                    log.debug("Already authenticated '" + principal.getName() + "'");
                return (true);
            }
            
            MessageInfo info = new BasicMessageInfo();
            info.setRequestMessage(request);
            info.setResponseMessage(response);
            
            AuthConfigFactory factory = AuthConfigFactory.getFactory();
            AuthConfigProvider provider = factory.getConfigProvider("HttpServlet", null, null);
            ClientAuthConfig config = provider.getClientAuthConfig("HttpServlet", null, null);
            String authContextID = config.getAuthContextID(info);
            ClientAuthContext ctx = config.getAuthContext(authContextID, client, null);
            ctx.secureRequest(info, client);
            
            AuthStatus status = sam.validateRequest(info, client, server);
            
            if(status == AuthStatus.SUCCESS) {
                register(request, response, principal, "JASPIC", "user", "password");
            }

            register(request, response, principal, "JASPIC", "user", "password");
            return true;
        } catch (AuthException ex) {
            Logger.getLogger(JaspicAuthenticator.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    protected String getAuthMethod() {
        return "JASPIC";
    }

}