/**
 * Created by Rafał on 2017-03-27.
 */
public abstract class  AbstractEvent {
    private final CrawlerEventType type;

    public AbstractEvent(CrawlerEventType type) {
        this.type = type;
    }

    public CrawlerEventType getType() {
        return type;
    }
}
