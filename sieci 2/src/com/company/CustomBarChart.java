package com.company;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class CustomBarChart extends AnchorPane{

    static int [] counter = new int[6];

    final CategoryAxis xAxis = new CategoryAxis();
    final NumberAxis yAxis = new NumberAxis();
    final BarChart<String,Number> bc = new BarChart<String,Number>(xAxis,yAxis);
    static XYChart.Series<String, Number> dataSeries;

    public CustomBarChart(Stage primaryStage){

        for(int ct : counter){
            ct = 0;
        }

        bc.setTitle("Distribution of marks");

        xAxis.setCategories(FXCollections.<String>observableArrayList(
                "2.0", "3.0", "3.5", "4.0", "4.5", "5.0"
                ));

        yAxis.setLabel("Counts");
        yAxis.setTickLabelFormatter(new StringConverter<Number>() {
            @Override
            public String toString(Number object) {
                if(object.intValue()!=object.doubleValue())
                    return "";
                return ""+(object.intValue());
            }

            @Override
            public Number fromString(String string) {
                Number val = Double.parseDouble(string);
                return val.intValue();
            }
        });
        //yAxis.setTickUnit(1);
        yAxis.setMinorTickCount(0);

        dataSeries = new XYChart.Series<String,Number>();

        dataSeries.setName("Marks");

        dataSeries.getData().add(new XYChart.Data<String,Number>("2.0",0));
        dataSeries.getData().add(new XYChart.Data<String,Number>("3.0",0));
        dataSeries.getData().add(new XYChart.Data<String,Number>("3.5",0));
        dataSeries.getData().add(new XYChart.Data<String,Number>("4.0",0));
        dataSeries.getData().add(new XYChart.Data<String,Number>("4.5",0));
        dataSeries.getData().add(new XYChart.Data<String,Number>("5.0",0));


        bc.getData().add(dataSeries);

        this.getChildren().addAll(bc);
    }

    public static void updateChartData(){
        int i = 0;
        for(XYChart.Data<String, Number> data : dataSeries.getData()){
           Number oldData = data.getYValue();
            if(counter[i]> oldData.intValue())data.setYValue(counter[i]);
            i++;
        }
    }
}
