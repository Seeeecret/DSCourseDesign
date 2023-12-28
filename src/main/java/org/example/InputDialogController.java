package org.example;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class InputDialogController {

    @FXML
    private TextField inputField;

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void onSaveButtonClick() {
        // Handle saving data or perform any other actions
        String inputData = inputField.getText();
        System.out.println("Input data saved: " + inputData);

        // Close the dialog
        stage.close();
    }
}
