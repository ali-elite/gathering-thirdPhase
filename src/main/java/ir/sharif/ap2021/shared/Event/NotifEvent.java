package ir.sharif.ap2021.shared.Event;

public class NotifEvent extends Event {

    private String order;
    private int notificationId;

    public NotifEvent(String order) {
        this.order = order;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    @Override
    public void visit(EventVisitor eventVisitor) {
        eventVisitor.doNotification(this);
    }
}
