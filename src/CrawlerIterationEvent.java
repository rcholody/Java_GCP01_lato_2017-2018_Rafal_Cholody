/**
 * Created by Rafał on 2017-03-25.
 */
public class CrawlerIterationEvent extends AbstractEvent {

    private final int iteration;




    public int getIteration() {
        return iteration;
    }

    public CrawlerIterationEvent(CrawlerEventType type, int iteration) {
        super (type);

        this.iteration = iteration;
    }
}
