package com.company;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.company.Crawler.students;
import static com.company.Crawler.tmp;

public class Main extends Application{

    public static void main(String[] args) throws Exception {

        (new Thread(() ->
                launch(args)
        )).start();

        final Logger[] loggers = new Logger[]{
                new ConsoleLogger(),
                new MailLogger(),
                new GUILogger()
        };

        final Crawler crawler = new Crawler(new URL("http://home.agh.edu.pl/~ggorecki/IS_Java/students.txt"));

        crawler.addIterationStartedListener(itr->System.out.println("ITERATION START"));
        crawler.addIterationComplitedListener(itr->System.out.println("ITERATION END"));

        crawler.addStudentaddedListener(Student-> {
                    for (Logger el : loggers) el.log("ADDED ", Student);
                }
        );

        crawler.addStudentremovedListener(Student-> {
                    for (Logger el : loggers) el.log("REMOVED ", Student);
                }
        );


        try{
            crawler.run();
        } catch(CrawlerException e){
            e.printStackTrace();
            e.toString();
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("ERROR");
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //CustomMenuBar customMenuBar = new CustomMenuBar(primaryStage);
        CustomTabPane customTabPane = new CustomTabPane(primaryStage);

        if(tmp != null){
            for(Student st : tmp){
                CustomTabPane.getData().add(st);
                CustomTableView.addStudent(st);
            }
        }

        Group group = new Group(customTabPane);
        Scene scene = new Scene(group, 800, 600);
        scene.setFill(Color.WHITE);
        primaryStage.setTitle("Crawler");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
