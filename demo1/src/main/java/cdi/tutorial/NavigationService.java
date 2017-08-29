package cdi.tutorial;

import javax.enterprise.event.Observes;
import java.io.Serializable;

public interface NavigationService extends Serializable {

    public void onNavigationEvent(@Observes NavigationEvent event);
}