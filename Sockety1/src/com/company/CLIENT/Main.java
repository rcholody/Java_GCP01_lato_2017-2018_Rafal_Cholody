package com.company.CLIENT;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load( getClass().getResource( "/Resources/LoginView.fxml" ) );
        primaryStage.setTitle( "Login" );
        primaryStage.setScene( new Scene( root, 500, 500 ) );
        primaryStage.show();
        primaryStage.setResizable( false );
    }
}
