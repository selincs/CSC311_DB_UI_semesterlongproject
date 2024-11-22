package viewmodel;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SignUpController {

    @FXML
    private Label usernameLbl, usernameVldLbl, pwLbl, pwVldLbl, dobLbl, dobVldLbl;

    @FXML
    private TextField usernameTF, pwTF, dobTF;

    @FXML
    private Button goBackBtn, newAccountBtn;

    public void initialize() {
        usernameVldLbl.setVisible(false);
        usernameTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {  // Focus lost
                validateUsername();
                validateCreateAcc();
            }
        });
        pwVldLbl.setVisible(false);
        pwTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {  // Focus lost
                validatePassword();
                validateCreateAcc();
            }
        });
        dobVldLbl.setVisible(false);
        dobTF.focusedProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {  // Focus lost
                validateDateOfBirth();
                validateCreateAcc();
            }
        });

        newAccountBtn.setDisable(true);
    }

    public void createNewAccount(ActionEvent actionEvent) {
        //Validation goes here

        //If account creation successful
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Account successfully created! Return to login.");
        alert.showAndWait();

        //else account creation failed, show labels

    }

    //Create acc button enabling validation
    private void validateCreateAcc() {
        boolean usernameValid = !usernameVldLbl.isVisible() && usernameTF != null && !usernameTF.getText().trim().isEmpty();
        boolean pwValid = !pwVldLbl.isVisible() && pwTF != null && !pwTF.getText().trim().isEmpty();
        boolean dobValid = !dobVldLbl.isVisible() && dobTF != null && !dobTF.getText().trim().isEmpty();

        newAccountBtn.setDisable(!(usernameValid && pwValid && dobValid));
    }

    public void goBack(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            Scene scene = new Scene(root, 900, 600);
            scene.getStylesheets().add(getClass().getResource("/css/lightTheme.css").toExternalForm());
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void validateUsername() {
        //Pattern to capture occasional name's with hyphens or apostrophes as valid
        //Any letters any case [A-Z] any digit[0-9] including hyphens, underscores or apostrophes, min 4 max 18 chars
        final String regexNamePattern = "([a-zA-Z0-9'_-]{4,18})";
        final Pattern pattern = Pattern.compile(regexNamePattern, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(usernameTF.getText());
        // Example validation: Show the label if the first name is empty or null
        // or doesn't match pattern
        if (usernameTF.getText() == null || usernameTF.getText().trim().isEmpty() || !matcher.matches()) {
            usernameVldLbl.setVisible(true);
        } else {
            usernameVldLbl.setVisible(false);
        }
    }

    private void validatePassword() {
        //Pattern to capture occasional name's with hyphens or apostrophes as valid
        //Any letters any case [A-Z] any digit[0-9] including hyphens, underscores or apostrophes, min 4 max 18 chars
        final String regexPwPattern = "([a-zA-Z0-9'_!@#$%^&*-]{6,18})";
        final Pattern pattern = Pattern.compile(regexPwPattern, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(pwTF.getText());
        // Example validation: Show the label if the first name is empty or null
        // or doesn't match pattern
        if (pwTF.getText() == null || pwTF.getText().trim().isEmpty() || !matcher.matches()) {
            pwVldLbl.setVisible(true);
        } else {
            pwVldLbl.setVisible(false);
        }
    }

    private void validateDateOfBirth() {
        //Regex pattern to capture any living human date
        // MM as 0+[1-9] or 1+[0,1, or 2] , "-" or "/" , DD as [01-31], "-" or "/", YYYY as 19## or 20##
        final String regexUSDatePattern =
                "((0[1-9])|(1[0-2]))[-/]((0[1-9])|([12][0-9])|(3[01]))[-/]((19|20)\\d{2})";
        final Pattern pattern = Pattern.compile(regexUSDatePattern, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(dobTF.getText());
        if (dobTF.getText() == null || dobTF.getText().trim().isEmpty() || !matcher.matches()) {
            dobVldLbl.setVisible(true);
        } else {
            dobVldLbl.setVisible(false);
        }
    }
}
