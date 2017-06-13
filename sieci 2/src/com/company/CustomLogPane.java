package com.company;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class CustomLogPane extends AnchorPane{

    private ObservableList<Student> tmp =
            FXCollections.observableArrayList();

    public TextArea getTextArea() {
        return textArea;
    }

    public void setTextArea(TextArea textArea) {
        this.textArea = textArea;
    }

    public VBox getvBox() {
        return vBox;
    }

    public void setvBox(VBox vBox) {
        this.vBox = vBox;
    }

    private TextArea textArea;
    private VBox vBox;


    public CustomLogPane(Stage primaryStage){
        textArea = new TextArea();
        textArea.setEditable(false);
        textArea.prefHeightProperty().bind(primaryStage.heightProperty());

        vBox = new VBox(textArea);

        tmp = CustomTabPane.getData();

        vBox.prefWidthProperty().bind(primaryStage.widthProperty());
        this.getChildren().addAll(vBox);

    }
}
