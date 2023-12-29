package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.ExpressionGUI;
import org.example.pojo.ExpressionTree;
import org.example.utils.ExpressionUtil;

public class DiffDialogController {
    @FXML
    public VBox rootBox;
    @FXML
    public ComboBox<String> variableComboBox;
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
        if (inputText == null) {
            return inputField.getText();
        }
        return inputText;
    }

    public void setComboBox() {
        ExpressionTree expressionTree = expressionGUI.getExpressionTree();
        if (expressionTree == null) {
            variableComboBox.promptTextProperty().setValue("No variable found.");
            variableComboBox.setDisable(true);
            return;
        }
        expressionTree.getVariableCountMap().forEach((k, v) -> {
            variableComboBox.getItems().add(k);
        });
        if (variableComboBox.getItems().isEmpty()) {
            variableComboBox.promptTextProperty().setValue("No variable found.");
            variableComboBox.setDisable(true);
        }
    }

    @FXML
    private void onOKButtonClick() {
        String variable = variableComboBox.getValue();
        ExpressionTree guiTree = expressionGUI.getExpressionTree();
        if (guiTree == null) {
//            判断表达式是否为空,即用户是否已经输入过表达式
            ExpressionGUI.showErrorAlert("Please enter a expression first.");
            return;
        } else if (guiTree.getVariableCountMap().isEmpty()) {
//            判断表达式中是否有变量
            ExpressionGUI.showWarningAlert("No variable was found in expression.Please check your expression.");
            return;
        } else if (variable == null || variable.isEmpty()) {
//            判断是否选择了变量
            ExpressionGUI.showWarningAlert("Please select a variable first.");
            return;
        }
        try {
            ExpressionUtil.Diff(guiTree, variable);
        } catch (IllegalArgumentException e) {
            ExpressionGUI.showErrorAlert(e.getMessage());
            return;
        }
        ExpressionGUI.showSuccessAlert("Differentiation completed.");

        stage.close();
        TextArea outputTextArea = expressionGUI.getOutputTextArea();
        outputTextArea.appendText("Derivative expression: ");
        outputTextArea.appendText(ExpressionUtil.guiWriteExpr(guiTree));
        outputTextArea.appendText("\n");
    }
}
