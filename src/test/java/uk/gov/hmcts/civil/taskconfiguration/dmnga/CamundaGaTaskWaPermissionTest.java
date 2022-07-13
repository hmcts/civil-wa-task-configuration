package uk.gov.hmcts.civil.taskconfiguration.dmnga;

import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTableBaseUnitTest;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTable.WA_TASK_PERMISSIONS_CIVIL_GENERALAPPLICATION;

public class CamundaGaTaskWaPermissionTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_PERMISSIONS_CIVIL_GENERALAPPLICATION;
    }

    static Stream<Arguments> scenarioProvider() {

        return Stream.of(
            Arguments.of(
                "anything",
                asList(
                    Map.of(
                        "autoAssignable", false,
                        "roleName", "task-supervisor",
                        "value", "Read,Manage,Cancel"
                    )),
                Arguments.of(
                    "null",
                    asList(
                        Map.of(
                            "autoAssignable", false,
                            "roleName", "task-supervisor",
                            "value", "Read,Manage,Cancel"
                        ))))
        );
    }

    @ParameterizedTest(name = "case data: {0}")
    @MethodSource("scenarioProvider")
    void given_anyevent_event_ids_should_evaluate_judge_dmn(String caseData,
                                                            List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("case", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @SuppressWarnings("checkstyle:indentation")
    @Test
    void given_reviewRevisitedApplication_taskType_when_evaluate_dmn_then_it_returns_expected_rule() {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", "ReviewRevisitedApplication"));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(List.of(
            Map.of(
                "autoAssignable", false,
                "roleName", "task-supervisor",
                "value", "Read,Manage,Cancel"
            ),
            Map.of(
                "roleName", "national-business-centre",
                "value", "Read,Manage,Own,Cancel",
                "roleCategory", "ADMINISTRATOR",
                "autoAssignable", false
            )
        )));
    }

    @SuppressWarnings("checkstyle:indentation")
    @Test
    void given_scheduleApplicationHearing_taskType_when_evaluate_dmn_then_it_returns_expected_rule() {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", "ScheduleApplicationHearing"));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(List.of(
            Map.of(
                "autoAssignable", false,
                "roleName", "task-supervisor",
                "value", "Read,Manage,Cancel"
            ),
            Map.of(
                "roleName", "hearing-centre-admin",
                "value", "Read,Manage,Own,Cancel",
                "roleCategory", "ADMINISTRATOR",
                "assignmentPriority",1,
                "autoAssignable", false
            )
        )));
    }

    @SuppressWarnings("checkstyle:indentation")
    @Test
    void given_reviewSpecificAccessRequestsLegalOps_taskType_when_evaluate_dmn_then_it_returns_expected_rule() {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes",
                                Map.of("taskType", "ReviewSpecificAccessRequestsLegalOps"));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(List.of(
            Map.of(
                "autoAssignable", false,
                "roleName", "task-supervisor",
                "value", "Read,Manage,Cancel"
            ),
            Map.of(
                "roleName", "hearing-team-leader",
                "value", "Read,Manage,Own,Cancel",
                "roleCategory", "ADMINISTRATOR",
                "assignmentPriority",1,
                "autoAssignable", false
            )
        )));
    }

    @SuppressWarnings("checkstyle:indentation")
    @Test
    void given_reviewSpecificAccessRequestsAdmin_taskType_when_evaluate_dmn_then_it_returns_expected_rule() {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", "ReviewSpecificAccessRequestsAdmin"));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(List.of(
            Map.of(
                "autoAssignable", false,
                "roleName", "task-supervisor",
                "value", "Read,Manage,Cancel"
            ),
            Map.of(
                "roleName", "hearing-team-leader",
                "value", "Read,Manage,Own,Cancel",
                "roleCategory", "ADMINISTRATOR",
                "assignmentPriority",1,
                "autoAssignable", false
            ),
            Map.of(
                "roleName", "ctsc-team-leader",
                "value", "Read,Manage,Own,Cancel",
                "roleCategory", "ADMINISTRATOR",
                "assignmentPriority",1,
                "autoAssignable", false
            ),
            Map.of(
                "roleName", "nbc-team-leader",
                "value", "Read,Manage,Own,Cancel",
                "roleCategory", "ADMINISTRATOR",
                "assignmentPriority",1,
                "autoAssignable", false
            )
        )));
    }

    @SuppressWarnings("checkstyle:indentation")
    @ParameterizedTest
    @CsvSource(value = {
        "LegalAdvisorDecideOnApplication", "LegalAdvisorRevisitApplication"
    })
    void given_legalAdvisorDecideOnApp_RevisitApp_when_evaluate_dmn_then_it_returns_expected_rule(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(List.of(
            Map.of(
                "roleName", "task-supervisor",
                "value", "Read,Manage,Cancel",
                "autoAssignable", false
            ),
            Map.of(
                "roleName", "tribunal-caseworker",
                "value", "Read,Manage,Own,Cancel",
                "roleCategory", "LEGAL OPERATIONS",
                "assignmentPriority", 1,
                "autoAssignable", false
            )
        )));
    }

    @SuppressWarnings("checkstyle:indentation")
    @ParameterizedTest
    @CsvSource(value = {
        "JudgeDecideOnApplication", "JudgeRevisitApplication"
    })
    void given_judgeDecideOnApp_RevisitApp_taskType_when_evaluate_dmn_then_it_returns_expected_rule(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(List.of(
            Map.of(
                "roleName", "task-supervisor",
                "value", "Read,Manage,Cancel",
                "autoAssignable", false
            ),
            Map.of(
                "roleName", "Judge",
                "value", "Read,Manage,Own,Cancel",
                "roleCategory", "JUDICIAL",
                "authorisations", "294",
                "assignmentPriority", 1,
                "autoAssignable", false
            )
        )));
    }
}
