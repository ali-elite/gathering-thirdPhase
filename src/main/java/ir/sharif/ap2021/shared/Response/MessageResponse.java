package ir.sharif.ap2021.shared.Response;

import ir.sharif.ap2021.shared.Model.Chat;
import ir.sharif.ap2021.shared.Model.Message;
import ir.sharif.ap2021.shared.Model.Thought;
import ir.sharif.ap2021.shared.Model.User;

public class MessageResponse extends Response{


    private String order;
    private Message myMessage;
    private User myUser;
    private String hyper;

    private User hypedUser;
    private Thought hypedThought;
    private Chat hypedChat;

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

    public String getHyper() {
        return hyper;
    }

    public void setHyper(String hyper) {
        this.hyper = hyper;
    }

    public User getHypedUser() {
        return hypedUser;
    }

    public void setHypedUser(User hypedUser) {
        this.hypedUser = hypedUser;
    }

    public Thought getHypedThought() {
        return hypedThought;
    }

    public void setHypedThought(Thought hypedThought) {
        this.hypedThought = hypedThought;
    }

    public Chat getHypedChat() {
        return hypedChat;
    }

    public void setHypedChat(Chat hypedChat) {
        this.hypedChat = hypedChat;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.doMessage(this);
    }
}
