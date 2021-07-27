package ir.sharif.ap2021.shared.Response;

import ir.sharif.ap2021.shared.Model.Chat;
import ir.sharif.ap2021.shared.Model.Message;
import ir.sharif.ap2021.shared.Model.Notification;
import ir.sharif.ap2021.shared.Model.Thought;
import ir.sharif.ap2021.shared.Model.User;

import java.util.ArrayList;

public class MainMenuResponse extends Response {

    private String order;
    private User user;
    private ArrayList<Thought> thoughts;
    private ArrayList<Notification> notifications;
    private ArrayList<Chat> chats;
    private ArrayList<String> usernames;
    private ArrayList<Message> messages;
    private ArrayList<User> thoughtOwners;
    private ArrayList<User> users;

    public MainMenuResponse(String order) {

        this.order = order;
        thoughts = new ArrayList<>();
        notifications = new ArrayList<>();
        chats = new ArrayList<>();
        usernames = new ArrayList<>();
        messages = new ArrayList<>();
        thoughtOwners = new ArrayList<>();
        users = new ArrayList<>();

    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ArrayList<Thought> getThoughts() {
        return thoughts;
    }

    public void setThoughts(ArrayList<Thought> thoughts) {
        this.thoughts = thoughts;
    }

    public ArrayList<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(ArrayList<Notification> notifications) {
        this.notifications = notifications;
    }

    public ArrayList<Chat> getChats() {
        return chats;
    }

    public void setChats(ArrayList<Chat> chats) {
        this.chats = chats;
    }

    public ArrayList<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(ArrayList<String> usernames) {
        this.usernames = usernames;
    }

    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void setMessages(ArrayList<Message> messages) {
        this.messages = messages;
    }

    public ArrayList<User> getThoughtOwners() {
        return thoughtOwners;
    }

    public void setThoughtOwners(ArrayList<User> thoughtOwners) {
        this.thoughtOwners = thoughtOwners;
    }

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.doMainMenu(this);
    }

}
