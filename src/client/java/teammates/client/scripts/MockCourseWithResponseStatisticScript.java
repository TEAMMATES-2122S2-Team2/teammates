package teammates.client.scripts;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import teammates.client.connector.DatastoreClient;
import teammates.common.datatransfer.DataBundle;
import teammates.common.datatransfer.FeedbackParticipantType;
import teammates.common.datatransfer.InstructorPrivileges;
import teammates.common.datatransfer.attributes.AccountAttributes;
import teammates.common.datatransfer.attributes.CourseAttributes;
import teammates.common.datatransfer.attributes.FeedbackQuestionAttributes;
import teammates.common.datatransfer.attributes.FeedbackResponseAttributes;
import teammates.common.datatransfer.attributes.FeedbackResponseStatisticAttributes;
import teammates.common.datatransfer.attributes.FeedbackSessionAttributes;
import teammates.common.datatransfer.attributes.InstructorAttributes;
import teammates.common.datatransfer.attributes.StudentAttributes;
import teammates.common.datatransfer.questions.FeedbackQuestionDetails;
import teammates.common.datatransfer.questions.FeedbackTextQuestionDetails;
import teammates.common.datatransfer.questions.FeedbackTextResponseDetails;
import teammates.common.exception.InvalidParametersException;
import teammates.common.util.Const;
import teammates.logic.api.Logic;

/**
 * Script to mock a course and populate large number of responses.
 */
public final class MockCourseWithResponseStatisticScript extends DatastoreClient {
    // Change the following params for different course setup
    private static final int NUMBER_OF_STUDENTS = 100;
    private static final int NUMBER_OF_TEAMS = 20;
    private static final int NUMBER_OF_FEEDBACK_QUESTIONS = 10;

    // For each student, the number of responses depends on:
    // number_of_students / number_of_teams * (per team feedback strategy)
    private static final FeedbackParticipantType FEEDBACK_QUESTION_RECIPIENT_TYPE =
            FeedbackParticipantType.OWN_TEAM_MEMBERS_INCLUDING_SELF;

    // Change the course ID to be recognizable
    private static final String COURSE_ID = "TestData.500S30Q100T";
    private static final String COURSE_NAME = "MockLargeCourse";
    private static final String COURSE_TIME_ZONE = "UTC";

    private static final String INSTRUCTOR_ID = "LoadInstructor_id";
    private static final String INSTRUCTOR_NAME = "LoadInstructor";
    private static final String INSTRUCTOR_EMAIL = "tmms.test@gmail.tmt";

    private static final String STUDENT_ID = "LoadStudent.tmms";
    private static final String STUDENT_NAME = "LoadStudent";
    private static final String STUDENT_EMAIL = "studentEmail@gmail.tmt";

    private static final String TEAM_NAME = "Team ";
    private static final String GIVER_SECTION_NAME = "Section 1";
    private static final String RECEIVER_SECTION_NAME = "Section 1";

    private static final String FEEDBACK_SESSION_NAME = "Test Feedback Session";

    private static final String FEEDBACK_QUESTION_ID = "QuestionTest";
    private static final String FEEDBACK_QUESTION_TEXT = "Test Question";

    private static final String FEEDBACK_RESPONSE_ID = "ResponseForQ";

    // time range in second, default 1 month
    private static final long TIME_RANGE = 2592000;
    private static final Instant END_TIME = Instant.now().plusSeconds(500);
    private static final Instant START_TIME = END_TIME.minusSeconds(TIME_RANGE);
    // interval of static objects, in millisecond
    private static final long TIME_INTERVAL = 3600000;

    private static Logic logic = Logic.inst();

    private MockCourseWithResponseStatisticScript() {
    }

