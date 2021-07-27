package ir.sharif.ap2021.server.Controller.Helper;

import ir.sharif.ap2021.server.Config.ErrorConfig;
import ir.sharif.ap2021.server.Controller.ClientHandler;
import ir.sharif.ap2021.server.Hibernate.Connector;
import ir.sharif.ap2021.server.Hibernate.DatabaseDisconnectException;
import ir.sharif.ap2021.shared.Event.NotifEvent;
import ir.sharif.ap2021.shared.Model.Notification;
import ir.sharif.ap2021.shared.Model.User;
import ir.sharif.ap2021.shared.Response.NotifResponse;
import ir.sharif.ap2021.shared.Response.Response;

import java.io.IOException;
import java.util.List;

public class NotifController {

    private NotifEvent notifEvent;
    private Connector connector;
    private ClientHandler clientHandler;
    private ErrorConfig errorConfig;

    public NotifController(NotifEvent notifEvent, Connector connector, ClientHandler clientHandler) throws IOException {
        this.notifEvent = notifEvent;
        this.connector = connector;
        this.clientHandler = clientHandler;
        errorConfig = new ErrorConfig();
    }


    public Response answer() throws DatabaseDisconnectException {

        User myUser = connector.fetch(User.class,clientHandler.user.getId());

        if (notifEvent.getOrder().equals("load")) {

            NotifResponse response = new NotifResponse("load");
            List<Notification> notifications = connector.fetchAll(Notification.class);

            for (Notification n : notifications) {
                if (n.getReceiver().equals(myUser)) {
                    response.getNotifications().add(n);
                }
            }

            return response;
        }


        if (notifEvent.getOrder().equals("accept")) {

            Notification notification = connector.fetch(Notification.class,notifEvent.getNotificationId());
            User user = connector.fetch(User.class,clientHandler.user.getId());
            User sender = notification.getSender();

            user.getFollowers().add(sender.getId());
            sender.getFollowings().add(user.getId());

            notification.setText(sender.getUserName() + " " + errorConfig.getFollowedYou());

            Notification myNotif = new Notification(false, user, sender, errorConfig.getYouFollowed() + " " + user.getUserName());
            connector.save(myNotif);
            Notification savedMyNotification = connector.fetchNotification(myNotif);
            while (savedMyNotification == null)
                savedMyNotification = connector.fetchNotification(myNotif);
            sender.getNotifications().add(savedMyNotification.getId());


            notification.setAnswered(true);

            connector.save(notification);
            connector.save(user);
            connector.save(sender);

            NotifResponse response = new NotifResponse("accept");
            response.setMyNotif(notification);
            response.setMyUser(user);

            return response;

        }

        if (notifEvent.getOrder().equals("reject")) {

            Notification notification = connector.fetch(Notification.class,notifEvent.getNotificationId());
            User user = connector.fetch(User.class,clientHandler.user.getId());
            User sender = notification.getSender();

            notification.setText(errorConfig.getYouRejected() + " " + sender.getUserName());

            Notification myNotif = new Notification(false, user, sender, user.getUserName() + " " + errorConfig.getRejectedYou());

            connector.save(myNotif);
            Notification savedMyNotification = connector.fetchNotification(myNotif);
            while (savedMyNotification == null)
                savedMyNotification = connector.fetchNotification(myNotif);
            sender.getNotifications().add(savedMyNotification.getId());

            notification.setAnswered(true);


            connector.save(notification);
            connector.save(user);
            connector.save(sender);

            NotifResponse response = new NotifResponse("reject");
            response.setMyNotif(notification);
            response.setMyUser(user);

            return response;

        }


        if (notifEvent.getOrder().equals("remove")) {

            Notification notification = connector.fetch(Notification.class,notifEvent.getNotificationId());
            User user = connector.fetch(User.class,clientHandler.user.getId());
            User sender = notification.getSender();


            notification.setText(errorConfig.getYouSilentRejected() + " " + sender.getUserName());
            notification.setAnswered(true);

            connector.save(notification);
            connector.save(user);

            NotifResponse response = new NotifResponse("remove");
            response.setMyNotif(notification);
            response.setMyUser(user);

            return response;
        }


        return null;
    }
}
