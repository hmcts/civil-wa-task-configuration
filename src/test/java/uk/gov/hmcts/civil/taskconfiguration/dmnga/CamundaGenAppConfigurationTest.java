package uk.gov.hmcts.civil.taskconfiguration.dmnga;

import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTable;
import uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTableBaseUnitTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CamundaGenAppConfigurationTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = DmnDecisionTable.WA_TASK_CONFIGURATION_CIVIL_GENERALAPPLICATION;
    }


    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getRules().size(), is(33));
    }

    @SuppressWarnings("checkstyle:indentation")
    @ParameterizedTest
    @CsvSource(value = {
        "NULL_VALUE, ''",
        "'', ''"
    }, nullValues = "NULL_VALUE")
    void when_caseData_then_return_expected_name_and_value_rows(String appealType, String expectedAppealType) {
        Map<String, Object> caseData = new HashMap<>(); // allow null values
        //caseData.put("appealType", appealType);
        caseData.put("claimant1PartyName", "claimant1PartyName");
        caseData.put("claimant2PartyName", "claimant2PartyName");
        caseData.put("caseManagementLocation", Map.of(
                "region", "4",
                "baseLocation", "574546"

        ));

        VariableMap inputVariables = new VariableMapImpl();
        caseData.put("description", null);
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of(
            "dueDate","2023-03-22T16:00:00Z"));
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "caseName",
            "value", "claimant1PartyName & claimant2PartyName",
            "canReconfigure","true"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "caseManagementCategory",
            "value", "Civil",
            "canReconfigure","true"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "location",
            "value", "574546",
            "canReconfigure","true"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "locationName",
            "value", "",
            "canReconfigure","true"
        )));
    }


    @Test
    void when_taskId_then_return_Access_requests() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("claimant1PartyName", "claimant1PartyName");
        caseData.put("claimant2PartyName", "claimant2PartyName");
        caseData.put("caseManagementLocation", Map.of(
                "region", "4",
                "baseLocation", "574546"

        ));

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);

        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "reviewSpecificAccessRequestJudiciary",
            "dueDate","2023-03-22T16:00:00Z"
        ));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("workType"))
            .collect(Collectors.toList());

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "workType",
            "value", "access_requests",
            "canReconfigure","true"
        )));
    }

    @Test
    void when_taskType_reviewSpecificAccessRequestCtsc_then_return_Access_requests() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("claimant1PartyName", "claimant1PartyName");
        caseData.put("claimant2PartyName", "claimant2PartyName");
        caseData.put("caseManagementLocation", Map.of(
            "region", "4",
            "baseLocation", "574546"

        ));

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);

        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "reviewSpecificAccessRequestCTSC",
            "dueDate","2023-03-22T16:00:00Z"
        ));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("workType"))
            .collect(Collectors.toList());
        List<Map<String, Object>> roleCategoryResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("roleCategory"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));
        assertThat(roleCategoryResultList.size(), is(1));

        assertTrue(roleCategoryResultList.contains(Map.of(
            "name", "roleCategory",
            "value", "CTSC",
            "canReconfigure","true"
        )));
        assertTrue(workTypeResultList.contains(Map.of(
            "name", "workType",
            "value", "access_requests",
            "canReconfigure","true"
        )));
    }

    @Test
    void when_taskId_then_return_decision_making_work() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("claimant1PartyName", "claimant1PartyName");
        caseData.put("claimant2PartyName", "claimant2PartyName");
        caseData.put("caseManagementLocation", Map.of(
                "region", "4",
                "baseLocation", "574546"

        ));

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);

        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "JudgeDecideOnApplication",
            "dueDate","2023-03-22T16:00:00Z"
        ));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("workType"))
            .collect(Collectors.toList());

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "workType",
            "value", "decision_making_work",
            "canReconfigure","true"
        )));
    }

    @Test
    void when_urgent_application_returns_majorPriority() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("claimant1PartyName", "claimant1PartyName");
        caseData.put("claimant2PartyName", "claimant2PartyName");
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "Yes"
        ));
        caseData.put("caseManagementLocation", Map.of(
            "region", "4",
            "baseLocation", "574546"

        ));
        caseData.put("caseManagementCategory", Map.of(
            "value", Map.of("code", "e9f6b8b8-c9ed-4092-945a-0c67edbcfb3c", "label", "GA"),
            "list_items", List.of(Map.of(
                "code", "e9f6b8b8-c9ed-4092-945a-0c67edbcfb3c", "label", "GA"))));

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of(
            "dueDate","2023-03-22T16:00:00Z"));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "caseManagementCategory",
            "value", "GA",
            "canReconfigure","true"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "majorPriority",
            "value", "2000",
            "canReconfigure","true"
        )));

    }

    @Test
    void when_not_urgent_application_returns_majorPriority_as_5000() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("claimant1PartyName", "claimant1PartyName");
        caseData.put("claimant2PartyName", "claimant2PartyName");
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "No"
        ));
        caseData.put("caseManagementLocation", Map.of(
            "region", "4",
            "baseLocation", "574546"

        ));
        caseData.put("caseManagementCategory", Map.of(
            "value", Map.of("code", "e9f6b8b8-c9ed-4092-945a-0c67edbcfb3c", "label", "GA"),
            "list_items", List.of(Map.of(
                "code", "e9f6b8b8-c9ed-4092-945a-0c67edbcfb3c", "label", "GA"))));

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of(
                                                         "dueDate","2023-03-22T16:00:00Z"));
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "caseManagementCategory",
            "value", "GA",
            "canReconfigure","true"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "majorPriority",
            "value", "5000",
            "canReconfigure","true"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "priorityDate",
            "value", "2023-03-22T16:00:00Z",
            "canReconfigure","true"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "minorPriority",
            "value", "500",
            "canReconfigure","true"
        )));

    }

    @Test
    void when_caseMgmtCat_provided_then_return_provided_value() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("claimant1PartyName", "claimant1PartyName");
        caseData.put("claimant2PartyName", "claimant2PartyName");
        caseData.put("caseManagementLocation", Map.of(
                "region", "4",
                "baseLocation", "574546"

        ));
        caseData.put("caseManagementCategory", Map.of(
                "value", Map.of("code", "e9f6b8b8-c9ed-4092-945a-0c67edbcfb3c", "label", "GA"),
                "list_items", List.of(Map.of(
                        "code", "e9f6b8b8-c9ed-4092-945a-0c67edbcfb3c", "label", "GA"))));

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);

        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "JudgeDecideOnApplication",
            "dueDate","2023-03-22T16:00:00Z"
        ));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
                "name", "caseManagementCategory",
                "value", "GA",
                "canReconfigure","true"
        )));
    }

    @Test
    void when_locationName_provided_then_return_provided_value() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("claimant1PartyName", "claimant1PartyName");
        caseData.put("claimant2PartyName", "claimant2PartyName");
        caseData.put("locationName", "CCMCC");
        caseData.put("caseManagementLocation", Map.of(
            "region", "4",
            "baseLocation", "574546"

        ));
        caseData.put("caseManagementCategory", Map.of(
            "value", Map.of("code", "e9f6b8b8-c9ed-4092-945a-0c67edbcfb3c", "label", "GA"),
            "list_items", List.of(Map.of(
                "code", "e9f6b8b8-c9ed-4092-945a-0c67edbcfb3c", "label", "GA"))));

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);

        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "JudgeDecideOnApplication",
            "dueDate","2023-03-22T16:00:00Z"
        ));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "locationName",
            "value", "CCMCC",
            "canReconfigure","true"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "region",
            "value", "4",
            "canReconfigure","true"
        )));
    }

    @Test
    void when_caseMgmtCat_not_provided_then_return_civil() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("claimant1PartyName", "claimant1PartyName");
        caseData.put("claimant2PartyName", "claimant2PartyName");
        caseData.put("caseManagementLocation", Map.of(
                "region", "4",
                "baseLocation", "574546"

        ));

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);

        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "JudgeDecideOnApplication",
            "dueDate","2023-03-22T16:00:00Z"
        ));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
                "name", "caseManagementCategory",
                "value", "Civil",
                "canReconfigure","true"
        )));
    }

    @ParameterizedTest
    @CsvSource({
        "reviewSpecificAccessRequestLegalOps"
    })
    void when_taskId_then_return_roleCategory_single(String taskType) {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("claimant1PartyName", "claimant1PartyName");
        caseData.put("claimant2PartyName", "claimant2PartyName");
        caseData.put("caseManagementLocation", Map.of(
                "region", "4",
                "baseLocation", "574546"

        ));

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType,
                                                         "dueDate","2023-03-22T16:00:00Z"));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> roleCategoryResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("roleCategory"))
            .collect(Collectors.toList());

        System.out.println(roleCategoryResultList);
        assertThat(roleCategoryResultList.size(), is(1));

        assertTrue(roleCategoryResultList.contains(Map.of(
            "name", "roleCategory",
            "value", "LEGAL_OPERATIONS",
            "canReconfigure","true"
        )));
    }

    @ParameterizedTest
    @CsvSource({
        "JudgeDecideOnApplication", "JudgeRevisitApplication"
    })
    void when_taskId_then_return_roleCategory_multiple(String taskType) {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("claimant1PartyName", "claimant1PartyName");
        caseData.put("claimant2PartyName", "claimant2PartyName");
        caseData.put("caseManagementLocation", Map.of(
                "region", "4",
                "baseLocation", "574546"

        ));

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType,
                                                         "dueDate","2023-03-22T16:00:00Z"));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> roleCategoryResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("roleCategory"))
            .collect(Collectors.toList());

        System.out.println(roleCategoryResultList);
        assertThat(roleCategoryResultList.size(), is(1));

        assertTrue(roleCategoryResultList.contains(Map.of(
            "name", "roleCategory",
            "value", "JUDICIAL",
            "canReconfigure","true"
        )));
        assertFalse(roleCategoryResultList.contains(Map.of(
            "name", "roleCategory",
            "value", "LEGAL_OPERATIONS",
            "canReconfigure","true"
        )));
    }

    @Test
    void when_taskId_then_return_routine_work_and_role_category() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("claimant1PartyName", "claimant1PartyName");
        caseData.put("claimant2PartyName", "claimant2PartyName");
        caseData.put("caseManagementLocation", Map.of(
            "region", "4",
            "baseLocation", "574546"

        ));

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);

        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "ReviewApplicationOrder",
            "dueDate","2023-03-22T16:00:00Z"
        ));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("workType"))
            .collect(Collectors.toList());
        List<Map<String, Object>> roleCategoryResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("roleCategory"))
            .collect(Collectors.toList());

        assertThat(workTypeResultList.size(), is(1));
        assertThat(roleCategoryResultList.size(), is(1));
        assertTrue(workTypeResultList.contains(Map.of(
            "name", "workType",
            "value", "routine_work",
            "canReconfigure","true"
        )));
        assertTrue(roleCategoryResultList.contains(Map.of(
            "name", "roleCategory",
            "value", "ADMIN",
            "canReconfigure","true"
        )));

    }

    @ParameterizedTest
    @CsvSource({
        "reviewSpecificAccessRequestJudiciary","reviewSpecificAccessRequestAdmin",
        "reviewSpecificAccessRequestLegalOps","reviewSpecificAccessRequestCTSC"
    })
    void when_taskId_then_return_roleAssignmentId_isNotNull(String taskType) {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("claimant1PartyName", "claimant1PartyName");
        caseData.put("claimant2PartyName", "claimant2PartyName");

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType,
                                                         "roleAssignmentId","123a-b-456",
                                                         "dueDate","2023-03-22T16:00:00Z"));
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> roleAssignmentIdResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("additionalProperties_roleAssignmentId"))
            .collect(Collectors.toList());

        System.out.println(roleAssignmentIdResultList);
        assertThat(roleAssignmentIdResultList.size(), is(1));

        assertTrue(roleAssignmentIdResultList.contains(Map.of(
            "name", "additionalProperties_roleAssignmentId",
            "value", "123a-b-456",
            "canReconfigure","true"
        )));
    }

    @ParameterizedTest
    @CsvSource({
        "reviewSpecificAccessRequestJudiciary","reviewSpecificAccessRequestAdmin",
        "reviewSpecificAccessRequestLegalOps","reviewSpecificAccessRequestCTSC"
    })
    void when_taskId_then_return_roleAssignmentId_isNull(String taskType) {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("claimant1PartyName", "claimant1PartyName");
        caseData.put("claimant2PartyName", "claimant2PartyName");

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType,
                                                         "dueDate","2023-03-22T16:00:00Z"));
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> roleAssignmentIdResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("additionalProperties_roleAssignmentId"))
            .collect(Collectors.toList());

        assertThat(roleAssignmentIdResultList.size(), is(1));
        assertTrue(roleAssignmentIdResultList.contains(Map.of(
            "name", "additionalProperties_roleAssignmentId",
            "value", "1234",
            "canReconfigure","true"
        )));
    }

    @Test
    void when_taskId_scheduleForHearing_and_caseProgressionEnabled_then_return_nextSteps() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("claimant1PartyName", "claimant1PartyName");
        caseData.put("claimant2PartyName", "claimant2PartyName");
        caseData.put("caseManagementLocation", Map.of(
            "region", "4",
            "baseLocation", "574546"

        ));

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);

        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "ScheduleApplicationHearing",
            "dueDate","2023-03-22T16:00:00Z"
        ));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> description = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("description"))
            .collect(Collectors.toList());
        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("workType"))
            .collect(Collectors.toList());
        List<Map<String, Object>> roleCategoryResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("roleCategory"))
            .collect(Collectors.toList());
        assertThat(description.size(), is(1));
        assertThat(workTypeResultList.size(), is(1));
        assertThat(roleCategoryResultList.size(), is(1));
        assertTrue(description.contains(Map.of(
            "name", "description",
            "value", "[ScheduleApplicationHearing](/cases/case-details/${[CASE_REFERENCE]}/trigger/"
                + "HEARING_SCHEDULED_GA/"
                + "HEARING_SCHEDULED_GAHearingNoticeGADetail)",
            "canReconfigure","true"
        )));
        assertTrue(workTypeResultList.contains(Map.of(
            "name", "workType",
            "value", "hearing_work",
            "canReconfigure","true"
        )));
        assertTrue(roleCategoryResultList.contains(Map.of(
            "name", "roleCategory",
            "value", "ADMIN",
            "canReconfigure","true"
        )));
    }
}

