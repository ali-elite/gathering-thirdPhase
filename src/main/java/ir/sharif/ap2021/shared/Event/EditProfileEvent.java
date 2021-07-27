package ir.sharif.ap2021.shared.Event;

import java.time.LocalDate;

public class EditProfileEvent extends Event {

    private String first;
    private String last;
    private String user;
    private String phone;
    private String email;
    private String bio;
    private byte[] avatar;
    private LocalDate birthday;

    public EditProfileEvent(String first, String last, String user, String phone, String email, String bio, LocalDate birthday) {
        this.first = first;
        this.last = last;
        this.user = user;
        this.phone = phone;
        this.email = email;
        this.bio = bio;
        this.birthday = birthday;
    }

    public String getFirst() {
        return first;
    }

    public String getLast() {
        return last;
    }

    public String getUser() {
        return user;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public String getBio() {
        return bio;
    }

    public byte[] getAvatar() {
        return avatar;
    }

    public void setAvatar(byte[] avatar) {
        this.avatar = avatar;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    @Override
    public void visit(EventVisitor eventVisitor) {
        eventVisitor.editProfile(this);
    }
}
