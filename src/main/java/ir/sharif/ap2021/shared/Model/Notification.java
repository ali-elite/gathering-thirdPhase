package ir.sharif.ap2021.shared.Model;


import ir.sharif.ap2021.shared.Util.SaveAble;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "notifications_table")
public class Notification implements SaveAble {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinTable(name = "notification_sender")
    private User sender;

    @ManyToOne
    @JoinTable(name = "notification_receiver")
    private User receiver;

    private boolean isAnswered;
    private boolean isRequest;
    private String text;


    public Notification(boolean isRequest, User sender, User receiver, String text) {

        this.sender = sender;
        this.receiver = receiver;
        this.text = text;
        this.isRequest = isRequest;
        isAnswered = false;

    }


    public Notification() {
        isAnswered = false;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public boolean isAnswered() {
        return isAnswered;
    }

    public void setAnswered(boolean answered) {
        isAnswered = answered;
    }

    public boolean isRequest() {
        return isRequest;
    }

    public void setRequest(boolean request) {
        isRequest = request;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
