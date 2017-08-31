package cdi.tutorial;


import com.vaadin.cdi.CDIView;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import javax.inject.Inject;

@CDIView("login")
public class LoginView extends CustomComponent implements View, Button.ClickListener {

    @Inject
    private UserInfo user;

    @Inject
    private UserDAO userDAO;

    private TextField usernameField;
    private PasswordField passwordField;
    private Button loginButton;

    @Inject
    private javax.enterprise.event.Event<NavigationEvent> navigationEvent;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

        usernameField = new TextField("Username");
        passwordField = new PasswordField("Password");
        loginButton = new Button("Login");
        loginButton.addClickListener(this);

        loginButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);

        VerticalLayout layout = new VerticalLayout();
        setCompositionRoot(layout);
        layout.setSizeFull();

        layout.addComponent(usernameField);
        layout.addComponent(passwordField);
        layout.addComponent(loginButton);

    }

    @Override
    public void buttonClick(Button.ClickEvent event) {
        String username = usernameField.getValue();
        String password = passwordField.getValue();

        User loginUser = userDAO.getUserBy(username, password);
        if (loginUser == null) {
            new Notification("Wrong password", Notification.TYPE_ERROR_MESSAGE)
                    .show(getUI().getPage());
            return;
        }

        user.setUser(loginUser);

        navigationEvent.fire(new NavigationEvent("chat"));
    }
}