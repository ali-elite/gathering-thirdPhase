package ir.sharif.ap2021.server.Controller.Helper;

import ir.sharif.ap2021.server.Controller.ClientHandler;
import ir.sharif.ap2021.server.Hibernate.Connector;
import ir.sharif.ap2021.server.Hibernate.DatabaseDisconnectException;
import ir.sharif.ap2021.shared.Event.EditProfileEvent;
import ir.sharif.ap2021.shared.Event.NotifEvent;
import ir.sharif.ap2021.shared.Model.User;
import ir.sharif.ap2021.shared.Response.EditProfileResponse;
import ir.sharif.ap2021.shared.Response.Response;

import java.util.List;

public class EditProfileController {

    private EditProfileEvent event;
    private Connector connector;
    private ClientHandler clientHandler;

    public EditProfileController(EditProfileEvent editProfileEvent, Connector connector, ClientHandler clientHandler) {
        this.event = editProfileEvent;
        this.connector = connector;
        this.clientHandler = clientHandler;
    }

    public Response answer() throws DatabaseDisconnectException {


        List<User> emails = connector.fetchUserWithEmail(event.getEmail());
        if (!emails.isEmpty() && !emails.contains(clientHandler.user)) {
            return new EditProfileResponse(false, "WrongEmail");
        }

        List<User> usernames = connector.fetchUserWithUsername(event.getUser());

        if (!usernames.isEmpty() && !usernames.contains(clientHandler.user)) {
            return new EditProfileResponse(false, "WrongUsername");
        }


        User user = connector.fetch(User.class,clientHandler.user.getId());


        user.setFirstName(event.getFirst());
//        logger.info("user " + user.getId() + " has changed his/her firstname to " + event.getFirst());

        user.setLastName(event.getLast());
//        logger.info("user " + user.getId() + " has changed his/her lastname to " + event.getLast());

        user.setUserName(event.getUser());
//        logger.info("user " + user.getId() + " has changed his/her username to " + event.getUser());

        user.setPhoneNumber(event.getPhone());
//        logger.info("user " + user.getId() + " has changed his/her phoneNumber to " + event.getPhone());

        user.setEmail(event.getEmail());
//        logger.info("user " + user.getId() + " has changed his/her email to " + event.getEmail());

        user.setBirthday(event.getBirthday());
//        logger.info("user " + user.getId() + " has changed his/her birthday to " + event.getBirthday());

        user.setBiography(event.getBio());
//        logger.info("user " + user.getId() + " has changed his/her biography to " + event.getBio());

        if(event.getAvatar() != null){
            user.setAvatar(event.getAvatar());
        }

        connector.save(user);
        EditProfileResponse response = new EditProfileResponse(true, "Welcome");
        response.setMyUser(user);

        return response;
    }
}
