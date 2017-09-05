package com.rb.backend.service;


import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;
import com.rb.backend.User;

@Repository(forEntity = User.class)
public interface UserRepository extends EntityRepository<User, Long> {

    public User findByEmail(String email);

}