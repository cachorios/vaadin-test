package cdi.tutorial;

import com.vaadin.cdi.CDIView;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ErrorMessage;
import com.vaadin.ui.*;

import javax.inject.Inject;

/**
 * Created by cachorios on 27/08/2017.
 */
@CDIView("login")
public class LoginView extends CustomComponent implements View, Button.ClickListener {

    @Inject
    private javax.enterprise.event.Event<NavigationEvent> navigationEvent;

    @Inject
    private UserInfo user;

    @Inject
    private UserDAO userDAO;

    private TextField nombreField;
    private PasswordField claveField;
    private Button loginButton;

    private Navigator navigator;


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        //navigator = getUI().getNavigator();

        nombreField = new TextField("Usuario");
        claveField = new PasswordField("Clave");
        loginButton = new Button("Entrar");

        loginButton.addClickListener(this);
        loginButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        VerticalLayout layout = new VerticalLayout();
        setCompositionRoot(layout);
        layout.setSizeFull();
        layout.setMargin(true);
        layout.setSpacing(true);

        layout.addComponent(nombreField);
        layout.addComponent(claveField);
        layout.addComponent(loginButton);

    }

    @Override
    public void buttonClick(Button.ClickEvent clickEvent) {
        // Dummy implementation
        String username = nombreField.getValue();
        String password = claveField.getValue();

        User loginUser = userDAO.getUserBy(username,password);

        if (loginUser == null) {
            new Notification("Contaseña incorrecta  ", Notification.Type.ERROR_MESSAGE)
                    .show(getUI().getPage());
            return;
        }

        user.setUser(loginUser);

        /*
        if (navigator != null) {
            navigator.navigateTo("chat");
            //navigator.navigateTo("hello");
        }
        */
        navigationEvent.fire(new NavigationEvent("chat"));
    }

    private static final class WrongPasswordErrorMessage implements
            ErrorMessage {

        @Override
        public ErrorLevel getErrorLevel() {
            return ErrorMessage.ErrorLevel.ERROR;
        }

        @Override
        public String getFormattedHtmlMessage() {
            return "Wrong password!";
        }
    }
}
