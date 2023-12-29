package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.ExpressionGUI;
import org.example.pojo.Expression;
import org.example.pojo.ExpressionTree;
import org.example.utils.ExpressionUtil;

public class CompoundDialogController {

    @FXML
    public ComboBox<String> operatorComboBox;
    @FXML
    public TextField compoundExprField;

    private ExpressionGUI expressionGUI;
    private String inputText;

    private Stage stage;

    public ExpressionGUI getExpressionGUI() {
        return expressionGUI;
    }

    public void setExpressionGUI(ExpressionGUI expressionGUI) {
        this.expressionGUI = expressionGUI;
    }

    public String getInputText() {
        if (inputText == null) {
            return compoundExprField.getText();
        }
        return inputText;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void onSaveButtonClick() {
        String operator = operatorComboBox.getValue();
        if (operator == null || operator.isEmpty()) {
            operator = "+";
        }
        String compoundExprText = compoundExprField.getText();
        if (compoundExprText == null || compoundExprText.isEmpty()) {
            ExpressionGUI.showWarningAlert("Please enter a expression first.");
            return;
        } else if (!ExpressionUtil.isPrefixExpression(compoundExprText)) {
            ExpressionGUI.showWarningAlert("Please enter a correct prefix expression.");
            return;
        }

        ExpressionTree compoundExprTree = ExpressionUtil.testReadExpr(new ExpressionTree(), compoundExprText);
        ExpressionTree GUIExprTree = expressionGUI.getExpressionTree();
        expressionGUI.setExpressionTree(ExpressionUtil.CompoundExpr(operator, compoundExprTree, GUIExprTree));
        expressionGUI.getOutputTextArea()
                .appendText("Infix compound expression: "
                        + ExpressionUtil.testWriteExpr(expressionGUI.getExpressionTree())
                        + "\n");
    }

    public Stage getStage() {
        return stage;
    }
}
