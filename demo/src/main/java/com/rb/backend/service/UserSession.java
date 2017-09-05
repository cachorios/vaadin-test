package com.rb.backend.service;


import com.rb.backend.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.deltaspike.core.api.config.ConfigResolver;

import javax.annotation.PostConstruct;


import javax.enterprise.inject.Alternative;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;


import java.io.Serializable;
@Alternative
@SessionScoped
public class UserSession implements Serializable {

    @Inject
    UserFacade userFacade;
    private User user;

    @PostConstruct
    public void init(){
        final String propertyValue = ConfigResolver.getPropertyValue("jpa-demo.uskey");

        // If no Google OAuth API key available, use fake login
        if (StringUtils.isEmpty(propertyValue)) {
            demoLogin();
        }
    }

    protected void demoLogin(){
        final String email = "cachorios@gmail.com";

        this.user = userFacade.findByEmail(email);

        if(user == null){
            this.user = userFacade.save(new User("prueba1@gmail.com"));
            this.user = userFacade.save(new User("prueba2@gmail.com"));
            this.user = userFacade.save(new User("facu@gmail.com"));
            this.user = userFacade.save(new User(email));

        }
    }

    public User getUser(){
        return user;
    }

    public boolean isLoggedIn(){
        return user != null;
    }

    ////?
    public void login(String email) {
        try{
            user = userFacade.findByEmail(email);
        }catch (Exception e){
        }
        if(user == null){
            userFacade.save(new User(email));
            user = userFacade.findByEmail(email);
        }
    }

}
