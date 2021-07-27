package ir.sharif.ap2021.shared.Model;

import ir.sharif.ap2021.shared.Util.SaveAble;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_categories_table")
public class UserCategory implements SaveAble {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne
    private User ownerUser;
    @ManyToMany
    private final List<User> users;
    private String name;

    public UserCategory(String name,User ownerUser) {

        this.name = name;
        this.ownerUser = ownerUser;
        users = new ArrayList<>();

    }

    public UserCategory() {
        users = new ArrayList<>();
    }

    public List<User> getUsers() {
        return users;
    }

    public String getName() {
        return name;
    }

    public User getOwnerUser() {
        return ownerUser;
    }

    public int getId() {
        return id;
    }


}
