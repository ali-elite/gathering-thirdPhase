package ir.sharif.ap2021.client.View.ModelView;

import ir.sharif.ap2021.client.Config.ErrorConfig;
import ir.sharif.ap2021.client.Config.FxmlConfig;
import ir.sharif.ap2021.client.Config.ImageConfig;
import ir.sharif.ap2021.client.Controller.StaticController;
import ir.sharif.ap2021.client.Listener.ThoughtListener;
import ir.sharif.ap2021.client.View.Menu.ForwardSelection;
import ir.sharif.ap2021.shared.Event.ThoughtEvent;
import ir.sharif.ap2021.shared.Model.Thought;
import ir.sharif.ap2021.shared.Model.User;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;

public class OutThought implements Initializable {

    ErrorConfig errorConfig = new ErrorConfig();
    ImageConfig imageConfig = new ImageConfig();
    FxmlConfig fxmlConfig = new FxmlConfig();

    ThoughtListener thoughtListener = new ThoughtListener();

    private static User mainUser;
    private static User ownerUser;
    private static User doedUser;
    private static Thought thought;


    @FXML
    private Label text;
    @FXML
    private Circle avatar;
    @FXML
    private Label likes, rets, opinions, statusLabel, timeLabel, namesLabel;
    @FXML
    private ImageView likeIMG, retIMG, tIMG, oIMG;
    @FXML
    private TextArea replyText;
    @FXML
    private Button replyBtn, insertBtn;
    @FXML
    private AnchorPane replyPane;

    private byte[] data;
    private final ArrayList<Pane> comments = new ArrayList<>();

    public OutThought() throws IOException {
        thoughtListener.setOutThought(this);
    }


    public ArrayList<Pane> getComments() {
        return comments;
    }

    public static User getMainUser() {
        return mainUser;
    }

    public static void setMainUser(User mainUser) {
        OutThought.mainUser = mainUser;
    }

    public static User getOwnerUser() {
        return ownerUser;
    }

    public static void setOwnerUser(User ownerUser) {
        OutThought.ownerUser = ownerUser;
    }

    public static User getDoedUser() {
        return doedUser;
    }

    public static void setDoedUser(User doedUser) {
        OutThought.doedUser = doedUser;
    }

    public static Thought getThought() {
        return thought;
    }

