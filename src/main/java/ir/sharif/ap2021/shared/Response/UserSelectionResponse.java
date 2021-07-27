package ir.sharif.ap2021.shared.Response;

import ir.sharif.ap2021.shared.Model.User;

public class UserSelectionResponse extends Response {


    private String order;
    private String type;
    private boolean isExist;
    private String[] users;
    private User user;


    public UserSelectionResponse(String order) {
        this.order = order;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.doUserSelect(this);
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public boolean isExist() {
        return isExist;
    }

    public void setExist(boolean exist) {
        isExist = exist;
    }

    public String[] getUsers() {
        return users;
    }

    public void setUsers(String[] users) {
        this.users = users;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
