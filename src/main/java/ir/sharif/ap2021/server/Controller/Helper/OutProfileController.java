package ir.sharif.ap2021.server.Controller.Helper;

import com.mysql.cj.xdevapi.Client;
import ir.sharif.ap2021.server.Config.ErrorConfig;
import ir.sharif.ap2021.server.Controller.ClientHandler;
import ir.sharif.ap2021.server.Hibernate.Connector;
import ir.sharif.ap2021.server.Hibernate.DatabaseDisconnectException;
import ir.sharif.ap2021.shared.Event.OutProfileEvent;
import ir.sharif.ap2021.shared.Model.Chat;
import ir.sharif.ap2021.shared.Model.Notification;
import ir.sharif.ap2021.shared.Model.User;
import ir.sharif.ap2021.shared.Response.OutProfileResponse;
import ir.sharif.ap2021.shared.Response.Response;

import java.io.IOException;

public class OutProfileController {

    private Connector connector;
    private ClientHandler clientHandler;
    private OutProfileEvent event;
    private ErrorConfig errorConfig;

    public OutProfileController(OutProfileEvent outProfileEvent, Connector connector, ClientHandler clientHandler) throws IOException {
        this.event = outProfileEvent;
        this.connector = connector;
        this.clientHandler = clientHandler;
        errorConfig = new ErrorConfig();
    }


