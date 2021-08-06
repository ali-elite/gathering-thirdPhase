package ir.sharif.ap2021.client.View.Menu;

import ir.sharif.ap2021.client.Config.ErrorConfig;
import ir.sharif.ap2021.client.Config.FxmlConfig;
import ir.sharif.ap2021.client.Controller.StaticController;
import ir.sharif.ap2021.client.Listener.ChatListener;
import ir.sharif.ap2021.shared.Event.ChatEvent;
import ir.sharif.ap2021.shared.Model.Chat;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class ChatMenu implements Initializable {


    ChatListener chatListener = new ChatListener();
    ErrorConfig errorConfig = new ErrorConfig();
    FxmlConfig fxmlConfig = new FxmlConfig();

    private static Chat chat;
    private byte[] data;

    @FXML
    private ScrollPane screenScroll;
    @FXML
    private TextArea textField;
    @FXML
    private ToolBar bar;
    @FXML
    private ImageView chosenImg;
    @FXML
    private TextField messageMinuts, messageHour;
    @FXML
    private DatePicker messageDatePicker;
    @FXML
    private Button leaveBtn, getLinkBtn;

    private static final ArrayList<Pane> messages = new ArrayList<>();

    public ChatMenu() throws IOException {
        chatListener.setChatMenu(this);
    }

    public void show() throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlConfig.getChatmenu())));
        Scene scene = new Scene(root);
        Platform.runLater(() -> StaticController.getMyStage().setScene(scene));

    }


    public Chat getChat() {
        return chat;
    }

    public static void setChat(Chat chat) {
        ChatMenu.chat = chat;
    }

    public static ArrayList<Pane> getMessages() {
        return messages;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ChatEvent chatEvent = new ChatEvent("loadChats");
        chatEvent.setTheChat(chat);

        try {
            chatListener.loadChats(chatEvent);
        } catch (IOException e) {
            e.printStackTrace();
        }


        VBox vbox = new VBox();

        for (Pane p : messages) {
            vbox.getChildren().add(p);
        }

        screenScroll.setContent(vbox);

        if (!chat.isGroup()) {
            leaveBtn.setVisible(false);
            getLinkBtn.setVisible(false);
        }

    }


    public void send(ActionEvent event) throws IOException {

        ChatEvent chatEvent = new ChatEvent("send");
        chatEvent.setChatId(chat.getId());
        chatEvent.setPm(textField.getText());
        if (data != null) {
            chatEvent.setImage(data);
        }


        if (textField.getText().length() <= 300) {
            chatListener.listen(chatEvent);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(errorConfig.getMessageLength());
            alert.showAndWait();
        }
    }

    public void attach(ActionEvent event) throws IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png"));
        File file = fileChooser.showOpenDialog(StaticController.getMyStage());


        if (file != null) {

            BufferedImage bImage = ImageIO.read(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "png", bos);
            data = bos.toByteArray();

            Image img = new Image(file.toURI().toString());
            chosenImg.setImage(img);
        }

    }


    public void back(ActionEvent event) throws IOException {
        StaticController.getMyMainMenu().show();
    }


    public void afterSend() {

        Platform.runLater(() -> {
            textField.setText(null);
            chosenImg.setImage(null);
            initialize(null, null);
        });


    }


    public void sendScheduled(ActionEvent actionEvent) {


        if (!checkDigit(messageHour.getText()) || Integer.parseInt(messageHour.getText()) > 24 || Integer.parseInt(messageHour.getText()) < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(errorConfig.getInvalidHour());
            alert.showAndWait();
            return;
        }
        if (!checkDigit(messageMinuts.getText()) || Integer.parseInt(messageMinuts.getText()) > 60 || Integer.parseInt(messageMinuts.getText()) < 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(errorConfig.getInvalidMinuts());
            alert.showAndWait();
            return;
        }

        LocalDate date = messageDatePicker.getValue();
        LocalTime time = LocalTime.of(Integer.parseInt(messageHour.getText()), Integer.parseInt(messageMinuts.getText()));
        LocalDateTime localDateTime = LocalDateTime.of(date, time);

        if (localDateTime.isBefore(LocalDateTime.now())) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(errorConfig.getTimeMachine());
            alert.showAndWait();

        } else {

            ChatEvent chatEvent = new ChatEvent("sendScheduled");
            chatEvent.setChatId(chat.getId());
            chatEvent.setPm(textField.getText());
            chatEvent.setLocalDateTime(localDateTime);
            if (data != null) {
                chatEvent.setImage(data);
            }

            if (textField.getText().length() <= 300) {
                chatListener.listen(chatEvent);
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText(errorConfig.getMessageLength());
                alert.showAndWait();
            }
        }


    }

    public void leaveGroup(ActionEvent actionEvent) {

        ChatEvent chatEvent = new ChatEvent("leaveGroup");
        chatEvent.setChatId(chat.getId());
        if (chat.getUsers().contains(StaticController.getMyUser())) {
            chatListener.listen(chatEvent);
        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(errorConfig.getAlreadyLeftGp());
            alert.showAndWait();

        }


    }

    public void groupLink(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("The Group Id is: \n" + " @gp_" + chat.getId());
        alert.showAndWait();

    }

    private boolean checkDigit(String s) {

        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }


}
