package org.example.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.*;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.ExpressionGUI;
import org.example.collections.MyHashMap;
import org.example.exceptions.VariableInTrigFucException;
import org.example.pojo.Expression;
import org.example.pojo.ExpressionTree;
import org.example.utils.ExpressionUtil;

public class AssignDialogController {

    @FXML
    private TextField variableField;

    @FXML
    private TextField valueField;

    @FXML
    private ComboBox<String> trigModeComboBox;

    private ExpressionGUI expressionGUI;

    public void setExpressionGUI(ExpressionGUI expressionGUI) {
        this.expressionGUI = expressionGUI;
    }


    public ExpressionGUI getExpressionGUI() {
        return expressionGUI;
    }

    private Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void onSaveButtonClick() {
        try {
            String variable = variableField.getText();

            String inputString = valueField.getText();
            String trigMode = trigModeComboBox.getValue();
//            判断输入是否为空
            if (variable.isEmpty() || inputString.isEmpty()) {
                showWarningAlert("Please enter values for both variable and value first.");
                return;
            } else if (expressionGUI == null || expressionGUI.getExpressionTree() == null) {
//                判断表达式是否为空,即用户是否已经输入过表达式
                showErrorAlert("Please enter a expression first.");
                stage.close();
                return;
            } else if (variable.length() >= 2) {
//                判断变量长度是否合法
                showWarningAlert("Variable name can't be longer than 1 character.");
                return;
            } else if (variable.charAt(0) < 'a' || variable.charAt(0) > 'z') {
//                判断变量名是否合法
                showWarningAlert("Variable name must be a character.");
                return;
            } else if (("disabled".equals(trigMode) || trigMode == null) && !ExpressionUtil.isNumeric(inputString)) {
//                判断正常输入模式下, 输入是否为数字
                showWarningAlert("Please enter a number as variable's value.");
                return;
            } else if ((trigMode != null && !trigMode.equals("disabled")) && !ExpressionUtil.isPrefixExpression(inputString)) {
//                判断三角函数输入模式下, 输入是否为前缀表达式
                showWarningAlert("Please enter a correct prefixExpression as trigonometric function argument\n.");
                return;
            }
            MyHashMap<String, Expression> countMap = expressionGUI.getExpressionTree().getVariableCountMap();
            if (!countMap.containsKey(variable)) {
                showWarningAlert("This Variable do not exist in expression.");
                return;
            }
            if ("disabled".equals(trigMode) || trigMode == null) {
                double value = Double.parseDouble(inputString);
                ExpressionUtil.Assign(variable.substring(0,1), value, expressionGUI.getExpressionTree());
            } else {
                ExpressionUtil.assignTrigFunction(trigMode, variable, inputString, expressionGUI.getExpressionTree());
            }
            showSuccessAlert("Data saved successfully!");

            stage.close();
        } catch (IllegalArgumentException e) {
            showErrorAlert("Invalid input format. Please enter a valid content.");
        } catch (VariableInTrigFucException e) {
//            判断变量是否在三角函数中
            showErrorAlert("Variable can't be in trigonometric function model.");
        } catch (Exception e) {
            // Handle other validation errors
            showErrorAlert("Error: " + e.getMessage());
            stage.close();
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

    private void showWarningAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
