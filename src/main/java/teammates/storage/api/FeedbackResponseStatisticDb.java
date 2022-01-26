package teammates.storage.api;

import java.util.List;

import static com.googlecode.objectify.ObjectifyService.ofy;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.cmd.LoadType;

import teammates.common.datatransfer.attributes.FeedbackResponseStatisticAttributes;
import teammates.storage.entity.FeedbackResponseStatistic;

/**
 * Handles CRUD operations for accounts.
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

    public List<FeedbackResponseStatisticAttributes> getFeedbackResponseStatistics(long startTime, long endTime) {
        List<FeedbackResponseStatistic> entities = load()
                .filter("begin >", startTime)
                .filter("begin <", endTime)
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
