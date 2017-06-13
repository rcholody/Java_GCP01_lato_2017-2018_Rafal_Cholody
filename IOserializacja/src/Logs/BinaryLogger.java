package Logs;

import java.io.*;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import Student.*;

public class BinaryLogger implements Logger, Closeable {

    private String fileName;
    private DataOutputStream dataOutputStream;
    private FileOutputStream fileOutputStream;
    private FileInputStream fileInputStream;
    private DataInputStream dataInputStream;

    public BinaryLogger(String fileName, boolean append){
        this.fileName = fileName;
        try {
            fileOutputStream=new FileOutputStream(fileName,append);
            dataOutputStream=new DataOutputStream(fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        if (fileOutputStream != null) fileOutputStream.close();
        if (dataOutputStream != null) dataOutputStream.close();
        if (fileInputStream != null) fileInputStream.close();
        if (dataInputStream != null) dataInputStream.close();
    }

    @Override
    public void log(String status, Student student) {
        long time = new Date().getTime();
        try{
            dataOutputStream.writeDouble(student.getMark());
            dataOutputStream.writeUTF(student.getFirstName());
            dataOutputStream.writeUTF(student.getLastName());
            dataOutputStream.writeInt(student.getAge());
            dataOutputStream.writeUTF(status);
            dataOutputStream.writeLong(time);
            dataOutputStream.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public List<LoggedStudent> listStudent(){

        LinkedList<LoggedStudent> students = new LinkedList<LoggedStudent>();
        try {
            fileInputStream = new FileInputStream(fileName);
            dataInputStream = new DataInputStream(fileInputStream);

            while (true) {
                LoggedStudent tmp = new LoggedStudent();
                try {
                    tmp.setMark(dataInputStream.readDouble());
                    tmp.setFirstName(dataInputStream.readUTF());
                    tmp.setLastName(dataInputStream.readUTF());
                    tmp.setAge(dataInputStream.readInt());
                    tmp.setStatus(dataInputStream.readUTF());
                    tmp.setTime(dataInputStream.readLong());

                    students.add(tmp);
                } catch (EOFException e){
                    e.printStackTrace();
                    break;
                } catch (IOException e){
                    e.printStackTrace();
                    break;
                }
            }

            fileInputStream.close();
            dataInputStream.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return students;

    }
}
