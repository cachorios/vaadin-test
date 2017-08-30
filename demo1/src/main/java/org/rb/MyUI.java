package org.rb;


import cdi.tutorial.Greeting;
import cdi.tutorial.NavigationEvent;
import cdi.tutorial.UserInfo;
import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button;
import com.vaadin.ui.Label;

import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;

/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("valo")
@CDIUI("")
@Push
@SuppressWarnings("serial")
public class MyUI extends UI {

    @Inject
    private javax.enterprise.event.Event<NavigationEvent> navigationEvent;

    @Override
    protected void init(VaadinRequest request) {

        navigationEvent.fire(new NavigationEvent("login"));
    }

}