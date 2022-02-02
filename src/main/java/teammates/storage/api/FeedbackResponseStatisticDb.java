package teammates.storage.api;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.LoadType;

import teammates.common.datatransfer.attributes.FeedbackResponseStatisticAttributes;
import teammates.storage.entity.FeedbackResponseStatistic;

/**
 * Handles CRUD operations for feedback response statistic objects.
 *
 * @see FeedbackResponseStatistic
 * @see FeedbackResponseStatisticAttributes
 */
public final class FeedbackResponseStatisticDb extends
        EntitiesDb<FeedbackResponseStatistic, FeedbackResponseStatisticAttributes> {

    private static final FeedbackResponseStatisticDb instance = new FeedbackResponseStatisticDb();

    private FeedbackResponseStatisticDb() {
        // prevent initialization
    }

    public static FeedbackResponseStatisticDb inst() {
        return instance;
    }

    /**
     * Gets a statistic object.
     */
    public FeedbackResponseStatisticAttributes getFeedbackResponseStatistic(long begin) {
        return makeAttributesOrNull(getFeedbackResponseStatisticEntity(begin));
    }

    /**
     * Deletes a statistic object.
     *
     * <p>Fails silently if there is no such statistic object.
     */
    public void deleteFeedbackResponseStatistic(long begin) {
        deleteEntity(Key.create(FeedbackResponseStatistic.class, begin));
    }

    private FeedbackResponseStatistic getFeedbackResponseStatisticEntity(long begin) {
        return load().id(begin).now();
    }

    /**
     * Gets the key of the latest statistic entity.
     */
    public long getLatestFeedbackResponseStatisticEntityId() {
        List<Key<FeedbackResponseStatistic>> keys = load().order("-__key__").limit(1).keys().list();
        if (keys.isEmpty()) {
            return 0;
        } else {
            return keys.get(0).getId();
        }
    }

    /**
     * Gets a list of statistic objects between start time and end time.
     */
    public List<FeedbackResponseStatisticAttributes> getFeedbackResponseStatistics(long startTime, long endTime) {
        Key<FeedbackResponseStatistic> startTimeKey = Key.create(FeedbackResponseStatistic.class, startTime);
        Key<FeedbackResponseStatistic> endTimeKey = Key.create(FeedbackResponseStatistic.class, endTime);
        List<FeedbackResponseStatistic> entities = load()
                .filterKey(">=", startTimeKey)
                .filterKey("<", endTimeKey)
                .list();
        return makeAttributes(entities);
    }

    @Override
    LoadType<FeedbackResponseStatistic> load() {
        return ofy().load().type(FeedbackResponseStatistic.class);
    }

    @Override
    boolean hasExistingEntities(FeedbackResponseStatisticAttributes entityToCreate) {
        Key<FeedbackResponseStatistic> keyToFind = Key.create(FeedbackResponseStatistic.class, entityToCreate.getBegin());
        return !load().filterKey(keyToFind).keys().list().isEmpty();
    }

    @Override
    FeedbackResponseStatisticAttributes makeAttributes(FeedbackResponseStatistic entity) {
        assert entity != null;

        return FeedbackResponseStatisticAttributes.valueOf(entity);
    }
}
