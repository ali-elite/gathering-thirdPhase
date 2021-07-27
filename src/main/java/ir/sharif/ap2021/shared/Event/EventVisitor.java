package ir.sharif.ap2021.shared.Event;

public interface EventVisitor {

    void exit();
    void login(String username,String password);
    void signup(String firstname,String lastname,String username,String email,String password);
    void doMainMenu(MainMenuEvent mainMenuEvent);
    void doChat(ChatEvent chatEvent);
    void doUserSelect(String order, String username, String type);
    void doOutProfile(OutProfileEvent outProfileEvent);
    void doNotification(NotifEvent notifEvent);
    void editProfile(EditProfileEvent editProfileEvent);
    void shareThought(ShareThoughtEvent shareThoughtEvent);
    void doNewGroup(NewGroupEvent newGroupEvent);
    void doThought(ThoughtEvent thoughtEvent);
    void doMessage(MessageEvent messageEvent);

}
