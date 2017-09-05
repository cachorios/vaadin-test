package com.rb.backend;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Matti Tahvonen
 */
public class CdiConfig {

    @Produces
    @Dependent
    @PersistenceContext(unitName = "demoDb")
    public EntityManager entityManager;
    
}
