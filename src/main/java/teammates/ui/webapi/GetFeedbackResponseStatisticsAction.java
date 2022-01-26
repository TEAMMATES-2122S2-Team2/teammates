package teammates.ui.webapi;

import teammates.common.datatransfer.attributes.FeedbackResponseStatisticAttributes;
import teammates.common.util.Const;
import teammates.ui.output.FeedbackResponseStatisticsData;

import java.time.Instant;
import java.util.List;

/**
 * Gets feedback response statistics for a specified time period.
 */
class GetFeedbackResponseStatisticsAction extends Action {

    @Override
    AuthType getMinAuthLevel() {
        return AuthType.LOGGED_IN;
    }

    @Override
    void checkSpecificAccessControl() throws UnauthorizedAccessException {
        if (!userInfo.isAdmin) {
            throw new UnauthorizedAccessException("Admin account is required to access this resource.");
        }
    }

    @Override
    public JsonResult execute() {
        String startTimeString = getNonNullRequestParamValue(Const.ParamsNames.QUERY_STATS_STARTTIME);
        long startTime;
        try {
            startTime = Long.parseLong(startTimeString);
        } catch (NumberFormatException e) {
            throw new InvalidHttpParameterException("Invalid startTime parameter", e);
        }
        try {
            // test for bounds
            Instant.ofEpochMilli(startTime).minus(Const.FEEDBACK_SESSIONS_SEARCH_WINDOW).toEpochMilli();
        } catch (ArithmeticException e) {
            throw new InvalidHttpParameterException("Invalid startTime parameter", e);
        }

        String endTimeString = getNonNullRequestParamValue(Const.ParamsNames.QUERY_STATS_ENDTIME);
        long endTime;
        try {
            endTime = Long.parseLong(endTimeString);
        } catch (NumberFormatException e) {
            throw new InvalidHttpParameterException("Invalid endTime parameter", e);
        }
        try {
            // test for bounds
            Instant.ofEpochMilli(endTime).plus(Const.FEEDBACK_SESSIONS_SEARCH_WINDOW).toEpochMilli();
        } catch (ArithmeticException e) {
            throw new InvalidHttpParameterException("Invalid endTime parameter", e);
        }

        if (startTime > endTime) {
            throw new InvalidHttpParameterException("The filter range is not valid. End time should be after start time.");
        }

        List<FeedbackResponseStatisticAttributes> feedbackResponseStatistics =
                logic.getFeedbackResponseStatistics(startTime, endTime);

        FeedbackResponseStatisticsData output = new FeedbackResponseStatisticsData((feedbackResponseStatistics));
        return new JsonResult(output);
    }
}
