package Logs;

import FXML.CrawlerController;
import Student.Student;

public class GuiLogger implements Logger {

   /* private CrawlerController crawlerController;

    public GuiLogger(CrawlerController crawlerController){
        this.crawlerController = crawlerController;
    }
*/
    @Override
    public synchronized void log(String status, Student student) {
        if(String.valueOf(status).equals("ADDED ")) {
            Main.Main.crawlerController.addStudent(student);
            Main.Main.crawlerController.updateChartAdd(student.getMark());
            Main.Main.crawlerController.addedStudent(student);
        }
        if(String.valueOf(status).equals("DELETE ")){
            Main.Main.crawlerController.rmvStudent(student);
            Main.Main.crawlerController.updateChartRmv(student.getMark());
            Main.Main.crawlerController.removedStudent(student);
        }
    }
}