    public Response answer() throws DatabaseDisconnectException {

        if (event.getOrder().equals("block")) {

            User user = connector.fetch(User.class, clientHandler.user.getId());
            User follower = connector.fetch(User.class, event.getUserid());
            OutProfileResponse response = new OutProfileResponse("block");

            if (!user.getBlackList().contains(follower.getId())) {

                user.getBlackList().add(follower.getId());

                if (user.getFollowers().contains(follower.getId())) {
                    user.getFollowers().remove((Integer) follower.getId());
                }

                if (user.getFollowings().contains(follower.getId())) {
                    user.getFollowings().remove((Integer) follower.getId());
                }


                if (follower.getFollowers().contains(user.getId())) {
                    follower.getFollowers().remove((Integer) user.getId());
                }

                if (follower.getFollowings().contains(user.getId())) {
                    follower.getFollowings().remove((Integer) user.getId());
                }

            }

            connector.save(user);
            connector.save(follower);

            response.setMyUser(user);
            response.setOutUser(follower);

            return response;
        }


        if (event.getOrder().equals("follow")) {

            User user = connector.fetch(User.class, clientHandler.user.getId());
            User follower = connector.fetch(User.class, event.getUserid());
            OutProfileResponse response = new OutProfileResponse("follow");

            if (user.getFollowings().contains(follower.getId())) {

                user.getFollowings().remove((Integer) follower.getId());
                follower.getFollowers().remove((Integer) user.getId());

                Notification unfollowedYou = new Notification(false, user, follower, user.getUserName() + " " + errorConfig.getUnfollowedYou());
                connector.save(unfollowedYou);
                Notification savedUnfollowedYou = null;
                while (savedUnfollowedYou == null) {
                    savedUnfollowedYou = connector.fetchNotification(unfollowedYou);
                }
                follower.getNotifications().add(savedUnfollowedYou.getId());


                Notification youUnfollowed = new Notification(false, follower, user, errorConfig.getYouUnfollowed() + " " + follower.getUserName());
                connector.save(youUnfollowed);
                Notification savedYouUnfollowed = null;
                while (savedYouUnfollowed == null) {
                    savedYouUnfollowed = connector.fetchNotification(youUnfollowed);
                }
                user.getNotifications().add(savedYouUnfollowed.getId());


                connector.save(user);
                connector.save(follower);

                response.setRequest(false);
                response.setUnfollow(true);
                response.setRepeat(false);
                response.setMyUser(user);
                response.setOutUser(follower);
                return response;

            } else {

                if (follower.isPrivate()) {

                    for (Integer i : follower.getNotifications()) {

                        Notification notification = connector.fetch(Notification.class, i);
                        if (notification.getSender().getId() == user.getId() && !notification.isAnswered() && notification.isRequest()) {

                            response.setUnfollow(false);
                            response.setRepeat(true);
                            response.setRequest(false);
                            return response;
                        }


                    }

                    Notification n = new Notification(true, user, follower, user.getUserName() + " " + errorConfig.getRequested());
                    connector.save(n);
                    Notification savedN = null;
                    while (savedN == null) {
                        savedN = connector.fetchNotification(n);
                    }
                    follower.getNotifications().add(savedN.getId());


                    connector.save(user);
                    connector.save(follower);


                    response.setUnfollow(false);
                    response.setRepeat(false);
                    response.setRequest(true);
                    response.setMyUser(user);
                    response.setOutUser(follower);

                    return response;


                } else {

                    user.getFollowings().add(follower.getId());
                    follower.getFollowers().add(user.getId());


                    Notification followedYou = new Notification(false, user, follower, user.getUserName() + " " + errorConfig.getFollowedYou());
                    connector.save(followedYou);
                    Notification savedFollowedYou = null;
                    while (savedFollowedYou == null)
                        savedFollowedYou = connector.fetchNotification(followedYou);

                    follower.getNotifications().add(savedFollowedYou.getId());

                    Notification youFollowed = new Notification(false, follower, user, errorConfig.getYouFollowed() + " " + follower.getUserName());
                    connector.save(youFollowed);
                    Notification savedYouFollowed = null;
                    while (savedYouFollowed == null)
                        savedYouFollowed = connector.fetchNotification(youFollowed);

                    user.getNotifications().add(savedYouFollowed.getId());

                    connector.save(user);
                    connector.save(follower);

                    response.setUnfollow(true);
                    response.setRepeat(false);
                    response.setRequest(false);
                    response.setMyUser(user);
                    response.setOutUser(follower);

                    return response;
                }
            }
        }

        if (event.getOrder().equals("mute")) {

            User user = connector.fetch(User.class, clientHandler.user.getId());
            User follower = connector.fetch(User.class, event.getUserid());
            OutProfileResponse response = new OutProfileResponse("mute");

            if (user.getMuteList().contains(follower.getId())) {
                user.getMuteList().remove((Integer) follower.getId());
                response.setMessage("unMuted");
            } else {
                user.getMuteList().add(follower.getId());
                response.setMessage("muted");
            }

            connector.save(user);
            connector.save(follower);

            response.setMyUser(user);
            response.setOutUser(follower);

            return response;

        }

        if (event.getOrder().equals("report")) {

            User follower = connector.fetch(User.class, event.getUserid());
            follower.setReportedTimes(follower.getReportedTimes() + 1);
            connector.save(follower);

            return new OutProfileResponse("report");
        }

        if (event.getOrder().equals("message")) {

            OutProfileResponse response = new OutProfileResponse("message");
            User someUser = connector.fetch(User.class, event.getUserid());

            for (Integer z : clientHandler.user.getChats()) {

                Chat chat = connector.fetch(Chat.class, z);
                if (chat.getUsers().contains(clientHandler.user)
                        && chat.getUsers().contains(someUser)) {
                    response.setMessage("chat loaded");
                    response.setChat(chat);
                    return response;
                }

            }


            User user1 = connector.fetch(User.class, clientHandler.user.getId());
            User user2 = connector.fetch(User.class, event.getUserid());

            Chat chat = new Chat(user1.getUserName() + " with " + user2.getUserName(), false);
            chat.getUsers().add(user1);
            chat.getUsers().add(user2);
            connector.save(chat);
            Chat savedChat = connector.fetchChat(chat);
            while (savedChat == null)
                savedChat = connector.fetchChat(chat);

            user1.getChats().add(savedChat.getId());
            user2.getChats().add(savedChat.getId());


            connector.save(user1);
            connector.save(user2);

            response.setMessage("chat created");
            response.setChat(savedChat);
            response.setMyUser(user1);
            response.setOutUser(user2);
            return response;

        }


        return null;
    }
}
