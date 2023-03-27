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
import uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTable;
import uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTableBaseUnitTest;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CamundaGaWaTaskTypesTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = DmnDecisionTable.WA_TASK_TYPES_CIVIL_GENERALAPPLICATION;
    }

    static Stream<Arguments> scenarioProvider() {

        return Stream.of(
            Arguments.of(
                asList(
                    Map.of(
                        "taskTypeId", "JudgeDecideOnApplication",
                        "taskTypeName", "Decide on an Application"
                    ),
                    Map.of(
                        "taskTypeId", "ReviewApplication",
                        "taskTypeName", "Review an Application"
                    ),
                    Map.of(
                        "taskTypeId", "LegalAdvisorDecideOnApplication",
                        "taskTypeName", "Decide on an Application"
                    ),
                    Map.of(
                        "taskTypeId", "JudgeRevisitApplication",
                        "taskTypeName", "Revisit an Application"
                    ),
                    Map.of(
                        "taskTypeId", "ReviewRevisitedApplication",
                        "taskTypeName", "Review a revisited Application"
                    ),
                    Map.of(
                        "taskTypeId", "LegalAdvisorRevisitApplication",
                        "taskTypeName", "Revisit an Application"
                    ),
                    Map.of(
                        "taskTypeId", "ScheduleApplicationHearing",
                        "taskTypeName", "Schedule an Application Hearing"
                    ),
                    Map.of(
                        "taskTypeId", "reviewSpecificAccessRequestJudiciary",
                        "taskTypeName", "Specific Access Request"
                    ),
                    Map.of(
                        "taskTypeId", "reviewSpecificAccessRequestLegalOps",
                        "taskTypeName", "Specific Access Request"
                    ),
                    Map.of(
                        "taskTypeId", "reviewSpecificAccessRequestAdmin",
                        "taskTypeName", "Specific Access Request"
                    ),
                    Map.of(
                        "taskTypeId", "reviewSpecificAccessRequestCTSC",
                        "taskTypeName", "Specific Access Request"
                    )
                )
            )
        );
    }

    @ParameterizedTest
    @MethodSource("scenarioProvider")
    void should_evaluate_dmn_and_verify_task_types(List<Map<String, Object>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {

        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getRules().size(), is(11));

    }
}
