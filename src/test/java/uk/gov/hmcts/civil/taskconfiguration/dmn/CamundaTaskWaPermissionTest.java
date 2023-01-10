package uk.gov.hmcts.civil.taskconfiguration.dmn;

import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTable;
import uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTableBaseUnitTest;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;

class CamundaTaskWaPermissionTest extends DmnDecisionTableBaseUnitTest {

    private static final Map<String, Serializable> taskSupervisor = Map.of(
        "autoAssignable", false,
        "name", "task-supervisor",
        "value", "Read,Manage,Cancel"
    );

    private static final Map<String, Serializable> judge = Map.of(
        "autoAssignable", false,
        "assignmentPriority", 1,
        "authorisations", "294",
        "name", "judge",
        "roleCategory", "JUDICIAL",
        "value", "Read,Own,Manage,Cancel"
    );

    private static final Map<String, Serializable> tribunalCaseworker = Map.of(
        "autoAssignable", false,
        "authorisations", "294",
        "name", "tribunal-caseworker",
        "roleCategory", "LEGAL_OPERATIONS",
        "value", "Read,Own,Manage,Cancel"
    );

    private static final Map<String, Serializable> hearingCentreAdmin = Map.of(
        "autoAssignable", false,
        "name", "hearing-centre-admin",
        "roleCategory", "ADMIN",
        "value", "Read,Own,Manage,Cancel"
    );

    private static final Map<String, Serializable> nbcTeamLeader = Map.of(
        "autoAssignable", false,
        "name", "nbc-team-leader",
        "roleCategory", "ADMIN",
        "value", "Read,Own,Manage,Cancel"
    );

    private static final Map<String, Serializable> hearingCentreTeamleader = Map.of(
        "autoAssignable", false,
        "name", "hearing-centre-team-leader",
        "roleCategory", "ADMIN",
        "value", "Read,Own,Manage,Cancel"
    );

    private static final Map<String, Serializable> ctscTeamLeader = Map.of(
        "autoAssignable", false,
        "name", "ctsc-team-leader",
        "roleCategory", "ADMIN",
        "value", "Read,Own,Manage,Cancel"
    );

    private static final Map<String, Serializable> leadershipJudge = Map.of(
        "autoAssignable", true,
        "assignmentPriority", 1,
        "authorisations", "294",
        "name", "leadership-judge",
        "roleCategory", "ADMIN",
        "value", "Read,Own,Manage,Cancel"
    );

