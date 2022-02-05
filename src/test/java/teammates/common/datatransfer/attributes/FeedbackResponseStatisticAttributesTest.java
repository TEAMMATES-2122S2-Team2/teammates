package teammates.common.datatransfer.attributes;

import java.time.Instant;

import org.testng.annotations.Test;

import teammates.storage.entity.FeedbackResponseStatistic;

/**
 * SUT {@link FeedbackResponseStatisticAttributes}.
 */
public class FeedbackResponseStatisticAttributesTest extends BaseAttributesTest {

    @Test
    public void testBuilder() {
        Instant timeNow = Instant.now();

        ______TS("typical success case");
        FeedbackResponseStatisticAttributes frsa = FeedbackResponseStatisticAttributes.builder(1)
                .withAmount(2)
                .build();

        assertEquals(1, frsa.getBegin());
        assertEquals(2, frsa.getAmount());
        assertNotNull(frsa.getCreatedAt());
        // auto-generated timestamp should not before the begin time
        assertTrue(frsa.getCreatedAt().toEpochMilli() >= frsa.getBegin());

        ______TS("failure: non-positive timestamp");
        assertThrows(AssertionError.class, () -> FeedbackResponseStatisticAttributes.builder(-1));
        assertThrows(AssertionError.class, () -> FeedbackResponseStatisticAttributes.builder(0));

        ______TS("failure: future date");
        assertThrows(AssertionError.class, () ->
                FeedbackResponseStatisticAttributes.builder(timeNow.plusSeconds(300).toEpochMilli()));

        ______TS("failure: negative amount");
        assertThrows(AssertionError.class, () -> FeedbackResponseStatisticAttributes.builder(1)
                .withAmount(-1));
    }

    @Override
    @Test
    public void testToEntity() {
        FeedbackResponseStatisticAttributes frsa = FeedbackResponseStatisticAttributes.builder(1)
                .withAmount(2)
                .build();

        FeedbackResponseStatistic frs = frsa.toEntity();

        assertEquals(frsa.getBegin(), frs.getBegin());
        assertEquals(frsa.getAmount(), frs.getAmount());
        assertEquals(frsa.getCreatedAt(), frs.getCreatedAt());
    }

}
