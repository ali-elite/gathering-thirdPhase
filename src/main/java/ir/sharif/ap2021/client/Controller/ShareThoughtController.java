package ir.sharif.ap2021.client.Controller;

import ir.sharif.ap2021.client.Config.ErrorConfig;
import ir.sharif.ap2021.client.Config.MainConfig;
import ir.sharif.ap2021.client.Listener.ShareThoughtListener;
import ir.sharif.ap2021.shared.Response.ShareThoughtResponse;
import javafx.scene.control.Alert;

import java.io.IOException;

public class ShareThoughtController {

    ShareThoughtListener shareThoughtListener;
    ErrorConfig errorConfig;

    public ShareThoughtController(ShareThoughtListener shareThoughtListener) throws IOException {
        this.shareThoughtListener = shareThoughtListener;
        errorConfig = new ErrorConfig();
    }

    public void share(ShareThoughtResponse response) throws IOException {

        StaticController.setMyUser(response.getUser());
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setContentText(errorConfig.getProfileChanged());
//        alert.showAndWait();
        shareThoughtListener.getNewThought().back(null);
    }

}
