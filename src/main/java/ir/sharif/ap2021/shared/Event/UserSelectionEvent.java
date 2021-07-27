package ir.sharif.ap2021.shared.Event;


public class UserSelectionEvent extends Event {

    private String order;
    private String type;
    private String username;

    public UserSelectionEvent(String order, String username, String type) {
        this.order = order;
        this.username = username;
        this.type = type;
    }

    public String getOrder() {
        return order;
    }

    public String getUsername() {
        return username;
    }

    public String getType() {
        return type;
    }

    @Override
    public void visit(EventVisitor eventVisitor) {
        eventVisitor.doUserSelect(order,username,type);
    }
}
