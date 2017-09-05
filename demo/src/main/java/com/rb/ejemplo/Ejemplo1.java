package com.rb.ejemplo;


import com.rb.backend.service.UserFacade;
import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.cdiviewmenu.ViewMenuItem;

import javax.inject.Inject;


@CDIView
@ViewMenuItem(title = "Ejempl 1", order = 2, icon = FontAwesome.USER)
public class Ejemplo1 extends CustomComponent implements View {
    @Inject
    UserFacade userFacade;
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(new Label("El Usuario es: " + userFacade.findByEmail("cachorios@gmail.com").toString()));

        setCompositionRoot(layout);
    }
}
