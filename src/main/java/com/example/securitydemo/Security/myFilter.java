package com.example.securitydemo.Security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.codec.Base64;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.authentication.NullRememberMeServices;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.Assert;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;

/**
 * Created by zzj on 17-10-31.
 */

public class myFilter extends OncePerRequestFilter {
    private AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource = new WebAuthenticationDetailsSource();
    private AuthenticationEntryPoint authenticationEntryPoint;
    private AuthenticationManager authenticationManager;
    private RememberMeServices rememberMeServices = new NullRememberMeServices();
    private boolean ignoreFailure = false;
    private String credentialsCharset = "UTF-8";
    @Autowired
    private MyProvider myProvider;


    public AuthenticationDetailsSource<HttpServletRequest, ?> getAuthenticationDetailsSource() {
        return authenticationDetailsSource;
    }

    public void setAuthenticationDetailsSource(AuthenticationDetailsSource<HttpServletRequest, ?> authenticationDetailsSource) {
        this.authenticationDetailsSource = authenticationDetailsSource;
    }

    public AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return authenticationEntryPoint;
    }

    public void setAuthenticationEntryPoint(AuthenticationEntryPoint authenticationEntryPoint) {
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    public AuthenticationManager getAuthenticationManager() {
        return authenticationManager;
    }

    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public RememberMeServices getRememberMeServices() {
        return rememberMeServices;
    }

    public void setRememberMeServices(RememberMeServices rememberMeServices) {
        this.rememberMeServices = rememberMeServices;
    }

    public void setIgnoreFailure(boolean ignoreFailure) {
        this.ignoreFailure = ignoreFailure;
    }

    public String getCredentialsCharset() {
        return credentialsCharset;
    }

    public void setCredentialsCharset(String credentialsCharset) {
        this.credentialsCharset = credentialsCharset;
    }


    @Override
    public void afterPropertiesSet() {
        Assert.notNull(this.authenticationManager, "An AuthenticationManager is required");

        if (!isIgnoreFailure()) {
            Assert.notNull(this.authenticationEntryPoint,
                    "An AuthenticationEntryPoint is required");
        }
    }

    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response, FilterChain chain) throws IOException,
            ServletException {
        final boolean debug = logger.isDebugEnabled();

        String header = request.getHeader("Authorization");

        if (header == null || !header.startsWith("Basic ")) {
            chain.doFilter(request, response);
            return;
        }
        String username;

        try {

            String[] tokens = extractAndDecodeHeader(header, request);
            assert tokens.length == 2;

            username = tokens[0];


            UsernamePasswordAuthenticationToken authRequest =
                    new UsernamePasswordAuthenticationToken(username, tokens[1], new HashSet<SimpleGrantedAuthority>());
            Authentication authResult = authenticationManager
                    .authenticate(authRequest);


            logger.debug("AK Authentication success: " + authResult);

            SecurityContextHolder.getContext().setAuthentication(authResult);

            rememberMeServices.loginSuccess(request, response, authResult);

            onSuccessfulAuthentication(request, response, authResult);

        } catch (AuthenticationException failed) {
            SecurityContextHolder.clearContext();
            if (debug) {
                logger.debug("AK Authentication request for failed: " + failed);
            }
            rememberMeServices.loginFail(request, response);
            onUnsuccessfulAuthentication(request, response, failed);
            chain.doFilter(request, response);
            return;
        }
        //验证成功直接跳过接下来的过滤器
        chain.doFilter(request, response);
    }

    /**
     * Decodes the header into a username and password.
     *
     * @throws BadCredentialsException if the Basic header is not present or is not valid
     * Base64
     */
    private String[] extractAndDecodeHeader(String header, HttpServletRequest request)
            throws IOException {

        byte[] base64Token = header.substring(6).getBytes("UTF-8");
        byte[] decoded;
        try {
            decoded = Base64.decode(base64Token);
        }
        catch (IllegalArgumentException e) {
            throw new BadCredentialsException(
                    "Failed to decode basic authentication token");
        }

        String token = new String(decoded, getCredentialsCharset(request));

        int delim = token.indexOf(":");

        if (delim == -1) {
            throw new BadCredentialsException("Invalid basic authentication token");
        }
        return new String[] { token.substring(0, delim), token.substring(delim + 1) };
    }

    protected String getCredentialsCharset(HttpServletRequest httpRequest) {
        return credentialsCharset;
    }


    protected void onSuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response, Authentication authResult) throws IOException {
    }

    protected void onUnsuccessfulAuthentication(HttpServletRequest request,
                                                HttpServletResponse response, AuthenticationException failed)
            throws IOException {
    }

    protected boolean isIgnoreFailure() {
        return ignoreFailure;
    }

}