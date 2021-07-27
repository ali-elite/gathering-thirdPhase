package ir.sharif.ap2021.client.Controller;

import ir.sharif.ap2021.client.Config.ErrorConfig;
import ir.sharif.ap2021.client.Config.MainConfig;
import ir.sharif.ap2021.shared.Response.NewGroupResponse;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class NewGroupController {

    ErrorConfig errorConfig = new ErrorConfig();

    public NewGroupController() throws IOException {

    }

    public void control(NewGroupResponse response) {

        if (response.getOrder().equals("groupMessage")) {

            StaticController.setMyUser(response.getMyUser());

            Platform.runLater(() -> {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(errorConfig.getMessageSent());
                alert.showAndWait();

            });
        }

        if (response.getOrder().equals("makeGroup")) {

            StaticController.setMyUser(response.getMyUser());

            Platform.runLater(() -> {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(errorConfig.getGroupCreated());
                alert.showAndWait();

            });
        }

    }


}
