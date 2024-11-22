package viewmodel;

import com.azure.storage.blob.BlobClient;
import dao.DbConnectivityClass;
import dao.StorageUploader;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Person;
import service.MyLogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.control.ProgressBar;
import viewmodel.Major;

public class DB_GUI_Controller implements Initializable {

    //Documentation passes Connection String here, but ours is refactored to the programs design
    StorageUploader store = new StorageUploader();
    @FXML
    TextField first_name, last_name, department,  email, imageURL;
    @FXML
    private Button addBtn, clearBtn, editBtn, deleteBtn;
    @FXML
    private Label userUpdateLbl, emailVldLbl, f_NameVldLbl, l_NameVldLbl, majorValidLbl;
    @FXML
    private ComboBox<Major> majorComboBox;
    @FXML
    ImageView img_view;
    @FXML
    MenuBar menuBar;
    @FXML
    private TableView<Person> tv;
    @FXML
    private TableColumn<Person, Integer> tv_id;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private TableColumn<Person, String> tv_fn, tv_ln, tv_department, tv_major, tv_email;

    private final DbConnectivityClass cnUtil = new DbConnectivityClass();
    private final ObservableList<Person> data = cnUtil.getData();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            //disable edit + delete button unless a record is selected
            editBtn.disableProperty().bind(tv.getSelectionModel().selectedItemProperty().isNull());
            deleteBtn.disableProperty().bind(tv.getSelectionModel().selectedItemProperty().isNull());

            //majorComboBox.getItems().setAll(Major.values());
            majorComboBox.setItems(FXCollections.observableArrayList(Major.values()));

