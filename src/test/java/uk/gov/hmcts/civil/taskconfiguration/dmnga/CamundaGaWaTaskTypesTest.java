package uk.gov.hmcts.civil.taskconfiguration.dmnga;

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
                        "task_type_id", "JudgeDecideOnApplication",
                        "task_type_name", "Decide on an Application"
                    ),
                    Map.of(
                        "task_type_id", "ReviewApplication",
                        "task_type_name", "Review an Application"
                    ),
                    Map.of(
                        "task_type_id", "LegalAdvisorDecideOnApplication",
                        "task_type_name", "Decide on an Application"
                    ),
                    Map.of(
                        "task_type_id", "JudgeRevisitApplication",
                        "task_type_name", "Revisit an Application"
                    ),
                    Map.of(
                        "task_type_id", "ReviewRevisitedApplication",
                        "task_type_name", "Review a revisited Application"
                    ),
                    Map.of(
                        "task_type_id", "LegalAdvisorRevisitApplication",
                        "task_type_name", "Revisit an Application"
                    ),
                    Map.of(
                        "task_type_id", "ScheduleApplicationHearing",
                        "task_type_name", "Schedule an Application Hearing"
                    ),
                    Map.of(
                        "task_type_id", "reviewSpecificAccessRequestJudiciary",
                        "task_type_name", "Specific Access Request"
                    ),
                    Map.of(
                        "task_type_id", "reviewSpecificAccessRequestLegalOps",
                        "task_type_name", "Specific Access Request"
                    ),
                    Map.of(
                        "task_type_id", "reviewSpecificAccessRequestAdmin",
                        "task_type_name", "Specific Access Request"
                    ),
                    Map.of(
                        "task_type_id", "reviewSpecificAccessRequestCTSC",
                        "task_type_name", "Specific Access Request"
                    )
                )
            )
        );
    }

    @ParameterizedTest
    @MethodSource("scenarioProvider")
    void should_evaluate_dmn_and_verify_task_types( List<Map<String, Object>> expectation) {

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
