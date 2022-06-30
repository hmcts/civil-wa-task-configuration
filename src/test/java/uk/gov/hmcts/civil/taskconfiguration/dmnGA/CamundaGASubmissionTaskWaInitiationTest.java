package uk.gov.hmcts.civil.taskconfiguration.dmnGA;

import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTable;
import uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTableBaseUnitTest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CamundaGASubmissionTaskWaInitiationTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = DmnDecisionTable.WA_TASK_INITIATION_CIVIL_GENERALAPPLICATION;
    }

    @ParameterizedTest
    @MethodSource("scenarioProvider")
    void given_input_should_return_outcome_dmn(String eventId,
                                               String postEventState,
                                               Map<String, ? extends Serializable> expectedDmnOutcome) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", postEventState);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertThat(dmnDecisionTableResult.getResultList(), is(singletonList(expectedDmnOutcome)));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "workingDaysAllowed",
            "value", 2
        )));
    }

    public static Stream<Arguments> scenarioProvider() {
        Map<String, Object> caseData = new HashMap<>(); // allow null values
        //caseData.put("appealType", appealType);
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "Yes"

        ));
        return Stream.of(
            Arguments.of(
                "GENERAL_APPLICATION_CREATION", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION",
                Map.of(
                    "taskId", "ReviewApplication",
                    "name", "Review Application for Summ J/ment \n" +
                        "Review Application for Ext of Time\n" +
                        "Review Application to Strike Out\n" +
                        "Review Application for Relief from Sanctions\n" +
                        "Review Application for Stay Claim\n" +
                        "Review Application for Amend Statement of Case",
                    "workingDaysAllowed", caseData,
                    "processCategories","generalApplications"
                )
            )
        );
    }

    @ParameterizedTest
    @MethodSource("judgeWrittenRep")
    void given_input_should_return_outcome_dmn_for_Judge_written_rep(String eventId,
                                               String postEventState,
                                               Map<String, ? extends Serializable> expectedDmnOutcome) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", postEventState);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertThat(dmnDecisionTableResult.getResultList(), is(singletonList(expectedDmnOutcome)));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "workingDaysAllowed",
            "value", 2
        )));
    }

    public static Stream<Arguments> judgeWrittenRep() {
        Map<String, Object> caseData = new HashMap<>(); // allow null values
        //caseData.put("appealType", appealType);
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "Yes"

        ));
        return Stream.of(
            Arguments.of(
                "JUDGE_MAKES_DECISION", "RESPOND_TO_JUDGE_WRITTEN_REPRESENTATION",
                Map.of(
                    "taskId", "DecideOnApplication",
                    "name", "Application for Summ J/ment \n" +
                        "Application for Ext of Time\n" +
                        "Application to Strike Out\n" +
                        "Application for Relief from Sanctions\n" +
                        "Application for Stay Claim\n" +
                        "Application for Amend Statement of Case",
                    "workingDaysAllowed", caseData,
                    "processCategories","generalApplications"
                )
            )
        );
    }

    @ParameterizedTest
    @MethodSource("judgeAddlnInfo")
    void given_input_should_return_outcome_dmn_for_Judge_Additional_Info(String eventId,
                                                                     String postEventState,
                                                                     Map<String, ? extends Serializable> expectedDmnOutcome) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", postEventState);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertThat(dmnDecisionTableResult.getResultList(), is(singletonList(expectedDmnOutcome)));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "workingDaysAllowed",
            "value", 2
        )));
    }

    public static Stream<Arguments> judgeAddlnInfo() {
        Map<String, Object> caseData = new HashMap<>(); // allow null values
        //caseData.put("appealType", appealType);
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "Yes"

        ));
        return Stream.of(
            Arguments.of(
                "JUDGE_MAKES_DECISION", "RESPOND_TO_JUDGE_ADDITIONAL_INFO",
                Map.of(
                    "taskId", "DecideOnApplication",
                    "name", "Application for Summ J/ment \n" +
                        "Application for Ext of Time\n" +
                        "Application to Strike Out\n" +
                        "Application for Relief from Sanctions\n" +
                        "Application for Stay Claim\n" +
                        "Application for Amend Statement of Case",
                    "workingDaysAllowed", caseData,
                    "processCategories","generalApplications"
                )
            )
        );
    }

    @ParameterizedTest
    @MethodSource("judgeHearingOrder")
    void given_input_should_return_outcome_dmn_for_Judge_Hearing_order(String eventId,
                                                                         String postEventState,
                                                                         Map<String, ? extends Serializable> expectedDmnOutcome) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", postEventState);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertThat(dmnDecisionTableResult.getResultList(), is(singletonList(expectedDmnOutcome)));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "workingDaysAllowed",
            "value", 2
        )));
    }

    public static Stream<Arguments> judgeHearingOrder() {
        Map<String, Object> caseData = new HashMap<>(); // allow null values
        //caseData.put("appealType", appealType);
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "Yes"

        ));
        return Stream.of(
            Arguments.of(
                "JUDGE_MAKES_DECISION", "LISTED_FOR_A_HEARING",
                Map.of(
                    "taskId", "ScheduleApplicationHearing",
                    "name", "Application Hearing Schdeduled",
                    "workingDaysAllowed", caseData,
                    "processCategories","generalApplications"
                )
            )
        );
    }

    @ParameterizedTest
    @MethodSource("judgeAddlnTimeExpired")
    void given_input_should_return_outcome_dmn_for_Judge_Addln_Time_Expired(String eventId,
                                                                       String postEventState,
                                                                       Map<String, ? extends Serializable> expectedDmnOutcome) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", postEventState);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertThat(dmnDecisionTableResult.getResultList(), is(singletonList(expectedDmnOutcome)));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "workingDaysAllowed",
            "value", 2
        )));
    }

    public static Stream<Arguments> judgeAddlnTimeExpired() {
        Map<String, Object> caseData = new HashMap<>(); // allow null values
        //caseData.put("appealType", appealType);
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "Yes"

        ));
        return Stream.of(
            Arguments.of(
                "ADDITIONAL_RESPONSE_TIME_EXPIRED", "RESPOND_TO_JUDGE_ADDITIONAL_INFO",
                Map.of(
                    "taskId", "ReviewRevisitedApplication",
                    "name", "Review Summ J/ment App - revisited written reps\n" +
                        "Review Summ J/ment App - revisited further info\n" +
                        "Review Summ J/ment App - revisited directions order\n" +
                        "\n" +
                        "Review Ext of Time App - revisited written reps\n" +
                        "Review Ext of Time App - revisited further info\n" +
                        "Review Ext of Time App - revisited directions order\n" +
                        "\n" +
                        "Review Strike Out App  - revisited written reps\n" +
                        "Review Strike Out App  - revisited further info\n" +
                        "Review Strike Out App  - revisited directions order\n" +
                        "\n" +
                        "Review Relief from Sanctions App  - revisited written reps\n" +
                        "Review Relief from Sanctions App  - revisited further info\n" +
                        "Review Relief from Sanctions App  - revisited directions order\n" +
                        "\n" +
                        "Review Stay Claim App - revisited written reps\n" +
                        "Review Stay Claim App - revisited further info\n" +
                        "Review Stay Claim App - revisited directions order\n" +
                        "\n" +
                        "Review Amend Statement of Case App - revisited written reps\n" +
                        "Review Amend Statement of Case App - revisited further info\n" +
                        "Review Amend Statement of Case App - revisited directions order",
                    "workingDaysAllowed", caseData,
                    "processCategories","generalApplications"
                )
            )
        );
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getRules().size(), is(1));

    }

}