            //add button validation based on regex
            //email validation
            email.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {  // Focus lost
                    validateEmail();
                    validateAddUser();
                }
            });
            //name validation - first
            first_name.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {  // Focus lost
                    validateFirstName();
                    validateAddUser();
                }
            });
            //last name
            last_name.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (!newValue) {  // Focus lost
                    validateLastName();
                    validateAddUser();
                }
            });
            //Listener to update departmentTF on major selection from combobox
            majorComboBox.valueProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    department.setText(newValue.getMajorCode());
                    majorValidLbl.setVisible(false);
                    majorValidLbl.setManaged(false);
                } else {
                    majorValidLbl.setVisible(true);
                    majorValidLbl.setManaged(true);
                    department.clear();
                }
                validateAddUser(); //Call validate for add button after combo box interaction
            });

            //Should I mandate a file upload validation wise?

            tv_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            tv_fn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
            tv_ln.setCellValueFactory(new PropertyValueFactory<>("lastName"));
            tv_department.setCellValueFactory(new PropertyValueFactory<>("department"));
            tv_major.setCellValueFactory(new PropertyValueFactory<>("major"));
            tv_email.setCellValueFactory(new PropertyValueFactory<>("email"));
            tv.setItems(data);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    protected void addNewRecord() {
        Major selectedMajor = majorComboBox.getValue(); // Get selected enum value

        if (selectedMajor == null) {
            // Handle the case where no major is selected
            System.out.println("Please select a major.");
            //Change console to gui
            return;
        }

            Person p = new Person(first_name.getText(), last_name.getText(), department.getText(),
                    selectedMajor, email.getText(), imageURL.getText());
            cnUtil.insertUser(p);
            cnUtil.retrieveId(p);
            p.setId(cnUtil.retrieveId(p));
            data.add(p);
            userUpdateLbl.setText("User Successfully Added!");
            //clear form after adding user
            clearForm();

    }

    @FXML
    protected void clearForm() {
        first_name.setText("");
        last_name.setText("");
        department.setText("");
        majorComboBox.setValue(null);
        majorComboBox.setPromptText("Select a Major");
        email.setText("");
        imageURL.setText("");
        validateAddUser();
    }

    @FXML
    protected void logOut(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
            Scene scene = new Scene(root, 900, 600);
            scene.getStylesheets().add(getClass().getResource("/css/lightTheme.css").getFile());
            Stage window = (Stage) menuBar.getScene().getWindow();
            window.setScene(scene);
            window.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void closeApplication() {
        System.exit(0);
    }

    //Should I make this display my readme?
    @FXML
    protected void displayAbout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/view/about.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root, 600, 500);
            stage.setScene(scene);
            stage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //This needs work -> analyze
    //Email and imgURL cannot be updated because of thrown error in current state
    //potential work around is to just restructure constructor but maybe i can fix later
    @FXML
    protected void editRecord() {
        Person p = tv.getSelectionModel().getSelectedItem();
        int index = data.indexOf(p);
        Person p2 = new Person(index + 1, first_name.getText(), last_name.getText(), department.getText(),
                majorComboBox.getValue(), email.getText(),  imageURL.getText());
        cnUtil.editUser(p.getId(), p2);
        data.remove(p);
        data.add(index, p2);
        tv.getSelectionModel().select(index);
        System.out.println("Edited?");
        userUpdateLbl.setText("User Successfully Edited!");
    }

    //also this?
    @FXML
    protected void deleteRecord() {
        Person p = tv.getSelectionModel().getSelectedItem();
        int index = data.indexOf(p);
        cnUtil.deleteRecord(p);
        data.remove(index);
        tv.getSelectionModel().select(index);
    }


    //If image is not null, show image and progress bar of upload
    @FXML
    protected void showImage() {
        File file = (new FileChooser()).showOpenDialog(img_view.getScene().getWindow());
        if (file != null) {
            img_view.setImage(new Image(file.toURI().toString()));

            Task<Void> uploadTask = createUploadTask(file, progressBar);
            progressBar.progressProperty().bind(uploadTask.progressProperty());
            new Thread(uploadTask).start();
        }
    }
    //Populate imageURL field from StorageUploader uploadFile
    //uploadFile returns void, so figure out how to get the imageURL to populate that field for person
    private Task<Void> createUploadTask(File file, ProgressBar progressBar) {
        return new Task<>() {
            @Override
            protected Void call() throws Exception {
                BlobClient blobClient = store.getContainerClient().getBlobClient(file.getName());
                long fileSize = Files.size(file.toPath());
                long uploadedBytes = 0;

                try (FileInputStream fileInputStream = new FileInputStream(file);
                     OutputStream blobOutputStream = blobClient.getBlockBlobClient().getBlobOutputStream()) {

                    byte[] buffer = new byte[1024 * 1024]; // 1 MB buffer size
                    int bytesRead;

                    while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                        blobOutputStream.write(buffer, 0, bytesRead);
                        uploadedBytes += bytesRead;

                        // Calculate and update progress as a percentage
                        int progress = (int) ((double) uploadedBytes / fileSize * 100);
                        updateProgress(progress, 100);
                    }
                }

                return null;
            }
        };
    }

    @FXML
    protected void addRecord() {
        showSomeone();
    }

    //This method needs love / is throwing issues -> Can I fix this to give me the id of a selected user to edit in TV?
   //first ensure major is not being over written elsewhere
    @FXML
    protected void selectedItemTV(MouseEvent mouseEvent) {
        clearForm();
        Person p = tv.getSelectionModel().getSelectedItem();
        first_name.setText(p.getFirstName());
        last_name.setText(p.getLastName());
        department.setText(p.getDepartment());
        majorComboBox.setPromptText(p.getMajor().getDisplayName());
        email.setText(p.getEmail());
        imageURL.setText(p.getImageURL());
    }

    public void lightTheme(ActionEvent actionEvent) {
        try {
            Scene scene = menuBar.getScene();
            Stage stage = (Stage) scene.getWindow();
            stage.getScene().getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("/css/lightTheme.css").toExternalForm());
            stage.setScene(scene);
            stage.show();
            System.out.println("light " + scene.getStylesheets());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void darkTheme(ActionEvent actionEvent) {
        try {
            Stage stage = (Stage) menuBar.getScene().getWindow();
            Scene scene = stage.getScene();
            scene.getStylesheets().clear();
            scene.getStylesheets().add(getClass().getResource("/css/darkTheme.css").toExternalForm());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showSomeone() {
        Dialog<Results> dialog = new Dialog<>();
        dialog.setTitle("New User");
        dialog.setHeaderText("Please specify…");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        TextField textField1 = new TextField("Name");
        TextField textField2 = new TextField("Last Name");
        TextField textField3 = new TextField("Email ");
        ObservableList<Major> options =
                FXCollections.observableArrayList(Major.values());
        ComboBox<Major> comboBox = new ComboBox<>(options);
        comboBox.getSelectionModel().selectFirst();
        dialogPane.setContent(new VBox(8, textField1, textField2,textField3, comboBox));
        Platform.runLater(textField1::requestFocus);
        dialog.setResultConverter((ButtonType button) -> {
            if (button == ButtonType.OK) {
                return new Results(textField1.getText(),
                        textField2.getText(), comboBox.getValue());
            }
            return null;
        });
        Optional<Results> optionalResult = dialog.showAndWait();
        optionalResult.ifPresent((Results results) -> {
            MyLogger.makeLog(
                    results.fname + " " + results.lname + " " + results.major);
        });
    }

 //   private static enum Major {BUSINESS, CSC, CPIS, Mathematics}

    private static class Results {

        String fname;
        String lname;
        Major major;

        public Results(String name, String date, Major venue) {
            this.fname = name;
            this.lname = date;
            this.major = venue;
        }
    }

    private void validateEmail() {
        //Regex pattern to match any farmingdale.edu email address that starts
        //with a case-insensitive letter and has at least one character following
        final String regexEmailPattern = "(([a-zA-z])(\\w)+)@(\\w+)[.](\\w+)";
        final Pattern pattern = Pattern.compile(regexEmailPattern, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(email.getText());

        if (email.getText() == null || email.getText().trim().isEmpty() || !matcher.matches()) {
            emailVldLbl.setVisible(true);
            emailVldLbl.setManaged(true);
        } else {
            emailVldLbl.setVisible(false);
            emailVldLbl.setManaged(false);
        }
    }
    //Helper methods to accommodate both first name and last name using the same pattern
    private void validateFirstName() {
        validateName(first_name.getText(), f_NameVldLbl);
    }

    private void validateLastName() {
        validateName(last_name.getText(), l_NameVldLbl);
    }

    private void validateName(String name, Label validationLabel) {
        //Pattern to capture occasional name's with hyphens or apostrophes as valid
        //Any letters any case [A-Z] including hyphens or apostrophes
        final String regexNamePattern = "([a-zA-Z'-]{2,25})";
        final Pattern pattern = Pattern.compile(regexNamePattern, Pattern.MULTILINE);
        final Matcher matcher = pattern.matcher(name);
        // Example validation: Show the label if the first name is empty or null
        // or doesn't match pattern
        if (name == null || name.trim().isEmpty() || !matcher.matches()) {
            validationLabel.setVisible(true);
            validationLabel.setManaged(true);
        } else {
            validationLabel.setVisible(false);
            validationLabel.setManaged(false);
        }
    }

    //imgURL not currently validated
    private void validateAddUser() {
        boolean firstNameValid = !f_NameVldLbl.isVisible() && first_name != null && !first_name.getText().trim().isEmpty();
        boolean lastNameValid = !l_NameVldLbl.isVisible() && l_NameVldLbl != null && !last_name.getText().trim().isEmpty();
        boolean emailValid = !emailVldLbl.isVisible() && email != null && !email.getText().trim().isEmpty();
        boolean majorSelected = majorComboBox.getValue() != null; // Check if a major is selected


        addBtn.setDisable(!(firstNameValid && lastNameValid && !imageURL.getText().isEmpty() && emailValid && majorComboBox.getValue() != null));
    }

}