    public static void setThought(Thought thought) {
        OutThought.thought = thought;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (thought.getLikers().contains(
                StaticController.getMyUser())) {

            likeIMG.setImage(new Image(imageConfig.getFillLike()));
        } else {
            likeIMG.setImage(new Image(imageConfig.getLike()));
        }

        if (thought.getRethoughters().contains(StaticController.getMyUser())) {
            retIMG.setImage(new Image(imageConfig.getFillRet()));
        } else {
            retIMG.setImage(new Image(imageConfig.getRet()));
        }

        namesLabel.setText(ownerUser.getFirstName() + " " + ownerUser.getLastName());
        timeLabel.setText(String.valueOf(thought.getLocalDateTime()));
        text.setText(thought.getText());
        likes.setText(String.valueOf(thought.getLikes()));
        rets.setText(String.valueOf(thought.getRethought()));
        opinions.setText(String.valueOf(thought.getOpinions().size()));
        if (thought.getType().equals("t")) {
            statusLabel.setText(errorConfig.getShareTitle());
        } else {
            statusLabel.setText(errorConfig.getReplyTitle() + doedUser.getUserName());
        }

        if (ownerUser.getAvatar() == null) {

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

            ByteArrayInputStream bis = new ByteArrayInputStream(ownerUser.getAvatar());
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


        if (thought.getImage() != null) {

            ByteArrayInputStream bis = new ByteArrayInputStream(thought.getImage());
            BufferedImage bufferedImage = null;
            try {
                bufferedImage = ImageIO.read(bis);
            } catch (IOException e) {
                e.printStackTrace();
            }
            assert bufferedImage != null;
            Image image = SwingFXUtils.toFXImage(bufferedImage, null);
            tIMG.setImage(image);

        }


        replyBtn.setVisible(false);
        replyText.setVisible(false);
        insertBtn.setVisible(false);
        oIMG.setVisible(false);

    }


    public void reload() {
        initialize(null, null);
    }


    public void like(MouseEvent mouseEvent) throws IOException {


        ThoughtEvent thoughtChangeEvent = new ThoughtEvent("like", thought.getId());
        thoughtListener.listen(thoughtChangeEvent);


    }

    public void ret(MouseEvent mouseEvent) throws IOException {

        ThoughtEvent thoughtChangeEvent = new ThoughtEvent("ret", thought.getId());
        thoughtListener.listen(thoughtChangeEvent);


    }

    public void mention(MouseEvent mouseEvent) throws IOException {

        replyBtn.setVisible(!replyBtn.isVisible());
        replyText.setVisible(!replyText.isVisible());
        insertBtn.setVisible(!insertBtn.isVisible());
        oIMG.setVisible(!oIMG.isVisible());

    }


    public void saveMessage(ActionEvent event) throws IOException {

        ThoughtEvent thoughtChangeEvent = new ThoughtEvent("saveMessage", thought.getId());
        thoughtListener.listen(thoughtChangeEvent);

    }

    public void forwardMessage(ActionEvent event) throws IOException {

        ChatForwardView.setThought(thought);
        ForwardSelection forwardSelection = new ForwardSelection();
        forwardSelection.show();

    }

    public void muteAuthor(ActionEvent event) throws IOException {

        if (thought.getUserId() == StaticController.getMyUser().getId()) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(errorConfig.getSelfMute());
            alert.showAndWait();
            return;

        }

        if (StaticController.getMyUser().getMuteList().contains(thought.getUser().getId())) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(errorConfig.getAlreadyMuted());
            alert.showAndWait();


        } else {

            ThoughtEvent thoughtChangeEvent = new ThoughtEvent("muteAuthor", thought.getId());
            thoughtListener.listen(thoughtChangeEvent);


        }

    }

    public void reportSpam(ActionEvent event) throws IOException {

        ThoughtEvent thoughtChangeEvent = new ThoughtEvent("spam", thought.getId());
        thoughtListener.listen(thoughtChangeEvent);


    }

    public void authorProfile(ActionEvent event) throws IOException {

        if (mainUser.getId() == ownerUser.getId()) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(errorConfig.getYourThought());
            alert.showAndWait();

        } else {

            OutProfile.setUser(doedUser);
            OutProfile.setFrom(null);
            OutProfile outProfile = new OutProfile();
            outProfile.show();

        }


    }

    public void showOpinions(ActionEvent event) throws IOException {

        if (thought.getOpinions().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(errorConfig.getYourThought());
            alert.showAndWait();

        } else {

            ThoughtEvent thoughtChangeEvent = new ThoughtEvent("opinions", thought.getId());
            thoughtListener.listen(thoughtChangeEvent);
        }
    }


    public void reply(ActionEvent event) throws IOException {

        ThoughtEvent thoughtChangeEvent = new ThoughtEvent("mention", thought.getId());
        thoughtChangeEvent.setMentionText(replyText.getText());
        if(data != null){
            thoughtChangeEvent.setMentionImg(data);
        }


        if (replyText.getText().length() <= 300) {
            thoughtListener.listen(thoughtChangeEvent);
            replyText.setText(null);

        } else {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(errorConfig.getOpinionLength());
            alert.showAndWait();

        }

    }


    public void insert(ActionEvent event) throws IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png"));
        File file = fileChooser.showOpenDialog(StaticController.getMyStage());


        if (file != null) {

            BufferedImage bImage = ImageIO.read(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "png", bos);
            data = bos.toByteArray();

            Image img = new Image(file.toURI().toString());
            tIMG.setImage(img);

        }

    }

    public void doBack(ActionEvent actionEvent) throws IOException {
        StaticController.getMyMainMenu().show();
    }

    public void getLink(ActionEvent actionEvent) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("The Thought Id is: \n" + " @thought_" + thought.getId());
        alert.showAndWait();

    }

    public void show() throws IOException {

        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlConfig.getOutThought())));
        Scene scene = new Scene(root);
        Platform.runLater(() -> StaticController.getMyStage().setScene(scene));

    }
}
