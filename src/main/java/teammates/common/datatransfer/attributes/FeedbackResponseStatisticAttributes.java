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

    private FeedbackResponseStatisticAttributes(long begin) {
        this.begin = begin;
        this.createdAt = Instant.now();
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
        // Nothing to check
        return new ArrayList<>();
    }

    @Override
    public FeedbackResponseStatistic toEntity() {
        FeedbackResponseStatistic frs = new FeedbackResponseStatistic(begin, amount);
        if (createdAt != null) {
            frs.setCreatedAt(createdAt);
        }
        return frs;
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
        // Nothing to sanitize
    }

    /**
     * Returns a builder for {@link FeedbackResponseAttributes}.
     */
    public static Builder builder(long begin) {
        return new Builder(begin);
    }

    /**
     * Updates with {@link UpdateOptions}.
     */
    public void update(UpdateOptions updateOptions) {
        updateOptions.amountOption.ifPresent(s -> amount = s);
    }

    /**
     * Returns a {@link UpdateOptions.Builder} to build {@link UpdateOptions} for a response.
     */
    public static UpdateOptions.Builder updateOptionsBuilder(long begin) {
        return new UpdateOptions.Builder(begin);
    }

    /**
     * A builder for {@link FeedbackResponseStatisticAttributes}.
     */
    public static class Builder extends BasicBuilder<FeedbackResponseStatisticAttributes, Builder> {

        private FeedbackResponseStatisticAttributes frsa;

        private Builder(long begin) {
            super(new UpdateOptions(begin));
            thisBuilder = this;
            assert begin > 0;
            assert begin <= Instant.now().toEpochMilli();
            frsa = new FeedbackResponseStatisticAttributes(begin);
        }

        public Builder withAmount(long amount) {
            assert amount >= 0;
            frsa.amount = amount;

            return this;
        }

        @Override
        public FeedbackResponseStatisticAttributes build() {
            frsa.update(updateOptions);

            return frsa;
        }
    }

    /**
     * Helper class to specific the fields to update in {@link FeedbackResponseStatisticAttributes}.
     */
    public static class UpdateOptions {
        private long begin;

        private UpdateOption<Long> amountOption = UpdateOption.empty();

        private UpdateOptions(long begin) {
            assert begin > 0;

            this.begin = begin;
        }

        public long getBegin() {
            return begin;
        }

        @Override
        public String toString() {
            return "FeedbackResponseStatisticAttributes.UpdateOptions ["
                    + "begin = " + begin
                    + ", amount = " + amountOption
                    + "]";
        }

        /**
         * Builder class to build {@link UpdateOptions}.
         */
        public static class Builder extends BasicBuilder<UpdateOptions, Builder> {

            private Builder(long begin) {
                super(new UpdateOptions(begin));
                thisBuilder = this;
            }

            public Builder withAmount(long amount) {
                assert amount >= 0;

                updateOptions.amountOption = UpdateOption.of(amount);
                return thisBuilder;
            }

            @Override
            public UpdateOptions build() {
                return updateOptions;
            }

        }

    }

    /**
     * Basic builder to build {@link FeedbackResponseStatisticAttributes} related classes.
     *
     * @param <T> type to be built
     * @param <B> type of the builder
     */
    private abstract static class BasicBuilder<T, B extends BasicBuilder<T, B>> {

        UpdateOptions updateOptions;
        B thisBuilder;

        BasicBuilder(UpdateOptions updateOptions) {
            this.updateOptions = updateOptions;
        }

        public abstract T build();

    }
}
