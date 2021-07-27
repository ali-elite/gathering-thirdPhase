package ir.sharif.ap2021.shared.Response;

public interface ResponseVisitor {

    void exit();
    void login(boolean isEntered,String message);
    void signup(boolean isEntered, String message);
    void doMainMenu(MainMenuResponse mainMenuResponse);
    void doChat(ChatResponse chatResponse);
    void doUserSelect(UserSelectionResponse userSelectionResponse);
    void doOutProfile(OutProfileResponse outProfileResponse);
    void doNotification(NotifResponse notifResponse);
    void editProfile(EditProfileResponse editProfileResponse);
    void shareThought(ShareThoughtResponse shareThoughtResponse);
    void doNewGroup(NewGroupResponse response);
    void doThought(ThoughtResponse thoughtResponse);
    void doMessage(MessageResponse messageResponse);

}
