package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import org.example.pojo.Expression;
import org.example.utils.ExpressionUtil;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {
    private TextArea inputTextArea;
    private TextArea outputTextArea;


    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
/*        stage.setTitle("Expression Parser");

        inputTextArea = new TextArea();
        inputTextArea.setPromptText("Enter expression here...");

        outputTextArea = new TextArea();
        outputTextArea.setEditable(false);

        Button parseButton = new Button("Parse and Evaluate");
        parseButton.setOnAction(e -> ExpressionUtil.WriteExpr(ExpressionUtil.testReadExpr(new Expression(), inputTextArea.getText())));

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10, 10, 10, 10));
        layout.getChildren().addAll(inputTextArea, parseButton, outputTextArea);

        Scene scene = new Scene(layout, 400, 300);
        stage.setScene(scene);

        stage.show();*/
        scene = new Scene(loadFXML("primary"), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch(args);
    }

}