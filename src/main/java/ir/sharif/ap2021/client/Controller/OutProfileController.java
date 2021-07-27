package ir.sharif.ap2021.client.Controller;

import ir.sharif.ap2021.client.Config.ErrorConfig;
import ir.sharif.ap2021.client.Listener.OutProfileListener;
import ir.sharif.ap2021.client.View.Menu.ChatMenu;
import ir.sharif.ap2021.client.View.ModelView.OutProfile;
import ir.sharif.ap2021.shared.Response.OutProfileResponse;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.IOException;

public class OutProfileController {

    ErrorConfig errorConfig = new ErrorConfig();
    OutProfileListener outProfileListener;


    public OutProfileController(OutProfileListener outProfileListener) throws IOException {
        this.outProfileListener = outProfileListener;
    }

    public void control(OutProfileResponse response) throws IOException {

        if (response.getOrder().equals("block")) {

            StaticController.setMyUser(response.getMyUser());
            OutProfile.setUser(response.getOutUser());

            Platform.runLater(() -> {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(errorConfig.getBlocked());
                alert.showAndWait();
                outProfileListener.getOutProfile().initialize(null, null);

            });

        }


        if (response.getOrder().equals("follow")) {

            StaticController.setMyUser(response.getMyUser());
            OutProfile.setUser(response.getOutUser());

            if (response.isRepeat()) {

                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(errorConfig.getAlreadyRequested());
                    alert.showAndWait();

                });
            }

            if (response.isRequest()) {
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText(errorConfig.getRequestSent());
                    alert.showAndWait();

                });

            }

            Platform.runLater(() -> outProfileListener.getOutProfile().initialize(null, null));


        }

        if (response.getOrder().equals("mute")) {

            StaticController.setMyUser(response.getMyUser());
            OutProfile.setUser(response.getOutUser());

            Platform.runLater(() -> outProfileListener.getOutProfile().initialize(null, null));

        }

        if (response.getOrder().equals("report")) {

            Platform.runLater(() -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(errorConfig.getReported());
                alert.showAndWait();
            });


        }

        if (response.getOrder().equals("message")) {

            if (response.getMessage().equals("chat created")) {
                StaticController.setMyUser(response.getMyUser());
                OutProfile.setUser(response.getOutUser());
            }

            ChatMenu.setChat(response.getChat());
            ChatMenu chatMenu = new ChatMenu();
            chatMenu.show();

        }

    }

}
