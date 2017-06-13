package Main;

import FXML.CrawlerController;
import FXML.LoginFormController;
import FXML.NewUserFormController;
import Logs.*;
import Crawler.*;
import Student.StorageStudent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

public class Main extends Application{

    private LoginFormController loginFormController;
    private NewUserFormController newUserFormController;
    public static CrawlerController crawlerController;
    private String dir;
    private Properties props = new Properties();
    private OutputStream out = null;

    public static void main(String[] args){

        try {
            StorageStudent storageStudent = new StorageStudent("imges.bin");
            FileInputStream fileInputStream1 = new FileInputStream("img.png");
            FileInputStream fileInputStream2 = new FileInputStream("img2.png");

            byte[] image1 = new byte[fileInputStream1.available()];
            byte[] image2 = new byte[fileInputStream2.available()];
            byte[] image3, image4;

            fileInputStream1.read(image1);
            fileInputStream2.read(image2);
            storageStudent.setImage(image1,0);
            storageStudent.setImage(image2,1);

            storageStudent.close();
            fileInputStream1.close();
            fileInputStream2.close();

            storageStudent.open();

            image3 = storageStudent.getImage(0);
            image4 = storageStudent.getImage(1);
            if(!(image1.equals(image3))||!(image2.equals(image4))){
                System.out.print("works :P\n");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Properties props = new Properties();
        OutputStream out = null;

        try{
            File f = new File("conf.properties");
            props.setProperty("user","admin");
            props.setProperty("password",passHash("qwerty"));

            out = new FileOutputStream(f);

            props.store(out,"This is an optional header comment string");


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(out!=null){
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        props = new Properties();
        InputStream in = null;

        try{
            in = new FileInputStream("conf.properties");

            props.load(in);

            System.out.println(props.getProperty("user"));
            System.out.println(props.getProperty("password"));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(in!=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        (new Thread((new Runnable() {
            @Override
            public void run() {
                launch(Main.class, args);
            }
        }))).start();

  /*      try {

            out = new FileOutputStream("Resources/conf.properties");

            props.setProperty("Name","Admin");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
  */

        (new Thread(() -> {
            final Logger[] loggers = new Logger[]{
                    new ConsoleLogger(),
                    new MailLogger(),
                    new GuiLogger(),
                    new TextLogger("textLogger.txt", true),
                    new SerializedLogger("serializedLogger.bin",true),
                    new BinaryLogger("binaryLogger.bin",true),
                    new CompressedLogger("compressedLogger.zip",true)
            };



            while(startFlag) {
                try {
                    Thread.sleep(2 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            final Crawler crawler;
            try {
                crawler = new Crawler(new URL("http://home.agh.edu.pl/~ggorecki/IS_Java/students.txt"));

                crawler.addIterationStartedListener(itr -> System.out.println("ITERATION START"));
                crawler.addIterationComplitedListener(itr -> System.out.println("ITERATION END"));

                crawler.addStudentaddedListener(Student -> {
                            for (Logger el : loggers) el.log("ADDED ", Student);
                        }
                );

                crawler.addStudentremovedListener(Student -> {
                            for (Logger el : loggers) el.log("REMOVED ", Student);
                        }
                );

                try {
                    crawler.run();
                } catch (CrawlerException e) {
                    e.printStackTrace();
                    e.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("ERROR");
                }


            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

        })).start();


    }
    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/FXML/loginForm.fxml"));
        FXMLLoader loader2 = new FXMLLoader(this.getClass().getResource("/FXML/newUserForm.fxml"));
        FXMLLoader loader3 = new FXMLLoader(this.getClass().getResource("/FXML/crawler.fxml"));

        try {
            loader.load();
        } catch(IOException e){
            e.printStackTrace();
        }

        try {
            loader2.load();
        } catch(IOException e){
            e.printStackTrace();
        }

        try {
            loader3.load();
        } catch(IOException e){
            e.printStackTrace();
        }

        Parent root = loader.getRoot();
        Parent root2 = loader2.getRoot();
        Parent root3 = loader3.getRoot();

        loginFormController = loader.getController();
        newUserFormController = loader2.getController();
        crawlerController = loader3.getController();

        Scene scene = new Scene(root);
        Scene scene2 = new Scene(root2);
        Scene scene3 = new Scene(root3,800,600);

        primaryStage.setTitle("Crawler");

        crawlerController.setStage(primaryStage);
        crawlerController.setScene(scene);

        loginFormController.setStage(primaryStage);
        loginFormController.setSceneCrawler(scene3);
        loginFormController.setSceneNewUser(scene2);

        newUserFormController.setStage(primaryStage);
        newUserFormController.setScene(scene);

        primaryStage.setScene(scene);

        primaryStage.show();
    }
    public static boolean startFlag = true;

    public static boolean getFlag(){
        return startFlag;
    }

    public static String passHash(String password){
        String tmp = new String();
        try{
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(password.getBytes());
            byte[] bytes = md5.digest();
            StringBuilder stringBuilder =new StringBuilder();
            for(int i=0;i<bytes.length;i++){
                stringBuilder.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            tmp = stringBuilder.toString();

        }catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        return tmp;
    }
}
