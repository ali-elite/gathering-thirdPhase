package ir.sharif.ap2021.client.View.ModelView;

import ir.sharif.ap2021.client.Config.ErrorConfig;
import ir.sharif.ap2021.client.Listener.NotifListener;
import ir.sharif.ap2021.shared.Event.NotifEvent;
import ir.sharif.ap2021.shared.Model.Notification;
import ir.sharif.ap2021.shared.Model.User;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NotifView implements Initializable {

    ErrorConfig errorConfig = new ErrorConfig();

    private Notification notification;
    private NotifListener notifListener;
    private User user;

    @FXML
    private Label textLabel;
    @FXML
    private Button acceptBtn, rejectBtn, removeBtn;
    @FXML
    private Circle avatar;

    public NotifView() throws IOException {
    }


    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) throws IOException {
        this.notification = notification;
        notifListener = new NotifListener();
        notifListener.setNotifView(this);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void accept(ActionEvent event) throws IOException {

        NotifEvent notifEvent = new NotifEvent("accept");
        notifEvent.setNotificationId(notification.getId());
        notifListener.listen(notifEvent);

    }

    public void reject(ActionEvent event) throws IOException {

        NotifEvent notifEvent = new NotifEvent("reject");
        notifEvent.setNotificationId(notification.getId());
        notifListener.listen(notifEvent);

    }

    public void remove(ActionEvent event) throws IOException {

        NotifEvent notifEvent = new NotifEvent("remove");
        notifEvent.setNotificationId(notification.getId());
        notifListener.listen(notifEvent);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (!notification.isRequest() || notification.isAnswered()) {

            acceptBtn.setDisable(true);
            rejectBtn.setDisable(true);
            removeBtn.setDisable(true);

        }

        textLabel.setText(notification.getText());

//        BufferedImage bufferedImage = null;
//        try {
//            bufferedImage = ImageIO.read(new File(errorConfig.getMainConfig().getResourcesPath() + user.getAvatar()));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        assert bufferedImage != null;
//        Image image = SwingFXUtils.toFXImage(bufferedImage, null);
//
//        avatar.setFill(new ImagePattern(image));

    }


}
