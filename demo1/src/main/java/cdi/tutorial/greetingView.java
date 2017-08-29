package cdi.tutorial;


import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import javax.inject.Inject;

/**
 * Created by cachorios on 27/08/2017.
 */

@CDIView("hello")
public class greetingView extends CustomComponent implements View {
    @Inject Greeting greeting;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event){
        final VerticalLayout layout = new VerticalLayout();
        layout.setMargin(true);

        Button button  = new Button("Click me!");
        button.addClickListener(clickEvent -> {
            layout.addComponent(new Label(greeting.getText()));
        });

        layout.addComponent(button);
        setCompositionRoot(layout);
    }
}
