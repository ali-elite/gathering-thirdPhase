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
    @JoinTable(name = "message_sender", joinColumns = @JoinColumn(name = "message_sender"))
    private User sender;

    @Lob
    @Column(name = "image", columnDefinition = "MEDIUMBLOB")
    private byte[] image;
    private boolean isForwarded;
    private boolean isDeleted;
    private LocalDateTime time;

    @ManyToMany
    @JoinTable(name = "message_seen_users", joinColumns = @JoinColumn(name = "message_seen_users"))
    private final List<User> seenUsers;

    private boolean check1;
    private boolean check2;
    private boolean check3;


    public Message(User sender, boolean isForwarded, String text) {

        this.sender = sender;
        this.isForwarded = isForwarded;
        this.text = text;
        time = LocalDateTime.now();
        isDeleted = false;
        seenUsers = new ArrayList<>();
        check1 = true;

    }

    public Message() {
        time = LocalDateTime.now();
        isDeleted = false;
        seenUsers = new ArrayList<>();
        check1 = true;
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

    public boolean isCheck1() {
        return check1;
    }

    public void setCheck1(boolean check1) {
        this.check1 = check1;
    }

    public boolean isCheck2() {
        return check2;
    }

    public void setCheck2(boolean check2) {
        this.check2 = check2;
    }

    public boolean isCheck3() {
        return check3;
    }

    public void setCheck3(boolean check3) {
        this.check3 = check3;
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
