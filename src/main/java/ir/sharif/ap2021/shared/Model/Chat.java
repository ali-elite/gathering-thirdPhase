package ir.sharif.ap2021.shared.Model;


import ir.sharif.ap2021.shared.Util.SaveAble;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "chats_table")
public class Chat implements SaveAble {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean isGroup;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(name = "chat_users", joinColumns = @JoinColumn(name = "chat_users"))
    List<User> users = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "chat_messages", joinColumns = @JoinColumn(name = "chat_messages"))
    List<Message> messages = new ArrayList<>();

    public Chat(String name, boolean isGroup) {

        this.isGroup = isGroup;
        this.name = name;

    }


    public Chat() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isGroup() {
        return isGroup;
    }

    public void setGroup(boolean group) {
        isGroup = group;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Chat chat = (Chat) o;
        return id == chat.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
