package teammates.ui.webapi;

import java.util.List;

import teammates.common.datatransfer.attributes.FeedbackResponseStatisticAttributes;
import teammates.common.util.Const;
import teammates.ui.output.FeedbackResponseStatisticsData;

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
        String endTimeString = getNonNullRequestParamValue(Const.ParamsNames.QUERY_STATS_ENDTIME);
        long startTime;
        long endTime;
        try {
            startTime = Long.parseLong(startTimeString);
            endTime = Long.parseLong(endTimeString);
        } catch (NumberFormatException e) {
            throw new InvalidHttpParameterException("Invalid startTime or endTine parameter", e);
        }

        if (startTime > endTime) {
            throw new InvalidHttpParameterException("The end time should be after the start time.");
        }

        List<FeedbackResponseStatisticAttributes> feedbackResponseStatistics =
                logic.getFeedbackResponseStatistics(startTime, endTime);

        FeedbackResponseStatisticsData output = new FeedbackResponseStatisticsData(feedbackResponseStatistics);
        return new JsonResult(output);
    }
}
