package viewmodel;

import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.User;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import service.UserSession;


public class LoginController {


    @FXML
    private Button loginBtn, signUpButton;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Label usernameLabel, passwordLabel, credentialsLbl;

    @FXML
    private GridPane rootpane;

    @FXML
    private TextField usernameTextField;

    public void initialize() {
        rootpane.setBackground(new Background(
                        createImage("https://edencoding.com/wp-content/uploads/2021/03/layer_06_1920x1080.png"),
                        null,
                        null,
                        null,
                        null,
                        null
                )
        );


        rootpane.setOpacity(0);
        FadeTransition fadeOut2 = new FadeTransition(Duration.seconds(10), rootpane);
        fadeOut2.setFromValue(0);
        fadeOut2.setToValue(1);
        fadeOut2.play();
        credentialsLbl.setVisible(false);
    }
    private static BackgroundImage createImage(String url) {
        return new BackgroundImage(
                new Image(url),
                BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT,
                new BackgroundPosition(Side.LEFT, 0, true, Side.BOTTOM, 0, true),
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, true, true, false, true));
    }
    @FXML
    public void login(ActionEvent actionEvent) {
        String username = usernameTextField.getText();
        String password = passwordField.getText();
        User user = new User(username, password);
        if (UserSession.getInstance().findUser(user)) {
            System.out.println("User found in instance!");

            //move try statement here
        } else {
            System.out.println("login failed");
            credentialsLbl.setVisible(true);
            //do something to tell user login failed, label or pop up
        }

        try {
            //staticStage.getScene().setRoot(accountHome(DataCenter.getInstance().userList.get(DataCenter.getInstance().userIdx(user))));
            Parent root = FXMLLoader.load(getClass().getResource("/view/db_interface_gui.fxml"));
            Scene scene = new Scene(root, 900, 600);

            //this choice will be gotten from privileges once that works
            //implement that part here by getting privileges from gotten user idx's data member privileges
            //and implement that somehow
            scene.getStylesheets().add(getClass().getResource("/css/lightTheme.css").toExternalForm());
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
//
//        try {
//            Parent root = FXMLLoader.load(getClass().getResource("/view/db_interface_gui.fxml"));
//            Scene scene = new Scene(root, 900, 600);
//
//            //this choice will be gotten from privileges once that works
//            //implement that part here by getting privileges from gotten user idx's data member privileges
//            //and implement that somehow
//            scene.getStylesheets().add(getClass().getResource("/css/lightTheme.css").toExternalForm());
//            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
//            window.setScene(scene);
//            window.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public void signUp(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/signUp.fxml"));
            Scene scene = new Scene(root, 900, 600);
            scene.getStylesheets().add(getClass().getResource("/css/lightTheme.css").toExternalForm());
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
