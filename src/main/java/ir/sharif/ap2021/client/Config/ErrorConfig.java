package ir.sharif.ap2021.client.Config;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ErrorConfig {

    MainConfig mainConfig = new MainConfig();


    private String messageLength;
    private String enterFirst;
    private String enterLast;
    private String enterUser;
    private String enterEmail;
    private String validEmail;
    private String validPhone;
    private String profileChanged;
    private String messageSent;
    private String enterMessage;
    private String enterPassword;
    private String emptyBlacklist;
    private String searchYourself;
    private String emptyFollower;
    private String emptyFollowing;
    private String matchPassword;
    private String enterBoth;
    private String passwordChanged;
    private String lastSeenChanged;
    private String privacyChanged;
    private String sureDelete;
    private String activityChanged;
    private String groupCreated;
    private String enterName;
    private String thoughtLength;
    private String noMoreBack;
    private String enterRepeat;
    private String editForwarded;
    private String alreadyBlocked;
    private String followsYou;
    private String shareTitle;
    private String replyTitle;
    private String alreadyMuted;
    private String muted;
    private String yourThought;
    private String noOpinion;
    private String opinionLength;
    private String followBefore;
    private String blocked;
    private String reported;
    private String requestSent;
    private String emailExists;
    private String userExists;
    private String noUserExists;
    private String incorrectPass;
    private String followedYou;
    private String youFollowed;
    private String rejectedYou;
    private String youRejected;
    private String youSilentRejected;
    private String unfollowedYou;
    private String youUnfollowed;
    private String alreadyRequested;
    private String requested;
    private String savedMessages;
    private String lastSeenRecently;
    private String spammed;
    private String deactiveUser;
    private String noServer;
    private String lefTheGroup;
    private String scheduled;
    private String unblocked;
    private String shutdownServer;
    private String selfMute;
    private String noHyperlink;
    private String joinGroup;
    private String alreadyLeftGp;
    private String thoughtCreated;
    private String invalidHour;
    private String invalidMinuts;
    private String timeMachine;


    public ErrorConfig() throws IOException {
        setProperties();
    }


    private void setProperties() throws IOException {

        Properties properties = new Properties();
        FileReader fileReader = new FileReader(mainConfig.getErrorConfigPath());
        properties.load(fileReader);

        messageLength = properties.getProperty("messageLength");
        enterFirst = properties.getProperty("enterFirst");
        enterLast = properties.getProperty("enterLast");
        enterUser = properties.getProperty("enterUser");
        enterEmail = properties.getProperty("enterEmail");
        validEmail = properties.getProperty("validEmail");
        validPhone = properties.getProperty("validPhone");
        profileChanged = properties.getProperty("profileChanged");
        messageSent = properties.getProperty("messageSent");
        enterMessage = properties.getProperty("enterMessage");
        enterPassword = properties.getProperty("enterPassword");
        emptyBlacklist = properties.getProperty("emptyBlacklist");
        searchYourself = properties.getProperty("searchYourself");
        emptyFollower = properties.getProperty("emptyFollower");
        enterBoth = properties.getProperty("enterBoth");
        passwordChanged = properties.getProperty("passwordChanged");
        lastSeenChanged = properties.getProperty("lastSeenChanged");
        privacyChanged = properties.getProperty("privacyChanged");
        sureDelete = properties.getProperty("sureDelete");
        matchPassword = properties.getProperty("matchPassword");
        activityChanged = properties.getProperty("activityChanged");
        groupCreated = properties.getProperty("groupCreated");
        enterName = properties.getProperty("enterName");
        thoughtLength = properties.getProperty("thoughtLength");
        noMoreBack = properties.getProperty("noMoreBack");
        enterRepeat = properties.getProperty("enterRepeat");
        editForwarded = properties.getProperty("editForwarded");
        alreadyBlocked = properties.getProperty("alreadyBlocked");
        followsYou = properties.getProperty("followsYou");
        emptyFollowing = properties.getProperty("emptyFollowing");
        opinionLength = properties.getProperty("opinionLength");
        shareTitle = properties.getProperty("shareTitle");
        replyTitle = properties.getProperty("replyTitle");
        alreadyMuted = properties.getProperty("alreadyMuted");
        muted = properties.getProperty("muted");
        yourThought = properties.getProperty("yourThought");
        noOpinion = properties.getProperty("noOpinion");
        followBefore = properties.getProperty("followBefore");
        blocked = properties.getProperty("blocked");
        reported = properties.getProperty("reported");
        requestSent = properties.getProperty("requestSent");
        emailExists = properties.getProperty("emailExists");
        userExists = properties.getProperty("userExists");
        noUserExists = properties.getProperty("noUserExists");
        incorrectPass = properties.getProperty("incorrectPass");
        followedYou = properties.getProperty("followedYou");
        youFollowed = properties.getProperty("youFollowed");
        rejectedYou = properties.getProperty("rejectedYou");
        youRejected = properties.getProperty("youRejected");
        youSilentRejected = properties.getProperty("youSilentRejected");
        unfollowedYou = properties.getProperty("unfollowedYou");
        youUnfollowed = properties.getProperty("youUnfollowed");
        alreadyRequested = properties.getProperty("alreadyRequested");
        requested = properties.getProperty("requested");
        savedMessages = properties.getProperty("savedMessages");
        lastSeenRecently = properties.getProperty("lastSeenRecently");
        spammed = properties.getProperty("spammed");
        deactiveUser = properties.getProperty("deactiveUser");
        noServer = properties.getProperty("noServer");
        lefTheGroup = properties.getProperty("lefTheGroup");
        scheduled = properties.getProperty("scheduled");
        unblocked = properties.getProperty("unblocked");
        shutdownServer = properties.getProperty("shutdownServer");
        selfMute = properties.getProperty("selfMute");
        noHyperlink = properties.getProperty("noHyperlink");
        joinGroup = properties.getProperty("joinGroup");
        alreadyLeftGp = properties.getProperty("alreadyLeftGp");
        thoughtCreated = properties.getProperty("thoughtCreated");
        invalidHour = properties.getProperty("invalidHour");
        invalidMinuts = properties.getProperty("invalidMinuts");
        timeMachine = properties.getProperty("timeMachine");

    }

    public MainConfig getMainConfig() {
        return mainConfig;
    }

    public String getMessageLength() {
        return messageLength;
    }

    public String getEnterFirst() {
        return enterFirst;
    }

    public String getEnterLast() {
        return enterLast;
    }

    public String getEnterUser() {
        return enterUser;
    }

    public String getEnterEmail() {
        return enterEmail;
    }

    public String getValidEmail() {
        return validEmail;
    }

    public String getValidPhone() {
        return validPhone;
    }

    public String getProfileChanged() {
        return profileChanged;
    }

    public String getMessageSent() {
        return messageSent;
    }

    public String getEnterMessage() {
        return enterMessage;
    }

    public String getEnterPassword() {
        return enterPassword;
    }

    public String getEmptyBlacklist() {
        return emptyBlacklist;
    }

    public String getSearchYourself() {
        return searchYourself;
    }

    public String getEmptyFollower() {
        return emptyFollower;
    }

    public String getMatchPassword() {
        return matchPassword;
    }

    public String getEnterBoth() {
        return enterBoth;
    }

    public String getPasswordChanged() {
        return passwordChanged;
    }

    public String getLastSeenChanged() {
        return lastSeenChanged;
    }

    public String getPrivacyChanged() {
        return privacyChanged;
    }

    public String getSureDelete() {
        return sureDelete;
    }

    public String getActivityChanged() {
        return activityChanged;
    }

    public String getGroupCreated() {
        return groupCreated;
    }

    public String getEnterName() {
        return enterName;
    }

    public String getThoughtLength() {
        return thoughtLength;
    }

    public String getNoMoreBack() {
        return noMoreBack;
    }

    public String getEnterRepeat() {
        return enterRepeat;
    }

    public String getEditForwarded() {
        return editForwarded;
    }

    public String getAlreadyBlocked() {
        return alreadyBlocked;
    }

    public String getFollowsYou() {
        return followsYou;
    }

    public String getEmptyFollowing() {
        return emptyFollowing;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public String getReplyTitle() {
        return replyTitle;
    }

    public String getAlreadyMuted() {
        return alreadyMuted;
    }

    public String getMuted() {
        return muted;
    }

    public String getYourThought() {
        return yourThought;
    }

    public String getNoOpinion() {
        return noOpinion;
    }

    public String getOpinionLength() {
        return opinionLength;
    }

    public String getFollowBefore() {
        return followBefore;
    }

    public String getBlocked() {
        return blocked;
    }

    public String getReported() {
        return reported;
    }

    public String getRequestSent() {
        return requestSent;
    }

    public String getEmailExists() {
        return emailExists;
    }

    public String getUserExists() {
        return userExists;
    }

    public String getNoUserExists() {
        return noUserExists;
    }

    public String getIncorrectPass() {
        return incorrectPass;
    }

    public String getFollowedYou() {
        return followedYou;
    }

    public String getYouFollowed() {
        return youFollowed;
    }

    public String getRejectedYou() {
        return rejectedYou;
    }

    public String getYouRejected() {
        return youRejected;
    }

    public String getYouSilentRejected() {
        return youSilentRejected;
    }

    public String getUnfollowedYou() {
        return unfollowedYou;
    }

    public String getYouUnfollowed() {
        return youUnfollowed;
    }

    public String getAlreadyRequested() {
        return alreadyRequested;
    }

    public String getRequested() {
        return requested;
    }

    public String getSavedMessages() {
        return savedMessages;
    }

    public String getLastSeenRecently() {
        return lastSeenRecently;
    }

    public String getSpammed() {
        return spammed;
    }

    public String getDeactiveUser() {
        return deactiveUser;
    }

    public String getNoServer() {
        return noServer;
    }

    public String getLefTheGroup() {
        return lefTheGroup;
    }

    public String getScheduled() {
        return scheduled;
    }

    public String getUnblocked() {
        return unblocked;
    }

    public String getShutdownServer() {
        return shutdownServer;
    }

    public String getSelfMute() {
        return selfMute;
    }

    public String getNoHyperlink() {
        return noHyperlink;
    }

    public String getJoinGroup() {
        return joinGroup;
    }

    public String getAlreadyLeftGp() {
        return alreadyLeftGp;
    }

    public String getThoughtCreated() {
        return thoughtCreated;
    }

    public String getInvalidHour() {
        return invalidHour;
    }

    public String getInvalidMinuts() {
        return invalidMinuts;
    }

    public String getTimeMachine() {
        return timeMachine;
    }
}
