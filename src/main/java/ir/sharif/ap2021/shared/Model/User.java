package ir.sharif.ap2021.shared.Model;


import ir.sharif.ap2021.shared.Util.SaveAble;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users_table")
public class User implements SaveAble {

    @Column
    private String firstName;
    @Column
    private String lastName;


    private String userName;

    private String biography;
    private LocalDate birthday;
    private String email;
    private String phoneNumber;
    private String password;
    private byte[] avatar;
    private String LastSeenPrivacy;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;


    private boolean isActive;
    private boolean isPrivate;
    private boolean isDeleted;
    private int reportedTimes;
    private LocalDateTime lastSeen;

    @ElementCollection
    @JoinTable(name = "user_thoughts")
    private List<Integer> thoughts;

    @ElementCollection
    @JoinTable(name = "user_followers")
    private List<Integer> followers;

    @ElementCollection
    @JoinTable(name = "user_followings")
    private List<Integer> followings;

    @ElementCollection
    @JoinTable(name = "user_blacklist")
    private List<Integer> blackList;

    @ElementCollection
    @JoinTable(name = "user_muteList")
    private List<Integer> muteList;

    @ElementCollection
    @JoinTable(name = "user_notifications")
    private List<Integer> notifications;

    @ElementCollection
    @JoinTable(name = "user_chats")
    private List<Integer> chats;


    public User(String firstName, String lastName, String userName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.password = password;
        lastSeen = LocalDateTime.now();
        LastSeenPrivacy = "Public";
        isActive = true;
        isPrivate = false;
        isDeleted = false;
        thoughts = new ArrayList<>();
        followers = new ArrayList<>();
        followings = new ArrayList<>();
        blackList = new ArrayList<>();
        notifications = new ArrayList<>();
        muteList = new ArrayList<>();
        chats = new ArrayList<>();
    }

    public User() {
        lastSeen = LocalDateTime.now();
        LastSeenPrivacy = "Public";
        isActive = true;
        isPrivate = false;
        isDeleted = false;
        thoughts = new ArrayList<>();
        followers = new ArrayList<>();
        followings = new ArrayList<>();
        blackList = new ArrayList<>();
        notifications = new ArrayList<>();
        muteList = new ArrayList<>();
        chats = new ArrayList<>();
    }



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getBiography() {
        return biography;
    }

    public void setBiography(String biography) {
        this.biography = biography;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastSeenPrivacy() {
        return LastSeenPrivacy;
    }

    public void setLastSeenPrivacy(String lastSeenPrivacy) {
        LastSeenPrivacy = lastSeenPrivacy;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public boolean isPrivate() {
        return isPrivate;
    }

    public void setPrivate(boolean aPrivate) {
        isPrivate = aPrivate;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public int getReportedTimes() {
        return reportedTimes;
    }

    public void setReportedTimes(int reportedTimes) {
        this.reportedTimes = reportedTimes;
    }

    public LocalDateTime getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(LocalDateTime lastSeen) {
        this.lastSeen = lastSeen;
    }

    public List<Integer> getThoughts() {
        return thoughts;
    }

    public void setThoughts(List<Integer> thoughts) {
        this.thoughts = thoughts;
    }

    public List<Integer> getFollowers() {
        return followers;
    }

    public void setFollowers(List<Integer> followers) {
        this.followers = followers;
    }

    public List<Integer> getFollowings() {
        return followings;
    }

    public void setFollowings(List<Integer> followings) {
        this.followings = followings;
    }

    public List<Integer> getBlackList() {
        return blackList;
    }

    public void setBlackList(List<Integer> blackList) {
        this.blackList = blackList;
    }

    public List<Integer> getMuteList() {
        return muteList;
    }

    public void setMuteList(List<Integer> muteList) {
        this.muteList = muteList;
    }

    public List<Integer> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<Integer> notifications) {
        this.notifications = notifications;
    }

    public List<Integer> getChats() {
        return chats;
    }

    public void setChats(List<Integer> chats) {
        this.chats = chats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userName.equals(user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", userName='" + userName + '\'' +
                ", biography='" + biography + '\'' +
                ", birthday=" + birthday +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", password='" + password + '\'' +
                ", avatar=" + Arrays.toString(avatar) +
                ", LastSeenPrivacy='" + LastSeenPrivacy + '\'' +
                ", id=" + id +
                ", isActive=" + isActive +
                ", isPrivate=" + isPrivate +
                ", isDeleted=" + isDeleted +
                ", reportedTimes=" + reportedTimes +
                ", lastSeen=" + lastSeen +
                ", thoughts=" + thoughts +
                ", followers=" + followers +
                ", followings=" + followings +
                ", blackList=" + blackList +
                ", muteList=" + muteList +
                ", notifications=" + notifications +
                ", chats=" + chats +
                '}';
    }
}