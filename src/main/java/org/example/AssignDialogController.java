package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.*;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AssignDialogController {

    @FXML
    private TextField variableField;

    @FXML
    private TextField valueField;

    @FXML
    private ComboBox<String> trigModeComboBox;
    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void onSaveButtonClick() {
        try {
            // Validate input
            char variable = variableField.getText().charAt(0);
            double value = Double.parseDouble(valueField.getText());
            String trigMode = trigModeComboBox.getValue();

            // Perform actions with the input data (e.g., assign value to variable)
            // You can add your logic here based on the user input

            // Show success alert
            showSuccessAlert("Data saved successfully!");

            // Close the dialog
            stage.close();
        } catch (NumberFormatException e) {
            // Handle parsing errors (e.g., if the value is not a valid double)
            showErrorAlert("Invalid input format. Please enter a valid number.");
        } catch (Exception e) {
            // Handle other validation errors
            showErrorAlert("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void showSuccessAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showErrorAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
