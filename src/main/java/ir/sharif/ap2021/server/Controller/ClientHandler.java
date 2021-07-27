package ir.sharif.ap2021.server.Controller;


import ir.sharif.ap2021.server.Controller.Helper.*;
import ir.sharif.ap2021.server.Controller.Network.CliectDisconnectException;
import ir.sharif.ap2021.server.Controller.Network.ResponseSender;
import ir.sharif.ap2021.server.Hibernate.Connector;
import ir.sharif.ap2021.server.Hibernate.DatabaseDisconnectException;
import ir.sharif.ap2021.shared.Event.*;
import ir.sharif.ap2021.shared.Model.User;
import ir.sharif.ap2021.shared.Response.LoginResponse;
import ir.sharif.ap2021.shared.Response.MainMenuResponse;
import ir.sharif.ap2021.shared.Response.Response;
import ir.sharif.ap2021.shared.Response.SignupResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ClientHandler implements EventVisitor {
    private final List<Response> responseList;
    private final Connector connector;
    //    private final ModelLoader modelLoader;
//    private final Collection collection;
    private final ResponseSender responseSender;
    private volatile boolean running;
    public User user;

    public ClientHandler(ResponseSender responseSender
            , Connector connector
//                         ,ModelLoader modelLoader,
    ) {
        this.responseSender = responseSender;
        responseList = new ArrayList<>(100);
        this.connector = connector;
//        this.modelLoader = modelLoader;
//        collection = new Collection(connector, modelLoader);

    }


    public void start() {
        running = true;
        new Thread(this::visitEvents).start();
    }

    private void visitEvents() {
        while (running) {
            Event event;
            try {
                event = responseSender.getEvent();
//                try {
//                    connector.save(new RequestLog(event, (player == null) ? null : player.getUsername()));
                event.visit(this);
//                } catch (DatabaseDisconnectException e) {
//                    responseSender.sendResponse(new GoTo("EXIT", "database disconnected"));
//                    break;
//                }
                responseSender.sendResponse(responseList.toArray(new Response[0]));
                responseList.clear();
            } catch (CliectDisconnectException e) {
//                if (game != null) game.endGame(side);
//                if (gameBuilder != null && gameBuilder instanceof OnlineGameBuilder)
//                    ((OnlineGameBuilder) gameBuilder).cancel();
                break;
            }
        }
        responseSender.close();
    }

    private void addToResponses(Response... responses) {
        addToResponses(true, responses);
    }

    private void addToResponses(boolean log, Response... responses)
//            throws DatabaseDisconnectException
    {
        synchronized (responseList) {
            for (Response response : responses)
                if (response != null) {
                    responseList.add(response);
//                    if (log) connector.save(new ResponseLog(response, player.getUsername()));
                }
        }
    }

    public ResponseSender getResponseSender() {
        return responseSender;
    }

    @Override
    public void exit() {

    }

    @Override
    public void login(String username, String password) {
        try {
            List<User> fetched = connector.fetchUserWithUsername(username);
            if (!fetched.isEmpty()) {
                if (fetched.get(0).getPassword().equals(password)) {
                    this.user = fetched.get(0);
                    Response response = new LoginResponse(true, "Welcome " + username);
                    addToResponses(response);
                } else {
                    Response response = new LoginResponse(false, "wrong password");
                    addToResponses(false, response);
                }
            } else {
                Response response = new LoginResponse(false, "username does not exist");
                addToResponses(false, response);
            }
        } catch (DatabaseDisconnectException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void signup(String firstname, String lastname, String username, String email, String password) {
        try {
            List<User> user = connector.fetchUserWithUsername(username);
            if (user.isEmpty()) {
                User newUser = new User(firstname, lastname, username, email, password);
                connector.save(newUser);
                User savedUser = connector.fetchUser(newUser);
                while (savedUser == null)
                    savedUser = connector.fetchUser(newUser);
                this.user = savedUser;
                Response response = new SignupResponse(true, "Welcome " + username);
                addToResponses(response);
            } else {
                Response response = new SignupResponse(false, "username already exists");
                addToResponses(response);
            }

        } catch (DatabaseDisconnectException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void doMainMenu(MainMenuEvent mainMenuEvent) {

        MainMenuController mainMenuController = new MainMenuController(mainMenuEvent, this, connector);
        Response response = null;
        try {
            response = mainMenuController.answer();
        } catch (DatabaseDisconnectException e) {
            e.printStackTrace();
        }
        addToResponses(response);
    }

    @Override
    public void doUserSelect(String order, String username, String type) {

        UserSelectionController userSelectionController = new UserSelectionController(connector, this);
        userSelectionController.setOrder(order);
        userSelectionController.setUsername(username);
        userSelectionController.setType(type);

        Response response = null;
        try {
            response = userSelectionController.answer();
        } catch (DatabaseDisconnectException e) {
            e.printStackTrace();
        }
        addToResponses(response);

    }

    @Override
    public void doOutProfile(OutProfileEvent outProfileEvent) {

        OutProfileController outProfileController = null;
        try {
            outProfileController = new OutProfileController(outProfileEvent, connector, this);
        } catch (IOException e) {
            e.printStackTrace();
        }


        Response response = null;
        try {
            response = outProfileController.answer();
        } catch (DatabaseDisconnectException e) {
            e.printStackTrace();
        }
        addToResponses(response);

    }

    @Override
    public void doNotification(NotifEvent notifEvent) {

        NotifController notifController = null;
        try {
            notifController = new NotifController(notifEvent, connector, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Response response = null;
        try {
            response = notifController.answer();
        } catch (DatabaseDisconnectException e) {
            e.printStackTrace();
        }
        addToResponses(response);

    }

    @Override
    public void editProfile(EditProfileEvent editProfileEvent) {


        EditProfileController editProfileController = new EditProfileController(editProfileEvent, connector, this);
        Response response = null;
        try {
            response = editProfileController.answer();
        } catch (DatabaseDisconnectException e) {
            e.printStackTrace();
        }
        addToResponses(response);

    }

    @Override
    public void shareThought(ShareThoughtEvent shareThoughtEvent) {

        ShareThoughtController shareThoughtController = new ShareThoughtController(shareThoughtEvent, connector, this);
        Response response = null;
        try {
            response = shareThoughtController.answer();
        } catch (DatabaseDisconnectException e) {
            e.printStackTrace();
        }
        addToResponses(response);
    }

    @Override
    public void doNewGroup(NewGroupEvent newGroupEvent) {

        NewGroupController newGroupController = new NewGroupController(newGroupEvent, connector, this);
        Response response = null;
        try {
            response = newGroupController.answer();
        } catch (DatabaseDisconnectException e) {
            e.printStackTrace();
        }
        addToResponses(response);
    }

    @Override
    public void doThought(ThoughtEvent thoughtEvent) {

        ThoughtController thoughtController = null;
        try {
            thoughtController = new ThoughtController(thoughtEvent, connector, this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Response response = null;
        try {
            response = thoughtController.answer();
        } catch (DatabaseDisconnectException e) {
            e.printStackTrace();
        }
        addToResponses(response);

    }

    @Override
    public void doMessage(MessageEvent messageEvent) {

        MessageController messageController = new MessageController(messageEvent, connector, this);
        Response response = null;
        try {
            response = messageController.answer();
        } catch (DatabaseDisconnectException e) {
            e.printStackTrace();
        }

    }


    @Override
    public void doChat(ChatEvent chatEvent) {

        ChatController chatController = new ChatController(chatEvent, connector, this);
        Response response = null;
        try {
            response = chatController.answer();
        } catch (DatabaseDisconnectException e) {
            e.printStackTrace();
        }
        addToResponses(response);

    }

}


