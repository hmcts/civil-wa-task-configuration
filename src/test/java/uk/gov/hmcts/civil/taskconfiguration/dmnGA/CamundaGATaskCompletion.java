package uk.gov.hmcts.civil.taskconfiguration.dmnGA;

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

public class CamundaGATaskCompletion extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = DmnDecisionTable.WA_TASK_COMPLETION_CIVIL_GENERALAPPLICATION;
    }

    static Stream<Arguments> scenarioProvider() {

        return Stream.of(
            Arguments.of(
                "GENERAL_APPLICATION_CREATION",
                asList(
                    Map.of(
                        "taskType", "ReviewApplication",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                    )),
                Arguments.of(
                    "ADDITIONAL_RESPONSE_TIME_EXPIRED",
                    asList(
                        Map.of(
                            "taskType", "ReviewRevisitedApplication",
                            "completionMode", "Auto"
                        ),
                        Map.of(
                        ))),
                Arguments.of(
                    "GENERAL_APPLICATION_CREATION",
                    asList(
                        Map.of(
                        ))),
                Arguments.of(
                    "JUDGE_MAKES_DECISION",
                    asList(
                        Map.of(
                            "taskType", "DecideOnApplication",
                            "completionMode", "Auto"
                        ),
                        Map.of(
                            "taskType", "ScheduleApplicationHearing",
                            "completionMode", "Auto"
                        ))),
                Arguments.of(
                    "RESPOND_TO_APPLICATION",
                    asList(
                        Map.of(
                            "taskType", "RevisitApplication",
                            "completionMode", "Auto"
                        )))
            )
        );
    }

    @ParameterizedTest(name = "event id: {0}")
    @MethodSource("scenarioProvider")
    void given_event_ids_should_evaluate_dmn(String eventId, List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {

        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getRules().size(), is(7));

    }
}