    private static final Map<String, Serializable> seniorTribunal = Map.of(
        "autoAssignable", false,
        "name", "senior-tribunal-caseworker",
        "roleCategory", "ADMIN",
        "value", "Read,Own,Manage,Cancel"
    );

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
                        "value", "Read,Manage,Cancel,Unassign,Assign"
                    )),
                    Arguments.of(
                        "null",
                        asList(
                            Map.of(
                                "autoAssignable", false,
                                "name", "task-supervisor",
                                "value", "Read,Manage,Cancel,Unassign,Assign",
                                "roleCategory", "JUDICIAL"
                            ))))
                    );
    }

    public static Stream<Arguments> genericScenarioProvider() {
        return Stream.of(
            Arguments.of(
                "anything",
                asList(
                    Map.of(
                        "autoAssignable", false,
                        "name", "task-supervisor",
                        "value", "Read,Manage,Cancel,Unassign,Assign"
                    )),
                Arguments.of(
                    "null",
                    asList(
                        Map.of(
                            "autoAssignable", false,
                            "name", "task-supervisor",
                            "value", "Read,Manage,Cancel,Unassign,Assign"
                        )
                    )
                )
            )
        );
    }

    @ParameterizedTest(name = "case data: {0}")
    @MethodSource({"scenarioProvider","genericScenarioProvider"})
    void given_anyevent_event_ids_should_evaluate_judge_dmn(String caseData,
                                                      List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("case", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @SuppressWarnings("checkstyle:indentation")
    @ParameterizedTest
    @CsvSource(value = {
        "summaryJudgmentDirections"
    })
    void given_fasttract_taskType_when_evaluate_dmn_then_it_returns_expected_rule(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(List.of(
            Map.of(
                "name", "task-supervisor",
                "value", "Read,Manage,Cancel,Unassign,Assign",
                "autoAssignable", false
            ),
            Map.of(
                "name", "judge",
                "value", "Read,Own,Manage,Cancel,Claim,Unassign,Assign,Complete",
                "roleCategory", "JUDICIAL",
                "authorisations","294",
                "assignmentPriority",1,
                "autoAssignable", false
            )
        )));

    }

    @SuppressWarnings("checkstyle:indentation")
    @ParameterizedTest
    @CsvSource(value = {
        "FastTrackDirections"
    })
    void given_fastTrackDirections_taskType_when_evaluate_dmn_then_it_returns_expected_rule(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(List.of(
            Map.of(
                "name", "task-supervisor",
                "value", "Read,Manage,Cancel,Unassign,Assign",
                "autoAssignable", false
            ),
            Map.of(
                "name", "judge",
                "value", "Read,Own,Manage,Cancel,Claim,Unassign,Assign,Complete",
                "roleCategory", "JUDICIAL",
                "authorisations","294",
                "assignmentPriority",1,
                "autoAssignable", false
            )
        )));

    }

    @SuppressWarnings("checkstyle:indentation")
    @ParameterizedTest
    @CsvSource(value = {
        "SmallClaimsTrackDirections"
    })
    void given_smallClaimsTrackDirections_taskType_when_evaluate_dmn_then_it_returns_expected_rule(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(List.of(
            Map.of(
                "name", "task-supervisor",
                "value", "Read,Manage,Cancel,Unassign,Assign",
                "autoAssignable", false
            ),
            Map.of(
                "name", "judge",
                "value", "Read,Own,Manage,Cancel,Claim,Unassign,Assign,Complete",
                "roleCategory", "JUDICIAL",
                "authorisations","294",
                "assignmentPriority",1,
                "autoAssignable", false
            )
        )));

    }

    @SuppressWarnings("checkstyle:indentation")
    @ParameterizedTest
    @CsvSource(value = {
        "SmallClaimsTrackDirectionsReferral"
    })
    void given_smallClaimsReferral_taskType_when_evaluate_dmn_then_it_returns_expected_rule(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(List.of(
            Map.of(
                "name", "task-supervisor",
                "value", "Read,Manage,Cancel,Unassign,Assign",
                "autoAssignable", false
            ),
            Map.of(
                "name", "judge",
                "value", "Read,Own,Manage,Cancel,Claim,Unassign,Assign,Complete",
                "roleCategory", "JUDICIAL",
                "authorisations","294",
                "assignmentPriority",1,
                "autoAssignable", false
            )
        )));

    }

    @SuppressWarnings("checkstyle:indentation")
    @ParameterizedTest
    @CsvSource(value = {
        "reviewSpecificAccessRequestJudiciary"
    })
    void given_leadership_judge_role_when_evaluate_dmn_then_it_returns_expected_rule(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(List.of(
            Map.of(
                "name", "task-supervisor",
                "value", "Read,Manage,Cancel,Unassign,Assign",
                "autoAssignable", false
            ),
            Map.of(
                "name", "leadership-judge",
                "value", "Read,Own,Manage,Cancel,Claim,Unassign,Assign,Complete",
                "roleCategory", "JUDICIAL",
                "authorisations","294",
                "assignmentPriority",1,
                "autoAssignable", true
            )
        )));

    }

    @SuppressWarnings("checkstyle:indentation")
    @ParameterizedTest
    @CsvSource(value = {
        "transferCaseOffline"
    })
    void given_transferCaseOffline_when_evaluate_dmn_then_it_returns_expected_rule(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(List.of(
            Map.of(
                "name", "task-supervisor",
                "value", "Read,Manage,Cancel,Unassign,Assign",
                "autoAssignable", false
            ),
            Map.of(
                "name", "hearing-centre-admin",
                "value", "Read,Own,Manage,Cancel,Claim,Unassign,Assign,Complete",
                "roleCategory", "ADMIN",
                "autoAssignable", false
            )
        )));

    }

    @SuppressWarnings("checkstyle:indentation")
    @ParameterizedTest
    @CsvSource(value = {
        "LegalAdvisorSmallClaimsTrackDirections"
    })
    void given_legalAdvisorClaTrDirections_taskType_when_evaluate_dmn_then_it_returns_expected_rule(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(List.of(
            Map.of(
                "name", "task-supervisor",
                "value", "Read,Manage,Cancel,Unassign,Assign",
                "autoAssignable", false
            ),
            Map.of(
                "name", "tribunal-caseworker",
                "value", "Read,Own,Manage,Cancel,Claim,Unassign,Assign,Complete",
                "roleCategory", "LEGAL_OPERATIONS",
                "autoAssignable", false
            )
        )));

    }

    @SuppressWarnings("checkstyle:indentation")
    @ParameterizedTest
    @CsvSource(value = {
        "ScheduleAHearing"
    })
    void given_scheduleAHearing_taskType_when_evaluate_dmn_then_it_returns_expected_rule(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(List.of(
            Map.of(
                "name", "task-supervisor",
                "value", "Read,Manage,Cancel,Unassign,Assign",
                "autoAssignable", false
            ),
            Map.of(
                "name", "hearing-centre-admin",
                "value", "Read,Own,Manage,Cancel,Claim,Unassign,Assign,Complete",
                "roleCategory", "ADMIN",
                "autoAssignable", false
            )
        )));

    }

    @SuppressWarnings("checkstyle:indentation")
    @ParameterizedTest
    @CsvSource(value = {
        "reviewSpecificAccessRequestAdmin"
    })
    void given_reviewSpeciRequestAdmin_taskType_when_evaluate_dmn_then_it_returns_expected_rule(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(List.of(
            Map.of(
                "name", "task-supervisor",
                "value", "Read,Manage,Cancel,Unassign,Assign",
                "autoAssignable", false
            ),
            Map.of(
                "name", "hearing-centre-team-leader",
                "value", "Read,Own,Manage,Cancel,Claim,Unassign,Assign,Complete",
                "roleCategory", "ADMIN",
                "autoAssignable", true
            ),
            Map.of(
                "name", "nbc-team-leader",
                "value", "Read,Own,Manage,Cancel,Claim,Unassign,Assign,Complete",
                "roleCategory", "ADMIN",
                "autoAssignable", true
            )
        )));

    }
}
