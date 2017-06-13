package Logs;

import Student.Student;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CompressedLogger implements Logger, Closeable {

    private String fileName;
    private ZipOutputStream zipOutputStream;
    private TextLogger textLogger;
    private FileOutputStream fileOutputStream;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd_HH.mm.ss");
    private File file;
    private FileInputStream fileInputStream;

    public CompressedLogger(String fileName, boolean append){
        this.fileName = fileName;
        try {
            fileOutputStream = new FileOutputStream(fileName,append);
            zipOutputStream = new ZipOutputStream(fileOutputStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() throws IOException {
        if(zipOutputStream!=null) zipOutputStream.close();
        if(fileOutputStream!=null) fileOutputStream.close();
    }

    @Override
    public void log(String status, Student student) {
        String tmp = dateFormat.format(new Date())+".txt";
        try{
            textLogger = new TextLogger(tmp,true);
            textLogger.log(status,student);
            textLogger.close();
            file = new File(tmp);
            fileInputStream = new FileInputStream(file);

            zipOutputStream.putNextEntry(new ZipEntry(tmp));
            while(true){
                int b = fileInputStream.read();
                if(b!=-1){
                    zipOutputStream.write(b);
                }
                else{
                    break;
                }
            }
            zipOutputStream.closeEntry();
            zipOutputStream.flush();
            fileInputStream.close();

        } catch (IOException e){
            e.printStackTrace();
        } finally{
            if(file!=null){
                if(file.exists())
                    file.delete();
                try{
                    if(fileInputStream!=null)
                        fileInputStream.close();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
    }
}

























