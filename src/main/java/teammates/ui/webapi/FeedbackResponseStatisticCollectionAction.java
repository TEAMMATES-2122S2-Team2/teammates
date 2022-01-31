package teammates.ui.webapi;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

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
        // Query the number of feedback submissions within the past one hour
        // Truncates (rounds down) to the nearest hour
        Instant endTime = Instant.now().truncatedTo(ChronoUnit.HOURS);
        Instant startTime = endTime.minus(1, ChronoUnit.HOURS);

        int numFeedbacks = logic.getNumFeedbackResponsesByTimeRange(startTime, endTime);

        FeedbackResponseStatisticAttributes attributes =
                new FeedbackResponseStatisticAttributes(startTime.toEpochMilli());
        attributes.setAmount(numFeedbacks);

        try {
            logic.createFeedbackResponseStatistic(attributes);
        } catch (InvalidParametersException | EntityAlreadyExistsException e) {
            log.severe("Unexpected error", e);
        }
        return new JsonResult("Successful");
    }
}
