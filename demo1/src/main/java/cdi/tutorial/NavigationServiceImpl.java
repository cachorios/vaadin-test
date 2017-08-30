package cdi.tutorial;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import com.vaadin.cdi.CDIViewProvider;
import com.vaadin.cdi.NormalUIScoped;
import com.vaadin.navigator.Navigator;
import com.vaadin.ui.UI;

@NormalUIScoped
public class NavigationServiceImpl implements NavigationService {

    @Inject
    private CDIViewProvider viewProvider;

    @Inject
    private UI ui;

    @Inject
    private ErrorView errorView;

    @PostConstruct
    public void initialize() {
        if (ui.getNavigator() == null) {
            Navigator navigator = new Navigator(ui, ui);
            navigator.addProvider(viewProvider);
            navigator.setErrorView(errorView);
        }
    }

    public void onNavigationEvent(@Observes NavigationEvent event) {
        try {
            ui.getNavigator().navigateTo(event.getNavigateTo());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}