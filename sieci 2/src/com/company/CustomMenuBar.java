package com.company;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.input.*;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class CustomMenuBar extends MenuBar{

    //Menu
    private Menu progr;
    private Menu about;

    //Menu items
    private MenuItem close;
    private MenuItem ab;

    CustomMenuBar(Stage primaryStage){
        progr = new Menu("Program");
        about = new Menu("About");
        close = new MenuItem("Close");
        ab = new MenuItem("About");
        close.setMnemonicParsing(true);
        ab.setMnemonicParsing(true);
        close.setAccelerator(new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_DOWN));
        ab.setAccelerator(new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN));
        close.setOnAction(event -> primaryStage.close());
        ab.setOnAction(event ->{
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Author: anonymous" ,ButtonType.OK);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            alert.setTitle("About");
            alert.setHeaderText("Example Program Informations");
            alert.setContentText("Author: anonymous");
            alert.initOwner(primaryStage);
            alert.show();
        });


        progr.getItems().addAll(close);
        about.getItems().addAll(ab);
        this.prefWidthProperty().bind(primaryStage.widthProperty());
        this.getMenus().addAll(progr, about);

    }
}
