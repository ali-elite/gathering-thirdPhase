package ir.sharif.ap2021.server.Controller.Helper;

import ir.sharif.ap2021.server.Controller.ClientHandler;
import ir.sharif.ap2021.server.Hibernate.Connector;
import ir.sharif.ap2021.server.Hibernate.DatabaseDisconnectException;
import ir.sharif.ap2021.shared.Event.MainMenuEvent;
import ir.sharif.ap2021.shared.Model.Chat;
import ir.sharif.ap2021.shared.Model.Thought;
import ir.sharif.ap2021.shared.Model.User;
import ir.sharif.ap2021.shared.Response.MainMenuResponse;
import ir.sharif.ap2021.shared.Response.Response;

import java.time.LocalDateTime;
import java.util.List;

public class MainMenuController {

    MainMenuEvent mainMenuEvent;
    int id;
    String order;
    Connector connector;
    ClientHandler clientHandler;

    public MainMenuController(MainMenuEvent mainMenuEvent, ClientHandler clientHandler, Connector connector) {
        this.mainMenuEvent = mainMenuEvent;
        this.id = mainMenuEvent.getId();
        this.order = mainMenuEvent.getOrder();
        this.clientHandler = clientHandler;
        this.connector = connector;
    }


    public Response answer() throws DatabaseDisconnectException {
        User user;
        if (id == 0) {
            List<User> us = connector.fetchUserWithUsername(mainMenuEvent.getUsername());
            while (us.isEmpty()) {
                us = connector.fetchUserWithUsername(mainMenuEvent.getUsername());
            }
            user = us.get(0);

        } else
            user = connector.fetch(User.class, id);

        if (order.equals("load")) {
            MainMenuResponse loadResponse = new MainMenuResponse("load");
            loadResponse.setUser(user);
            return loadResponse;
        }

        if (order.equals("loadUser")) {
            MainMenuResponse loadResponse = new MainMenuResponse("loadUser");
            loadResponse.setUser(user);
            return loadResponse;
        }

        if (order.equals("gatherThought")) {
            MainMenuResponse gatherResponse = new MainMenuResponse("gatherThought");
            List<Thought> thoughts = connector.fetchThoughtWithUserId(id);
            gatherResponse.getThoughts().addAll(thoughts);
            return gatherResponse;
        }

        if (order.equals("timeLineThought")) {
            MainMenuResponse timeLineResponse = new MainMenuResponse("timeLineThought");
            List<Thought> temp = timeLineResponse.getThoughts();

            for (Integer i : user.getThoughts()) {
                Thought t = connector.fetch(Thought.class, i);
                if (t.getLocalDateTime().isAfter(LocalDateTime.now().minusDays(1))
                        && t.getSpamReports() < 20) {
                    temp.add(t);
                }
            }

            for (Integer i : user.getFollowers()) {
                User follower = connector.fetch(User.class, i);
                for (Integer j : follower.getThoughts()) {
                    Thought t = connector.fetch(Thought.class, j);
                    if (t.getLocalDateTime().isAfter(LocalDateTime.now().minusDays(1))
                            && t.getSpamReports() < 20 && !user.getMuteList().contains(t.getUserId())) {
                        temp.add(t);
                    }
                }
            }

            for (Integer i : user.getFollowings()) {
                User following = connector.fetch(User.class, i);
                for (Integer j : following.getThoughts()) {
                    Thought t = connector.fetch(Thought.class, j);
                    if (t.getLocalDateTime().isAfter(LocalDateTime.now().minusDays(1))
                            && t.getSpamReports() < 20 && !temp.contains(t) && !user.getMuteList().contains(t.getUserId())) {
                        temp.add(t);
                    }
                }
            }

            return timeLineResponse;
        }

        if (order.equals("exploreThought")) {
            MainMenuResponse exploreResponse = new MainMenuResponse("exploreThought");
            List<Thought> thoughts = connector.fetchAll(Thought.class);

            if (!thoughts.isEmpty())
                exploreResponse.getThoughts().add(thoughts.get(0));

            return exploreResponse;
        }


        if (order.equals("chats")) {
            MainMenuResponse chatsResponse = new MainMenuResponse("chats");

            List<Chat> chats = connector.fetchAll(Chat.class);
            List<Chat> chatTemp = chatsResponse.getChats();
//            List<User> chatUsers = chatsResponse.getUsers();

            for (Chat chat : chats) {
                if (chat.getUsers().contains(user)) {
                    chatTemp.add(chat);
//                    if (!chat.isGroup()) {
//                        for (User u : chat.getUsers()) {
//                            if (!u.equals(clientHandler.user)) {
//                                chatUsers.add(u);
//                            }
//                        }
//                    }
                }
            }

            return chatsResponse;
        }

        if (order.equals("group")) {
            MainMenuResponse groupResponse = new MainMenuResponse("group");

            for (Integer i : user.getFollowers()) {
                User u = connector.fetch(User.class, i);
                groupResponse.getUsernames().add(u.getUserName());
            }

            return groupResponse;

        }

        if (order.equals("logOut")) {
            if (clientHandler.user != null) {
                connector.save(clientHandler.user);
                clientHandler.user = null;
                return new MainMenuResponse("logOut");
            }
        }

        if (order.equals("forwards")) {

            MainMenuResponse forwardsResponse = new MainMenuResponse("forwards");

            List<Chat> chats = connector.fetchAll(Chat.class);
            List<Chat> chatTemp = forwardsResponse.getChats();

            for (Chat chat : chats) {
                if (chat.getUsers().contains(user)) {
                    chatTemp.add(chat);
                }
            }

            return forwardsResponse;
        }

        if (order.equals("groupMessage")) {

            MainMenuResponse groupMessageResponse = new MainMenuResponse("groupMessage");

            for (Integer i : user.getFollowers()) {
                User u = connector.fetch(User.class, i);
                groupMessageResponse.getUsernames().add(u.getUserName());
            }

            return groupMessageResponse;
        }

        if (order.equals("blacklist")) {

            MainMenuResponse blacklistResponse = new MainMenuResponse("blacklist");

            for (Integer i : user.getBlackList()) {
                User u = connector.fetch(User.class, i);
                blacklistResponse.getUsernames().add(u.getUserName());
            }

            return blacklistResponse;
        }

        if (order.equals("changePassword")) {

            user.setPassword(mainMenuEvent.getPassword());
            connector.save(user);
            MainMenuResponse response = new MainMenuResponse("changePassword");
            response.setUser(user);

            return response;
        }

        if (order.equals("lastSeen")) {

            user.setLastSeenPrivacy(mainMenuEvent.getLastSeen());
            connector.save(user);
            MainMenuResponse response = new MainMenuResponse("lastSeen");
            response.setUser(user);

            return response;
        }

        if (order.equals("changeActivity")) {

            user.setActive(mainMenuEvent.isDiactive());
            connector.save(user);

            MainMenuResponse response = new MainMenuResponse("changeActivity");
            response.setUser(user);

            return response;
        }

        if (order.equals("changePrivacy")) {

            user.setPrivate(mainMenuEvent.isDiactive());
            connector.save(user);

            MainMenuResponse response = new MainMenuResponse("changePrivacy");
            response.setUser(user);

            return response;
        }

        if (order.equals("delete")) {

            user.setPrivate(mainMenuEvent.isDiactive());
            connector.delete(user);

            return new MainMenuResponse("delete");
        }

        if (order.equals("unblock")) {
            user.getBlackList().remove((Integer) connector.fetchUserWithUsername(mainMenuEvent.getSelectedUser()).get(0).getId());
            connector.save(user);
            MainMenuResponse response = new MainMenuResponse("unblock");
            response.setUser(user);
            return response;
        }


        return null;
    }

}