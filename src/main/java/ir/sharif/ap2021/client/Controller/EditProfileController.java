package ir.sharif.ap2021.client.Controller;

import ir.sharif.ap2021.client.Config.ErrorConfig;
import ir.sharif.ap2021.client.Listener.EditProfileListener;
import ir.sharif.ap2021.shared.Response.EditProfileResponse;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import java.io.IOException;

public class EditProfileController {

    ErrorConfig errorConfig = new ErrorConfig();
    EditProfileListener editProfileListener;


    public EditProfileController(EditProfileListener editProfileListener) throws IOException {
        this.editProfileListener = editProfileListener;
    }

    public void edit(EditProfileResponse response) throws IOException {

        if (!response.isChanged()) {
            if (response.getMessage().equals("WrongEmail")) {
                editProfileListener.getEditProfile().answerError(errorConfig.getEmailExists());
            } else {
                editProfileListener.getEditProfile().answerError(errorConfig.getUserExists());
            }
        } else {

            StaticController.setMyUser(response.getMyUser());

            Platform.runLater(() -> {

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText(errorConfig.getProfileChanged());
                alert.showAndWait();

            });

            editProfileListener.getEditProfile().back(null);
        }


    }


}
