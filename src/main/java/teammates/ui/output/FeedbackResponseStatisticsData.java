package teammates.ui.output;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import teammates.common.datatransfer.attributes.FeedbackResponseStatisticAttributes;

/**
 * The API output format of a list of {@link FeedbackResponseStatisticAttributes}.
 */
public class FeedbackResponseStatisticsData extends ApiOutput {

    private List<FeedbackResponseStatisticData> responses;

    public FeedbackResponseStatisticsData(List<FeedbackResponseStatisticAttributes> responses) {
        this.responses = responses.stream().map(FeedbackResponseStatisticData::new).collect(Collectors.toList());
    }

    public FeedbackResponseStatisticsData() {
        responses = Collections.emptyList();
    }

    public void setResponses(List<FeedbackResponseStatisticData> responses) {
        this.responses = responses;
    }

    public List<FeedbackResponseStatisticData> getResponses() {
        return responses;
    }
}
