package teammates.ui.webapi;

import teammates.ui.output.FeedbackResponseStatisticsDateRangeData;

/**
 * Gets the time range for the feedback statistics data.
 */
class GetFeedbackResponseStatisticsDateRangeAction extends Action {

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
        long first = logic.getFirstFeedbackResponseStatisticEntityId();
        long latest = logic.getLatestFeedbackResponseStatisticEntityId();

        FeedbackResponseStatisticsDateRangeData output = new FeedbackResponseStatisticsDateRangeData(first, latest);
        return new JsonResult(output);
    }
}
