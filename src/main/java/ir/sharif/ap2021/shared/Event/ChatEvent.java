package ir.sharif.ap2021.shared.Event;

import ir.sharif.ap2021.shared.Model.Chat;

public class ChatEvent extends Event{

    private String order;
    private int chatId;
    private String pm;
    private String changed;
    private String someText;
    private Chat chat; // just for interClinet usage

    public ChatEvent(String order) {
        this.order = order;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getPm() {
        return pm;
    }

    public void setPm(String pm) {
        this.pm = pm;
    }

    public String getChanged() {
        return changed;
    }

    public void setChanged(String changed) {
        this.changed = changed;
    }

    public String getSomeText() {
        return someText;
    }

    public void setSomeText(String someText) {
        this.someText = someText;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public Chat getTheChat() {
        return chat;
    }

    public void setTheChat(Chat chat) {
        this.chat = chat;
    }

    @Override
    public void visit(EventVisitor eventVisitor) {
        eventVisitor.doChat(this);
    }
}
