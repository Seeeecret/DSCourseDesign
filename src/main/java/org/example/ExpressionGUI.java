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
import org.example.controller.CompoundDialogController;
import org.example.pojo.Expression;
import org.example.pojo.ExpressionTree;
import org.example.utils.ExpressionUtil;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

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
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("ExpressionGUI.fxml"));
//        loader.setController(this);
//          这段代码有误,loader.load()返回的是一个Parent对象，是fxml文件中定义的页面的最外层对象,此时是AnchorPane不是VBox
//        VBox root = loader.load();
        Scene scene = new Scene(loadFXML("ExpressionGUI"), 700, 400);

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
//        String inputText = openNewWindowsGetInput();
////        如果用户是点击了关闭按钮,那么就不会执行下面的代码
//        if (!inputText.equals("close")) {
//            outputTextArea.appendText("Input from dialog: " + inputText + "\n");
//            expressionTree = ExpressionUtil.testReadExpr(new ExpressionTree(), inputText);
//            outputTextArea.appendText("Expression read successfully.\n");
//        }
    }

    @FXML
    private void onWriteButtonClick() {
        outputTextArea.appendText("Infix expression: ");
        String writeExpr = ExpressionUtil.testWriteExpr(expressionTree);
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

        CompoundDialogController compoundDialogController = loader.getController();
        compoundDialogController.setStage(compundExprStage);
        compoundDialogController.setExpressionGUI(this);

        Scene compundExprScene = new Scene(anchorPane);
        compundExprStage.setScene(compundExprScene);
        compundExprStage.showAndWait();

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
//            加载InputDialog FXML文件
            FXMLLoader loader = new FXMLLoader(ExpressionGUI.class.getResource("InputDialog.fxml"));
            AnchorPane load = loader.load();
//            创建一个新的Stage用于输入对话框
            Stage inputDialogStage = new Stage();
            inputDialogStage.initModality(Modality.APPLICATION_MODAL);
            inputDialogStage.setTitle("Input Dialog");
//            设置加载的FXML的InputDialogController
            InputDialogController inputDialogController = loader.getController();
            inputDialogController.setStage(inputDialogStage);
//            设置inputDialogController的expressionGUI
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

    @Deprecated
    private String openNewWindowsGetInput() throws IOException {
        FXMLLoader loader = new FXMLLoader(ExpressionGUI.class.getResource("InputDialog.fxml"));
        AnchorPane anchorPane = loader.load();
        AtomicReference<String> inputText = new AtomicReference<>("");
        // Create a new stage for the input dialog
        Stage inputDialogStage = new Stage();
        inputDialogStage.initModality(Modality.APPLICATION_MODAL);
        inputDialogStage.setTitle("Input Dialog");

        // Set the InputDialogController for the loaded FXML
        InputDialogController inputDialogController = loader.getController();
        inputDialogController.setStage(inputDialogStage);
        inputDialogController.setExpressionGUI(this);

        // Set the scene and show the input dialog
        Scene inputDialogScene = new Scene(anchorPane);
        inputDialogStage.setOnCloseRequest(event -> {
            inputText.set("close");
        });
        // Get the input text
        inputDialogStage.setScene(inputDialogScene);

        inputDialogStage.showAndWait();

        // Get the input text from InputDialogController
        if (!inputText.get().equals("close")) {
            inputText.set(inputDialogController.getInputText());
        }
        return inputText.get();

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
