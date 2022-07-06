package uk.gov.hmcts.civil.taskconfiguration.dmn;

import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
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

class CamundaTaskWaPermissionTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = DmnDecisionTable.WA_TASK_PERMISSIONS_CIVIL_DAMAGES;
    }

    static Stream<Arguments> scenarioProvider() {

        return Stream.of(
            Arguments.of(
                "anything",
                asList(
                    Map.of(
                        "autoAssignable", false,
                        "name", "task-supervisor",
                        "value", "Read,Manage,Cancel",
                        "roleCategory", "JUDICIAL"
                    )),
                    Arguments.of(
                        "null",
                        asList(
                            Map.of(
                                "autoAssignable", false,
                                "name", "task-supervisor",
                                "value", "Read,Manage,Cancel",
                                "roleCategory", "JUDICIAL"
                            ))))
                    );
    }

    @ParameterizedTest(name = "case data: {0}")
    @MethodSource({"scenarioProvider","scenarioProvidersdo"})
    void given_anyevent_event_ids_should_evaluate_judge_dmn(String caseData,
                                                      List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("case", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @SuppressWarnings("checkstyle:indentation")
    @Test
    void given_summaryJudgmentDirections_taskType_when_evaluate_dmn_then_it_returns_expected_rule() {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", "summaryJudgmentDirections"));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(List.of(
            Map.of(
                "autoAssignable", false,
                "name", "task-supervisor",
                "value", "Read,Manage,Cancel",
                "roleCategory", "JUDICIAL"
            ),
            Map.of(
                "name", "Judge",
                "value", "Read,Manage,Own,Cancel",
                "roleCategory", "JUDICIAL",
                "authorisations","294",
                "assignmentPriority",1,
                "autoAssignable", false
            )
        )));

    }

    @SuppressWarnings("checkstyle:indentation")
    @Test
    void given_reviewSpecificAccessRequestLegalOps_taskType_when_evaluate_dmn_then_it_returns_expected_rule() {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", "reviewSpecificAccessRequestLegalOps"));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(List.of(
            Map.of(
                "autoAssignable", false,
                "name", "task-supervisor",
                "roleCategory","JUDICIAL",
                "value", "Read,Manage,Cancel"
            ),
            Map.of(
                "name", "leadership-judge",
                "value", "Read,Manage,Own,Cancel",
                "roleCategory", "JUDICIAL",
                "assignmentPriority",1,
                "autoAssignable", true
            )
        )));

    }

    static Stream<Arguments> scenarioProvidersdo() {

        return Stream.of(
            Arguments.of(
                "anything",
                asList(
                    Map.of(
                        "autoAssignable", false,
                        "name", "task-supervisor",
                        "value", "Read,Manage,Cancel",
                        "roleCategory", null
                    )),
                Arguments.of(
                    "null",
                    asList(
                        Map.of(
                            "autoAssignable", false,
                            "name", "task-supervisor",
                            "value", "Read,Manage,Cancel",
                            "roleCategory", null
                        ))))
        );
    }

    @SuppressWarnings("checkstyle:indentation")
    @Test
    void given_summaryJudgmentDirections_taskType_when_evaluate_dmn_then_it_returns_expected_rule_forSdo() {
        VariableMap inputVariables = new VariableMapImpl();

        inputVariables.putValue("taskAttributes", Map.of("taskType", "FastTrackDirections"));
        inputVariables.putValue("taskAttributes", Map.of("taskType", "SmallClaimsTrackDirections"));
        inputVariables.putValue("taskAttributes", Map.of("taskType", "SmallClaimsTrackDirectionsReferral"));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(List.of(
            Map.of(
                "autoAssignable", false,
                "name", "task-supervisor",
                "roleCategory","JUDICIAL",
                "value", "Read,Manage,Cancel"
            ),
            Map.of(
                "name", "Judge",
                "value", "Read,Own,Manage,Cancel",
                "roleCategory", "JUDICIAL",
                "authorisations","294",
                "assignmentPriority",1,
                "autoAssignable", false
            )
        )));

    }

    @SuppressWarnings("checkstyle:indentation")
    @Test
    void given_reviewSpecificAccessRequestTri_caser_taskType_when_evaluate_dmn_then_it_returns_expected_rule_forSdo() {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", "LegalAdviserSmallClaimsTrackDirections"));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(List.of(
            Map.of(
                "autoAssignable", false,
                "name", "task-supervisor",
                "roleCategory","JUDICIAL",
                "value", "Read,Manage,Cancel"
            ),
            Map.of(
                "name", "tribunal-caseworker",
                "value", "Read,Own,Manage,Cancel",
                "roleCategory", "LEGAL_OPERATIONS",
                "autoAssignable", false
            )
        )));

    }

    @SuppressWarnings("checkstyle:indentation")
    @Test
    void given_reviewSpecificAccessRequestHear_admin_taskType_when_evaluate_dmn_then_it_returns_expected_rule_forSdo() {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", "ScheduleAHearing"));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(List.of(
            Map.of(
                "autoAssignable", false,
                "name", "task-supervisor",
                "roleCategory","JUDICIAL",
                "value", "Read,Manage,Cancel"
            ),
            Map.of(
                "name", "hearing-centre-admin",
                "value", "Read,Own,Manage,Cancel",
                "roleCategory", "ADMINISTRATOR",
                "autoAssignable", false
            ),
            Map.of(
                "name", "legal-advisor",
                "value", "Read,Own,Manage,Cancel",
                "roleCategory", "ADMINISTRATOR",
                "autoAssignable", false
            )
        )));

    }

    @SuppressWarnings("checkstyle:indentation")
    @Test
    void given_reviewSpecificAccessRequestLegalAdv_taskType_when_evaluate_dmn_then_it_returns_expected_rule_forSdo() {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", "ReviewSpecificAccessRequestsLegalOps"));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(List.of(
            Map.of(
                "autoAssignable", false,
                "name", "task-supervisor",
                "roleCategory","JUDICIAL",
                "value", "Read,Manage,Cancel"
            ),
            Map.of(
                "name", "nbc-team-leader",
                "value", "Read,Own,Manage,Cancel",
                "roleCategory", "ADMINISTRATOR",
                "autoAssignable", false
            ),
            Map.of(
                "name", "hearing-centre-admin",
                "value", "Read,Own,Manage,Cancel",
                "roleCategory", "ADMINISTRATOR",
                "autoAssignable", false
            ),
            Map.of(
                "name", "legal-advisor",
                "value", "Read,Own,Manage,Cancel",
                "roleCategory", "ADMINISTRATOR",
                "autoAssignable", false
            )
        )));

    }

    @SuppressWarnings("checkstyle:indentation")
    @Test
    void given_reviewSpecificAccessRequestLegalOpera_taskType_when_evaluate_dmn_then_it_returns_expected_rule_forSdo() {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", "ReviewSpecificAccessRequestsAdmin"));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(List.of(
            Map.of(
                "autoAssignable", false,
                "name", "task-supervisor",
                "roleCategory","JUDICIAL",
                "value", "Read,Manage,Cancel"
            ),
            Map.of(
                "name", "hearing-centre-team-leade",
                "value", "Read,Own,Manage,Cancel",
                "roleCategory", "ADMINISTRATOR",
                "autoAssignable", false
            ),
            Map.of(
                "name", "ctsc-team-leader",
                "value", "Read,Own,Manage,Cancel",
                "roleCategory", "ADMINISTRATOR",
                "autoAssignable", false
            ),
            Map.of(
                "name", "nbc-team-leader",
                "value", "Read,Own,Manage,Cancel",
                "roleCategory", "ADMINISTRATOR",
                "autoAssignable", false
            ),
            Map.of(
                "name", "legal-advisor",
                "value", "Read,Own,Manage,Cancel",
                "roleCategory", "ADMINISTRATOR",
                "autoAssignable", false
            ),
            Map.of(
                "name", "hearing-centre-admin",
                "value", "Read,Own,Manage,Cancel",
                "roleCategory", "ADMINISTRATOR",
                "autoAssignable", false
            )
        )));

    }

    @SuppressWarnings("checkstyle:indentation")
    @Test
    void given_reviewSpecificAccessRequest_Admin_taskType_when_evaluate_dmn_then_it_returns_expected_rule_forSdo() {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", "reviewSpecificAcesssRequestJudiciary"));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(List.of(
            Map.of(
                "autoAssignable", false,
                "name", "task-supervisor",
                "roleCategory","JUDICIAL",
                "value", "Read,Manage,Cancel"
            ),
            Map.of(
                "name", "leadership-judge",
                "value", "Read,Own,Manage,Cancel",
                "roleCategory", "ADMINISTRATOR",
                "autoAssignable", false
            ),
            Map.of(
                "name", "legal-advisor",
                "value", "Read,Own,Manage,Cancel",
                "roleCategory", "ADMINISTRATOR",
                "autoAssignable", false
            ),
            Map.of(
                "name", "hearing-centre-admin",
                "value", "Read,Own,Manage,Cancel",
                "roleCategory", "ADMINISTRATOR",
                "autoAssignable", false
            )
        )));

    }

}
