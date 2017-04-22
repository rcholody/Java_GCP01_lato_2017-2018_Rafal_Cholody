
public class CrawlerEvent extends AbstractEvent {
    //private final CrawlerEventType crawlerEventType;
    private final  Student student;

    public CrawlerEvent(CrawlerEventType crawlerEventType, Student student) {
      super (crawlerEventType);

        //this.crawlerEventType = crawlerEventType;
        this.student = student;
    }


    public Student getStudent() {
        return student;
    }
}
