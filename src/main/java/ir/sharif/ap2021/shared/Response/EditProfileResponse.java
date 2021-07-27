package ir.sharif.ap2021.shared.Response;

import ir.sharif.ap2021.shared.Model.User;

public class EditProfileResponse extends Response {

    private boolean isChanged;
    private String message;
    private User myUser;

    public EditProfileResponse(boolean isChanged, String message) {
        this.isChanged = isChanged;
        this.message = message;
    }

    public boolean isChanged() {
        return isChanged;
    }

    public void setChanged(boolean changed) {
        isChanged = changed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getMyUser() {
        return myUser;
    }

    public void setMyUser(User myUser) {
        this.myUser = myUser;
    }

    @Override
    public void visit(ResponseVisitor responseVisitor) {
        responseVisitor.editProfile(this);
    }
}
