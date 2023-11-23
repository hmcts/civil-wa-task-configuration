package uk.gov.hmcts.civil.taskconfiguration.dmn;

import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTable;
import uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTableBaseUnitTest;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class CamundaTaskCompletionTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = DmnDecisionTable.WA_TASK_COMPLETION_CIVIL_DAMAGES;
    }

    static Stream<Arguments> scenarioProvider() {

        return Stream.of(
            Arguments.of(
                "STANDARD_DIRECTION_ORDER_DJ",
                asList(
                    Map.of(
                        "taskType", "summaryJudgmentDirections",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "TAKE_CASE_OFFLINE",
                asList(
                    Map.of(
                        "taskType", "transferCaseOffline"
                    ),
                    Map.of(
                        "completionMode", "Auto"
                    )
                )
            )
        );
    }

    static Stream<Arguments> scenarioProviderSdo() {
        return Stream.of(
            Arguments.of(
                "CREATE_SDO",
                asList(
                    Map.of(
                        "taskType", "FastTrackDirections",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "SmallClaimsTrackDirections",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "LegalAdvisorSmallClaimsTrackDirections",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "SmallClaimsTrackDirectionsReferral",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "InitialDirectionFlightDelay",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "NotSuitable_SDO",
                asList(
                    Map.of(
                        "taskType", "summaryJudgmentDirections",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "FastTrackDirections",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "SmallClaimsTrackDirections",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "SmallClaimsTrackDirectionsReferral",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "InitialDirectionFlightDelay",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "REFER_TO_JUDGE",
                asList(
                    Map.of(
                        "taskType", "LegalAdvisorSmallClaimsTrackDirections",
                        "completionMode", "Auto"
                    )
                )
            )
        );
    }

    static Stream<Arguments> scenarioProviderHearingNotice() {
        return Stream.of(
            Arguments.of(
                "HEARING_SCHEDULED",
                asList(
                    Map.of(
                        "taskType", "ScheduleAHearing",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "completionMode", "Auto"

                    ),
                    Map.of(
                        "taskType", "adjournedReList",
                        "completionMode", "Auto"
                    )
                )
            )
        );
    }

    static Stream<Arguments> scenarioProviderCaseNote() {
        return Stream.of(
            Arguments.of(
                "ADD_CASE_NOTE",
                asList(
                    Map.of(
                        "taskType", "removeHearing",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "preHearingContact",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "reviewOrder",
                        "completionMode", "Auto"
                    )
                )
            )
        );
    }

    static Stream<Arguments> scenarioProviderToC() {

        return Stream.of(
            Arguments.of(
                "TRANSFER_ONLINE_CASE",
                asList(
                    Map.of(
                        "taskType", "transferOnlineCase",
                        "completionMode", "Auto"
                    )
                )
            )
        );
    }

    @ParameterizedTest(name = "event id: {0}")
    @MethodSource({"scenarioProvider"})
    void given_event_ids_should_evaluate_dmn(String eventId, List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @ParameterizedTest(name = "event id: {0}")
    @MethodSource({"scenarioProviderSdo"})
    void given_event_ids_should_evaluate_sdo_dmn(String eventId, List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @ParameterizedTest(name = "event id: {0}")
    @MethodSource({"scenarioProviderHearingNotice"})
    void given_event_ids_should_evaluate_hearingNotice_dmn(String eventId, List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @ParameterizedTest(name = "event id: {0}")
    @MethodSource({"scenarioProviderCaseNote"})
    void given_event_ids_should_evaluate_reviewOrder_dmn(String eventId, List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @ParameterizedTest(name = "event id: {0}")
    @MethodSource({"scenarioProviderToC"})
    void given_event_ids_should_evaluate_Toc_dmn(String eventId, List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {

        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getRules().size(), is(23));
    }
}
