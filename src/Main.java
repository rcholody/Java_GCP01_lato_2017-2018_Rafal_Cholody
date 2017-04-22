import java.io.IOException;

/**
 * Created by Rafał on 2017-03-24.
 */
public class Main {
    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        final Logger[] loggers = new Logger[]
                {
                        new ConsoleLogger(),
                        new MailLogger(args[0], args[1], args[2])
                };
        try {
            crawler.addAddedStudentsListener(new CrawlerListener() {
                @Override
                public void actionPerformed(AbstractEvent abstractEvent) {
                  //  System.out.println("Dodano: " + ((CrawlerEvent) abstractEvent).getStudent().getFirstName());
                }
            });

            crawler.addBeginIterListeners(new CrawlerListener() {
                @Override
                public void actionPerformed(AbstractEvent abstractEvent) {
                   // System.out.println("Begin: " + ((CrawlerIterationEvent) abstractEvent).getIteration());
                }
            });
            crawler.addAddedStudentsListener(new CrawlerListener() {
                @Override
                public void actionPerformed(AbstractEvent abstractEvent) {
                    for (Logger el : loggers){
                       // el.log("ADDED", ((CrawlerEvent) abstractEvent).getStudent()
                       // );

                    }
                  //  System.out.println(crawler.extractStudents(OrderMode.MARK));
                    System.out.println(crawler.extractMark(ExtremumMode.MAX));
                }

            });
            crawler.addDeletedStudentListener(new CrawlerListener() {
                @Override
                public void actionPerformed(AbstractEvent abstractEvent) {
                   // for (Logger el : loggers)
                   //     el.log("REMOVED", ((CrawlerEvent) abstractEvent).getStudent()
                    //    );
                }
            });
            crawler.run();
        } catch (IOException e) {
            e.printStackTrace();

        }
    }
}
