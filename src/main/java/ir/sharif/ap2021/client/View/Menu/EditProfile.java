package ir.sharif.ap2021.client.View.Menu;

import ir.sharif.ap2021.client.Config.ErrorConfig;
import ir.sharif.ap2021.client.Config.ImageConfig;
import ir.sharif.ap2021.client.Controller.StaticController;
import ir.sharif.ap2021.client.Listener.EditProfileListener;
import ir.sharif.ap2021.shared.Event.EditProfileEvent;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
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
import java.util.ResourceBundle;


public class EditProfile implements Initializable {


    ErrorConfig errorConfig = new ErrorConfig();
    ImageConfig imageConfig = new ImageConfig();

    @FXML
    private TextField firstNameTF, lastNameTf, userNameTF,
            phoneTF, emailTF;
    @FXML
    private Label myLabel;
    @FXML
    private Button subBtn, chooseBtn, backBtn;
    @FXML
    private Circle avatar;
    @FXML
    private TextArea bioText;
    @FXML
    private DatePicker birthday;

    private byte[] data;
    private final EditProfileListener editProfileListener = new EditProfileListener(this);

    public EditProfile() throws IOException {
    }

    public void submit(ActionEvent event) throws IOException, InterruptedException {


        if (firstNameTF.getText().equals("")) {
            myLabel.setText(errorConfig.getEnterFirst());
            return;
        }
        if (lastNameTf.getText().equals("")) {
            myLabel.setText(errorConfig.getEnterLast());
            return;
        }
        if (userNameTF.getText().equals("")) {
            myLabel.setText(errorConfig.getEnterUser());
            return;
        }

        if (emailTF.getText().equals("")) {
            myLabel.setText(errorConfig.getEnterEmail());
            return;
        }

        if (!(emailTF.getText().length() > 6) || !emailTF.getText().contains("@") || !emailTF.getText().endsWith(".com")) {
            myLabel.setText(errorConfig.getValidEmail());
            return;
        }

//        if (!phoneTF.getText().equals("") && (phoneTF.getText().length() > 12 || !checkNumber(phoneTF.getText()))) {
//            isReady = false;
//            myLabel.setText(errorConfig.getValidPhone());
//        }


        EditProfileEvent editProfileEvent = new EditProfileEvent(firstNameTF.getText(),
                lastNameTf.getText(), userNameTF.getText(), phoneTF.getText(), emailTF.getText(),
                bioText.getText(), birthday.getValue());

        if (data != null)
            editProfileEvent.setAvatar(data);

        editProfileListener.listen(editProfileEvent);

    }

    public void chooseFile(ActionEvent event) throws IOException {

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png"));
        File file = fileChooser.showOpenDialog(StaticController.getMyStage());


        if (file != null) {

            BufferedImage bImage = ImageIO.read(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(bImage, "png", bos);
            data = bos.toByteArray();

            Image img = new Image(file.toURI().toString());
            avatar.setFill(new ImagePattern(img));
        }


    }

    public void back(ActionEvent event) throws IOException {
        StaticController.getMyMainMenu().show();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        firstNameTF.setText(StaticController.getMyUser().getFirstName());
        lastNameTf.setText(StaticController.getMyUser().getLastName());
        userNameTF.setText(StaticController.getMyUser().getUserName());
        phoneTF.setText(StaticController.getMyUser().getPhoneNumber());
        emailTF.setText(StaticController.getMyUser().getEmail());
        bioText.setText(StaticController.getMyUser().getBiography());
        birthday.setValue(StaticController.getMyUser().getBirthday());

        if (StaticController.getMyUser().getAvatar() == null) {

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

            ByteArrayInputStream bis = new ByteArrayInputStream(StaticController.getMyUser().getAvatar());
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

    public boolean checkNumber(String s) {

        for (int i = 0; i < s.length(); i++) {
            if (!Character.isDigit(s.charAt(i))) {
                return false;
            }
        }

        return true;
    }

    public void answerError(String errorText) {
        Platform.runLater(() -> myLabel.setText(errorText));

    }

}
