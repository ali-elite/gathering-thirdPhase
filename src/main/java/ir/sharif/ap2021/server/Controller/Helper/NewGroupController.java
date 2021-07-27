package ir.sharif.ap2021.server.Controller.Helper;

import ir.sharif.ap2021.server.Controller.ClientHandler;
import ir.sharif.ap2021.server.Hibernate.Connector;
import ir.sharif.ap2021.server.Hibernate.DatabaseDisconnectException;
import ir.sharif.ap2021.shared.Event.NewGroupEvent;
import ir.sharif.ap2021.shared.Model.Chat;
import ir.sharif.ap2021.shared.Model.Message;
import ir.sharif.ap2021.shared.Model.User;
import ir.sharif.ap2021.shared.Response.NewGroupResponse;
import ir.sharif.ap2021.shared.Response.Response;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class NewGroupController {

    private NewGroupEvent event;
    private Connector connector;
    private ClientHandler clientHandler;


    public NewGroupController(NewGroupEvent event, Connector connector, ClientHandler clientHandler) {
        this.event = event;
        this.connector = connector;
        this.clientHandler = clientHandler;
    }

    public Response answer() throws DatabaseDisconnectException {

        if (event.getOrder().equals("makeGroup")) {

            List<User> users = new ArrayList<>();
            User myUser = connector.fetch(User.class,clientHandler.user.getId());

            for (String s : event.getUsers()) {
                users.add(connector.fetchUserWithUsername(s).get(0));
            }

            Chat gp = new Chat(event.getName(), true);

            for (User user : users) {

                gp.getUsers().add(user);
                user.getChats().add(gp.getId());

                connector.save(user);
            }

            gp.getUsers().add(myUser);
            connector.save(gp);
            Chat savedGp = connector.fetchChat(gp);
            while (savedGp == null)
                savedGp = connector.fetchChat(gp);

            myUser.getChats().add(savedGp.getId());

            connector.save(myUser);

            NewGroupResponse response = new NewGroupResponse("makeGroup");
            response.setMyUser(myUser);
            return response;

        }

        if (event.getOrder().equals("groupMessage")) {

            List<User> users = new ArrayList<>();
            User myUser = connector.fetch(User.class,clientHandler.user.getId());

            for (String s : event.getUsers()) {
                users.add(connector.fetchUserWithUsername(s).get(0));
            }

            Chat gp = new Chat("This is a group message", true);

            Message message = new Message(myUser, false, event.getName());
            if(event.getImage() != null){
                message.setImage(event.getImage());
            }

            connector.save(message);

            gp.getMessages().add(message);

            for (User user : users) {

                gp.getUsers().add(user);
                user.getChats().add(gp.getId());

                connector.save(user);
            }

            gp.getUsers().add(myUser);
            connector.save(gp);
            Chat savedGp = connector.fetchChat(gp);
            while (savedGp == null)
                savedGp = connector.fetchChat(gp);

            myUser.getChats().add(savedGp.getId());

            connector.save(myUser);

            NewGroupResponse response = new NewGroupResponse("groupMessage");
            response.setMyUser(myUser);
            return response;
        }


        return null;
    }


}
