package ir.sharif.ap2021.client.View.ModelView;

import ir.sharif.ap2021.client.Config.ImageConfig;

import ir.sharif.ap2021.client.Controller.StaticController;
import ir.sharif.ap2021.client.Listener.ChatListener;
import ir.sharif.ap2021.shared.Event.ChatEvent;
import ir.sharif.ap2021.shared.Model.Chat;
import ir.sharif.ap2021.shared.Model.Thought;
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
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatForwardView implements Initializable {

    ImageConfig imageConfig = new ImageConfig();

    ChatListener chatListener = new ChatListener();

    private Chat chat;
    private User user;
    private int unseen;
    private static Thought thought;

    @FXML
    private Circle avatar;
    @FXML
    private Label label, unseenText;

    public ChatForwardView() throws IOException {
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

    public static Thought getThought() {
        return thought;
    }

    public static void setThought(Thought thought) {
        ChatForwardView.thought = thought;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

//        if (chat.isGroup()) {
//            avatar.setFill(new ImagePattern(new Image(imageConfig.getGroup())));
//            label.setText(chat.getName());
//        } else {
//
//            BufferedImage bufferedImage = null;
//
//            if (user != null) {
//                try {
//                    bufferedImage = ImageIO.read(new File(imageConfig.getMainConfig().getResourcesPath() + user.getAvatar()));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                assert bufferedImage != null;
//                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
//
//                avatar.setFill(new ImagePattern(image));
//                label.setText(user.getUserName());
//            } else {
//
//                try {
//                    bufferedImage = ImageIO.read(new File(imageConfig.getMainConfig().getResourcesPath() + StaticController.getMyUser().getAvatar()));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                assert bufferedImage != null;
//                Image image = SwingFXUtils.toFXImage(bufferedImage, null);
//
//                avatar.setFill(new ImagePattern(image));
//                label.setText("Saved Messages");
//            }
//
//        }

        unseenText.setText(String.valueOf(unseen));
    }


    public void forwardChat(MouseEvent mouseEvent) throws IOException {

        ChatEvent chatEvent = new ChatEvent("forward");
        chatEvent.setChatId(chat.getId());
        chatEvent.setSomeText(getThought().getText());
        chatListener.listen(chatEvent);

    }



}
