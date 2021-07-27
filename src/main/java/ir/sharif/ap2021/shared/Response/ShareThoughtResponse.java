package ir.sharif.ap2021.shared.Response;

import ir.sharif.ap2021.shared.Model.User;

public class ShareThoughtResponse extends Response{

    private User user;


    public ShareThoughtResponse(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.shareThought(this);
    }
}
