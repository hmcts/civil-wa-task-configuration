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
        assertThat(logic.getRules().size(), is(186));
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
        caseData.put("caseManagementLocation", Map.of(
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

    @ParameterizedTest
    @CsvSource(value = {
        "ReviewCaseFlagsForClaimant, UNSPEC_CLAIM, 4, 366774, CTSC Salford",
        "ReviewCaseFlagsForDefendant, UNSPEC_CLAIM, 4, 366774, CTSC Salford",
        "ReviewCaseFlagsForClaimant, SPEC_CLAIM, 2, 283922, CTSC Stoke",
        "ReviewCaseFlagsForDefendant, SPEC_CLAIM, 2, 283922, CTSC Stoke"
    })
    void when_caseFlagsClaimant_then_return_expected_location(
        String taskType, String caseType,
        String expectedRegion, String expectedEpimms, String expectedName) {

        Map<String, Object> caseData = new HashMap<>(); // allow null values
        //caseData.put("appealType", appealType);
        caseData.put("CaseAccessCategory", caseType);
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"

        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("caseManagementLocation", Map.of(
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
        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            taskType
        ));
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "location",
            "value", expectedEpimms
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "region",
            "value", expectedRegion
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "locationName",
            "value", expectedName
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

        inputVariables.putValue("toggle", Map.of("WA 3.5", "Prod"));

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

    @Test
    void when_taskId_transfer_case_offline_minti_then_return_expected_decision() {
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
            "transferCaseOfflineMinti"
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
            "value", "routine_work"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "roleCategory",
            "value", "CTSC"
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

    @ParameterizedTest
    @CsvSource({
        "HelpWithFeesHearingFee"
    })
    void when_taskId_HelpWithFeesHearingFee_then_return_workType_routine_work(String taskType) {
        VariableMap inputVariables = new VariableMapImpl();
        Map<String, Object> caseData = new HashMap<>();
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"

        ));
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
    void when_taskId_HelpWithHearingFee_then_return_expected_decision() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"
        ));

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "HelpWithFeesHearingFee"
        ));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "workType",
            "value", "routine_work"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "roleCategory",
            "value", "CTSC"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "name", "dueDateIntervalDays",
            "value", "10"
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
            "name", "roleCategory",
            "value", "JUDICIAL"
        )));
    }

    @Test
    void when_taskId_NihlFastTrackDirections_then_return_decision_making_work() {

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
            "NIHLFastTrackDirections"
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
            "name", "roleCategory",
            "value", "JUDICIAL"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "majorPriority",
            "value", "5000"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "description",
            "value", "[Fast Track Directions - NIHL](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                + "/CREATE_SDO/CREATE_SDOFastTrack)<br /><br />[Not Suitable for SDO](/cases/case-details"
                + "/${[CASE_REFERENCE]}/trigger/NotSuitable_SDO/NotSuitable_SDONotSuitableSDO)"
        )));
    }

    @Test
    void when_taskId_JudgeDecideOnReconsiderRequest_then_return_routine_work_desc() {

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
            "JudgeDecideOnReconsiderRequest"
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
        List<Map<String, Object>> roleCategoryResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("roleCategory"))
            .collect(Collectors.toList());
        assertTrue(roleCategoryResultList.contains(Map.of(
            "name", "roleCategory",
            "value", "JUDICIAL",
            "canReconfigure", "true"
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

    @Test
    void when_taskId_OnlineCaseTransferReceived_then_return_routine_work_desc() {

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
            "OnlineCaseTransferReceived"
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
    void when_taskId_send_cvp_hearing_link_then_return_expected_decision() {
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
            "sendCvpHearingLink"
        ));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "description",
            "value", "[Add a case note](/cases/case-details/${[CASE_REFERENCE]}/trigger/ADD_CASE_NOTE/ADD_CASE_NOTE)"
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
            "canReconfigure", "false",
            "name", "dueDateIntervalDays",
            "value", "5"
        )));
    }

    @Test
    void when_taskId_send_review_hearing_exception_then_return_expected_decision() {
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
            "reviewHearingException"
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
                "canReconfigure", "true",
                "name", "description",
                "value", "[Add a case note](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                        + "/ADD_CASE_NOTE/ADD_CASE_NOTE)"
        )));
    }

    @Test
    void when_taskId_then_return_routine_work_UpdateDetailsInCasemanSystem() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"

        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"

        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);
        caseData.put("featureToggleWA", "MCI");

        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "UpdateDetailsInCasemanSystem"
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
    void when_taskId_then_return_roleCategory_UpdateDetailsInCasemanSystem() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"

        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"

        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);
        caseData.put("featureToggleWA", "MCI");

        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "UpdateDetailsInCasemanSystem"
        ));

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

    @Test
    void when_taskId_then_return_description_UpdateDetailsInCasemanSystem() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"

        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"

        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);
        caseData.put("featureToggleWA", "MCI");

        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "UpdateDetailsInCasemanSystem"
        ));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("description"))
            .collect(Collectors.toList());

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "description",
            "value", "[Add a case note](/cases/case-details/${[CASE_REFERENCE]}/trigger/ADD_CASE_NOTE/ADD_CASE_NOTE)",
            "canReconfigure", "true"
        )));
    }

    @Test
    void when_taskId_claimant_welsh_request() {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "claimantWelshRequest"
        ));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "description",
            "value", "[Upload Translated Documents](/cases/case-details/${[CASE_REFERENCE]}/trigger/"
                + "UPLOAD_TRANSLATED_DOCUMENT/UPLOAD_TRANSLATED_DOCUMENTUploadTranslatedDocument)"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "workType",
            "value", "routine_work"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "roleCategory",
            "value", "CTSC"
        )));
    }

    @Test
    void when_taskId_transfer_case_offline_lip() {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "transferCaseOfflineLiP"
        ));


        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "description",
            "value", "[Case is Offline.The judgment must be entered offline.]"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "workType",
            "value", "routine_work"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "roleCategory",
            "value", "CTSC"
        )));
    }

    @Test
    void should_reconfigure_next_hearing_id_and_next_hearing_date() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("nextHearingDetails", Map.of(
            "hearingID", "HEARING1234",
            "hearingDateTime", "2024-01-07T21:36:05"
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .collect(Collectors.toList());

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "nextHearingId",
            "value", "HEARING1234",
            "canReconfigure", "true"
        )));
        assertTrue(workTypeResultList.contains(Map.of(
            "name", "nextHearingDate",
            "value", "2024-01-07T21:36:05",
            "canReconfigure", "true"
        )));
    }

    @Test
    void should_reconfigure_next_hearing_id_and_next_hearing_date_as_empty() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .collect(Collectors.toList());

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "nextHearingId",
            "value", "",
            "canReconfigure", "true"
        )));
        assertTrue(workTypeResultList.contains(Map.of(
            "name", "nextHearingDate",
            "value", "",
            "canReconfigure", "true"
        )));
    }

    @Test
    void when_taskId_OrderToSetAsideDefendedClaim_then_return_routine_work_desc() {

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("featureToggleWA", "JO");
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "OrderToSetAsideDefendedClaim"
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
        List<Map<String, Object>> roleCategoryResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("roleCategory"))
            .collect(Collectors.toList());
        assertTrue(roleCategoryResultList.contains(Map.of(
            "name", "roleCategory",
            "value", "JUDICIAL",
            "canReconfigure", "true"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "majorPriority",
            "value", "3000"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "description",
            "value", "[Make Order](/cases/case-details/${[CASE_REFERENCE]}/trigger"
                + "/GENERATE_DIRECTIONS_ORDER/GENERATE_DIRECTIONS_ORDER)"
        )));
    }

    @Test
    void when_taskId_final_order_welsh_request() {

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "finalOrderIssuedWelshRequest"
        ));
        inputVariables.putValue("caseData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "description",
            "value", "[Upload Translated Documents](/cases/case-details/${[CASE_REFERENCE]}/trigger/"
                + "UPLOAD_TRANSLATED_DOCUMENT/UPLOAD_TRANSLATED_DOCUMENTUploadTranslatedDocument)"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "workType",
            "value", "routine_work"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "roleCategory",
            "value", "ADMIN"
        )));
    }

    @Test
    void when_taskId_ClaimSettledDivergenceTakeCaseOffline_then_return_routine_work_desc() {

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("featureToggleWA", "SD");
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "ClaimSettledDivergenceTakeCaseOffline"
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
        List<Map<String, Object>> roleCategoryResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("roleCategory"))
            .collect(Collectors.toList());
        assertTrue(roleCategoryResultList.contains(Map.of(
            "name", "roleCategory",
            "value", "CTSC",
            "canReconfigure", "true"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "majorPriority",
            "value", "3000"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "description",
            "value", "[Claim Paid in Full Divergence - Take Case Offline]"
                + "(/cases/case-details/${[CASE_REFERENCE]}/trigger"
                + "/CASE_PROCEEDS_IN_CASEMAN/CASE_PROCEEDS_IN_CASEMAN)"
        )));
    }

    @Test
    void when_taskId_ClaimDiscontinuedDivergenceTakeCaseOffline_then_return_config() {

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("featureToggleWA", "SD");
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "ClaimDiscontinuedDivergenceTakeCaseOffline"
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
        List<Map<String, Object>> roleCategoryResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("roleCategory"))
            .collect(Collectors.toList());
        assertTrue(roleCategoryResultList.contains(Map.of(
            "name", "roleCategory",
            "value", "CTSC",
            "canReconfigure", "true"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "majorPriority",
            "value", "3000"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "description",
            "value", "[Claim Discontinued](/cases/case-details/${[CASE_REFERENCE]}"
                + "/trigger/CASE_PROCEEDS_IN_CASEMAN/CASE_PROCEEDS_IN_CASEMAN)"
        )));
    }

    @Test
    void when_taskId_ValidateDiscontinuanceCtsc_then_return_config() {

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("featureToggleWA", "SD");
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "ValidateDiscontinuanceCTSC"
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
        List<Map<String, Object>> roleCategoryResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("roleCategory"))
            .collect(Collectors.toList());
        assertTrue(roleCategoryResultList.contains(Map.of(
            "name", "roleCategory",
            "value", "CTSC",
            "canReconfigure", "true"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "majorPriority",
            "value", "3000"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "description",
            "value", "[Validate Discontinuance](/cases/case-details/${[CASE_REFERENCE]}/trigger/"
                + "VALIDATE_DISCONTINUE_CLAIM_CLAIMANT/VALIDATE_DISCONTINUE_CLAIM_CLAIMANT)"
        )));
    }

    @Test
    void when_taskId_ValidateDiscontinuanceAdmin_then_return_config() {

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("featureToggleWA", "SD");
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "ValidateDiscontinuanceAdmin"
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
        List<Map<String, Object>> roleCategoryResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("roleCategory"))
            .collect(Collectors.toList());
        assertTrue(roleCategoryResultList.contains(Map.of(
            "name", "roleCategory",
            "value", "ADMIN",
            "canReconfigure", "true"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "majorPriority",
            "value", "3000"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "description",
            "value", "[Validate Discontinuance](/cases/case-details/${[CASE_REFERENCE]}/trigger/"
                + "VALIDATE_DISCONTINUE_CLAIM_CLAIMANT/VALIDATE_DISCONTINUE_CLAIM_CLAIMANT)"
        )));
    }

    @Test
    void when_taskId_JudgmentOnlineSetAsideTakeCaseOffline_then_return_config() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("featureToggleWA", "JO");
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "JudgmentOnlineSetAsideTakeCaseOffline"
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
        List<Map<String, Object>> roleCategoryResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("roleCategory"))
            .collect(Collectors.toList());
        assertTrue(roleCategoryResultList.contains(Map.of(
            "name", "roleCategory",
            "value", "ADMIN",
            "canReconfigure", "true"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "majorPriority",
            "value", "3000"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "description",
            "value", "[SetAside - Take Case Offline](/cases/case-details/"
                + "${[CASE_REFERENCE]}/trigger/CASE_PROCEEDS_IN_CASEMAN/CASE_PROCEEDS_IN_CASEMAN)"
        )));
    }

    private Map<String, String> configDecision(String name, String canReconfigure, String value) {
        return Map.of("name", name, "canReconfigure", canReconfigure, "value", value);
    }

    @Test
    void when_taskId_then_return_routine_work_BundlefailedAmendAndRestich() {
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
            "bundlefailedAmendandRestich"
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
            "canReconfigure", "false"
        )));
        List<Map<String, Object>> roleCategoryResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("roleCategory"))
            .collect(Collectors.toList());
        assertTrue(roleCategoryResultList.contains(Map.of(
            "name", "roleCategory",
            "value", "ADMIN",
            "canReconfigure", "false"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "dueDateIntervalDays",
            "value", "5"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure",
            "false",
            "name",
            "description",
            "value",
            "[Amend and re-stitch bundle](/cases/case-details/${[CASE_REFERENCE]}"
                + "/trigger/AMEND_RESTITCH_BUNDLE/AMEND_RESTITCH_BUNDLERestitchBundle)"
        )));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "reviewMessageCW; Small Claim;; "
            + "[Small Claim, CW, Review message](/cases/case-details/${[CASE_REFERENCE]}#Messages); Yes",
        "reviewMessageCW; Fast Track;; "
            + "[Fast Track, CW, Review message](/cases/case-details/${[CASE_REFERENCE]}#Messages); No",
        "reviewMessageLA; Small Claim;; "
            + "[Small Claim, LA, Review message](/cases/case-details/${[CASE_REFERENCE]}#Messages); Yes",
        "reviewMessageLA; Fast Track;; "
            + "[Fast Track, LA, Review message](/cases/case-details/${[CASE_REFERENCE]}#Messages); No",
        "reviewMessageJudicial; Small Claim; CJ; "
            + "[Small Claim, CJ, Review message](/cases/case-details/${[CASE_REFERENCE]}#Messages); Yes",
        "reviewMessageJudicial; Fast Track; CJ; "
            + "[Fast Track, CJ, Review message](/cases/case-details/${[CASE_REFERENCE]}#Messages); No",
        "reviewMessageJudicial; Small Claim; DJ; "
            + "[Small Claim, DJ, Review message](/cases/case-details/${[CASE_REFERENCE]}#Messages); Yes",
        "reviewMessageJudicial; Fast Track; DJ; "
            + "[Fast Track, DJ, Review message](/cases/case-details/${[CASE_REFERENCE]}#Messages); No",
        "reviewMessageJudicial; Small Claim; Judge; "
            + "[Small Claim, Judge, Review message](/cases/case-details/${[CASE_REFERENCE]}#Messages); Yes",
        "reviewMessageJudicial; Fast Track; Judge; "
            + "[Fast Track, Judge, Review message](/cases/case-details/${[CASE_REFERENCE]}#Messages); No",
        "reviewMessageWLU; Fast Track; WLU; "
            + "[Fast Track, WLU, Review message](/cases/case-details/${[CASE_REFERENCE]}#Messages); No",
        "reviewMessageWLU; Small Claim; WLU; "
            + "[Small Claim, WLU, Review message](/cases/case-details/${[CASE_REFERENCE]}#Messages); Yes",
        }, delimiter = ';')
    void when_reviewMessage_then_return_allocatedTrackAndDescription(
        String taskType, String allocatedTrack,
        String judgeLabel, String expectedDescription, String isUrgent) {

        Map<String, Object> caseData = new HashMap<>(); // allow null values

        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));

        caseData.put("lastMessage", Map.of(
            "isUrgent", isUrgent
        ));
        caseData.put("lastMessageAllocatedTrack", allocatedTrack);
        caseData.put("lastMessageJudgeLabel", judgeLabel);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            taskType
        ));
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "majorPriority",
            "value", isUrgent.equals("Yes") ? "2000" : "5000"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "workType",
            "value", taskType.equals("reviewMessageCW") ? "routine_work" :
                taskType.equals("reviewMessageWLU") ? "welsh_translation_work" :
                    "decision_making_work"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "description",
            "value", expectedDescription
        )));
    }

    @Test
    void when_taskId_then_return_routine_work_ManageStay_notUrgent() {
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
            "manageStay"
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
            "canReconfigure", "false"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "roleCategory",
            "value", "ADMIN"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "description",
            "value", "[Manage stay](/cases/case-details/${[CASE_REFERENCE]}/trigger/MANAGE_STAY)"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "dueDateIntervalDays",
            "value", "6"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "majorPriority",
            "value", "5000"
        )));
    }

    @Test
    void when_taskId_then_return_routine_work_ManageStay_urgent() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));

        caseData.put("urgentFlag", "Yes");

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "manageStay"
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
            "canReconfigure", "false"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "roleCategory",
            "value", "ADMIN"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "description",
            "value", "[Manage stay](/cases/case-details/${[CASE_REFERENCE]}/trigger/MANAGE_STAY)"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "dueDateIntervalDays",
            "value", "6"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "majorPriority",
            "value", "2000"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "dueDateSkipNonWorkingDays",
            "value", "false"
        )));
    }

    @Test
    void when_taskId_ScheduleHmcHearing_then_return_config() {
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
            "ScheduleHMCHearing"
        ));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "description",
            "value", "[Hearings (HMC)](/cases/case-details/${[CASE_REFERENCE]}/hearings)"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "workType",
            "value", "hearing_work"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "roleCategory",
            "value", "ADMIN"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "dueDateIntervalDays",
            "value", "20"
        )));
    }

    @Test
    void when_taskId_RemoveHmcHearing_then_return_config() {
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
            "RemoveHMCHearing"
        ));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "description",
            "value", "[Hearings (HMC)](/cases/case-details/${[CASE_REFERENCE]}/hearings)"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "workType",
            "value", "hearing_work"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "roleCategory",
            "value", "ADMIN"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "dueDateIntervalDays",
            "value", "20"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "majorPriority",
            "value", "2000"
        )));
    }

    @Test
    void when_taskId_ScheduleAHearing_then_return_config() {
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
            "ScheduleAHearing"
        ));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "description",
            "value", "[Create a hearing notice](/cases/case-details/${[CASE_REFERENCE]}"
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
    }

    @Test
    void when_taskId_removeHearing_then_return_config_hmc_nro() {
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
            "removeHearing"
        ));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "description",
            "value", "[Add a case note](/cases/case-details/${[CASE_REFERENCE]}/trigger/ADD_CASE_NOTE/ADD_CASE_NOTE)"
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

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "majorPriority",
            "value", "2000"
        )));
    }

    @Test
    void when_taskId_reviewOrder_then_return_description() {
        VariableMap inputVariables = new VariableMapImpl();
        Map<String, Object> caseData = new HashMap<>();
        inputVariables.putValue("taskAttributes", Map.of("taskType", "reviewOrder"));
        caseData.put("applicant1", Map.of("partyName", "Firstname LastName"));
        inputVariables.putValue("caseData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("description"))
            .collect(Collectors.toList());

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "description",
            "value", "[Confirm order review](/cases/case-details/${[CASE_REFERENCE]}/trigger/CONFIRM_ORDER_REVIEW/CONFIRM_ORDER_REVIEW)",
            "canReconfigure", "true"
        )));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "createHearingNoticeMT, multi_track_hearing_work",
        "createHearingNoticeInt, intermediate_track_hearing_work",
    })
    void when_createHearingNoticeInt_createHearingNoticeMT_then_return_expected_config(String taskType, String workType) {

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"

        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("caseManagementLocation", Map.of(
            "baseLocation", "2",
            "region", "1"
        ));

        VariableMap inputVariables = new VariableMapImpl();
        caseData.put("description", null);
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of(
            "taskType", taskType
        ));
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "description",
            "value", "[Create a hearing notice](/cases/case-details/${[CASE_REFERENCE]}/trigger/HEARING_SCHEDULED)"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "workType",
            "value", workType
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "roleCategory",
            "value", "ADMIN"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "location",
            "value", "2"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "region",
            "value", "1"
        )));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "allocateMultiTrack, multi_track_decision_making_work",
        "allocateIntermediateTrack, intermediate_track_decision_making_work"
    })
    void when_allocateMultiTrack_or_allocateIntermediateTrack_then_return_expected_config(String taskName, String workType) {

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"

        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("caseManagementLocation", Map.of(
            "baseLocation", "2",
            "region", "1"
        ));

        VariableMap inputVariables = new VariableMapImpl();
        caseData.put("description", null);
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of(
            "taskType", taskName
        ));
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "description",
            "value", "[Make an order [Intermediate Track or Multi Track]](/cases/case-details/${[CASE_REFERENCE]}/trigger/GENERATE_DIRECTIONS_ORDER)"
                + "<br /><br />[Standard Directions Order [Small Claims or Fast Track]](/cases/case-details/${[CASE_REFERENCE]}/trigger/CREATE_SDO)"
                + "<br /><br />[Not Suitable for SDO](/cases/case-details/${[CASE_REFERENCE]}/trigger/NotSuitable_SDO)"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "workType",
            "value", workType
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "roleCategory",
            "value", "JUDICIAL"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "location",
            "value", "2"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "region",
            "value", "1"
        )));

    }

    @ParameterizedTest
    @CsvSource(value = {
        "damagesListCMCMulti",
        "damagesListCCMCMulti",
        "damagesListPTRMulti",
        "damagesListTrialMulti",
        "specifiedListCMCMulti",
        "specifiedListCCMCMulti",
        "specifiedListPTRMulti",
        "specifiedListTrialMulti"
    })
    void when_multi_Listing_then_return_expected_config(String taskName) {

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"

        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("taskManagementLocations", Map.of(
            "cmcListingLocation", Map.of("region", "1",
                                         "location", "1234",
                                         "locationName", "a location name"),
            "ccmcListingLocation", Map.of("region", "2",
                                          "location", "54321",
                                          "locationName", "a different location name"),
            "ptrListingLocation", Map.of("region", "3",
                                         "location", "15154",
                                         "locationName", "ptr location name"),
            "trialListingLocation", Map.of("region", "4",
                                           "location", "57771",
                                           "locationName", "trial location name")
        ));

        VariableMap inputVariables = new VariableMapImpl();
        caseData.put("description", null);
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of(
            "taskType", taskName
        ));
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "description",
            "value",
            "[Confirm Listing](/cases/case-details/${[CASE_REFERENCE]}/trigger/CONFIRM_LISTING_COMPLETED/CONFIRM_LISTING_COMPLETEDConfirmListing)"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "workType",
            "value", "multi_track_hearing_work"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "roleCategory",
            "value", "ADMIN"
        )));

        switch (taskName) {
            case "damagesListCMCMulti":
                assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
                    "canReconfigure", "true",
                    "name", "location",
                    "value", "1234"
                )));
                assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
                    "canReconfigure", "true",
                    "name", "region",
                    "value", "1"
                )));
                assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
                    "canReconfigure", "true",
                    "name", "locationName",
                    "value", "a location name"
                )));
                break;
            case "damagesListCCMCMulti":
                assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
                    "canReconfigure", "true",
                    "name", "location",
                    "value", "54321"
                )));
                assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
                    "canReconfigure", "true",
                    "name", "region",
                    "value", "2"
                )));
                assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
                    "canReconfigure", "true",
                    "name", "locationName",
                    "value", "a different location name"
                )));
                break;
            case "damagesListPTRMulti":
                assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
                    "canReconfigure", "true",
                    "name", "location",
                    "value", "15154"
                )));
                assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
                    "canReconfigure", "true",
                    "name", "region",
                    "value", "3"
                )));
                assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
                    "canReconfigure", "true",
                    "name", "locationName",
                    "value", "ptr location name"
                )));
                break;
            case "damagesListTrialMulti":
                assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
                    "canReconfigure", "true",
                    "name", "location",
                    "value", "57771"
                )));
                assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
                    "canReconfigure", "true",
                    "name", "region",
                    "value", "4"
                )));
                assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
                    "canReconfigure", "true",
                    "name", "locationName",
                    "value", "trial location name"
                )));
                break;
            default:
                break;
        }
    }

    @ParameterizedTest
    @CsvSource(value = {
        "damagesListCMCInt",
        "damagesListPTRInt",
        "damagesListTrialInt",
        "specifiedListCMCInt",
        "specifiedListPTRInt",
        "specifiedListTrialInt"
    })
    void when_intermediate_Listing_then_return_expected_config(String taskName) {

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"

        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("taskManagementLocations", Map.of(
            "cmcListingLocation", Map.of("region", "1",
                                         "location", "1234",
                                         "locationName", "a location name"),
            "ptrListingLocation", Map.of("region", "3",
                                         "location", "15154",
                                         "locationName", "ptr location name"),
            "trialListingLocation", Map.of("region", "4",
                                           "location", "57771",
                                           "locationName", "trial location name")
        ));

        VariableMap inputVariables = new VariableMapImpl();
        caseData.put("description", null);
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of(
            "taskType", taskName
        ));
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "description",
            "value",
            "[Confirm Listing](/cases/case-details/${[CASE_REFERENCE]}/trigger/CONFIRM_LISTING_COMPLETED/CONFIRM_LISTING_COMPLETEDConfirmListing)"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "workType",
            "value", "intermediate_track_hearing_work"
        )));
        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "roleCategory",
            "value", "ADMIN"
        )));

        switch (taskName) {
            case "damagesListCMCInt":
                assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
                    "canReconfigure", "true",
                    "name", "location",
                    "value", "1234"
                )));
                assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
                    "canReconfigure", "true",
                    "name", "region",
                    "value", "1"
                )));
                assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
                    "canReconfigure", "true",
                    "name", "locationName",
                    "value", "a location name"
                )));
                break;
            case "damagesListPTRInt":
                assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
                    "canReconfigure", "true",
                    "name", "location",
                    "value", "15154"
                )));
                assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
                    "canReconfigure", "true",
                    "name", "region",
                    "value", "3"
                )));
                assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
                    "canReconfigure", "true",
                    "name", "locationName",
                    "value", "ptr location name"
                )));
                break;
            case "damagesListTrialInt":
                assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
                    "canReconfigure", "true",
                    "name", "location",
                    "value", "57771"
                )));
                assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
                    "canReconfigure", "true",
                    "name", "region",
                    "value", "4"
                )));
                assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
                    "canReconfigure", "true",
                    "name", "locationName",
                    "value", "trial location name"
                )));
                break;
            default:
                break;
        }
    }

    @ParameterizedTest
    @CsvSource({
        "confirmOrderReviewUnlessOrder, "
            + "[Review case - Unless order - 16 December 2024](/cases/case-details/${[CASE_REFERENCE]}#notes)",
        "confirmOrderReviewFreeTrial, "
            + "[Review case - Pre trial checklist - 16 December 2024](/cases/case-details/${[CASE_REFERENCE]}#notes)",
        "confirmOrderReviewGeneralOrder, "
            + "[Review case - General order - 16 December 2024](/cases/case-details/${[CASE_REFERENCE]}#notes)",
        "confirmOrderReviewReserveJudgement, "
            + "[Review case - Reserve Judgment - 16 December 2024](/cases/case-details/${[CASE_REFERENCE]}#notes)",
        "confirmOrderReviewOther, "
            + "[Review case - Other - 16 December 2024](/cases/case-details/${[CASE_REFERENCE]}#notes)",
        "confirmOrderReviewDismissCase, "
            + "[Review case - Dismiss case - 16 December 2024](/cases/case-details/${[CASE_REFERENCE]}#notes)",
        "confirmOrderReviewManageStay, "
            + "[Review case - Lift stay - 16 December 2024](/cases/case-details/${[CASE_REFERENCE]}#notes)",
        "confirmOrderReviewStayCase, "
            + "[Review case - Stay case - 16 December 2024](/cases/case-details/${[CASE_REFERENCE]}#notes)",
    })
    void when_taskId_reviewCase_then_return_description(String taskType, String expectedValue) {
        VariableMap inputVariables = new VariableMapImpl();
        Map<String, Object> caseData = new HashMap<>();
        inputVariables.putValue("taskAttributes", Map.of("taskType", taskType));
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("obligationWAFlag", Map.of("currentDate", "16 December 2024"));
        inputVariables.putValue("caseData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("description"))
            .collect(Collectors.toList());

        assertTrue(workTypeResultList.contains(Map.of(
            "name", "description",
            "value", expectedValue,
            "canReconfigure", "false"
        )));
    }

    @Test
    void when_taskId_takeCaseOfflineApplicationNonEA_then_return_config() {
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
            "takeCaseOfflineApplicationNonEA"
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
            "canReconfigure", "false"
        )));
        List<Map<String, Object>> roleCategoryResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("roleCategory"))
            .collect(Collectors.toList());
        assertTrue(roleCategoryResultList.contains(Map.of(
            "name", "roleCategory",
            "value", "ADMIN",
            "canReconfigure", "false"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "description",
            "value", "[Application updated to non-EA Court - Take Case Offline]"
        )));
    }

    @Test
    void when_taskId_takeCaseOfflineVaryJudgementApplication_then_return_config() {
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
            "takeCaseOfflineVaryJudgmentApplication"
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
            "canReconfigure", "false"
        )));
        List<Map<String, Object>> roleCategoryResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("roleCategory"))
            .collect(Collectors.toList());
        assertTrue(roleCategoryResultList.contains(Map.of(
            "name", "roleCategory",
            "value", "ADMIN",
            "canReconfigure", "false"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "description",
            "value", "[Take case offline - determination to be completed offline]"
        )));
    }

    @Test
    void when_taskId_respondToQueryCtsc_then_return_config() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"
        ));

        String queryId = "query-id";
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of(
            "taskType", "respondToQueryCTSC",
            "__processCategory__queryManagement_queryID_" + queryId, true
        ));
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("workType"))
            .collect(Collectors.toList());

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));
        assertTrue(workTypeResultList.contains(Map.of(
            "name", "workType",
            "value", "query_work",
            "canReconfigure", "false"
        )));
        List<Map<String, Object>> roleCategoryResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("roleCategory"))
            .collect(Collectors.toList());

        assertTrue(roleCategoryResultList.contains(Map.of(
            "name", "roleCategory",
            "value", "CTSC",
            "canReconfigure", "false"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "description",
            "value", "[Respond to a query](/query-management/query/${[CASE_REFERENCE]}/3/" + queryId + ")"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "dueDateIntervalDays",
            "value", "20"
        )));
    }

    @Test
    void when_taskId_respondToQueryWlu_then_return_config() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"
        ));

        String queryId = "query-id";
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of(
            "taskType", "respondToQueryWLU",
            "__processCategory__queryManagement_queryID_" + queryId, true
        ));
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("workType"))
            .collect(Collectors.toList());

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));
        assertTrue(workTypeResultList.contains(Map.of(
            "name", "workType",
            "value", "query_work",
            "canReconfigure", "false"
        )));
        List<Map<String, Object>> roleCategoryResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("roleCategory"))
            .collect(Collectors.toList());

        assertTrue(roleCategoryResultList.contains(Map.of(
            "name", "roleCategory",
            "value", "ADMIN",
            "canReconfigure", "false"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "description",
            "value", "[Respond to a query](/query-management/query/${[CASE_REFERENCE]}/3/" + queryId + ")"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "dueDateIntervalDays",
            "value", "20"
        )));
    }

    @Test
    void when_taskId_respondToQueryAdmin_then_return_config() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"
        ));

        String queryId = "query-id";
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of(
            "taskType", "respondToQueryAdmin",
            "__processCategory__queryManagement_queryID_" + queryId, true
        ));
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("workType"))
            .collect(Collectors.toList());

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));
        assertTrue(workTypeResultList.contains(Map.of(
            "name", "workType",
            "value", "query_work",
            "canReconfigure", "false"
        )));
        List<Map<String, Object>> roleCategoryResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("roleCategory"))
            .collect(Collectors.toList());

        assertTrue(roleCategoryResultList.contains(Map.of(
            "name", "roleCategory",
            "value", "ADMIN",
            "canReconfigure", "false"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "description",
            "value", "[Respond to a query](/query-management/query/${[CASE_REFERENCE]}/3/" + queryId + ")"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "majorPriority",
            "value", "2000"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "dueDateIntervalDays",
            "value", "2"
        )));
    }

    @Test
    void when_taskId_respondToHearingQueryWlu_then_return_config() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"
        ));

        String queryId = "query-id";
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of(
            "taskType", "respondToHearingQueryWLU",
            "__processCategory__queryManagement_queryID_" + queryId, true
        ));
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("workType"))
            .collect(Collectors.toList());

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));
        assertTrue(workTypeResultList.contains(Map.of(
            "name", "workType",
            "value", "query_work",
            "canReconfigure", "false"
        )));
        List<Map<String, Object>> roleCategoryResultList = dmnDecisionTableResult.getResultList().stream()
            .filter((r) -> r.containsValue("roleCategory"))
            .collect(Collectors.toList());

        assertTrue(roleCategoryResultList.contains(Map.of(
            "name", "roleCategory",
            "value", "ADMIN",
            "canReconfigure", "false"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "description",
            "value", "[Respond to a query](/query-management/query/${[CASE_REFERENCE]}/3/" + queryId + ")"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "majorPriority",
            "value", "2000"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "dueDateIntervalDays",
            "value", "2"
        )));
    }

    @Test
    void when_taskId_upload_translated_order_document() {
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("applicant1", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("applicant2", Map.of(
            "partyName", "Firstname LastName"
        ));
        caseData.put("featureToggleWA", "CUI_WELSH");
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseData", caseData);
        inputVariables.putValue("taskAttributes", Map.of(
            "taskType",
            "uploadTranslatedOrderDocument"
        ));

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "description",
            "value", "[Upload Translated Order Document](/cases/case-details/${[CASE_REFERENCE]}/trigger/"
                + "UPLOAD_TRANSLATED_DOCUMENT/UPLOAD_TRANSLATED_DOCUMENTUploadTranslatedDocument)"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "true",
            "name", "workType",
            "value", "routine_work"
        )));

        assertTrue(dmnDecisionTableResult.getResultList().contains(Map.of(
            "canReconfigure", "false",
            "name", "roleCategory",
            "value", "ADMIN"
        )));
    }
}