    @Override
    protected void doOperation() {
        try {
            DataBundle data = generateDataBundle();
            try {
                logic.removeDataBundle(data);
            } catch (Exception e) {
                // ignore
            }
            logic.persistDataBundle(data);
            updateResponseStatistic();
            System.out.println(data.feedbackResponses.size());
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    private static Map<String, AccountAttributes> generateAccounts() {
        return new HashMap<>();
    }

    private static Map<String, CourseAttributes> generateCourses() {
        Map<String, CourseAttributes> courses = new HashMap<>();

        courses.put(COURSE_NAME, CourseAttributes.builder(COURSE_ID)
                .withName(COURSE_NAME)
                .withTimezone(COURSE_TIME_ZONE)
                .build());

        return courses;
    }

    private static Map<String, InstructorAttributes> generateInstructors() {
        Map<String, InstructorAttributes> instructors = new HashMap<>();

        instructors.put(INSTRUCTOR_NAME,
                InstructorAttributes.builder(COURSE_ID, INSTRUCTOR_EMAIL)
                        .withGoogleId(INSTRUCTOR_ID)
                        .withName(INSTRUCTOR_NAME)
                        .withRole("Co-owner")
                        .withIsDisplayedToStudents(true)
                        .withDisplayedName("Co-owner")
                        .withPrivileges(new InstructorPrivileges(
                                Const.InstructorPermissionRoleNames.INSTRUCTOR_PERMISSION_ROLE_COOWNER))
                        .build()
        );

        return instructors;
    }

    private static Map<String, StudentAttributes> generateStudents() {
        Map<String, StudentAttributes> students = new LinkedHashMap<>();
        StudentAttributes studentAttribute;

        for (int i = 1; i <= NUMBER_OF_STUDENTS; i++) {
            studentAttribute = StudentAttributes.builder(COURSE_ID, i + STUDENT_EMAIL)
                    .withGoogleId(STUDENT_ID + i)
                    .withName(STUDENT_NAME + i)
                    .withComment("This student's name is " + STUDENT_NAME + i)
                    .withSectionName(GIVER_SECTION_NAME)
                    .withTeamName(TEAM_NAME + i % NUMBER_OF_TEAMS)
                    .build();

            students.put(STUDENT_NAME + i, studentAttribute);
        }

        return students;
    }

    private static Map<String, FeedbackSessionAttributes> generateFeedbackSessions() {
        Map<String, FeedbackSessionAttributes> feedbackSessions = new LinkedHashMap<>();

        FeedbackSessionAttributes session = FeedbackSessionAttributes
                .builder(FEEDBACK_SESSION_NAME, COURSE_ID)
                .withCreatorEmail(INSTRUCTOR_EMAIL)
                .withSessionVisibleFromTime(START_TIME)
                .withResultsVisibleFromTime(START_TIME)
                .withStartTime(START_TIME)
                .withEndTime(END_TIME)
                .build();

        feedbackSessions.put(FEEDBACK_SESSION_NAME, session);

        return feedbackSessions;
    }

    private static Map<String, FeedbackQuestionAttributes> generateFeedbackQuestions() {
        List<FeedbackParticipantType> showResponses = new ArrayList<>();
        showResponses.add(FeedbackParticipantType.RECEIVER);
        showResponses.add(FeedbackParticipantType.INSTRUCTORS);

        List<FeedbackParticipantType> showGiverName = new ArrayList<>();
        showGiverName.add(FeedbackParticipantType.INSTRUCTORS);

        List<FeedbackParticipantType> showRecepientName = new ArrayList<>();
        showRecepientName.add(FeedbackParticipantType.INSTRUCTORS);

        Map<String, FeedbackQuestionAttributes> feedbackQuestions = new LinkedHashMap<>();
        FeedbackQuestionDetails details = new FeedbackTextQuestionDetails(FEEDBACK_QUESTION_TEXT);

        for (int i = 1; i <= NUMBER_OF_FEEDBACK_QUESTIONS; i++) {
            feedbackQuestions.put(FEEDBACK_QUESTION_ID + " " + i,
                    FeedbackQuestionAttributes.builder()
                            .withFeedbackSessionName(FEEDBACK_SESSION_NAME)
                            .withQuestionDescription(FEEDBACK_QUESTION_TEXT)
                            .withCourseId(COURSE_ID)
                            .withQuestionDetails(details)
                            .withQuestionNumber(i)
                            .withGiverType(FeedbackParticipantType.STUDENTS)
                            .withRecipientType(FEEDBACK_QUESTION_RECIPIENT_TYPE)
                            .withShowResponsesTo(showResponses)
                            .withShowGiverNameTo(showGiverName)
                            .withShowRecipientNameTo(showRecepientName)
                            .build()
            );
        }

        return feedbackQuestions;
    }

    private static Map<String, FeedbackResponseAttributes> generateFeedbackResponses() {
        Map<String, FeedbackResponseAttributes> feedbackResponses = new HashMap<>();

        for (int i = 1; i <= NUMBER_OF_STUDENTS; i++) {
            for (int j = 1; j <= NUMBER_OF_STUDENTS; j++) {
                if (j % NUMBER_OF_TEAMS != i % NUMBER_OF_TEAMS) {
                    continue;
                }

                for (int k = 1; k <= NUMBER_OF_FEEDBACK_QUESTIONS; k++) {
                    String responseText = FEEDBACK_RESPONSE_ID + " " + k
                            + " from student " + i + " to student " + j;
                    FeedbackTextResponseDetails details =
                            new FeedbackTextResponseDetails(responseText);

                    FeedbackResponseAttributes attributes = FeedbackResponseAttributes.builder(
                            Integer.toString(k),
                            i + STUDENT_EMAIL,
                            j + STUDENT_EMAIL)
                            .withCourseId(COURSE_ID)
                            .withFeedbackSessionName(FEEDBACK_SESSION_NAME)
                            .withGiverSection(GIVER_SECTION_NAME)
                            .withRecipientSection(RECEIVER_SECTION_NAME)
                            .withResponseDetails(details)
                            .build();
                    attributes.setCreatedAt(END_TIME.minusSeconds((long) (Math.random() * (TIME_RANGE - 1000)) + 1000));

                    feedbackResponses.put(responseText, attributes);
                }
            }
        }
        System.out.println(feedbackResponses.size());

        return feedbackResponses;
    }

    private static DataBundle generateDataBundle() {
        DataBundle dataBundle = new DataBundle();

        dataBundle.accounts = generateAccounts();
        dataBundle.courses = generateCourses();
        dataBundle.instructors = generateInstructors();
        dataBundle.students = generateStudents();
        dataBundle.feedbackSessions = generateFeedbackSessions();
        dataBundle.feedbackQuestions = generateFeedbackQuestions();
        dataBundle.feedbackResponses = generateFeedbackResponses();

        return dataBundle;
    }

    /**
     * Update all response statistic objects.
     */
    private static void updateResponseStatistic() {
        // Obtain basically all keys of statistic objects
        // Note: the begin time is set to 1 as 0 cannot be the key
        List<Long> keys = logic.getFeedbackResponseStatisticsKeys(1, END_TIME.toEpochMilli());
        final long startTimeMilli = START_TIME.toEpochMilli();

        List<Long> newKeys = new ArrayList<>();
        // we need to generate new data points if first key is after the begin time
        for (long newKey = startTimeMilli; newKey < keys.get(0); newKey += TIME_INTERVAL) {
            newKeys.add(newKey);
        }
        // Append all existing keys at the back of newly generated ones
        newKeys.addAll(keys);

        var wrapper = new Object() {
            // default 0
            long lastKey;
        };

        newKeys.forEach(key -> {
            // We will skip all keys less than start time as they are not possible to be affected.
            if (key >= startTimeMilli) {
                // See @FeedbackResponseStatisticCollectionAction.java
                Instant endTime = Instant.ofEpochMilli(key);
                Instant startTime = Instant.ofEpochMilli(wrapper.lastKey);

                int numFeedbacks = logic.getNumFeedbackResponsesByTimeRange(startTime, endTime);

                FeedbackResponseStatisticAttributes attributes =
                        new FeedbackResponseStatisticAttributes(endTime.toEpochMilli());
                attributes.setCreatedAt(endTime);
                attributes.setAmount(numFeedbacks);

                try {
                    logic.updateFeedbackResponseStatistic(attributes);
                } catch (InvalidParametersException e) {
                    System.out.println("Unexpected exception: " + e.getMessage());
                }
            }

            wrapper.lastKey = key;
        });
    }

    public static void main(String[] args) {
        new MockCourseWithResponseStatisticScript().doOperationRemotely();
    }

}
