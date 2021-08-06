package ir.sharif.ap2021.client.View.ModelView;

import ir.sharif.ap2021.client.Config.ErrorConfig;
import ir.sharif.ap2021.client.Config.ImageConfig;
import ir.sharif.ap2021.client.Config.ItemConfig;
import ir.sharif.ap2021.client.Controller.StaticController;
import ir.sharif.ap2021.client.Listener.ChatListener;
import ir.sharif.ap2021.client.Listener.MessageListener;
import ir.sharif.ap2021.shared.Event.MessageEvent;
import ir.sharif.ap2021.shared.Model.Message;
import ir.sharif.ap2021.shared.Model.User;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MessageView implements Initializable {

    ItemConfig itemConfig = new ItemConfig();
    ErrorConfig errorConfig = new ErrorConfig();
    ImageConfig imageConfig = new ImageConfig();


    MessageListener messageListener = new MessageListener(this);
    ChatListener chatListener = new ChatListener();

    private Message message;
    private User sender;

    @FXML
    private Circle avatar;
    @FXML
    private Label textLabel, forwardLabel, userLabel, timeLabel;
    @FXML
    private Polygon triangle;
    @FXML
    private AnchorPane textPane;
    @FXML
    private ImageView messageImg, check1, check2, check3;
    @FXML
    private MenuBar menuBar;
    @FXML
    private Button editBtn;
    @FXML
    private TextArea editTextArea;
    @FXML
    private Hyperlink hypertext;

    public MessageView() throws IOException {
    }


    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public ChatListener getChatListener() {
        return chatListener;
    }

    public void setChatListener(ChatListener chatListener) {
        this.chatListener = chatListener;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        userLabel.setText(sender.getUserName());
        forwardLabel.setVisible(message.isForwarded());
        timeLabel.setText(message.getTime().toString());

        if (message.isDeleted()) {
            textLabel.setText(itemConfig.getDeleteText());
            textPane.setStyle(itemConfig.getDeleteStyle());
            menuBar.setStyle(itemConfig.getDeleteStyle());
            triangle.setFill(Color.valueOf(itemConfig.getDeleteColor()));
            triangle.setStroke(Color.valueOf(itemConfig.getDeleteColor()));
            menuBar.setVisible(false);
            messageImg.setVisible(false);
        } else {

            if (message.getText().startsWith("@")) {
                hypertext.setText(message.getText());
                textLabel.setVisible(false);

            } else {
                textLabel.setText(message.getText());
                hypertext.setVisible(false);
            }

            if (sender.getId() == StaticController.getMyUser().getId()) {
                textPane.setStyle(itemConfig.getOwnStyle());
                menuBar.setStyle(itemConfig.getOwnStyle());
                triangle.setFill(Color.valueOf(itemConfig.getOwnColor()));
                triangle.setStroke(Color.valueOf(itemConfig.getOwnColor()));
                messageImg.setVisible(true);
            }
        }

        if (sender.getAvatar() == null) {

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

            ByteArrayInputStream bis = new ByteArrayInputStream(sender.getAvatar());
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


        if (message.getImage() != null) {

            ByteArrayInputStream bis = new ByteArrayInputStream(message.getImage());
            BufferedImage bufferedImage = null;
            try {
                bufferedImage = ImageIO.read(bis);
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert bufferedImage != null;
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            messageImg.setImage(image);

        }

        editBtn.setVisible(false);
        editTextArea.setVisible(false);

        check1.setVisible(message.isCheck1());
        check2.setVisible(message.isCheck2());
        check3.setVisible(message.isCheck3());

    }


    public void seen(MouseEvent mouseEvent) {

        messageListener.listen(new MessageEvent("seen", message));

    }

    public void editMessage(ActionEvent event) {

        if (message.isForwarded()) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(errorConfig.getEditForwarded());
            alert.showAndWait();


        } else {

            editBtn.setVisible(!editBtn.isVisible());
            editTextArea.setVisible(!editTextArea.isVisible());
            editTextArea.setText(textLabel.getText());


        }

    }

    public void deleteMessage(ActionEvent event) {

        MessageEvent messageEvent = new MessageEvent("delete", message);
        messageListener.listen(messageEvent);

    }

    public void edit(ActionEvent event) {

        MessageEvent messageEvent = new MessageEvent("edit", message);
        messageEvent.setEditedText(editTextArea.getText());
        messageListener.listen(messageEvent);

        textLabel.setText(editTextArea.getText());
        editTextArea.setVisible(false);
        editBtn.setVisible(false);

    }

    public void hyper(ActionEvent event) {

        MessageEvent messageEvent = new MessageEvent("hyper", message);
        messageListener.listen(messageEvent);

    }

}
