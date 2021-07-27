package ir.sharif.ap2021.shared.Model;


import ir.sharif.ap2021.shared.Util.SaveAble;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "thoughts_table")
public class Thought implements SaveAble {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "thought_user")
    private User user;

    @ManyToOne
    @JoinColumn(name = "thought_doed")
    private User doed;

    private String username;
    private int userId;
    private int parent;
    private String type;
    private byte[] image;
    private int likes;
    private int spamReports;
    private String text;

    @ElementCollection
    @JoinTable(name = "thought_opinions")
    private final List<Integer> opinions;

    @ManyToMany
    @JoinTable(name = "thought_likers")
    private final List<User> likers;

    @ManyToMany
    @JoinTable(name = "thought_rethoughters")
    private final List<User> rethoughters;

    private LocalDateTime localDateTime;
    private long rethought;



    public Thought(String type, User user, User doed, String text, LocalDateTime localDateTime) {

        this.type = type;
        this.localDateTime = localDateTime;
        this.user = user;
        if (!type.equalsIgnoreCase("t")) {
            this.doed = doed;
        }
        this.text = text;

        username = user.getUserName();
        userId = user.getId();
        opinions = new ArrayList<>();
        likers = new ArrayList<>();
        rethoughters = new ArrayList<>();
    }

    public Thought() {

        opinions = new ArrayList<>();
        likers = new ArrayList<>();
        rethoughters = new ArrayList<>();

    }


    public void addRethought() {
        rethought++;
    }

    public void minusRethought() {
        if (rethought != 0) {
            rethought--;
        }
    }

    public void addLike() {
        likes++;
    }

    public void minusLike() {
        if (likes != 0) {
            likes--;
        }
    }

    public void addSpam() {
        spamReports++;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getDoed() {
        return doed;
    }

    public void setDoed(User doed) {
        this.doed = doed;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getSpamReports() {
        return spamReports;
    }

    public void setSpamReports(int spamReports) {
        this.spamReports = spamReports;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Integer> getOpinions() {
        return opinions;
    }

    public List<User> getLikers() {
        return likers;
    }

    public List<User> getRethoughters() {
        return rethoughters;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public long getRethought() {
        return rethought;
    }

    public void setRethought(long rethought) {
        this.rethought = rethought;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Thought thought = (Thought) o;
        return id == thought.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
