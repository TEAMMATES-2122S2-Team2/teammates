package teammates.common.datatransfer.attributes;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import teammates.storage.entity.FeedbackResponseStatistic;

/**
 * The data transfer object for {@link FeedbackResponseStatistic} entities.
 */
public final class FeedbackResponseStatisticAttributes extends EntityAttributes<FeedbackResponseStatistic> {

    private long begin;
    private long amount;
    private Instant createdAt;

    // TODO: use a builder instead of this constructor
    public FeedbackResponseStatisticAttributes(long begin) {
        this.begin = begin;
    }

    /**
     * Gets the {@link FeedbackResponseStatisticAttributes} instance of the given {@link FeedbackResponseStatic}.
     */
    public static FeedbackResponseStatisticAttributes valueOf(FeedbackResponseStatistic a) {
        FeedbackResponseStatisticAttributes feedbackResponseStatisticAttributes =
                new FeedbackResponseStatisticAttributes(a.getBegin());

        feedbackResponseStatisticAttributes.begin = a.getBegin();
        feedbackResponseStatisticAttributes.amount = a.getAmount();
        feedbackResponseStatisticAttributes.createdAt = a.getCreatedAt();

        return feedbackResponseStatisticAttributes;
    }

    /**
     * Gets a deep copy of this object.
     */
    public FeedbackResponseStatisticAttributes getCopy() {
        FeedbackResponseStatisticAttributes feedbackResponseStatisticAttributes =
                new FeedbackResponseStatisticAttributes(this.begin);

        feedbackResponseStatisticAttributes.begin = this.begin;
        feedbackResponseStatisticAttributes.amount = this.amount;
        feedbackResponseStatisticAttributes.createdAt = this.createdAt;

        return feedbackResponseStatisticAttributes;
    }

    public void setBegin(long begin) {
        this.begin = begin;
    }

    public long getBegin() {
        return begin;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getAmount() {
        return amount;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public List<String> getInvalidityInfo() {
        // TODO: Nothing to check
        return new ArrayList<>();
    }

    @Override
    public FeedbackResponseStatistic toEntity() {
        return new FeedbackResponseStatistic(begin, amount);
    }

    @Override
    public String toString() {
        return "FeedbackResponseStatisticAttributes [begin="
                + begin + ", amount=" + amount + "]";
    }

    @Override
    public int hashCode() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.begin).append(this.amount);
        return stringBuilder.toString().hashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        } else if (this == other) {
            return true;
        } else if (this.getClass() == other.getClass()) {
            FeedbackResponseStatisticAttributes otherFeedbackResponseStatistic = (FeedbackResponseStatisticAttributes) other;
            return Objects.equals(this.begin, otherFeedbackResponseStatistic.begin)
                    && Objects.equals(this.amount, otherFeedbackResponseStatistic.amount);
        } else {
            return false;
        }
    }

    @Override
    public void sanitizeForSaving() {
        // TODO: Nothing to sanitize
    }
}
