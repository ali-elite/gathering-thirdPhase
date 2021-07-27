package ir.sharif.ap2021.client.Listener;

import ir.sharif.ap2021.client.Controller.ChatController;
import ir.sharif.ap2021.client.View.Menu.ChatMenu;
import ir.sharif.ap2021.shared.Event.ChatEvent;
import ir.sharif.ap2021.shared.Event.Event;
import ir.sharif.ap2021.shared.Response.ChatResponse;

import java.io.IOException;

public class ChatListener implements EventListener {

    ChatController chatController = new ChatController(this);
    private ChatMenu chatMenu;


    public ChatListener () throws IOException {
    }

    public void handle(ChatResponse response) throws IOException {
        chatController.control(response);

    }

    public void loadChats(ChatEvent event) throws IOException {
        chatController.loadChats(event);
    }


    @Override
    public void listen(Event event) {

        myClientController.addEvent(event);
        myClientController.addEventListener(this);

    }


    public ChatMenu getChatMenu() {
        return chatMenu;
    }

    public void setChatMenu(ChatMenu chatMenu) {
        this.chatMenu = chatMenu;
    }
}
