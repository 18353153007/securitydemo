package com.example.securitydemo.Security;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component("myProvider")
public class MyProvider implements AuthenticationProvider, InitializingBean, MessageSourceAware {
    @Override
    public void afterPropertiesSet() throws Exception {

    }

    @Override
    public void setMessageSource(MessageSource messageSource) {

    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        System.out.println("*************");
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
