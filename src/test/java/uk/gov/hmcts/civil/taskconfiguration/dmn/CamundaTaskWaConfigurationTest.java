package uk.gov.hmcts.civil.taskconfiguration.dmn;

import lombok.Builder;
import lombok.Value;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CamundaTaskWaConfigurationTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = DmnDecisionTable.WA_TASK_CONFIGURATION_CIVIL_DAMAGES;
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {

        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getRules().size(), is(55));
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
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"

        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("caseManagementLocation",  Map.of(
            "baseLocation", "2",
            "region", "1"
        ));
        caseData.put("hearingSupportRequirementsDJ", Map.of(
            "hearingPreferredLocation", "Location"
        ));
        caseData.put("applicantPreferredCourt", Map.of(
            "partyType", "INDIVIDUAL"
        ));
        caseData.put("applicantPreferredCourt", Map.of(
            "partyType", "SOLE_TRADER"
        ));
        caseData.put("applicantPreferredCourt", Map.of(
            "partyType", "COMPANY"
        ));
        caseData.put("applicantPreferredCourt", Map.of(
            "partyType", "ORGANISATION"
        ));
        caseData.put("hearingBaseLocation", Map.of(
            "partyType", "MultiPartyScenario"
        ));
        caseData.put("hearingBaseLocation", Map.of(
            "partyType", "RefertoJudge"
        ));
        caseData.put("hearingBaseLocation", "Location");

        VariableMap inputVariables = new VariableMapImpl();
        caseData.put("description", null);
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "caseManagementCategory",
            "value", "Civil"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "caseName",
            "value", "Firstname LastName & Firstname LastName"
        )));
        assertFalse(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "location",
            "value", "1"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "region",
            "value", "1"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "locationName",
            "value", ""
        )));
    }

    @Test
    void when_taskId_then_return_Access_requests() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"

        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));

        caseData.put("featureToggleWA", "WA3.5");
        caseData.put("featureToggleWA", "Prod");

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
            "value", "access_requests",
            "canReconfigure", "true"
        )));
    }

    @Test
    void when_taskId_then_return_decision_making_work() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));

        caseData.put("featureToggleWA", "WA3.5");
        caseData.put("featureToggleWA", "Prod");

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);

        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "summaryJudgmentDirections"
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
            "canReconfigure", "true"
        )));
    }


    @ParameterizedTest
    @CsvSource({
        "summaryJudgmentDirections", "reviewSpecificAccessRequestJudiciary"
    })
    void when_taskId_then_return_roleCategory(String taskType) {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));

        caseData.put("featureToggleWA", "WA3.5");
        caseData.put("featureToggleWA", "Prod");

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("roleCategory"))
            .collect(Collectors.toList());

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "roleCategory",
            "value", "JUDICIAL",
            "canReconfigure", "true"
        )));
    }

    @Test
    void when_taskId_then_return_Access_requests_forsdo() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);

        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "reviewSpecificAccessRequestLegalOps"
        ));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("workType"))
            .collect(Collectors.toList());

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));

        assertFalse(workTypeResultList.contains(Map.of(
            "name", "workType",
            "value", "access_requests"
        )));
    }

    @Test
    void when_taskId_then_return_Access_requests_foradmin() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);

        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "reviewSpecificAccessRequestAdmin"
        ));

        inputVariables.putValue("taskAttributes", Map.of(
            "additionalProperties",
            "reviewSpecificAccessRequestAdmin"
        ));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("workType"))
            .collect(Collectors.toList());

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(0));

        assertFalse(workTypeResultList.contains(Map.of(
            "name", "workType",
            "value", "access_requests",
            "canReconfigure", "true"
        )));
    }

    @Test
    void when_taskId_then_return_Access_requests_forjudi() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));

        caseData.put("featureToggleWA", "WA3.5");
        caseData.put("featureToggleWA", "Prod");

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);

        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "reviewSpecificAcesssRequestJudiciary"
        ));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("workType"))
            .collect(Collectors.toList());

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(0));

        assertFalse(workTypeResultList.contains(Map.of(
            "name", "workType",
            "value", "access_requests",
            "canReconfigure", "false"
        )));
    }

    @Test
    void when_taskId_then_return_decision_making_work_forsdo() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("featureToggleWA", "WA3.5");
        caseData.put("featureToggleWA", "Prod");
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);

        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "FastTrackDirections"
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
            "canReconfigure", "true"
        )));
    }

    @Test
    void when_taskId_then_return_decision_making_work_smallclaims() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));

        caseData.put("featureToggleWA", "WA3.5");
        caseData.put("featureToggleWA", "Prod");

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);

        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "SmallClaimsTrackDirections"
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
            "canReconfigure", "true"
        )));
    }

    @Test
    void when_taskId_then_return_decision_making_work_legaladv() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));

        caseData.put("featureToggleWA", "WA3.5");
        caseData.put("featureToggleWA", "Prod");

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);

        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "LegalAdvisorSmallClaimsTrackDirections"
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
            "canReconfigure", "true"
        )));
    }

    @Test
    void when_taskId_then_return_decision_making_work_sctdrefe() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));

        caseData.put("featureToggleWA", "WA3.5");
        caseData.put("featureToggleWA", "Prod");
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);

        inputVariables.putValue("toggle", Map.of("WA 3.5","Prod"));

        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "SmallClaimsTrackDirectionsReferral"
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
            "canReconfigure", "true"
        )));
    }

    //ReviewCaseFlagsForClaimant
    @Test
    void when_taskId_review_case_flags_claimant_then_return_expected_decision() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"

        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));

        caseData.put("featureToggleWA", "Prod");

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "ReviewCaseFlagsForClaimant"
        ));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "description",
            "value", "[Directions - Case Flags](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                + "/CREATE_CASE_FLAGS/CREATE_CASE_FLAGSCreateCaseFlags)"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "workType",
            "value", "routine_work"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "dueDateIntervalDays",
            "value", "10"
        )));
    }

    //ReviewCaseFlagsForDefendant
    @Test
    void when_taskId_review_case_flags_defendant_then_return_expected_decision() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("respondent1", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("respondent2", Map.of(
            "partyName", "Firstname LastName"
        ));

        caseData.put("featureToggleWA", "Prod");

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "ReviewCaseFlagsForDefendant"
        ));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "description",
            "value", "[Directions - Case Flags](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                + "/CREATE_CASE_FLAGS/CREATE_CASE_FLAGSCreateCaseFlags)"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "workType",
            "value", "routine_work"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "dueDateIntervalDays",
            "value", "10"
        )));
    }

    //ScheduleAHearing
    @Test
    void when_taskId_schedule_a_hearing_then_return_expected_decision_Wacp() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"

        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"

        ));

        caseData.put("featureToggleWA", "Prod");

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "ScheduleAHearing"
        ));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "description",
            "value", "[Directions - Schedule A Hearing](/cases/case-details/${[CASE_REFERENCE]}"
                + "/trigger/HEARING_SCHEDULED/HEARING_SCHEDULEDHearingNoticeSelect)"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "workType",
            "value", "hearing_work"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "roleCategory",
            "value", "ADMIN"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "dueDateIntervalDays",
            "value", "20"
        )));
    }

    //transferCaseOffline
    @Test
    void when_taskId_transfer_case_offline_then_return_expected_decision_prod() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"

        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"

        ));

        caseData.put("featureToggleWA", "Prod");

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "transferCaseOffline"
        ));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "workType",
            "value", "hearing_work"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "roleCategory",
            "value", "ADMIN"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "dueDateIntervalDays",
            "value", "20"
        )));
    }

    //transferCaseOffline
    @Test
    void when_taskId_transfer_case_offline_then_return_expected_decision() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"

        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"

        ));

        caseData.put("featureToggleWA", "Prod");

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "transferCaseOffline"
        ));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "description",
            "value", "[Transfer Case Offline](/cases/case-details/${[CASE_REFERENCE]}"
                + "/trigger/CASE_PROCEEDS_IN_CASEMAN/CASE_PROCEEDS_IN_CASEMAN)"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "workType",
            "value", "hearing_work"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "roleCategory",
            "value", "ADMIN"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "dueDateIntervalDays",
            "value", "20"
        )));
    }

    @ParameterizedTest
    @CsvSource({
        "reviewOrder"
    })
    void when_taskId_reviewOrder_then_return_roleCategory(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        Map<String, Object> caseData = new HashMap<>();
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"

        ));
        caseData.put("featureToggleWA", "Prod");
        inputVariables.putValue("caseData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("roleCategory"))
            .collect(Collectors.toList());

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "roleCategory",
            "value", "ADMIN",
            "canReconfigure", "true"
        )));
    }

    @ParameterizedTest
    @CsvSource({
        "reviewOrder"
    })
    void when_taskId_reviewOrder_then_return_workType_routine_work(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        Map<String, Object> caseData = new HashMap<>();
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"

        ));
        caseData.put("featureToggleWA", "Prod");
        inputVariables.putValue("caseData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("workType"))
            .collect(Collectors.toList());

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "workType",
            "value", "routine_work",
            "canReconfigure", "true"
        )));
    }

    @Test
    void when_taskId_then_return_routine_work_desc() {

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("featureToggleWA", "WA3.5");
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "transferOnlineCase"
        ));
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("workType"))
            .collect(Collectors.toList());

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));
        assertTrue(workTypeResultList.contains(Map.of(
            "name", "workType",
            "value", "routine_work",
            "canReconfigure", "true"
        )));
    }

    @Test
    void when_taskId_InitialDirectionFlightDelay_then_return_decision_making_work() {

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("featureToggleWA", "WA3.5");
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "InitialDirectionFlightDelay"
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
            "canReconfigure", "true"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "description",
            "value", "[Initial Directions (Flight Delay)](/cases/case-details/${[CASE_REFERENCE]}"
                + "/trigger/CREATE_SDO/CREATE_SDOSmallClaims)"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "roleCategory",
            "value", "JUDICIAL"
        )));
    }

    @Value
    @Builder
    private static class Scenario {
        Map<String, Object> caseData;
        Map<String, Object> taskAttributes;
        String expectedAppealTypeValue;
        String expectedRegionValue;
        String expectedDescriptionValue;
        String expectedNextHearingIdValue;
        String expectedNextHearingDateValue;
    }

    private void getExpectedValue(List<Map<String, String>> rules, String name, String value) {
        Map<String, String> rule = new HashMap<>();
        rule.put("name", name);
        rule.put("value", value);
        rules.add(rule);
    }

    private List<Map<String, String>> getExpectedValues(Scenario scenario) {
        List<Map<String, String>> rules = new ArrayList<>();

        getExpectedValue(rules, "appealType", scenario.getExpectedAppealTypeValue());
        getExpectedValue(rules, "region", scenario.getExpectedRegionValue());
        getExpectedValue(rules, "description", scenario.getExpectedDescriptionValue());
        getExpectedValue(rules, "nextHearingId", scenario.getExpectedNextHearingIdValue());
        getExpectedValue(rules, "nextHearingDate", scenario.getExpectedNextHearingDateValue());

        return rules;
    }

    private Map<String, String> configDecision(String name, String canReconfigure, String value) {
        return Map.of("name", name, "canReconfigure", canReconfigure,"value", value);
    }
}
