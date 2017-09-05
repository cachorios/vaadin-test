package com.rb.backend.service;

import com.rb.backend.User;

import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
public class UserFacade {

    @Inject
    UserRepository repo;

    public User findByEmail(String email) {
        try {
            return repo.findByEmail(email);
        } catch (Exception e) {
            return null;
        }
    }
    public User save(User user) {
        return repo.save(user);
    }

}
