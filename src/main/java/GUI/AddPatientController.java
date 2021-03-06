package GUI;

import Hospital.Patient;
import Tools.AlertToUse;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import java.io.IOException;
import java.util.Optional;

/**
 * The Add Patient controller.
 */
public class AddPatientController {
    /**
     * Setting alerts field
     */
    private AlertToUse alertToUse;
    /**
     * Logger field
     */
    private static final Logger LOGGER = Logger.getLogger(AddPatientController.class.getName());
    /**
     * Patient field
     */
    private Patient patient;
    /**
     * Stage field
     */
    private Stage stage;
    /**
     * The First name field.
     */
    @FXML
    public TextField firstNameField;
    /**
     * The Last name field.
     */
    @FXML
    public TextField lastNameField;
    /**
     * The Social security number field.
     */
    @FXML
    public TextField socialSecurityNumberField;
    /**
     * The Diagnosis field.
     */
    @FXML
    public TextField diagnosisField;
    /**
     * The General practitioner field.
     */
    @FXML
    public TextField generalPractitionerField;
    /**
     * The Cancel button.
     */
    @FXML
    public Button cancelButton;
    /**
     * The Ok button.
     */
    @FXML
    public Button okButton;

    /**
     * Show stage.
     */
    public void showStage() {
        // Create the new stage
        stage = new Stage();

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/AddPatient.fxml"));

            // Set this class as the controller
            loader.setController(this);
            // Load the scene
            stage.setScene(new Scene(loader.load()));
            // Setup the window/stage
            stage.setTitle("Add A Patient");

            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Cancel add patient process.
     */
    public void cancelAbort() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit alert");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to close?");

        Optional<ButtonType> result = alert.showAndWait();

        if (result.get() == ButtonType.OK) {
            this.stage.close();
        } else {
            alert.close();
        }
    }

    /**
     * Adding patient method, click ok to add patient. Loops in order to get correct input.
     *
     * @throws NullPointerException the null pointer exception
     * @throws IOException          the io exception
     */
    public void okAddPatient() throws NullPointerException, IOException {
        try {
            if (firstNameField.getText().isBlank() || lastNameField.getText().isBlank() ||
                    generalPractitionerField.getText().isBlank()) {
                throw new NullPointerException("Fields are empty! Only Diagnosis can be empty");
            }
            if (socialSecurityNumberField.getText().length() != 11) {
                throw new IllegalArgumentException("Social Security Number not 11 digits(String)");
            }

            Patient patientToAdd = new Patient(firstNameField.getText(), lastNameField.getText(),
                    socialSecurityNumberField.getText(), diagnosisField.getText(), generalPractitionerField.getText());
            MainController.addPatientToList(patientToAdd);
            stage.close();
            LOGGER.info("Patient added");
        } catch (NullPointerException nullPointerException) {
            alertToUse = new AlertToUse();
            alertToUse.setAlertErrorAndShow("Invalid input", nullPointerException.getMessage(), "Please correct input (Dagnosis can be empty, rest needs to be filled!");
            LOGGER.error("Following excpetion: " + nullPointerException.getMessage());
        } catch (IllegalArgumentException iae) {
            alertToUse = new AlertToUse();
            alertToUse.setAlertErrorAndShow("Invalid input!", iae.getMessage(), "Please enter correct amount of digits.");
            LOGGER.error("User entered " + socialSecurityNumberField.getText() + " Different from the 11number demand for SSS.");
        }catch (Exception e ){
            LOGGER.error(e.getMessage());
        }
    }


}
