package Logs;

import Student.Student;

import java.io.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import Student.*;

public class SerializedLogger implements Logger, Closeable {

    private String fileName;
    private ObjectOutputStream objectOutputStream;
    private FileOutputStream fileOutputStream;
    private FileInputStream fileInputStream;
    private ObjectInputStream objectInputStream;

    public SerializedLogger(String fileName, boolean append) {
        this.fileName = fileName;
        try {
            fileOutputStream = new FileOutputStream(fileName, append);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        if (fileOutputStream != null) fileOutputStream.close();
        if (objectOutputStream != null) objectOutputStream.close();
        if (fileInputStream != null) fileInputStream.close();
        if (objectInputStream != null) objectInputStream.close();
    }

    @Override
    public void log(String status, Student student) {
        LoggedStudent loggedStudent = new LoggedStudent(student, new Date().getTime(), status);
        try {
            objectOutputStream.writeObject(loggedStudent);
            objectOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<LoggedStudent> listStudent() throws IOException {
        LinkedList<LoggedStudent> students = new LinkedList<LoggedStudent>();
        try {
            fileInputStream = new FileInputStream(fileName);
            objectInputStream = new ObjectInputStream(objectInputStream);

            while (true) {
                LoggedStudent tmp;
                try {
                    tmp = (LoggedStudent) objectInputStream.readObject();
                    students.add(tmp);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    break;
                } catch (EOFException e){
                    e.printStackTrace();
                    break;
                }
            }

            fileInputStream.close();
            objectInputStream.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return students;
    }
}

