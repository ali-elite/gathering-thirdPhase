package ir.sharif.ap2021.client.View.ModelView;

import ir.sharif.ap2021.client.Config.ErrorConfig;
import ir.sharif.ap2021.client.Config.ImageConfig;
import ir.sharif.ap2021.client.View.Menu.ChatMenu;
import ir.sharif.ap2021.shared.Model.Chat;
import ir.sharif.ap2021.shared.Model.User;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

public class ChatView implements Initializable {

    ImageConfig imageConfig = new ImageConfig();
    ErrorConfig errorConfig = new ErrorConfig();

    private Chat chat;
    private User user;
    private int unseen;

    @FXML
    private Circle avatar;
    @FXML
    private Label label, unseenText;

    public ChatView() throws IOException {
    }


    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getUnseen() {
        return unseen;
    }

    public void setUnseen(int unseen) {
        this.unseen = unseen;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (chat.isGroup()) {

            avatar.setFill(new ImagePattern(new Image(imageConfig.getGroup())));
            label.setText(chat.getName());

        } else {

            if (!chat.getName().equals(errorConfig.getSavedMessages())) {
                label.setText(user.getUserName());
            } else {
                label.setText(errorConfig.getSavedMessages());
            }


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

        }

        unseenText.setText(String.valueOf(unseen));
    }


    public void openChat(MouseEvent mouseEvent) throws IOException {

        ChatMenu.setChat(chat);
        ChatMenu chatMenu = new ChatMenu();
        chatMenu.show();

    }


}

