package Logs;

import Student.Student;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TextLogger implements Logger, Closeable {

    private String fileName;
    private OutputStreamWriter outputStreamWriter;
    private FileOutputStream fileOutputStream;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public TextLogger(String fileName, boolean append){
        this.fileName = fileName;
        try {
            fileOutputStream = new FileOutputStream(fileName,append);
            outputStreamWriter = new OutputStreamWriter(fileOutputStream,"UTF-8");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close()throws IOException {
        if(outputStreamWriter!=null) outputStreamWriter.close();
        if(fileOutputStream!=null) fileOutputStream.close();
    }

    @Override
    public void log(String status, Student student) {
        try {
            outputStreamWriter.write("" + dateFormat.format(new Date()) + " " + status + student.toString() + "\n");
            outputStreamWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
