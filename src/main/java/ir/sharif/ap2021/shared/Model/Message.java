package ir.sharif.ap2021.shared.Model;


import ir.sharif.ap2021.shared.Util.SaveAble;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "messages_table")
public class Message implements SaveAble {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private String text;

    @ManyToOne
    @JoinTable(name = "message_sender")
    private User sender;

    private byte[] image;
    private boolean isForwarded;
    private boolean isDeleted;
    private LocalDateTime time;

    @ManyToMany
    @JoinTable(name = "message_seen_users")
    private final List<User> seenUsers;


    public Message(User sender, boolean isForwarded, String text) {

        this.sender = sender;
        this.isForwarded = isForwarded;
        this.text = text;
        time = LocalDateTime.now();
        isDeleted = false;
        seenUsers = new ArrayList<>();

    }

    public Message() {
        time = LocalDateTime.now();
        isDeleted = false;
        seenUsers = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public boolean isForwarded() {
        return isForwarded;
    }

    public void setForwarded(boolean forwarded) {
        isForwarded = forwarded;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public List<User> getSeenUsers() {
        return seenUsers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Message message = (Message) o;
        return id == message.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
