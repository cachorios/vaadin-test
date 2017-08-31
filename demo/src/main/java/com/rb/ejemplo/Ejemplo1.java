package com.rb.ejemplo;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.cdiviewmenu.ViewMenuItem;


@CDIView
@ViewMenuItem(title = "Ejempl 1", order = 1, icon = FontAwesome.USER)
public class Ejemplo1 extends CustomComponent implements View {
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(new Label("Ejemplo 1"));

        setCompositionRoot(layout);
    }
}
