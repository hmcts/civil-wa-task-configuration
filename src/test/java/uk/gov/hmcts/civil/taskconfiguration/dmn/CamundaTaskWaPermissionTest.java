package uk.gov.hmcts.civil.taskconfiguration.dmn;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.provider.Arguments;
import uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTable;
import uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTableBaseUnitTest;

import java.io.Serializable;
import java.util.Map;
import java.util.stream.Stream;

import static java.util.Arrays.asList;

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

}
