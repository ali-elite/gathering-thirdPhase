package ir.sharif.ap2021.server.Controller.Helper;

import ir.sharif.ap2021.server.Config.ErrorConfig;
import ir.sharif.ap2021.server.Controller.ClientHandler;
import ir.sharif.ap2021.server.Hibernate.Connector;
import ir.sharif.ap2021.server.Hibernate.DatabaseDisconnectException;
import ir.sharif.ap2021.shared.Event.ThoughtEvent;
import ir.sharif.ap2021.shared.Model.Chat;
import ir.sharif.ap2021.shared.Model.Message;
import ir.sharif.ap2021.shared.Model.Thought;
import ir.sharif.ap2021.shared.Model.User;
import ir.sharif.ap2021.shared.Response.Response;
import ir.sharif.ap2021.shared.Response.ThoughtResponse;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class ThoughtController {

    private ThoughtEvent event;
    private Connector connector;
    private ClientHandler clientHandler;
    private ErrorConfig errorConfig = new ErrorConfig();

    public ThoughtController(ThoughtEvent event, Connector connector, ClientHandler clientHandler) throws IOException {
        this.event = event;
        this.connector = connector;
        this.clientHandler = clientHandler;
    }

    public Response answer() throws DatabaseDisconnectException {

        if (event.getOrder().equals("opinions")) {

            ThoughtResponse thoughtResponse = new ThoughtResponse("opinions");

            Thought myThought = connector.fetch(Thought.class, event.getThoughtId());
            List<Thought> myOpinions = thoughtResponse.getOpinions();

            for (Integer i : myThought.getOpinions()) {
                Thought t = connector.fetch(Thought.class, i);
                if (t != null) {
                    myOpinions.add(t);
                }
            }

            Thought parent = connector.fetch(Thought.class, myThought.getParent());
            thoughtResponse.setThought(myThought);
            thoughtResponse.setParent(parent);
            return thoughtResponse;

        }

        if (event.getOrder().equals("like")) {

            Thought thought = connector.fetch(Thought.class, event.getThoughtId());
            User myUser = connector.fetch(User.class,clientHandler.user.getId());
            User user = connector.fetch(User.class,thought.getUserId());

            if (thought.getLikers().contains(myUser)) {
                thought.minusLike();
                thought.getLikers().remove(myUser);
            } else {
                thought.addLike();
                thought.getLikers().add(myUser);
            }

            connector.save(thought);
            connector.save(user);
            connector.save(myUser);

            ThoughtResponse response = new ThoughtResponse("like");
            response.setMyUser(myUser);
            response.setThought(thought);

            return response;

        }

        if (event.getOrder().equals("ret")) {

            Thought thought = connector.fetch(Thought.class, event.getThoughtId());
            User myUser = connector.fetch(User.class,clientHandler.user.getId());
            User user = connector.fetch(User.class,thought.getUserId());

            if (thought.getRethoughters().contains(myUser)) {

                thought.minusRethought();
                thought.getRethoughters().remove(myUser);

                for (int i = 0; i < myUser.getThoughts().size() - 1; i++) {

                    if (myUser.getThoughts().get(i) == thought.getId()) {
                        myUser.getThoughts().remove(myUser.getThoughts().get(i));
                        break;
                    }

                }


            } else {
                thought.addRethought();
                thought.getRethoughters().add(myUser);
                myUser.getThoughts().add(thought.getId());
            }

            connector.save(thought);
            connector.save(user);
            connector.save(myUser);

            ThoughtResponse response = new ThoughtResponse("ret");
            response.setMyUser(myUser);
            response.setThought(thought);

            return response;
        }


        if (event.getOrder().equals("mention")) {

            Thought thought = connector.fetch(Thought.class, event.getThoughtId());
            User myUser = connector.fetch(User.class,clientHandler.user.getId());
            User user = connector.fetch(User.class,thought.getUserId());


            Thought opinion = new Thought("o", myUser, user, event.getMentionText(), LocalDateTime.now());
            opinion.setParent(thought.getId());

            connector.save(opinion);
            Thought savedOpinion = connector.fetchThought(opinion);
            while (savedOpinion == null)
                savedOpinion = connector.fetchThought(opinion);

            thought.getOpinions().add(savedOpinion.getId());


//            if (response.getMentionImg().equals("changed")) {
//                opinion.setImage("/ThoughtImages/" + opinion.getId() + ".png");
//
//
//                File old = new File(errorConfig.getMainConfig().getResourcesPath() + "/ThoughtImages/" + "31" + ".png");
//                File notOld = new File(errorConfig.getMainConfig().getResourcesPath() + "/ThoughtImages/" + opinion.getId() + ".png");
//
//                old.renameTo(notOld);
//            }

            //didnt add replies to main user but it can be

            connector.save(thought);
            connector.save(user);
            connector.save(myUser);

            ThoughtResponse response = new ThoughtResponse("mention");
            response.setMyUser(myUser);
            response.setThought(thought);

            return response;

        }

        if (event.getOrder().equals("muteAuthor")) {

            Thought thought = connector.fetch(Thought.class, event.getThoughtId());
            User myUser = connector.fetch(User.class,clientHandler.user.getId());
            User user = connector.fetch(User.class,thought.getUserId());


            myUser.getMuteList().add(user.getId());

            connector.save(user);
            connector.save(myUser);

            ThoughtResponse response = new ThoughtResponse("muteAuthor");
            response.setMyUser(myUser);

            return response;

        }

        if (event.getOrder().equals("spam")) {

            Thought thought = connector.fetch(Thought.class, event.getThoughtId());
            thought.addSpam();
            connector.save(thought);

            ThoughtResponse response = new ThoughtResponse("spam");
            response.setThought(thought);

            return response;

        }

        if (event.getOrder().equals("saveMessage")) {

            Thought thought = connector.fetch(Thought.class, event.getThoughtId());
            User myUser = connector.fetch(User.class,clientHandler.user.getId());
            User user = connector.fetch(User.class,thought.getUserId());


            Chat chat = null;

            for (Integer i : myUser.getChats()) {
                Chat loaded = connector.fetch(Chat.class, i);
                if (loaded.getName().equals(errorConfig.getSavedMessages())) {
                    chat = loaded;
                }
            }

            if (chat == null) {
                chat = new Chat(errorConfig.getSavedMessages(), false);
                chat.getUsers().add(myUser);
                connector.save(chat);
                Chat savedChat = connector.fetchChat(chat);
                while (savedChat == null)
                    savedChat = connector.fetchChat(chat);
                myUser.getChats().add(chat.getId());
            }

            Thought t = connector.fetch(Thought.class, event.getThoughtId());
            Message message = new Message(myUser, true, t.getText());

//            if (event.getThought().getImage() != null) {
//                message.setImage(response.getThought().getImage());
//            }

            chat.getMessages().add(message);


            connector.save(message);
            connector.save(chat);
            connector.save(myUser);


            ThoughtResponse response = new ThoughtResponse("saveMessage");
            response.setMyUser(myUser);

            return response;


        }


        return null;

    }
}
