package uk.gov.hmcts.reform.iataskconfiguration.dmn;

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
import uk.gov.hmcts.reform.iataskconfiguration.DmnDecisionTableBaseUnitTest;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.gov.hmcts.reform.iataskconfiguration.DmnDecisionTable.WA_TASK_ALLOWED_DAYS_IA_ASYLUM;

class CamundaTaskAllowedDaysTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_ALLOWED_DAYS_IA_ASYLUM;
    }

    static Stream<Arguments> scenarioProvider() {

        return Stream.of(
            Arguments.of(
                "provideRespondentEvidence",
                singletonList(
                    Map.of(
                        "taskId", "followUpOverdueRespondentEvidence",
                        "name", "Follow Up Overdue Respondent Evidence",
                        "workingDaysAllowed", 2
                    )
                )
            ),
            Arguments.of(
                "provideCaseBuilding",
                singletonList(
                    Map.of(
                        "taskId", "followUpOverdueCaseBuilding",
                        "name", "Follow Up Overdue Case Building",
                        "workingDaysAllowed", 2
                    )
                )
            ),
            Arguments.of(
                "provideReasonsForAppeal",
                singletonList(
                    Map.of(
                        "taskId", "followUpOverdueReasonsForAppeal",
                        "name", "Follow Up Overdue Reasons For Appeal",
                        "workingDaysAllowed", 2
                    )
                )
            ),
            Arguments.of(
                "provideClarifyingAnswers",
                singletonList(
                    Map.of(
                        "taskId", "followUpOverdueClarifyingAnswers",
                        "name", "Follow Up Overdue Clarifying Answers",
                        "workingDaysAllowed", 2
                    )
                )
            ),
            Arguments.of(
                "provideCmaRequirements",
                singletonList(
                    Map.of(
                        "taskId", "followUpOverdueCmaRequirements",
                        "name", "Follow Up Overdue Cma Requirements",
                        "workingDaysAllowed", 2
                    )
                )
            ),
            Arguments.of(
                "provideRespondentReview",
                singletonList(
                    Map.of(
                        "taskId", "followUpOverdueRespondentReview",
                        "name", "Follow Up Overdue Respondent Review",
                        "workingDaysAllowed", 2
                    )
                )
            ),
            Arguments.of(
                "provideHearingRequirements",
                singletonList(
                    Map.of(
                        "taskId", "followUpOverdueHearingRequirements",
                        "name", "Follow Up Overdue Hearing Requirements",
                        "workingDaysAllowed", 2
                    )
                )
            ),
            Arguments.of(
                "unknownTaskId",
                emptyList()
            )
        );
    }

    @ParameterizedTest(name = "task id: {0}")
    @MethodSource("scenarioProvider")
    void given_multiple_event_ids_should_evaluate_dmn(String eventId, List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskId", eventId);

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
