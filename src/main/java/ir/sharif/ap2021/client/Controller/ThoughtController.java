package ir.sharif.ap2021.client.Controller;

import ir.sharif.ap2021.client.Config.ErrorConfig;
import ir.sharif.ap2021.client.Config.FxmlConfig;
import ir.sharif.ap2021.client.View.Menu.Opinions;
import ir.sharif.ap2021.client.View.ModelView.ThoughtView;
import ir.sharif.ap2021.shared.Model.Thought;
import ir.sharif.ap2021.shared.Response.ThoughtResponse;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;


import java.io.IOException;


public class ThoughtController {

    ErrorConfig errorConfig = new ErrorConfig();
    FxmlConfig fxmlConfig = new FxmlConfig();

    public ThoughtController() throws IOException {
    }


    public void change(ThoughtResponse response, ThoughtView thoughtView) throws IOException {

        if (response.getOrder().equals("like")) {

            StaticController.setMyUser(response.getMyUser());
            Platform.runLater(() -> {
                thoughtView.setThought(response.getThought());
                thoughtView.reload();
            });


        }


        if (response.getOrder().equals("ret")) {

            StaticController.setMyUser(response.getMyUser());
            Platform.runLater(() -> {
                thoughtView.setThought(response.getThought());
                thoughtView.reload();
            });

        }

        if (response.getOrder().equals("mention")) {


            StaticController.setMyUser(response.getMyUser());
            Platform.runLater(() -> {
                thoughtView.setThought(response.getThought());
                thoughtView.reload();
            });


        }

        if (response.getOrder().equals("muteAuthor")) {

            StaticController.setMyUser(response.getMyUser());

            Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(errorConfig.getMuted());
            alert.showAndWait();

            });

        }

        if (response.getOrder().equals("spam")) {

            Platform.runLater(() -> {
            thoughtView.setThought(response.getThought());

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText(errorConfig.getSpammed());
            alert.showAndWait();

            });


        }


        if (response.getOrder().equals("saveMessage")) {

            StaticController.setMyUser(response.getMyUser());

        }


    }

    public void opinion(ThoughtResponse response) throws IOException {

        Thought thought = response.getThought();
        Thought parent = response.getParent();
        System.out.println(parent);
        StaticController.setLastThought(parent);
        Opinions.getOpinions().clear();

        for (int i = response.getOpinions().size() - 1; i > -1; i--) {

            ThoughtView thoughtView = new ThoughtView();
            Thought opinion = response.getOpinions().get(i);

            thoughtView.setThought(opinion);
            thoughtView.setOwnerUser(thought.getUser());
            thoughtView.setMainUser(StaticController.getMyUser());
            thoughtView.setDoedUser(thought.getDoed());


            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlConfig.getThought()));
            loader.setController(thoughtView);

            Opinions.getOpinions().add((Pane) loader.load());
        }

        Opinions opinions = new Opinions();
        opinions.show();

    }

}
