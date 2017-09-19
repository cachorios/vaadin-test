
package com.rb.backend;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;


public class CdiConf {

    @Produces
    @Dependent
    @PersistenceContext(unitName = "demoDb")
    public EntityManager entityManager;

}