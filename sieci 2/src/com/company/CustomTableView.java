package com.company;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static com.company.CustomTabPane.data;

public class CustomTableView extends AnchorPane{
    private TableView<Student> table = new TableView<Student>();

    private final HBox hb = new HBox();
    private final VBox vb = new VBox();


    public CustomTableView(Stage primaryStage){
        table.setEditable(false);

        TableColumn tcmark = new TableColumn("Mark");
        tcmark.prefWidthProperty().bind(table.widthProperty().multiply(0.25));
        tcmark.setCellValueFactory(
                new PropertyValueFactory<Student,Double>("mark"));

        TableColumn tcfirstname = new TableColumn("First Name");
        tcfirstname.prefWidthProperty().bind(table.widthProperty().multiply(0.25));
        tcfirstname.setCellValueFactory(
                new PropertyValueFactory<Student, String>("firstName"));

        TableColumn tclastname = new TableColumn("Last Name");
        tclastname.prefWidthProperty().bind(table.widthProperty().multiply(0.25));
        tclastname.setCellValueFactory(
                new PropertyValueFactory<Student, String>("secondName"));

        TableColumn tcage = new TableColumn("Age");
        tcage.prefWidthProperty().bind(table.widthProperty().multiply(0.25));
        tcage.setCellValueFactory(
                new PropertyValueFactory<Student,Integer>("age"));

        //final TextField addMark = new TextField();
        //addMark.setPromptText("Mark");
        //addMark.setMaxWidth(tcmark.getPrefWidth());

        final ChoiceBox<Double> addMark = new ChoiceBox<Double>();
        ObservableList<Double> items = FXCollections.observableArrayList(
                2.0,3.0,3.5,4.0,4.5,5.0
                );
        addMark.setItems(items);
        addMark.setPrefSize(60,30);


        final TextField addFirstName = new TextField();
        addFirstName.setPromptText("First Name");
        addFirstName.setMaxWidth(200);

        final TextField addLastName = new TextField();
        addLastName.setPromptText("Last Name");
        addLastName.setMaxWidth(200);

        final TextField addAge = new TextField();
        addAge.setPromptText("Age");
        addAge.setMaxWidth(200);

        final Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            data.add(new Student(
                    addMark.getSelectionModel().getSelectedItem(),
                    addFirstName.getText(),
                    addLastName.getText(),
                    Integer.parseInt(addAge.getText())
                    ));
           // addMark.clear();
            if(addMark.getSelectionModel().getSelectedItem()== 2.0 ) CustomBarChart.counter[0]++;
            if(addMark.getSelectionModel().getSelectedItem()== 3.0 ) CustomBarChart.counter[1]++;
            if(addMark.getSelectionModel().getSelectedItem()== 3.5 ) CustomBarChart.counter[2]++;
            if(addMark.getSelectionModel().getSelectedItem()== 4.0 ) CustomBarChart.counter[3]++;
            if(addMark.getSelectionModel().getSelectedItem()== 4.5 ) CustomBarChart.counter[4]++;
            if(addMark.getSelectionModel().getSelectedItem()== 5.0 ) CustomBarChart.counter[5]++;

            CustomBarChart.updateChartData();

            addFirstName.clear();
            addLastName.clear();
            addAge.clear();
        });

        final Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(event -> data.removeAll());

        //hb.getChildren().addAll(addMark,addFirstName,addLastName,addAge, addButton, deleteButton);
        hb.prefWidthProperty().bind(primaryStage.widthProperty());
        hb.setSpacing(3);

        table.setItems(data);
        table.getColumns().addAll(tcmark, tcfirstname, tclastname, tcage);


       // table.prefHeightProperty().bind(primaryStage.heightProperty());
        table.prefWidthProperty().bind(primaryStage.widthProperty());


        vb.getChildren().addAll(table, hb);

        this.getChildren().addAll(vb);
    }

    static void addStudent(Student st){
        if(st.getMark() == 2.0 ) CustomBarChart.counter[0]++;
        if(st.getMark() == 3.0 ) CustomBarChart.counter[1]++;
        if(st.getMark() == 3.5 ) CustomBarChart.counter[2]++;
        if(st.getMark() == 4.0 ) CustomBarChart.counter[3]++;
        if(st.getMark() == 4.5 ) CustomBarChart.counter[4]++;
        if(st.getMark() == 5.0 ) CustomBarChart.counter[5]++;

        CustomBarChart.updateChartData();
    }
    static void removeStudent(Student st){
        if(st.getMark() == 2.0 ) CustomBarChart.counter[0]--;
        if(st.getMark() == 3.0 ) CustomBarChart.counter[1]--;
        if(st.getMark() == 3.5 ) CustomBarChart.counter[2]--;
        if(st.getMark() == 4.0 ) CustomBarChart.counter[3]--;
        if(st.getMark() == 4.5 ) CustomBarChart.counter[4]--;
        if(st.getMark() == 5.0 ) CustomBarChart.counter[5]--;

        CustomBarChart.updateChartData();
    }
}
