package com.rb.ejemplo;

import com.vaadin.cdi.CDIView;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.cdiviewmenu.ViewMenuItem;


@CDIView
@ViewMenuItem(title = "Ejemplo 2", order = 2, icon = FontAwesome.AMAZON)
public class Ejemplo2 extends CustomComponent implements View {
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        VerticalLayout layout = new VerticalLayout();
        layout.addComponent(new Label("Ejemplo 2!!! Doooooos!!"));

        setCompositionRoot(layout);
    }
}
