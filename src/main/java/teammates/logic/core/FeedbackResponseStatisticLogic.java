package teammates.logic.core;

import teammates.common.datatransfer.attributes.FeedbackResponseStatisticAttributes;
import teammates.common.exception.EntityAlreadyExistsException;
import teammates.common.exception.InvalidParametersException;
import teammates.storage.api.FeedbackResponseStatisticDb;

/**
 * Handles operations related to feedback response statistic objects.
 *
 * @see FeedbackResponseStatisticAttributes
 * @see FeedbackResponseStatistic
 */
public final class FeedbackResponseStatisticLogic {

    private static final FeedbackResponseStatisticLogic instance =
            new FeedbackResponseStatisticLogic();

    private final FeedbackResponseStatisticDb feedbackResponseStatisticDb =
            FeedbackResponseStatisticDb.inst();

    private FeedbackResponseStatisticLogic() {
        // prevent initialization
    }

    public static FeedbackResponseStatisticLogic inst() {
        return instance;
    }

    /**
     * Creates a statistic object.
     *
     * @return the created statistic object
     * @throws InvalidParametersException if the statistic object is not valid
     * @throws EntityAlreadyExistsException if the statistic object already exists in the database.
     */
    FeedbackResponseStatisticAttributes createAccount(FeedbackResponseStatisticAttributes data)
            throws InvalidParametersException, EntityAlreadyExistsException {
        return feedbackResponseStatisticDb.createEntity(data);
    }

    /**
     * Gets a statistic object.
     */
    public FeedbackResponseStatisticAttributes getAccount(long begin) {
        return feedbackResponseStatisticDb.getFeedbackResponseStatistic(begin);
    }
}
