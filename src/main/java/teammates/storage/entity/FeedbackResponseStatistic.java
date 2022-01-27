package teammates.storage.entity;

import java.time.Instant;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;
import com.googlecode.objectify.annotation.Translate;

/**
 * Represents a feedback response statistic object.
 * Each object represents the number of new submissions for the time period beginning at beginTime
 */
@Entity
@Index
public class FeedbackResponseStatistic extends BaseEntity {
    /** Beginning of the statistic object, in TIMESTAMP. */
    @Id
    private long begin;

    /** Amount of SECONDS this object lasts. */
    private long amount;

    @Translate(InstantTranslatorFactory.class)
    private Instant createdAt;

    @SuppressWarnings("unused")
    private FeedbackResponseStatistic() {
        // required by Objectify
    }

    public FeedbackResponseStatistic(long begin, long amount) {
        this.setBegin(begin);
        this.setAmount(amount);
        this.setCreatedAt(Instant.now());
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
}
