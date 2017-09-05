package com.rb.ejemplo;


import com.rb.backend.service.UserFacade;
import com.vaadin.cdi.CDIView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.cdiviewmenu.ViewMenuItem;

import javax.inject.Inject;


@CDIView
@ViewMenuItem(title = "Ejemplo 2", order = 1, icon = FontAwesome.AMAZON)
public class Ejemplo2 extends CustomComponent implements View {
    @Inject
    UserFacade userFacade;
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        VerticalLayout layout = new VerticalLayout();

        layout.addComponent(new Label("El Usuario es: " + userFacade.findByEmail("facu@gmail.com").toString()));
        layout.addComponent(new Label("El Usuario es: " + userFacade.findByEmail("prueba1@gmail.com").toString()));
        layout.addComponent(new Label("El Usuario es: " + userFacade.findByEmail("prueba2@gmail.com").toString()));

        setCompositionRoot(layout);
    }
}
