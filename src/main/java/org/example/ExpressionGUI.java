package org.example;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.pojo.Expression;
import org.example.pojo.ExpressionTree;
import org.example.utils.ExpressionUtil;

import java.io.IOException;

public class ExpressionGUI extends Application {

    @FXML
    public Button openInputDialogButton;
    @FXML
    private TextField inputExpression;
    @FXML
    private Button readButton;
    @FXML
    private Button writeButton;
    @FXML
    private Button assignButton;
    @FXML
    private Button valueButton;
    @FXML
    private Button compoundButton;
    @FXML
    private Button diffButton;
    @FXML
    private Button mergeConstButton;
    @FXML
    private TextArea outputTextArea;
    private static Scene scene;
    private ExpressionTree expressionTree;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("ExpressionGUI.fxml"));
//        loader.setController(this);
//
//        VBox root = loader.load();
        Scene scene = new Scene(loadFXML("ExpressionGUI"), 600, 400);

        primaryStage.setTitle("Expression Parser GUI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(ExpressionGUI.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }
    @FXML
    private void onReadButtonClick() {
        expressionTree = new ExpressionTree();
        String input = inputExpression.getText();
        expressionTree = ExpressionUtil.testReadExpr(expressionTree, input);
        outputTextArea.appendText("Expression read successfully.\n");
    }

    @FXML
    private void onWriteButtonClick() {
        outputTextArea.appendText("Infix expression: ");
        String writeExpr = ExpressionUtil.testWriteExpr(expressionTree);
        outputTextArea.appendText(writeExpr);
        outputTextArea.appendText("\n");
    }

//    @FXML
//    private void onAssignButtonClick() {
//        char variable = 'x'; // You can modify this to get the variable from the user
//        ExpressionUtil.Assign(variable, 5, expressionTree);
//        outputTextArea.appendText("Variable assigned successfully.\n");
//    }

    @FXML
    private void onValueButtonClick() {
        double result = ExpressionUtil.Value(expressionTree);
        outputTextArea.appendText("Expression value: " + result + "\n");
    }

    @FXML
    private void onCompoundButtonClick() {
        ExpressionTree compoundExpression = new ExpressionTree();
        ExpressionUtil.testReadExpr(compoundExpression,"+71");
        ExpressionTree compoundedExpr1 = ExpressionUtil.CompoundExpr('*', expressionTree, compoundExpression);

        ExpressionTree compoundedExpr2 = ExpressionUtil.CompoundExpr('+', compoundExpression, expressionTree);
        outputTextArea.appendText("Infix compound expression: ");
        outputTextArea.appendText(ExpressionUtil.testWriteExpr(compoundedExpr1));
        outputTextArea.appendText("\n");
    }

    @FXML
    private void onDiffButtonClick() {
        String inputString1 = "+++*3^x3*2^x2x6";
        ExpressionTree E1 = ExpressionUtil.testReadExpr(new Expression(), inputString1);
        char variable = 'x'; // You can modify this to get the variable from the user
        ExpressionUtil.Diff(E1, "x");
        outputTextArea.appendText("Derivative expression: ");
        outputTextArea.appendText(ExpressionUtil.testWriteExpr(E1));
        outputTextArea.appendText("\n");
    }

    @FXML
    private void onMergeConstButtonClick() {
        String inputString7 = "+^x31";
        ExpressionTree E7 = ExpressionUtil.testReadExpr(new Expression(), inputString7);
        String inputString8 = "+*3*3^x21";
        ExpressionTree E8 = ExpressionUtil.testReadExpr(new Expression(), inputString8);
        String inputString2 = "++*15^x2*8x";
        ExpressionTree E2 = ExpressionUtil.testReadExpr(new Expression(), inputString2);
        E2 = ExpressionUtil.MergeConst(E2);
        outputTextArea.appendText("Constants merged successfully:");
        outputTextArea.appendText(ExpressionUtil.testWriteExpr(E2));
    }
    @FXML
    private void onOpenInputDialogButtonClick() {
        try {
            // Load the InputDialog FXML file
            FXMLLoader loader = new FXMLLoader(ExpressionGUI.class.getResource("InputDialog.fxml"));
//            VBox inputDialogRoot = loader.load();
//            Parent inputDialog = loadFXML("InputDialog");
//            AnchorPane root = (AnchorPane) inputDialog;
            AnchorPane load = loader.load();

            // Create a new stage for the input dialog
            Stage inputDialogStage = new Stage();
            inputDialogStage.initModality(Modality.APPLICATION_MODAL);
            inputDialogStage.setTitle("Input Dialog");

            // Set the InputDialogController for the loaded FXML
            InputDialogController inputDialogController = loader.getController();
            inputDialogController.setStage(inputDialogStage);

            // Set the scene and show the input dialog
            Scene inputDialogScene = new Scene(load);
            inputDialogStage.setScene(inputDialogScene);
            inputDialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void onAssignButtonClick() {
        try {
            // Load the AssignDialog FXML file
            FXMLLoader loader = new FXMLLoader(ExpressionGUI.class.getResource("AssignDialog.fxml"));
            AnchorPane anchorPane = loader.load();

            // Create a new stage for the assign dialog
            Stage assignDialogStage = new Stage();
            assignDialogStage.initModality(Modality.APPLICATION_MODAL);
            assignDialogStage.setTitle("Assign Value");

            // Set the AssignDialogController for the loaded FXML
            AssignDialogController assignDialogController = loader.getController();
            assignDialogController.setStage(assignDialogStage);

            // Set the scene and show the assign dialog
            Scene assignDialogScene = new Scene(anchorPane);
            assignDialogStage.setScene(assignDialogScene);
            assignDialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
