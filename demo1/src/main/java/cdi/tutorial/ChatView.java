package cdi.tutorial;

import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.internal.Conventions;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

/**
 * Created by cachorios on 27/08/2017.
 */

@CDIView
public class ChatView extends CustomComponent implements View {
    @Inject
    private UserDAO userDAO;

    @Inject
    private UserInfo userInfo;

    @Inject
    private javax.enterprise.event.Event<Message> messageEvent;

    private User targetUser;

    private Layout messageLayout;
    private static final int MAX_MESSAGES = 16;


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        String parameters = event.getParameters();
        Layout layout;

        if (parameters.isEmpty()) {
            targetUser = null;
            layout = buildUserSelectionLayout();
        } else {
            targetUser = userDAO.getUserBy(parameters);
            if (targetUser == null) {
                layout = buildErrorLayout();
            } else {
                layout = buildUserLayout(targetUser);
            }
        }
        setCompositionRoot(layout);
    }

    private Layout buildErrorLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("100%");
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.addComponent(new Label("No such user"));
        layout.addComponent(generateBackButton());
        return layout;
    }

    private Layout buildUserLayout( User targetUser ) {
        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("100%");
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.addComponent(new Label("Talking to " + targetUser.getName()));
        layout.addComponent(generateBackButton());
        layout.addComponent(buildChatLayout( targetUser));
        return layout;
    }

    private Component buildChatLayout(final User targetUser) {
        VerticalLayout chatLayout = new VerticalLayout();
        chatLayout.setSizeFull();
        chatLayout.setSpacing(true);

        messageLayout = new VerticalLayout();
        messageLayout.setWidth("100%");

        final TextField messageField = new TextField();
        messageField.setWidth("100%");
        final Button sendButon = new Button("Enviar");

        sendButon.addClickListener(clickEvent -> {
            String message = messageField.getValue();
            if (!message.isEmpty()) {
                messageField.setValue("");
                messageEvent.fire(new Message(userInfo.getUser(),
                        targetUser, message));
            }
        });

        sendButon.setClickShortcut(KeyCode.ENTER);
        Panel messagePanel = new Panel(messageLayout);

        messagePanel.setHeight("400px");
        messagePanel.setWidth("100%");
        chatLayout.addComponent(messagePanel);

        HorizontalLayout entryLayout = new HorizontalLayout(sendButon,messageField);

        entryLayout.setWidth("100%");
        entryLayout.setExpandRatio(messageField, 1);
        entryLayout.setSpacing(true);
        chatLayout.addComponent(entryLayout);
        return chatLayout;
    }

    private Layout buildUserSelectionLayout() {
        VerticalLayout layout = new VerticalLayout();
        layout.setWidth("100%");
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.addComponent(new Label("Select user to talk to:"));
        for (User user : userDAO.getUsers()) {
            if (user.equals(userInfo.getUser())) {
                continue;
            }
            layout.addComponent(generateUserSelectionButton(user));
        }
        return layout;
    }

    private Button generateBackButton() {
        Button button = new Button("Back");
        button.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                UI ui = getUI();
                if (ui != null) {
                    Navigator navi = ui.getNavigator();
                    if (navi != null) {
                        navi.navigateTo(Conventions
                                .deriveMappingForView(ChatView.class));
                    }
                }
            }
        });
        return button;
    }
    private Button generateUserSelectionButton(final User user) {
        Button button = new Button(user.getName());
        button.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(Button.ClickEvent event) {
                UI ui = getUI();
                if (ui != null) {
                    Navigator navi = ui.getNavigator();
                    if (navi != null) {
                        navi.navigateTo(Conventions
                                .deriveMappingForView(ChatView.class)
                                + "/"
                                + user.getUsername());
                    }
                }
            }
        });
        return button;
    }

    private void observeMessage(@Observes Message message) {
        User currentUser = userInfo.getUser();
        if (message.getRecipient().equals(currentUser) || message.getSender().equals(currentUser)) {
            if (messageLayout != null) {
                if (messageLayout.getComponentCount() >= MAX_MESSAGES) {
                    messageLayout.removeComponent(messageLayout
                            .getComponentIterator().next());
                }
                messageLayout.addComponent( new Label(message.toString()));
            }
        }

    }
}
