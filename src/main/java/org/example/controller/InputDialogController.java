package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.ExpressionGUI;
import org.example.pojo.Expression;
import org.example.pojo.ExpressionTree;
import org.example.utils.ExpressionUtil;

/**
 * 读入表达式窗口的专用控制器
 *
 * @author Secret
 * @date 2023/12/28
 */
public class InputDialogController {

    @FXML
    private TextField inputField;
    private String inputText;
    private Stage stage;

    private ExpressionGUI expressionGUI;

    public ExpressionGUI getExpressionGUI() {
        return expressionGUI;
    }

    public void setExpressionGUI(ExpressionGUI expressionGUI) {
        this.expressionGUI = expressionGUI;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }


    public String getInputText() {
        return inputText;
    }
    @FXML
    private void onSaveButtonClick() {
        // Handle saving data or perform any other actions
        inputText = inputField.getText();
        if (inputText.isEmpty()){
            ExpressionGUI.showWarningAlert("Please enter a expression first.");
            return;
        }else if(!ExpressionUtil.isPrefixExpression(inputText)){
            ExpressionGUI.showWarningAlert("Please enter a correct prefix expression.");
            return;
        }
        ExpressionTree expressionTree = ExpressionUtil.testReadExpr(new Expression(),inputText);
        expressionGUI.setExpressionTree(expressionTree);
        TextArea outputTextArea = expressionGUI.getOutputTextArea();
        outputTextArea.appendText("Input from dialog: " + inputText + "\n");
        outputTextArea.appendText("Expression read successfully.\n");
        System.out.println("Input data saved: " + inputText);

        // Close the dialog
        stage.close();
    }
}
