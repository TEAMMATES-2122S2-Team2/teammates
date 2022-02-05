package teammates.ui.output;

/**
 * The API output format of the feedback response statistics date range.
 */
public class FeedbackResponseStatisticsDateRangeData extends ApiOutput {

    private final long first;

    private final long latest;

    public FeedbackResponseStatisticsDateRangeData(long first, long latest) {
        this.first = first;
        this.latest = latest;
    }

    public long getFirst() {
        return first;
    }

    public long getLatest() {
        return latest;
    }
}
