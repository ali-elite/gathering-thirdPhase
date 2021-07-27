package ir.sharif.ap2021.shared.Event;

import java.util.ArrayList;

public class NewGroupEvent extends Event {

    String order;
    ArrayList<String> users;
    String name;
    String changed;

    public NewGroupEvent(String order, ArrayList<String> users, String name) {
        this.order = order;
        this.users = users;
        this.name = name;
    }

    public String getOrder() {

        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public ArrayList<String> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<String> users) {
        this.users = users;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChanged() {
        return changed;
    }

    public void setChanged(String changed) {
        this.changed = changed;
    }

    @Override
    public void visit(EventVisitor eventVisitor) {
        eventVisitor.doNewGroup(this);
    }
}
