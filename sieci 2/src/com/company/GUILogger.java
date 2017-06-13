package com.company;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.company.CustomTabPane.customLogPane;

public class GUILogger implements Logger {

    @Override
    public void log(String status, Student student) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss:ms");
        Date date = new Date();
        date.getTime();
        String frmtDate = dateFormat.format(date);
        //System.out.println(frmtDate);

        if(status == "ADDED ") {
            CustomTabPane.getData().add(student);
            CustomTableView.addStudent(student);
        }
        if(status == "DELETE "){
            CustomTabPane.getData().removeAll(student);
            CustomTableView.removeStudent(student);
        }

        if (student == null) System.out.println(status);
        else customLogPane.getTextArea().appendText(frmtDate + "\t" + "[ "+status+" ]\t" + student.toString() + "\n");
    }
}
