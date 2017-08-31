package cdi.tutorial;

import com.vaadin.ui.Layout;

import javax.inject.Inject;



public class MainView extends MainViewDesign {

    @Inject
    private javax.enterprise.event.Event<NavigationEvent> navigationEvent;
    public Layout getContentLayout(){
        return contenido;
    }


    public MainView() {

        login.addClickListener(event ->  navigationEvent.fire( new NavigationEvent("login")) );
        chat.addClickListener(event ->  navigationEvent.fire( new NavigationEvent("chat")) );

    }
}
