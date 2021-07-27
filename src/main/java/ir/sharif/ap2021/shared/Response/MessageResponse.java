package ir.sharif.ap2021.shared.Response;

import ir.sharif.ap2021.shared.Model.Message;
import ir.sharif.ap2021.shared.Model.User;

public class MessageResponse extends Response{


    private String order;
    private Message myMessage;
    private User myUser;

    public MessageResponse(String order) {
        this.order = order;
    }


    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Message getMyMessage() {
        return myMessage;
    }

    public void setMyMessage(Message myMessage) {
        this.myMessage = myMessage;
    }

    public User getMyUser() {
        return myUser;
    }

    public void setMyUser(User myUser) {
        this.myUser = myUser;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.doMessage(this);
    }
}
