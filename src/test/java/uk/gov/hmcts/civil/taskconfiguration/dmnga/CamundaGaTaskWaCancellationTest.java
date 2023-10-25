package uk.gov.hmcts.civil.taskconfiguration.dmnga;

import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTableBaseUnitTest;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTable.WA_TASK_CANCELLATION_CIVIL_GENERALAPPLICATION;

public class CamundaGaTaskWaCancellationTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_CANCELLATION_CIVIL_GENERALAPPLICATION;
    }

    @ParameterizedTest
    @MethodSource("scenarioProviderCaseClosed")
    void given_multiple_event_ids_should_evaluate_dmn_for_case_closed(String fromState,
                                                                         String eventId, String state,
                                                                         List<Map<String, Object>> expectedDmnOutcome) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("fromState", "");
        inputVariables.putValue("state", state);
        inputVariables.putValue("event", eventId);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertThat(dmnDecisionTableResult.getResultList(), is(expectedDmnOutcome));
    }

    public static Stream<Arguments> scenarioProviderCaseClosed() {
        List<Map<String, String>> outcome = List.of(
            Map.of(
                "action", "Cancel",
                "processCategories", "generalApplications"
            )
        );
        return Stream.of(
            Arguments.of(
                "any state", "MAIN_CASE_CLOSED", "APPLICATION_CLOSED",
                outcome
            ),
            Arguments.of(
                "", "MAIN_CASE_CLOSED", "APPLICATION_CLOSED",
                outcome
            ),
            Arguments.of(
                null, "MAIN_CASE_CLOSED", "APPLICATION_CLOSED",
                outcome

            )
        );
    }

    @ParameterizedTest
    @MethodSource("scenarioProviderLocationUpdateAwaitingJudicialDecision")
    void given_event_ids_should_evaluate_dmn_for_location_update_WithState_AwaitingJudicialDecision(String fromState,
                                                                      String eventId, String state,
                                                                      List<Map<String, Object>> expectedDmnOutcome) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("fromState", "");
        inputVariables.putValue("state", state);
        inputVariables.putValue("event", eventId);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertThat(dmnDecisionTableResult.getResultList(), is(expectedDmnOutcome));
    }

    public static Stream<Arguments> scenarioProviderLocationUpdateAwaitingJudicialDecision() {
        List<Map<String, String>> outcome = List.of(
            Map.of(
                "action", "Cancel",
                "processCategories", "generalApplicationsBeforeSdo"
            )
        );
        return Stream.of(
            Arguments.of(
                "any state", "TRIGGER_LOCATION_UPDATE", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION",
                outcome
            ),
            Arguments.of(
                "", "TRIGGER_LOCATION_UPDATE", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION",
                outcome
            ),
            Arguments.of(
                null, "TRIGGER_LOCATION_UPDATE", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION",
                outcome

            )
        );
    }

    @ParameterizedTest
    @MethodSource("scenarioProviderLocationUpdateToAdditionalResponseState")
    void given_event_ids_should_evaluate_dmn_for_location_update_WithState_AdditionalResponseState(
        String fromState,
        String eventId, String state,
        List<Map<String, Object>> expectedDmnOutcome) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("fromState", "");
        inputVariables.putValue("state", state);
        inputVariables.putValue("event", eventId);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertThat(dmnDecisionTableResult.getResultList(), is(expectedDmnOutcome));
    }

    public static Stream<Arguments> scenarioProviderLocationUpdateToAdditionalResponseState() {
        List<Map<String, String>> outcome = List.of(
            Map.of(
                "action", "Cancel",
                "processCategories", "generalApplicationsBeforeSdo"
            )
        );
        return Stream.of(
            Arguments.of(
                "any state", "TRIGGER_LOCATION_UPDATE", "ADDITIONAL_RESPONSE_TIME_EXPIRED",
                outcome
            ),
            Arguments.of(
                "", "TRIGGER_LOCATION_UPDATE", "ADDITIONAL_RESPONSE_TIME_EXPIRED",
                outcome
            ),
            Arguments.of(
                null, "TRIGGER_LOCATION_UPDATE", "ADDITIONAL_RESPONSE_TIME_EXPIRED",
                outcome

            )
        );
    }

    @ParameterizedTest
    @MethodSource("scenarioProviderCaseClosed1")
    void given_multiple_event_ids_should_evaluate_dmn_for_trigger_task_reconfig(String fromState,
                                                                      String eventId, String state,
                                                                      List<Map<String, Object>> expectedDmnOutcome) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("fromState", "");
        inputVariables.putValue("state", state);
        inputVariables.putValue("event", eventId);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertThat(dmnDecisionTableResult.getResultList(), is(expectedDmnOutcome));
    }

    public static Stream<Arguments> scenarioProviderCaseTriggerTaskReconfig() {
        List<Map<String, String>> outcome = List.of(
            Map.of(
                "action", "ReConfigure"
            )
        );
        return Stream.of(
            Arguments.of(
                "any state", "TRIGGER_TASK_RECONFIG", "any state",
                outcome
            ),
            Arguments.of(
                "", "TRIGGER_TASK_RECONFIG", "",
                outcome
            ),
            Arguments.of(
                null, "TRIGGER_TASK_RECONFIG", "",
                outcome

            )
        );
    }
    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getInputs().size(), is(3));
        assertThat(logic.getOutputs().size(), is(4));
        assertThat(logic.getRules().size(), is(4));
    }


}
