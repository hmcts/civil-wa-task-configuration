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
        assertThat(logic.getRules().size(), is(23));
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
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "caseName",
            "value", "claimant1PartyName & claimant2PartyName"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "caseManagementCategory",
            "value", "Civil"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "location",
            "value", "574546"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "locationName",
            "value", ""
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
            "reviewSpecificAccessRequestJudiciary"
        ));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("workType"))
            .collect(Collectors.toList());

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "workType",
            "value", "access_requests"
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
            "JudgeDecideOnApplication"
        ));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("workType"))
            .collect(Collectors.toList());

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "workType",
            "value", "decision_making_work"
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
            "JudgeDecideOnApplication"
        ));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
                "name", "caseManagementCategory",
                "value", "GA"
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
            "JudgeDecideOnApplication"
        ));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
                "name", "caseManagementCategory",
                "value", "Civil"
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
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> roleCategoryResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("roleCategory"))
            .collect(Collectors.toList());

        System.out.println(roleCategoryResultList);
        assertThat(roleCategoryResultList.size(), is(1));

        assertTrue(roleCategoryResultList.contains(Map.of(
            "name", "roleCategory",
            "value", "LEGAL_OPERATIONS"
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
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> roleCategoryResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("roleCategory"))
            .collect(Collectors.toList());

        System.out.println(roleCategoryResultList);
        assertThat(roleCategoryResultList.size(), is(1));

        assertTrue(roleCategoryResultList.contains(Map.of(
            "name", "roleCategory",
            "value", "JUDICIAL"
        )));
        assertFalse(roleCategoryResultList.contains(Map.of(
            "name", "roleCategory",
            "value", "LEGAL_OPERATIONS"
        )));
    }
}

