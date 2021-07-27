package ir.sharif.ap2021.server.Config;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class FxmlConfig {

    private MainConfig mainConfig = new MainConfig();

    private String app;
    private String blacklist;
    private String chat;
    private String chatForward;
    private String chatmenu;
    private String editProfile;
    private String forwardSelection;
    private String groupMessage;
    private String login;
    private String mainmenu;
    private String message;
    private String newGroup;
    private String newThought;
    private String notif;
    private String notifications;
    private String opinions;
    private String outProfile;
    private String profile;
    private String signup;
    private String thought;
    private String timeline;
    private String userSelection;



    public FxmlConfig() throws IOException {
        setProperties();
    }



    private void setProperties() throws IOException {

        FileReader fileReader = new FileReader(mainConfig.getFxmlConfigPath());
        Properties properties = new Properties();
        properties.load(fileReader);

        app = properties.getProperty("app");
        blacklist = properties.getProperty("blacklist");
        chat = properties.getProperty("chat");
        chatForward = properties.getProperty("chatForward");
        chatmenu = properties.getProperty("chatmenu");
        editProfile = properties.getProperty("editProfile");
        forwardSelection = properties.getProperty("forwardSelection");
        groupMessage = properties.getProperty("groupMessage");
        login = properties.getProperty("login");
        mainmenu = properties.getProperty("mainmenu");
        message = properties.getProperty("message");
        newGroup = properties.getProperty("newGroup");
        newThought = properties.getProperty("newThought");
        notif = properties.getProperty("notif");
        notifications = properties.getProperty("notifications");
        opinions = properties.getProperty("opinions");
        outProfile = properties.getProperty("outProfile");
        profile = properties.getProperty("profile");
        signup = properties.getProperty("signup");
        thought = properties.getProperty("thought");
        timeline = properties.getProperty("timeline");
        userSelection = properties.getProperty("userSelection");

    }


    public MainConfig getMainConfig() {
        return mainConfig;
    }

    public String getApp() {
        return app;
    }

    public String getBlacklist() {
        return blacklist;
    }

    public String getChat() {
        return chat;
    }

    public String getChatForward() {
        return chatForward;
    }

    public String getChatmenu() {
        return chatmenu;
    }

    public String getEditProfile() {
        return editProfile;
    }

    public String getForwardSelection() {
        return forwardSelection;
    }

    public String getGroupMessage() {
        return groupMessage;
    }

    public String getLogin() {
        return login;
    }

    public String getMainmenu() {
        return mainmenu;
    }

    public String getMessage() {
        return message;
    }

    public String getNewGroup() {
        return newGroup;
    }

    public String getNotif() {
        return notif;
    }

    public String getNotifications() {
        return notifications;
    }

    public String getOpinions() {
        return opinions;
    }

    public String getOutProfile() {
        return outProfile;
    }

    public String getProfile() {
        return profile;
    }

    public String getSignup() {
        return signup;
    }

    public String getThought() {
        return thought;
    }

    public String getTimeline() {
        return timeline;
    }

    public String getUserSelection() {
        return userSelection;
    }

    public String getNewThought() {
        return newThought;
    }
}
