package ir.sharif.ap2021.shared.Response;

import ir.sharif.ap2021.shared.Model.Chat;
import ir.sharif.ap2021.shared.Model.User;

public class OutProfileResponse extends Response{

    private String order;
    private String message;
    private Chat chat;
    private boolean isRepeat;
    private boolean isRequest;
    private boolean isUnfollow;
    private User myUser;
    private User outUser;

    public OutProfileResponse(String order) {
        this.order = order;
    }


    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isRepeat() {
        return isRepeat;
    }

    public void setRepeat(boolean repeat) {
        isRepeat = repeat;
    }

    public boolean isRequest() {
        return isRequest;
    }

    public void setRequest(boolean request) {
        isRequest = request;
    }

    public boolean isUnfollow() {
        return isUnfollow;
    }

    public void setUnfollow(boolean unfollow) {
        isUnfollow = unfollow;
    }

    public User getMyUser() {
        return myUser;
    }

    public void setMyUser(User myUser) {
        this.myUser = myUser;
    }

    public User getOutUser() {
        return outUser;
    }

    public void setOutUser(User outUser) {
        this.outUser = outUser;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.doOutProfile(this);
    }
}
