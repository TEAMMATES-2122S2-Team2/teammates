package teammates.ui.output;

import teammates.common.datatransfer.attributes.FeedbackResponseStatisticAttributes;

/**
 * The API output format of {@link FeedbackResponseStatisticAttributes}.
 */
public class FeedbackResponseStatisticData extends ApiOutput {

    private final Long timestamp;

    private final Long numResponses;

    public FeedbackResponseStatisticData(FeedbackResponseStatisticAttributes feedbackResponseStatisticAttributes) {
        this.timestamp = feedbackResponseStatisticAttributes.getBegin();
        this.numResponses = feedbackResponseStatisticAttributes.getAmount();
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public Long getNumResponses() {
        return numResponses;
    }

}
