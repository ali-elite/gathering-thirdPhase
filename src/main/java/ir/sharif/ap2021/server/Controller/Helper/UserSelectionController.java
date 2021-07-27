package ir.sharif.ap2021.server.Controller.Helper;

import ir.sharif.ap2021.server.Controller.ClientHandler;
import ir.sharif.ap2021.server.Hibernate.Connector;
import ir.sharif.ap2021.server.Hibernate.DatabaseDisconnectException;
import ir.sharif.ap2021.shared.Model.User;
import ir.sharif.ap2021.shared.Response.Response;
import ir.sharif.ap2021.shared.Response.UserSelectionResponse;

import java.util.List;

public class UserSelectionController {

    String username;
    String order;
    String type;
    Connector connector;
    ClientHandler clientHandler;


    public UserSelectionController(Connector connector, ClientHandler clientHandler) {
        this.connector = connector;
        this.clientHandler = clientHandler;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Response answer() throws DatabaseDisconnectException {

        if (order.equals("load")) {
            UserSelectionResponse loadResponse = new UserSelectionResponse("load");
            List<User> selected = connector.fetchUserWithUsername(username);

            loadResponse.setExist(!selected.isEmpty());

            if (loadResponse.isExist()) {
                loadResponse.setUser(selected.get(0));
            }

            return loadResponse;
        }

        if (order.equals("follower")) {
            UserSelectionResponse followerResponse = new UserSelectionResponse("follower");

            User myUser = connector.fetchUserWithUsername(username).get(0);

            String[] users = new String[myUser.getFollowers().size()];

            for (int i = 0; i < myUser.getFollowers().size(); i++) {
                users[i] = connector.fetch(User.class,myUser.getFollowers().get(myUser.getFollowers().size() - 1 - i)).getUserName();
            }

            followerResponse.setUsers(users);

            return followerResponse;
        }


        if (order.equals("following")) {
            UserSelectionResponse followingResponse = new UserSelectionResponse("following");

            User myUser = connector.fetchUserWithUsername(username).get(0);

            String[] users = new String[myUser.getFollowings().size()];

            for (int i = 0; i < myUser.getFollowings().size(); i++) {
                users[i] = connector.fetch(User.class,myUser.getFollowings().get(myUser.getFollowings().size() - 1 - i)).getUserName();
            }

            followingResponse.setUsers(users);

            return followingResponse;
        }


        if (order.equals("normal")) {
            return new UserSelectionResponse("normal");
        }


        return null;
    }
}
