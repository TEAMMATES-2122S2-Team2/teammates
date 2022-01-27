package teammates.ui.webapi;

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
        // FIXME: do query to gather statistics for the past one hour

        long currentTime = System.currentTimeMillis() / 1000;
        FeedbackResponseStatisticAttributes attributes =
                new FeedbackResponseStatisticAttributes(currentTime);
        attributes.setAmount(1000);

        try {
            logic.createFeedbackResponseStatistic(attributes);
        } catch (InvalidParametersException | EntityAlreadyExistsException e) {
            log.severe("Unexpected error", e);
        }
        return new JsonResult("Successful");
    }
}
