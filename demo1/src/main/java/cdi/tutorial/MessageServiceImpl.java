package cdi.tutorial;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.vaadin.ui.UI;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.inject.Inject;


/***
 * Para hacer esta clave un singleton de la app
 * */
@ApplicationScoped
public class MessageServiceImpl implements MessageService {

    @Inject
    private Event<Message> messageEvent;

    private Map<User, UI> activeUIMap = new HashMap<User, UI>();

    @Override
    public List<Message> getLatestMessages(User user1, User user2, int n) {
        // IMplementacon tonta
        return new LinkedList<Message>();
    }

    @Override
    public void registerParticipant(User user, UI ui) {
        activeUIMap.put(user, ui);
    }

    @Override
    public void unregisterParticipant(User user) {
        activeUIMap.remove(user);

    }
    private void observeMessage(@Observes final Message message) {

        UI recipientUI = activeUIMap.get(message.getRecipient());
        if (recipientUI != null && recipientUI.isAttached()
                && !recipientUI.isClosing()) {
            recipientUI.access(new Runnable() {

                @Override
                public void run() {
                    messageEvent.fire(message);
                }
            });
        }
    }
}