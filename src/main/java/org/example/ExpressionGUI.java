package org.example;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.example.controller.AssignDialogController;
import org.example.controller.CompoundGUIController;
import org.example.controller.DiffDialogController;
import org.example.controller.InputDialogController;
import org.example.pojo.ExpressionTree;
import org.example.utils.ExpressionUtil;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class ExpressionGUI extends Application {

    @FXML
    public Button openInputDialogButton;
    @FXML
    public Button clearOutputButton;
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

    public TextArea getOutputTextArea() {
        return outputTextArea;
    }

    private ExpressionTree expressionTree;

    public ExpressionTree getExpressionTree() {
        return expressionTree;
    }

    public void setExpressionTree(ExpressionTree expressionTree) {
        this.expressionTree = expressionTree;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(loadFXML("ExpressionGUI"), 825, 400);

        primaryStage.setTitle("Expression System GUI");
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
    private void onClearOutputButtonClick() {
        outputTextArea.clear();
    }

    @FXML
    private void onReadButtonClick() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(ExpressionGUI.class.getResource("InputDialog.fxml"));
            AnchorPane anchorPane = loader.load();
            AtomicReference<String> inputText = new AtomicReference<>("");
            Stage inputDialogStage = new Stage();
            inputDialogStage.initModality(Modality.APPLICATION_MODAL);
            inputDialogStage.setTitle("Input Dialog");

            InputDialogController inputDialogController = loader.getController();
            inputDialogController.setStage(inputDialogStage);
            inputDialogController.setExpressionGUI(this);

            Scene inputDialogScene = new Scene(anchorPane);
            // Get the input text
            inputDialogStage.setScene(inputDialogScene);
            inputDialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onWriteButtonClick() {
        outputTextArea.appendText("Infix expression: ");
        String writeExpr = ExpressionUtil.guiWriteExpr(expressionTree);
        outputTextArea.appendText(writeExpr);
        outputTextArea.appendText("\n");
    }


    @FXML
    private void onValueButtonClick() {

        try {
            double result = ExpressionUtil.Value(expressionTree);
            outputTextArea.appendText("Expression value: " + result + "\n");
        } catch (ArithmeticException e) {
            showWarningAlert(e.getMessage() + "Please check your expression.");
        }
    }

    @FXML
    private void onCompoundButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(ExpressionGUI.class.getResource("CompoundGUI.fxml"));
        AnchorPane anchorPane = loader.load();
        Stage compundExprStage = new Stage();
        compundExprStage.initModality(Modality.APPLICATION_MODAL);
        compundExprStage.setTitle("Compound Expression Dialog");

        CompoundGUIController compoundGUIController = loader.getController();
        compoundGUIController.setStage(compundExprStage);
        compoundGUIController.setExpressionGUI(this);
//        compoundGUIController.setComboBox();

        Scene compundExprScene = new Scene(anchorPane);
        compundExprStage.setScene(compundExprScene);
        compundExprStage.showAndWait();

    }

    @FXML
    private void onDiffButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(ExpressionGUI.class.getResource("DiffDialog.fxml"));
        AnchorPane anchorPane = loader.load();
        Stage diffStage = new Stage();
        diffStage.initModality(Modality.APPLICATION_MODAL);
        diffStage.setTitle("Compound Expression Dialog");

        DiffDialogController diffController = loader.getController();
        diffController.setStage(diffStage);
        diffController.setExpressionGUI(this);
        diffController.setComboBox();

        Scene compundExprScene = new Scene(anchorPane);
        diffStage.setScene(compundExprScene);
        diffStage.showAndWait();
    }

    @FXML
    private void
    onMergeConstButtonClick() {
        expressionTree = ExpressionUtil.MergeConst(expressionTree);
        outputTextArea.appendText("Constants merged successfully:");
    }


    @FXML
    public void onAssignButtonClick() {
        try {
            // 加载AssignDialog FXML文件
            FXMLLoader loader = new FXMLLoader(ExpressionGUI.class.getResource("AssignDialog.fxml"));
            AnchorPane anchorPane = loader.load();

            // 创建一个新的Stage用于分配对话框
            Stage assignDialogStage = new Stage();
            assignDialogStage.initModality(Modality.APPLICATION_MODAL);
            assignDialogStage.setTitle("Assign Value");

            // 设置加载的FXML的AssignDialogController
            AssignDialogController assignDialogController = loader.getController();
            assignDialogController.setStage(assignDialogStage);
            assignDialogController.setExpressionGUI(this);
//              设置assignDialogController的expressionTree
            Scene assignDialogScene = new Scene(anchorPane);
            assignDialogStage.setScene(assignDialogScene);
            assignDialogStage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showErrorAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void showWarningAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
