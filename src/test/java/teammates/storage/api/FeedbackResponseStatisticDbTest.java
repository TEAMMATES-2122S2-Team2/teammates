package teammates.storage.api;

import java.time.Instant;

import org.testng.annotations.Test;

import teammates.common.datatransfer.attributes.FeedbackResponseStatisticAttributes;
import teammates.common.exception.EntityAlreadyExistsException;
import teammates.common.exception.InvalidParametersException;
import teammates.test.BaseTestCaseWithLocalDatabaseAccess;

/**
 * SUT: {@link FeedbackResponseStatisticDb}.
 */
public class FeedbackResponseStatisticDbTest extends BaseTestCaseWithLocalDatabaseAccess {
    private final FeedbackResponseStatisticDb frsDb = FeedbackResponseStatisticDb.inst();

    @Test
    public void testGetStatistic() throws Exception {
        FeedbackResponseStatisticAttributes frsa = createNewStatistic();

        ______TS("typical success");
        FeedbackResponseStatisticAttributes result = frsDb.getFeedbackResponseStatistic(frsa.getBegin());
        assertNotNull(result);

        ______TS("failure: future date (non-existant id)");
        result = frsDb.getFeedbackResponseStatistic(Instant.now().plusSeconds(300).toEpochMilli());
        assertNull(result);

        ______TS("failure: non-positive parameter");
        assertThrows(AssertionError.class, () -> frsDb.getFeedbackResponseStatistic(-1));
        assertThrows(AssertionError.class, () -> frsDb.getFeedbackResponseStatistic(0));

        frsDb.deleteFeedbackResponseStatistic(frsa.getBegin());
    }

    @Test
    public void testCreateStatistic() throws Exception {
        ______TS("typical success case");
        FeedbackResponseStatisticAttributes frsa =
                FeedbackResponseStatisticAttributes.builder(Instant.now().toEpochMilli())
                .withAmount(1)
                .build();

        frsDb.createEntity(frsa);

        ______TS("duplicate statistic, creation fail");

        final FeedbackResponseStatisticAttributes dupFrsa = frsa;

        assertThrows(EntityAlreadyExistsException.class, () -> frsDb.createEntity(dupFrsa));

        frsDb.deleteFeedbackResponseStatistic(frsa.getBegin());
    }

    @Test
    public void testDeleteStatistic() throws Exception {
        ______TS("typical success case");
        FeedbackResponseStatisticAttributes frsa = createNewStatistic();

        frsDb.deleteFeedbackResponseStatistic(frsa.getBegin());

        ______TS("success: deleting non-existent id will not throw exception");

        frsDb.deleteFeedbackResponseStatistic(frsa.getBegin());

        ______TS("failure: non-positive parameter");
        assertThrows(AssertionError.class, () -> frsDb.deleteFeedbackResponseStatistic(-1));
        assertThrows(AssertionError.class, () -> frsDb.deleteFeedbackResponseStatistic(0));
    }

    private FeedbackResponseStatisticAttributes createNewStatistic()
            throws InvalidParametersException, EntityAlreadyExistsException {
        FeedbackResponseStatisticAttributes frsa = createNewStatisticAttributes();
        return frsDb.createEntity(frsa);
    }

    private FeedbackResponseStatisticAttributes createNewStatisticAttributes() {
        return FeedbackResponseStatisticAttributes.builder(Instant.now().toEpochMilli())
                .withAmount((long) (Math.random() * 1000))
                .build();
    }

}
