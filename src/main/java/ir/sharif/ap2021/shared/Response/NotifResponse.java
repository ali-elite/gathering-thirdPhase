package ir.sharif.ap2021.shared.Response;

import ir.sharif.ap2021.shared.Model.Notification;
import ir.sharif.ap2021.shared.Model.User;

import java.util.ArrayList;
import java.util.List;

public class NotifResponse extends Response {

    private String order;
    private List<Notification> notifications;
    private User myUser;
    private User outUser;
    private Notification myNotif;

    public NotifResponse(String order) {
        this.order = order;
        notifications = new ArrayList<>();
    }


    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public List<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Notification> notifications) {
        this.notifications = notifications;
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

    public Notification getMyNotif() {
        return myNotif;
    }

    public void setMyNotif(Notification myNotif) {
        this.myNotif = myNotif;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.doNotification(this);
    }
}
