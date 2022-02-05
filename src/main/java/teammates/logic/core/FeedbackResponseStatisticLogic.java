package teammates.logic.core;

import java.util.List;

import teammates.common.datatransfer.attributes.FeedbackResponseStatisticAttributes;
import teammates.common.exception.EntityAlreadyExistsException;
import teammates.common.exception.InvalidParametersException;
import teammates.storage.api.FeedbackResponseStatisticDb;

/**
 * Handles operations related to feedback response statistic objects.
 *
 * @see FeedbackResponseStatisticAttributes
 * @see FeedbackResponseStatisticDb
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
    public FeedbackResponseStatisticAttributes createFeedbackResponseStatistic(FeedbackResponseStatisticAttributes data)
            throws InvalidParametersException, EntityAlreadyExistsException {
        return feedbackResponseStatisticDb.createEntity(data);
    }

    /**
     * Updates a statistic object by overwriting the existing one, if necessary.
     */
    public FeedbackResponseStatisticAttributes updateFeedbackResponseStatistic(FeedbackResponseStatisticAttributes data)
            throws InvalidParametersException {
        return feedbackResponseStatisticDb.putEntity(data);
    }

    /**
     * Gets a statistic object.
     */
    public FeedbackResponseStatisticAttributes getFeedbackResponseStatistic(long begin) {
        return feedbackResponseStatisticDb.getFeedbackResponseStatistic(begin);
    }

    /**
     * Gets the key of the latest statistic entity.
     */
    public long getLatestFeedbackResponseStatisticEntityId() {
        return feedbackResponseStatisticDb.getLatestFeedbackResponseStatisticEntityId();
    }

    /**
     * Gets the key of the first statistic entity.
     */
    public long getFirstFeedbackResponseStatisticEntityId() {
        return feedbackResponseStatisticDb.getFirstFeedbackResponseStatisticEntityId();
    }

    /**
     * Gets a list of statistic objects between start time and end time.
     */
    public List<FeedbackResponseStatisticAttributes> getFeedbackResponseStatistics(long startTime, long endTime) {
        return feedbackResponseStatisticDb.getFeedbackResponseStatistics(startTime, endTime);
    }

    /**
     * Gets a list of statistic objects' keys between start time and end time.
     */
    public List<Long> getFeedbackResponseStatisticsKeys(long startTime, long endTime) {
        return feedbackResponseStatisticDb.getFeedbackResponseStatisticsKeys(startTime, endTime);
    }
}
