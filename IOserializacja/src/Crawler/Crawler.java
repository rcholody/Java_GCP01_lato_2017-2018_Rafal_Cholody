package Crawler;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Enums.ExtremumMode;
import Enums.OrderMode;
import Student.*;

import javafx.application.Platform;
import org.apache.commons.io.FileUtils;


public class Crawler {

    private List<Student> students;
    private List<Student> tmp;
    private int iteration;
    private URL pathURL;
    private String dir;

    private List<IterationListener> iterationStartedListeners = new ArrayList<>();
    public void addIterationStartedListener(IterationListener listener){
        iterationStartedListeners.add(listener);
    }
    public void removeIterationStartedListener(IterationListener listener){iterationStartedListeners.remove(listener); }

    private List<IterationListener> iterationComplitedListeners = new ArrayList<>();
    public void addIterationComplitedListener(IterationListener listener){
        iterationComplitedListeners.add(listener);
    }
    public void removeIterationComplitedListener(IterationListener listener){ iterationComplitedListeners.remove(listener); }

    private List<StudentListener> studentaddedListeners = new ArrayList<>();
    public void addStudentaddedListener(StudentListener listener){
        studentaddedListeners.add(listener);
    }
    public void removeStudentaddedListeners(StudentListener listener){
        studentaddedListeners.remove(listener);
    }

    private List<StudentListener> studentRemovedListener = new ArrayList<>();
    public void addStudentremovedListener(StudentListener listener){
        studentRemovedListener.add(listener);
    }
    public void removeStudentremovedListeners(StudentListener listener){
        studentaddedListeners.remove(listener);
    }


    public URL getPathURL() {
        return pathURL;
    }

    public void setPathURL(URL pathURL) {
        this.pathURL = pathURL;
    }

    public String getDir() {
        return dir;
    }

    public void setDir(String dir) {
        this.dir = dir;
    }

    public Crawler(URL pathURL) {
        this.pathURL = pathURL;
        this.dir = "C:\\Users\\Rafał\\Desktop\\Studia\\Informatyka\\JAVA\\IOserializacja\\students.txt";
        this.iteration = 1;
    }

    public void run() throws Exception {

        if(pathURL == null) throw new CrawlerException("Configure the correct url.");

        students = StudentParser.parse(pathURL);
        File file = new File(dir);
        tmp = StudentParser.parse(file);

        while(true){
            FileUtils.copyURLToFile(pathURL, file);

            System.out.print("Iteration: "+iteration+"\n");
            for(IterationListener il : iterationStartedListeners){
                il.handle(iteration);
            }

            if(tmp==null){
                for(Student st : students){
                    for(StudentListener sl : studentaddedListeners){
                        sl.handle(st);
                    }
                }
            }

            //if(students!=null&&tmp!=null) {
            if (students.equals(tmp)) {
                System.out.print("NO CHANGES\n");
                //System.out.print("AGE: <" + extractAge(ExtremumMode.MAX) + "," + extractAge(ExtremumMode.MIN) + ">\n");
                //System.out.print("MARK: <" + extractMark(ExtremumMode.MAX) + "," + extractMark(ExtremumMode.MIN) + ">\n");
                //System.out.print("Ordered by mark:\n");
                //students = extractStudents(OrderMode.MARK);
                //print(students);
            }
            else if (!students.equals(tmp)) {
                if(students.size()>tmp.size()) {
                    students.removeAll(tmp);
                    for(Student st : students) {
                        for (StudentListener sl : studentaddedListeners) {
                            sl.handle(st);
                        }
                    }
                    //printADD(students);

                }
                else if(students.size()<tmp.size()){
                    tmp.removeAll(students);
                    for(Student st : students) {
                        for (StudentListener sl : studentRemovedListener) {
                            sl.handle(st);
                        }
                    }
                    // printDELETED(tmp);
                }
                else if(students.size()==tmp.size()){
                    //listenerCall(EventCrawlerTypes.DATA_CHANGED);
                    students.removeAll(tmp);
                    printCHANGED(students);
                }
            }
            // }

            if(tmp!=null) {
                System.out.print("AGE: <" + extractAge(ExtremumMode.MAX) + "," + extractAge(ExtremumMode.MIN) + ">\n");
                System.out.print("MARK: <" + extractMark(ExtremumMode.MAX) + "," + extractMark(ExtremumMode.MIN) + ">\n");
                System.out.print("Ordered by mark:\n");
                students = extractStudents(OrderMode.MARK);
                print(students);
            }

            for(IterationListener il : iterationComplitedListeners){
                il.handle(iteration);
            }
            iteration++;

            tmp = StudentParser.parse(file);
            students = StudentParser.parse(pathURL);

            Thread.sleep(10 * 1000);
        }
    }

    public List<Student> extractStudents(OrderMode mode) {
        List<Student> tmp;
        tmp = students;
        if (mode == OrderMode.MARK) {
            Collections.sort(tmp, Student.Comparators.MARK);
        } else if (mode == OrderMode.AGE) {
            Collections.sort(tmp, Student.Comparators.AGE);
        } else if (mode == OrderMode.FIRST_NAME) {
            Collections.sort(tmp, Student.Comparators.FIRSTNAME);
        } else if (mode == OrderMode.SECOND_NAME) {
            Collections.sort(tmp, Student.Comparators.SECONDNAME);
        }
        return tmp;
    }

    public double extractMark(ExtremumMode mode) {
        List<Student> tmp;
        tmp = students;
        tmp = extractStudents(OrderMode.MARK);
        if (mode == ExtremumMode.MAX) {
            return tmp.get(0).getMark();
        } else {
            return tmp.get(tmp.size() - 1).getMark();
        }
    }

    public int extractAge(ExtremumMode mode) {
        List<Student> tmp;
        tmp = students;
        tmp = extractStudents(OrderMode.AGE);
        if (mode == ExtremumMode.MAX) {
            return tmp.get(0).getAge();
        } else {
            return tmp.get(tmp.size()-1).getAge();
        }
    }


    public void print(List<Student> l){
        for(Student s : l){
            System.out.println(s.getMark() + " " + s.getFirstName() + " " + s.getLastName() + " " + s.getAge());
        }
    }

    public void printADD(List<Student> l){
        for(Student s : l){
            System.out.println("ADDED: " + s.getMark() + " " + s.getFirstName() + " " + s.getLastName() + " " + s.getAge());
        }
    }
    public void printDELETED(List<Student> l){
        for(Student s : l){
            System.out.println("DELETED: " + s.getMark() + " " + s.getFirstName() + " " + s.getLastName() + " " + s.getAge());
        }
    }

    public void printCHANGED(List<Student> l){
        for(Student s : l){
            System.out.println("DATA CHANGED: " + s.getMark() + " " + s.getFirstName() + " " + s.getLastName() + " " + s.getAge());
        }
    }

    public List<Student> getStudents() {
        return students;
    }
    public void setStudents(List<Student> students) {
        this.students = students;
    }
    public List<Student> getTmp() {
        return tmp;
    }
    public void setTmp(List<Student> tmp) {
        this.tmp = tmp;
    }
}
