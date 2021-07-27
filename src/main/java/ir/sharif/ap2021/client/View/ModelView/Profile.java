package ir.sharif.ap2021.client.View.ModelView;

import ir.sharif.ap2021.client.Config.ErrorConfig;
import ir.sharif.ap2021.client.Config.ImageConfig;
import ir.sharif.ap2021.client.Controller.StaticController;
import ir.sharif.ap2021.client.Listener.UserSelectionListener;
import ir.sharif.ap2021.shared.Event.UserSelectionEvent;
import ir.sharif.ap2021.shared.Model.User;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Profile implements Initializable {

    ErrorConfig errorConfig = new ErrorConfig();
    ImageConfig imageConfig = new ImageConfig();

    private User user;
    private UserSelectionListener userSelectionListener = new UserSelectionListener();
    @FXML
    private Circle avatar;
    @FXML
    private Label bioLabel, statusLabel, followerNumberLabel, nicknameLabel, idLabel, lastseenLabel, followingNumberLabel;

    public Profile() throws IOException {
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        followerNumberLabel.setText(String.valueOf(user.getFollowers().size()));
        followingNumberLabel.setText(String.valueOf(user.getFollowings().size()));
        nicknameLabel.setText(user.getFirstName() + " " + user.getLastName());
        idLabel.setText("@" + user.getUserName());
        lastseenLabel.setText(String.valueOf(user.getLastSeen()));


        if (user.getAvatar() == null) {

            BufferedImage bufferedImage = null;
            try {
                bufferedImage = ImageIO.read(new File(errorConfig.getMainConfig().getResourcesPath() + imageConfig.getUser()));
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert bufferedImage != null;
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            avatar.setFill(new ImagePattern(image));

        } else {

            ByteArrayInputStream bis = new ByteArrayInputStream(user.getAvatar());
            BufferedImage bufferedImage = null;
            try {
                bufferedImage = ImageIO.read(bis);
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert bufferedImage != null;
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            avatar.setFill(new ImagePattern(image));

        }


        if (user.getBiography() == null) {
            bioLabel.setText(":)))");
        } else {
            bioLabel.setText(user.getBiography());
        }

        if (StaticController.getMyUser().getFollowers().contains(user.getId())) {
            statusLabel.setText(errorConfig.getFollowsYou());
        } else statusLabel.setText(" ");

    }

    public void selectFollower(MouseEvent mouseEvent) {


        if (StaticController.getMyUser().getFollowers().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(errorConfig.getEmptyFollower());
            alert.showAndWait();
        } else {
            userSelectionListener.listen(new UserSelectionEvent("follower", user.getUserName(), null));
        }


    }

    public void selectFollowing(MouseEvent mouseEvent) {

        if (StaticController.getMyUser().getFollowings().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText(errorConfig.getEmptyFollowing());
            alert.showAndWait();
        } else {
            userSelectionListener.listen(new UserSelectionEvent("following", user.getUserName(), null));
        }

    }

}
