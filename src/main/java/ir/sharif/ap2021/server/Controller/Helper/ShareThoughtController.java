package ir.sharif.ap2021.server.Controller.Helper;

import ir.sharif.ap2021.server.Controller.ClientHandler;
import ir.sharif.ap2021.server.Hibernate.Connector;
import ir.sharif.ap2021.server.Hibernate.DatabaseDisconnectException;
import ir.sharif.ap2021.shared.Event.ShareThoughtEvent;
import ir.sharif.ap2021.shared.Model.Thought;
import ir.sharif.ap2021.shared.Model.User;
import ir.sharif.ap2021.shared.Response.Response;
import ir.sharif.ap2021.shared.Response.ShareThoughtResponse;

import java.time.LocalDateTime;

public class ShareThoughtController {

    private ShareThoughtEvent event;
    private Connector connector;
    private ClientHandler clientHandler;

    public ShareThoughtController(ShareThoughtEvent event, Connector connector, ClientHandler clientHandler) {
        this.event = event;
        this.connector = connector;
        this.clientHandler = clientHandler;
    }

    public Response answer() throws DatabaseDisconnectException {

        User user = clientHandler.user;

        Thought thought = new Thought("t", user, user, event.getText(), LocalDateTime.now());
        connector.save(thought);


        Thought savedThought  = null;
        while (savedThought == null){
            savedThought = connector.fetchThought(thought);
        }


        user.getThoughts().add(savedThought.getId());


//        if (formEvent.getChange().equals("changed")) {
//            thought.setImage("/ThoughtImages/" + thought.getId() + ".png");
//
//
//            File old = new File(mainConfig.getResourcesPath() + "/ThoughtImages/" + "733" + ".png");
//            File notOld = new File(mainConfig.getResourcesPath() + "/ThoughtImages/" + thought.getId() + ".png");
//
//            old.renameTo(notOld);
//        }


        connector.save(user);
        return new ShareThoughtResponse(user);

    }


}
