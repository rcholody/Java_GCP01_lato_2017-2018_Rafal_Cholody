package com.company;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.control.spreadsheet.Grid;

public class CustomTabPane extends AnchorPane{

    static final ObservableList<Student> data =
           FXCollections.observableArrayList(
                   //  new Student(5.0, "Testow", "Tester", 21)
           );

    static ObservableList<Student> getData(){
        return data;
    }


    private Tab tabStudents;
    private Tab tabLog;
    private Tab tabHistogram;
    private TabPane tabs;
    private CustomMenuBar customBarMenu;

    static CustomLogPane customLogPane;

    public CustomTabPane(Stage primaryStage){

        tabs = new TabPane();
        customBarMenu = new CustomMenuBar(primaryStage);

        tabStudents = new Tab();
        tabLog = new Tab();
        tabHistogram = new Tab();

        tabStudents.setText("Students");
        tabStudents.setClosable(false);
        tabStudents.setContent(studentsSample(primaryStage));
        tabLog.setText("Log");
        tabLog.setClosable(false);
        tabLog.setContent(logSample(primaryStage));
        tabHistogram.setText("Histogram");
        tabHistogram.setClosable(false);
        tabHistogram.setContent(histogramSample(primaryStage));

        tabs.setTranslateY(30);
        tabs.getTabs().addAll(tabStudents,tabLog,tabHistogram);
        tabs.prefWidthProperty().bind(primaryStage.widthProperty());

        this.getChildren().addAll(tabs,customBarMenu);
    }

    private Pane studentsSample(Stage primaryStage){
        GridPane grid = new GridPane();
        CustomTableView customTableView = new CustomTableView(primaryStage);

        grid.getChildren().addAll(customTableView);
        return grid;
    }

    private Pane logSample(Stage primaryStage){
        GridPane grid = new GridPane();
        customLogPane = new CustomLogPane(primaryStage);
       grid.getChildren().addAll(customLogPane);
        return grid;
    }

    private Pane histogramSample(Stage primaryStage){
        GridPane grid = new GridPane();
        CustomBarChart customBarChart = new CustomBarChart(primaryStage);

        grid.getChildren().addAll(customBarChart);
        return grid;
    }
}
