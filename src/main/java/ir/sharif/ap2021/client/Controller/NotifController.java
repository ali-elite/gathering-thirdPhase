package ir.sharif.ap2021.client.Controller;


import ir.sharif.ap2021.client.Config.FxmlConfig;
import ir.sharif.ap2021.client.Listener.NotifListener;
import ir.sharif.ap2021.client.View.Menu.Notifications;
import ir.sharif.ap2021.client.View.ModelView.NotifView;
import ir.sharif.ap2021.shared.Model.Notification;
import ir.sharif.ap2021.shared.Response.NotifResponse;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class NotifController {

    FxmlConfig fxmlConfig = new FxmlConfig();
    NotifListener notifListener;

    public NotifController(NotifListener notifListener) throws IOException {
        this.notifListener = notifListener;
    }


    public void load(NotifResponse response) throws IOException {

        Notifications.getNotifViews().clear();

        for (int i = response.getNotifications().size() - 1; i > -1; i--) {

            NotifView notifView = new NotifView();

            Notification notification = response.getNotifications().get(i);
            notifView.setNotification(notification);
            notifView.setUser(notification.getSender());

            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlConfig.getNotif()));
            loader.setController(notifView);

            Notifications.getNotifViews().add((Pane) loader.load());
        }
    }


    public void accept(NotifResponse response) throws IOException {

        StaticController.setMyUser(response.getMyUser());
        notifListener.getNotifView().setNotification(response.getMyNotif());
        notifListener.getNotifView().initialize(null, null);

    }


    public void reject(NotifResponse response) throws IOException {

        StaticController.setMyUser(response.getMyUser());
        notifListener.getNotifView().setNotification(response.getMyNotif());
        notifListener.getNotifView().initialize(null, null);

    }


    public void remove(NotifResponse response) throws IOException {

        StaticController.setMyUser(response.getMyUser());
        notifListener.getNotifView().setNotification(response.getMyNotif());
        notifListener.getNotifView().initialize(null, null);

    }
}
