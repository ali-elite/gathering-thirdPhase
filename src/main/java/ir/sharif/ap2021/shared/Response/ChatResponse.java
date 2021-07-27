package ir.sharif.ap2021.shared.Response;

import ir.sharif.ap2021.shared.Model.Chat;
import ir.sharif.ap2021.shared.Model.User;

public class ChatResponse extends Response{

    private String order;
    private User myUser;
    private Chat chat;

    public ChatResponse(String order) {
        this.order = order;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public User getMyUser() {
        return myUser;
    }

    public void setMyUser(User myUser) {
        this.myUser = myUser;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.doChat(this);
    }

}
