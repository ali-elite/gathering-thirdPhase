package ir.sharif.ap2021.shared.Response;

import ir.sharif.ap2021.shared.Model.User;

public class NewGroupResponse extends Response {

    private String order;
    private User myUser;

    public NewGroupResponse(String order) {
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

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.doNewGroup(this);
    }


}
