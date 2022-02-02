package teammates.ui.webapi;

import java.time.Instant;

import teammates.common.datatransfer.attributes.FeedbackResponseStatisticAttributes;
import teammates.common.exception.EntityAlreadyExistsException;
import teammates.common.exception.InvalidParametersException;
import teammates.common.util.Logger;

/**
 * Counts the number of new feedback submissions in the past one hour and store it in database.
 */
public class FeedbackResponseStatisticCollectionAction extends AdminOnlyAction {
    private static final Logger log = Logger.getLogger();

    @Override
    public JsonResult execute() {
        // Query the number of feedback submissions since the last collection
        // Truncates (rounds down) to the nearest hour
        Instant endTime = Instant.now();
        Instant startTime = Instant.ofEpochMilli(logic.getLatestFeedbackResponseStatisticEntityId());

        int numFeedbacks = logic.getNumFeedbackResponsesByTimeRange(startTime, endTime);

        FeedbackResponseStatisticAttributes attributes =
                new FeedbackResponseStatisticAttributes(endTime.toEpochMilli());
        attributes.setAmount(numFeedbacks);

        try {
            logic.createFeedbackResponseStatistic(attributes);
        } catch (InvalidParametersException | EntityAlreadyExistsException e) {
            log.severe("Unexpected error", e);
        }
        return new JsonResult("Successful");
    }
}
