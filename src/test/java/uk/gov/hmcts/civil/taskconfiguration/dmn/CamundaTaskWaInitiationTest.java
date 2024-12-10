package uk.gov.hmcts.civil.taskconfiguration.dmn;

import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableRuleImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTable;
import uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTableBaseUnitTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CamundaTaskWaInitiationTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = DmnDecisionTable.WA_TASK_INITIATION_CIVIL_DAMAGES;
    }

    @Test
    void given_input_should_return_notify_interim_dj_outcome_dmn() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "WA3.5");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "NOTIFY_INTERIM_JUDGMENT_DEFENDANT");
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Directions after Judgment (Damages)"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("summaryJudgmentDirections"));
    }

    @Test
    void given_input_should_not_return_schedule_hearing_dj_outcome_dmn_prod() {
        Map<String, Object> data = new HashMap<>();
        data.put("eaCourtLocation", false);
        data.put("featureToggleWA", "Prod");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "STANDARD_DIRECTION_ORDER_DJ");
        inputVariables.putValue("postEventState", "CASE_PROGRESSION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Transfer Case Offline"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("transferCaseOffline"));
    }

    @Test
    void given_input_should_return_schedule_hearing_dj_small_claim() {
        Map<String, Object> data = new HashMap<>();
        data.put("eaCourtLocation", true);
        data.put("featureToggleWA", "Prod");
        data.put("allocatedTrack", "SMALL_CLAIM");
        data.put("caseManagementOrderSelection", "TRIAL_HEARING");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "STANDARD_DIRECTION_ORDER_DJ");
        inputVariables.putValue("postEventState", "CASE_PROGRESSION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Schedule A Small Claim Hearing"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ScheduleAHearing"));
    }

    @Test
    void given_input_should_return_schedule_hearing_dj_fast_claim() {
        Map<String, Object> data = new HashMap<>();
        data.put("eaCourtLocation", true);
        data.put("featureToggleWA", "Prod");
        data.put("allocatedTrack", "FAST_CLAIM");
        data.put("caseManagementOrderSelection", "TRIAL_HEARING");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "STANDARD_DIRECTION_ORDER_DJ");
        inputVariables.putValue("postEventState", "CASE_PROGRESSION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Schedule A Fast Track Hearing"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ScheduleAHearing"));
    }

    @Test
    void given_input_should_return_schedule_hearing_dj_disposal_claim() {
        Map<String, Object> data = new HashMap<>();
        data.put("eaCourtLocation", true);
        data.put("featureToggleWA", "Prod");
        data.put("caseManagementOrderSelection", "DISPOSAL_HEARING");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "STANDARD_DIRECTION_ORDER_DJ");
        inputVariables.putValue("postEventState", "CASE_PROGRESSION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Schedule A Disposal Hearing"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ScheduleAHearing"));
    }

    @Test
    void given_input_should_return_schedule_hearing_dj_disposal_claim_relisted() {
        Map<String, Object> data = new HashMap<>();
        data.put("caseManagementOrderSelection", "DISPOSAL_HEARING");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "HEARING_SCHEDULED_RETRIGGER");
        inputVariables.putValue("postEventState", "CASE_PROGRESSION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Schedule A Disposal Hearing"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ScheduleAHearing"));
    }

    @Test
    void given_input_should_return_transfer_offline_dj_outcome_dmn() {
        Map<String, Object> data = new HashMap<>();
        data.put("eaCourtLocation", false);
        data.put("featureToggleWA", "Prod");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "STANDARD_DIRECTION_ORDER_DJ");
        inputVariables.putValue("postEventState", "CASE_PROGRESSION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Transfer Case Offline"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("transferCaseOffline"));
    }

    @Test
    void given_input_should_not_return_schedule_hearing_sdo_outcome_dmn_prod() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("eaCourtLocation", false);
        data.put("featureToggleWA", "Prod");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CREATE_SDO");
        inputVariables.putValue("postEventState", "CASE_PROGRESSION");

        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Transfer Case Offline"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("transferCaseOffline"));
    }

    @Test
    void given_input_should_return_schedule_hearing_small_claim_unspec_and_spec() {
        Map<String, Object> data = new HashMap<>();
        data.put("eaCourtLocation", true);
        data.put("featureToggleWA", "Prod");
        data.put("allocatedTrack", "SMALL_CLAIM");
        data.put("responseClaimTrack", "SMALL_CLAIM");

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CREATE_SDO");
        inputVariables.putValue("postEventState", "CASE_PROGRESSION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList.get(0).get("name"), is("Schedule A Small Claim Hearing"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ScheduleAHearing"));
        assertThat(workTypeResultList.get(1).get("name"), is("Schedule A Small Claim Hearing"));
        assertThat(workTypeResultList.get(1).get("taskId"), is("ScheduleAHearing"));
    }

    @Test
    void given_input_should_return_schedule_hearing_fast_claim_unspec_and_spec() {
        Map<String, Object> data = new HashMap<>();
        data.put("eaCourtLocation", true);
        data.put("featureToggleWA", "Prod");
        data.put("allocatedTrack", "FAST_CLAIM");
        data.put("responseClaimTrack", "FAST_CLAIM");

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CREATE_SDO");
        inputVariables.putValue("postEventState", "CASE_PROGRESSION");

        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList.get(0).get("name"), is("Schedule A Fast Track Hearing"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ScheduleAHearing"));
        assertThat(workTypeResultList.get(1).get("name"), is("Schedule A Fast Track Hearing"));
        assertThat(workTypeResultList.get(1).get("taskId"), is("ScheduleAHearing"));
    }

    @Test
    void given_input_should_return_schedule_hearing_disposal() {
        Map<String, Object> data = new HashMap<>();
        data.put("eaCourtLocation", true);
        data.put("featureToggleWA", "Prod");
        data.put("orderType", "DISPOSAL");

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CREATE_SDO");
        inputVariables.putValue("postEventState", "CASE_PROGRESSION");

        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Schedule A Disposal Hearing"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ScheduleAHearing"));
    }

    @Test
    void given_input_should_return_schedule_hearing_small_claim_unspec_and_spec_relisted() {
        Map<String, Object> data = new HashMap<>();
        data.put("allocatedTrack", "SMALL_CLAIM");
        data.put("responseClaimTrack", "SMALL_CLAIM");

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "HEARING_SCHEDULED_RETRIGGER");
        inputVariables.putValue("postEventState", "CASE_PROGRESSION");

        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList.get(0).get("name"), is("Schedule A Small Claim Hearing"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ScheduleAHearing"));
        assertThat(workTypeResultList.get(1).get("name"), is("Schedule A Small Claim Hearing"));
        assertThat(workTypeResultList.get(1).get("taskId"), is("ScheduleAHearing"));
    }

    @Test
    void given_input_should_return_schedule_hearing_fast_claim_unspec_and_spec_relisted() {
        Map<String, Object> data = new HashMap<>();
        data.put("allocatedTrack", "FAST_CLAIM");
        data.put("responseClaimTrack", "FAST_CLAIM");

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "HEARING_SCHEDULED_RETRIGGER");
        inputVariables.putValue("postEventState", "CASE_PROGRESSION");

        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList.get(0).get("name"), is("Schedule A Fast Track Hearing"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ScheduleAHearing"));
        assertThat(workTypeResultList.get(1).get("name"), is("Schedule A Fast Track Hearing"));
        assertThat(workTypeResultList.get(1).get("taskId"), is("ScheduleAHearing"));
    }

    @Test
    void given_input_should_return_schedule_hearing_disposal_relisted() {
        Map<String, Object> data = new HashMap<>();
        data.put("orderType", "DISPOSAL");

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "HEARING_SCHEDULED_RETRIGGER");
        inputVariables.putValue("postEventState", "CASE_PROGRESSION");

        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Schedule A Disposal Hearing"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ScheduleAHearing"));
    }

    @Test
    void given_input_should_return_transfer_offline_sdo_outcome_dmn() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("eaCourtLocation", false);
        data.put("featureToggleWA", "Prod");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CREATE_SDO");
        inputVariables.putValue("postEventState", "CASE_PROGRESSION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Transfer Case Offline"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("transferCaseOffline"));
    }

    @Test
    void when_not_suitable_sdo_change_location_create_transfer_online_task() {

        Map<String, Object> data = new HashMap<>();
        data.put("notSuitableSdoOptions", "CHANGE_LOCATION");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "NotSuitable_SDO");
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("transferOnlineCase"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("routineTransfer"));
    }

    @Test
    void when_not_suitable_sdo_change_location_recreate_sdo_task_response_forCaseProgressionState() {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "WA3.5");
        data.put("notSuitableSdoOptions", "OTHER_REASONS");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "NotSuitable_SDO");
        inputVariables.putValue("postEventState", "CASE_PROGRESSION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("transferCaseOfflineNotSuitableSDO"));
        assertNull(workTypeResultList.get(0).get("processCategories"));
    }

    @Test
    void when_transfer_online_case_change_location_recreate_sdo_task_response_smallClaimTrackDirections() {

        Map<String, Object> data = new HashMap<>();
        data.put("totalClaimAmount", 2000);
        data.put("responseClaimTrack", "SMALL_CLAIM");
        data.put("featureToggleWA", "WA3.5");
        data.put("notSuitableSdoOptions", "CHANGE_LOCATION");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "TRANSFER_ONLINE_CASE");
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("SmallClaimsTrackDirections"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("standardDirectionsOrder"));
        assertThat(workTypeResultList
                       .get(1).get("taskId"), is("OnlineCaseTransferReceived"));
        assertThat(workTypeResultList.get(1).get("processCategories"), is("routineTransfer"));
    }

    @Test
    void when_ransfer_online_case_change_location_recreate_sdo_task_allocated_LAsmallclaimTrackDirections() {

        Map<String, Object> data = new HashMap<>();
        data.put("claimValue", Map.of(
            "statementOfValueInPennies", 90000
        ));
        data.put("allocatedTrack", "SMALL_CLAIM");
        data.put("featureToggleWA", "WA3.5");
        data.put("notSuitableSdoOptions", "CHANGE_LOCATION");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "TRANSFER_ONLINE_CASE");
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("SmallClaimsTrackDirections"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("standardDirectionsOrder"));
        assertThat(workTypeResultList
                       .get(1).get("taskId"), is("OnlineCaseTransferReceived"));
        assertThat(workTypeResultList.get(1).get("processCategories"), is("routineTransfer"));
    }

    @Test
    void when_transfer_online_case_change_location_recreate_sdo_task_response_LAsmallClaimTrackDirections() {

        Map<String, Object> data = new HashMap<>();
        data.put("totalClaimAmount", 900);
        data.put("responseClaimTrack", "SMALL_CLAIM");
        data.put("featureToggleWA", "WA3.5");
        data.put("notSuitableSdoOptions", "CHANGE_LOCATION");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "TRANSFER_ONLINE_CASE");
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("LegalAdvisorSmallClaimsTrackDirections"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("standardDirectionsOrder"));
        assertThat(workTypeResultList
                       .get(1).get("taskId"), is("OnlineCaseTransferReceived"));
        assertThat(workTypeResultList.get(1).get("processCategories"), is("routineTransfer"));
    }

    @Test
    void when_transfer_online_change_location_recreate_sdo_task_allocated_fastclaimTrackDirections() {

        Map<String, Object> data = new HashMap<>();
        data.put("claimValue", Map.of(
            "statementOfValueInPennies", 120000
        ));
        data.put("notSuitableSdoOptions", "CHANGE_LOCATION");
        data.put("allocatedTrack", "FAST_CLAIM");
        data.put("featureToggleWA", "WA3.5");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "TRANSFER_ONLINE_CASE");
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();
        assertThat(workTypeResultList.size(), is(2));


        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("FastTrackDirections"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("standardDirectionsOrder"));
        assertThat(workTypeResultList
                       .get(1).get("taskId"), is("OnlineCaseTransferReceived"));
        assertThat(workTypeResultList.get(1).get("processCategories"), is("routineTransfer"));
    }

    @Test
    void when_transfer_online_change_location_recreate_sdo_task_response_fastClaimTrackDirections() {

        Map<String, Object> data = new HashMap<>();
        data.put("totalClaimAmount", 2000);
        data.put("responseClaimTrack", "FAST_CLAIM");
        data.put("notSuitableSdoOptions", "CHANGE_LOCATION");
        data.put("featureToggleWA", "WA3.5");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "TRANSFER_ONLINE_CASE");
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList
                       .get(1).get("taskId"), is("OnlineCaseTransferReceived"));
        assertThat(workTypeResultList.get(1).get("processCategories"), is("routineTransfer"));

        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("FastTrackDirections"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("standardDirectionsOrder"));
    }

    @Test
    void when_transfer_online_sdo_change_location_recreate_sdo_task_allocated_SummaryDirection_for_dj() {

        Map<String, Object> data = new HashMap<>();
        data.put("claimValue", Map.of(
            "statementOfValueInPennies", 120000
        ));
        data.put("notSuitableSdoOptions", "CHANGE_LOCATION");
        data.put("allocatedTrack", "FAST_CLAIM");
        data.put("featureToggleWA", "WA3.5");
        data.put("setRequestDJDamagesFlagForWA", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "TRANSFER_ONLINE_CASE");
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList
                       .get(1).get("taskId"), is("OnlineCaseTransferReceived"));
        assertThat(workTypeResultList.get(1).get("processCategories"), is("routineTransfer"));

        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("summaryJudgmentDirections"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("defaultJudgment"));
    }

    @Test
    void when_not_suitable_sdo_Other_dont_create_SummaryDirection_for_dj() {

        Map<String, Object> data = new HashMap<>();
        data.put("claimValue", Map.of(
            "statementOfValueInPennies", 120000
        ));
        data.put("notSuitableSdoOptions", "OTHER");
        data.put("allocatedTrack", "FAST_CLAIM");
        data.put("featureToggleWA", "WA3.5");
        data.put("setRequestDJDamagesFlagForWA", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "NotSuitable_SDO");
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("transferCaseOfflineNotSuitableSDO"));
        assertNull(workTypeResultList.get(0).get("processCategories"));
    }

    @Test
    void when_claimant_spec_and_claimtype_flightdelay_then_InitialDirectionFlightDelay() {

        Map<String, Object> data = new HashMap<>();
        data.put("responseClaimTrack", "SMALL_CLAIM");
        data.put("featureToggleWA", "WA3.5");
        data.put("claimType", "FLIGHT_DELAY");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CLAIMANT_RESPONSE_SPEC");
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("InitialDirectionFlightDelay"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("standardDirectionsOrder"));

    }

    @Test
    void when_toc_and_claimtype_flightdelay_then_InitialDirectionFlightDelay() {

        Map<String, Object> data = new HashMap<>();
        data.put("responseClaimTrack", "SMALL_CLAIM");
        data.put("featureToggleWA", "WA3.5");
        data.put("claimType", "FLIGHT_DELAY");
        data.put("notSuitableSdoOptions", "CHANGE_LOCATION");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "TRANSFER_ONLINE_CASE");
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("OnlineCaseTransferReceived"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("routineTransfer"));
        assertThat(workTypeResultList
                       .get(1).get("taskId"), is("InitialDirectionFlightDelay"));
        assertThat(workTypeResultList.get(1).get("processCategories"), is("standardDirectionsOrder"));
    }

    @Test
    void when_transfer_online_case_create_online_case_transfer_received_created() {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "WA3.5");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "TRANSFER_ONLINE_CASE");
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("OnlineCaseTransferReceived"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("routineTransfer"));
    }

    // Tests for NIHLFastTrackDirections
    @ParameterizedTest
    @CsvSource({
        "ONE_V_ONE,",
        "ONE_V_TWO_ONE_LEGAL_REP,FULL_DEFENCE",
        "ONE_V_TWO_TWO_LEGAL_REP,FULL_DEFENCE",
        "TWO_V_ONE,",
    })
    void when_claimant_response_and_claimType_Nihl_then_FastTrackDirectionsNihl(String claimantResponseScenarioFlag,
                                                                                String respondent2ClaimResponseType) {

        Map<String, Object> data = new HashMap<>();
        data.put("allocatedTrack", "FAST_CLAIM");
        data.put("featureToggleWA", "WA3.5");
        data.put("claimType", "PERSONAL_INJURY");
        data.put("personalInjuryType", "NOISE_INDUCED_HEARING_LOSS");
        data.put("claimantResponseScenarioFlag", claimantResponseScenarioFlag);
        data.put("claimValue", Map.of(
            "statementOfValueInPennies", 100001));
        data.put("respondent1ClaimResponseType", "FULL_DEFENCE");
        data.put("respondent2ClaimResponseType", respondent2ClaimResponseType);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CLAIMANT_RESPONSE");
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("NIHLFastTrackDirections"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("standardDirectionsOrder"));
    }

    @ParameterizedTest
    @CsvSource({
        "ONE_V_ONE,",
        "ONE_V_TWO_ONE_LEGAL_REP,FULL_DEFENCE",
        "ONE_V_TWO_TWO_LEGAL_REP,FULL_DEFENCE",
        "TWO_V_ONE,",
    })
    void claimant_res_claimType_Nihl_less_than_1000_then_FastTrackDirectionsNihl(String claimantResponseScenarioFlag,
                                                                                String respondent2ClaimResponseType) {

        Map<String, Object> data = new HashMap<>();
        data.put("allocatedTrack", "FAST_CLAIM");
        data.put("featureToggleWA", "WA3.5");
        data.put("claimType", "PERSONAL_INJURY");
        data.put("personalInjuryType", "NOISE_INDUCED_HEARING_LOSS");
        data.put("claimantResponseScenarioFlag", claimantResponseScenarioFlag);
        data.put("claimValue", Map.of(
            "statementOfValueInPennies", 10000));
        data.put("respondent1ClaimResponseType", "FULL_DEFENCE");
        data.put("respondent2ClaimResponseType", respondent2ClaimResponseType);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CLAIMANT_RESPONSE");
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("NIHLFastTrackDirections"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("standardDirectionsOrder"));
    }

    @ParameterizedTest
    @CsvSource({
        "100001,FAST_CLAIM,"
    })
    void when_toc_location_and_claimType_Nihl_then_FastTrackDirectionsNihl(Integer statementOfValueInPennies,
                                                                               String allocatedTrack) {

        Map<String, Object> data = new HashMap<>();
        data.put("allocatedTrack", allocatedTrack);
        data.put("featureToggleWA", "WA3.5");
        data.put("claimType", "PERSONAL_INJURY");
        data.put("personalInjuryType", "NOISE_INDUCED_HEARING_LOSS");
        data.put("claimValue", Map.of("statementOfValueInPennies", statementOfValueInPennies));
        data.put("notSuitableSdoOptions", "CHANGE_LOCATION");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "TRANSFER_ONLINE_CASE");
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertTrue(workTypeResultList.contains(Map.of(
            "taskId", "NIHLFastTrackDirections",
            "processCategories", "standardDirectionsOrder",
            "name","Fast Track Directions - Noise induced hearing loss"
        )));
    }

    @ParameterizedTest
    @CsvSource({
        "Applicant gives reason,,,", ",Respondent 1 gives reason,,",",,Respondent2 gives Reason"
    })
    void when_request_for_reconsideration_create_judge_decide_on_reconsider_request_spec(
        String applicantReason, String respondent1Reason, String respondent2Reason) {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "WA3.5");
        data.put("totalClaimAmount", 900);
        data.put("responseClaimTrack", "SMALL_CLAIM");
        data.put("reasonForReconsiderationApplicant", applicantReason);
        data.put("reasonForReconsiderationRespondent1", respondent1Reason);
        data.put("reasonForReconsiderationRespondent2", respondent2Reason);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REQUEST_FOR_RECONSIDERATION");
        inputVariables.putValue("additionalData", caseData);
        inputVariables.putValue("postEventState", "CASE_PROGRESSION");
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("JudgeDecideOnReconsiderRequest"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("decisionOnReconsideration"));
    }

    @ParameterizedTest
    @CsvSource({
        "Applicant gives reason,Respondent 1 gives reason,,",
        ",Respondent 1 gives reason,Respondent2 gives Reason,",
        "Applicant gives reason,,Respondent2 gives Reason"
    })
    void when_multiple_request_for_reconsideration_do_not_create_judge_decide_on_reconsider_request_spec(
        String applicantReason, String respondent1Reason, String respondent2Reason) {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "WA3.5");
        data.put("totalClaimAmount", 900);
        data.put("responseClaimTrack", "SMALL_CLAIM");
        data.put("reasonForReconsiderationApplicant", applicantReason);
        data.put("reasonForReconsiderationRespondent1", respondent1Reason);
        data.put("reasonForReconsiderationRespondent2", respondent2Reason);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REQUEST_FOR_RECONSIDERATION");
        inputVariables.putValue("additionalData", caseData);
        inputVariables.putValue("postEventState", "CASE_PROGRESSION");
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(0));
    }

    @Test
    void when_manage_contact_information_created() {

        Map<String, Object> caseData = new HashMap<>();

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CONTACT_INFORMATION_UPDATED_WA");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("UpdateDetailsInCasemanSystem"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("updateContactInformation"));
    }

    @ParameterizedTest
    @CsvSource({
        "850,SMALL_CLAIM,standardClaim,CREATE_SDO"
    })
    void when_decision_on_reconsideration_create_sdo_create_small_track_directions_test(Integer claimAmount,
                                                                                        String responseTrack,
                                                                                        String claimType,
                                                                                        String respToReconsideration) {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "WA3.5");
        data.put("totalClaimAmount", claimAmount);
        data.put("responseClaimTrack", responseTrack);
        data.put("claimType", claimType);
        data.put("decisionOnRequestReconsiderationOptions", respToReconsideration);

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "DECISION_ON_RECONSIDERATION_REQUEST");
        inputVariables.putValue("additionalData", caseData);
        inputVariables.putValue("postEventState", "CASE_PROGRESSION");

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("SmallClaimsTrackDirections"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("standardDirectionsOrder"));
    }

    @Test
    void given_input_should_return_send_cvp_link_outcome_decision() {

        Map<String, Object> data = new HashMap<>();
        data.put("eaCourtLocation", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "SEND_CVP_JOIN_LINK");
        inputVariables.putValue("postEventState", "PREPARE_FOR_HEARING_CONDUCT_HEARING");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Send Video Hearing Joining Link"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("sendCvpHearingLink"));
    }

    @Test
    void given_input_should_return_review_hearing_exception_outcome_decision() {

        Map<String, Object> data = new HashMap<>();
        data.put("eaCourtLocation", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REVIEW_HEARING_EXCEPTION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Review HMC Hearing Request Failure"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("reviewHearingException"));
    }

    @Test
    void given_input_should_return_review_claimant_welsh_request_decision() {
        Map<String, Object> data = new HashMap<>();
        data.put("claimantBilingualLanguagePreference", "BOTH");

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "GENERATE_RESPONSE_DQ_LIP_SEALED");
        inputVariables.putValue("additionalData", caseData);
        inputVariables.putValue("postEventState", "AWAITING_APPLICANT_INTENTION");

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("claimantWelshRequest"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("requestTranslation"));
    }

    @Test
    void given_input_should_return_review_case_flag_for_defendant_during_support_needs() {
        Map<String, Object> data = new HashMap<>();
        data.put("respondent1DQVulnerabilityQuestions", Map.of("vulnerabilityAdjustmentsRequired", true));
        data.put("respondent1DQLanguage", Map.of("court", "BOTH"));

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "UPDATE_CLAIMANT_INTENTION_CLAIM_STATE");
        inputVariables.putValue("postEventState", "");
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("ReviewCaseFlagsForDefendant"));
        assertThat(workTypeResultList
                       .get(0).get("name"), is("Review Case Flags Defendant"));
    }

    @Test
    void given_input_should_return_review_claimant_welsh_request_decision_during_claimIssue() {
        Map<String, Object> data = new HashMap<>();
        data.put("claimantBilingualLanguagePreference", "BOTH");

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "GENERATE_LIP_DEFENDANT_CLAIM_FORM_SPEC");
        inputVariables.putValue("additionalData", caseData);
        inputVariables.putValue("postEventState", "PENDING_CASE_ISSUED");

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("claimantWelshRequest"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("requestTranslation"));
    }

    @Test
    void given_input_should_return_review_case_flag_for_claimant_during_support_needs() {
        Map<String, Object> data = new HashMap<>();
        data.put("applicant1DQVulnerabilityQuestions", Map.of("vulnerabilityAdjustmentsRequired", true));
        data.put("applicant1DQLanguage", Map.of("court", "BOTH"));

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "UPDATE_CLAIMANT_INTENTION_CLAIM_STATE");
        inputVariables.putValue("postEventState", "");
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("ReviewCaseFlagsForClaimant"));
        assertThat(workTypeResultList
                       .get(0).get("name"), is("Review Case Flags Claimant"));
    }

    @Test
    void given_input_should_create_sdo_task_for_lip_vs_lip_small_claim_in_judicial_referral() {
        Map<String, Object> data = new HashMap<>();
        data.put("responseClaimTrack", "SMALL_CLAIM");
        data.put("totalClaimAmount", 2000);

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "UPDATE_CLAIMANT_INTENTION_CLAIM_STATE");
        inputVariables.putValue("additionalData", caseData);
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("SmallClaimsTrackDirections"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("standardDirectionsOrder"));
    }

    @Test
    void given_input_should_create_sdo_task_for_lip_vs_lip_fast_claim_in_judicial_referral() {
        Map<String, Object> data = new HashMap<>();
        data.put("responseClaimTrack", "FAST_CLAIM");
        data.put("totalClaimAmount", 12000);

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "UPDATE_CLAIMANT_INTENTION_CLAIM_STATE");
        inputVariables.putValue("additionalData", caseData);
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("FastTrackDirections"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("standardDirectionsOrder"));
    }

    @Test
    void given_input_should_create_sdo_task_for_lip_vs_lip_small_claim_In_judicial_referral_claimIssue_in_bilingual() {
        Map<String, Object> data = new HashMap<>();
        data.put("responseClaimTrack", "SMALL_CLAIM");
        data.put("claimantBilingualLanguagePreference", "BOTH");
        data.put("totalClaimAmount", 2000);

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "UPDATE_CLAIM_STATE_AFTER_DOC_UPLOADED");
        inputVariables.putValue("additionalData", caseData);
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("SmallClaimsTrackDirections"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("standardDirectionsOrder"));
    }

    @Test
    void given_input_should_create_sdo_task_for_lip_vs_lip_fast_claim_in_judicial_referral_claimIssue_in_bilingual() {
        Map<String, Object> data = new HashMap<>();
        data.put("responseClaimTrack", "FAST_CLAIM");
        data.put("claimantBilingualLanguagePreference", "BOTH");
        data.put("totalClaimAmount", 12000);

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "UPDATE_CLAIM_STATE_AFTER_DOC_UPLOADED");
        inputVariables.putValue("additionalData", caseData);
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("FastTrackDirections"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("standardDirectionsOrder"));
    }

    @Test
    void given_claimant_rejects_repaymentPlan_should_return_transfer_case_offline_bilingual() {
        Map<String, Object> data = new HashMap<>();
        data.put("applicant1AcceptFullAdmitPaymentPlanSpec", false);
        data.put("claimantBilingualLanguagePreference", "BOTH");

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "UPDATE_CLAIM_STATE_AFTER_DOC_UPLOADED");
        inputVariables.putValue("additionalData", caseData);
        inputVariables.putValue("postEventState", "PROCEEDS_IN_HERITAGE_SYSTEM");

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("transferCaseOfflineLiP"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("claimantIntention"));
    }

    @Test
    void given_claimant_rejects_repaymentPlan_should_return_transfer_case_offline() {
        Map<String, Object> data = new HashMap<>();
        data.put("applicant1AcceptFullAdmitPaymentPlanSpec", false);
        data.put("claimantBilingualLanguagePreference", "ENGLISH");

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "UPDATE_CLAIMANT_INTENTION_CLAIM_STATE");
        inputVariables.putValue("additionalData", caseData);
        inputVariables.putValue("postEventState", "PROCEEDS_IN_HERITAGE_SYSTEM");

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("transferCaseOfflineLiP"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("claimantIntention"));
    }

    @Test
    void given_claimant_requested_judgment_by_admission_should_return_transfer_case_offline() {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> applicant1LiPResponse = new HashMap<>();
        applicant1LiPResponse.put("applicant1ChoosesHowToProceed", "SIGN_A_SETTLEMENT_AGREEMENT");
        data.put("applicant1LiPResponse", applicant1LiPResponse);

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REQUEST_JUDGEMENT_ADMISSION_SPEC");
        inputVariables.putValue("additionalData", caseData);
        inputVariables.putValue("postEventState", "PROCEEDS_IN_HERITAGE_SYSTEM");

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("transferCaseOfflineLiP"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("claimantIntention"));
    }

    @Test
    void given_claim_move_to_judicial_referral_should_return_legal_advisor_sdo() {
        Map<String, Object> data = new HashMap<>();
        data.put("totalClaimAmount", 800);

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "UPDATE_CLAIMANT_INTENTION_CLAIM_STATE");
        inputVariables.putValue("additionalData", caseData);
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("LegalAdvisorSmallClaimsTrackDirections"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("standardDirectionsOrder"));
    }

    @Test
    void when_refer_judge_defence_received_create_generate_directions_order_spec() {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "JO");
        data.put("totalClaimAmount", 900);
        data.put("responseClaimTrack", "SMALL_CLAIM");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_JUDGE_DEFENCE_RECEIVED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("OrderToSetAsideDefendedClaim"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("orderJudgmentSetAside"));
        assertThat(workTypeResultList.get(0).get("name"),
                   is("Defence received in time - order that judgment is set aside"));
    }

    @ParameterizedTest
    @CsvSource({
        "UPDATE_CLAIMANT_INTENTION_CLAIM_STATE",
        "UPDATE_CLAIM_STATE_AFTER_DOC_UPLOADED",
        "MEDIATION_UNSUCCESSFUL"
    })
    void when_cui_claimant_responds_and_claimtype_flightdelay_then_InitialDirectionFlightDelay(String eventId) {

        Map<String, Object> data = new HashMap<>();
        data.put("totalClaimAmount", 2000);
        data.put("responseClaimTrack", "SMALL_CLAIM");
        data.put("claimType", "FLIGHT_DELAY");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("InitialDirectionFlightDelay"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("standardDirectionsOrder"));
    }

    @Test
    void given_court_officer_order_event_should_trigger_task_adjournedReList() {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "COURT_OFFICER_ORDER");
        inputVariables.putValue("additionalData", caseData);
        inputVariables.putValue("postEventState", "CASE_PROGRESSION");

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("adjournedReList"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("caseProgression"));
    }

    @ParameterizedTest
    @CsvSource({
        "CASE_PROGRESSION",
        "All_FINAL_ORDERS_ISSUED",
    })
    void when_final_order_issued_for_cui_claimant_and_defendant_bilingual(String stateName) {

        Map<String, Object> data = new HashMap<>();
        Map<String, Object> respondentResponseLanguage = new HashMap<>();
        respondentResponseLanguage.put("respondent1ResponseLanguage", "BOTH");

        data.put("claimantBilingualLanguagePreference", "BOTH");
        data.put("respondent1LiPResponse", respondentResponseLanguage);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "GENERATE_DIRECTIONS_ORDER");
        inputVariables.putValue("postEventState", stateName);
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList
                       .get(1).get("taskId"), is("finalOrderIssuedWelshRequest"));
        assertThat(workTypeResultList.get(1).get("processCategories"), is("requestTranslation"));
    }

    @ParameterizedTest
    @CsvSource({
        "CASE_PROGRESSION",
        "All_FINAL_ORDERS_ISSUED",
    })
    void when_final_order_issued_for_cui_claimant_bilingual(String stateName) {

        Map<String, Object> data = new HashMap<>();

        data.put("claimantBilingualLanguagePreference", "BOTH");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "GENERATE_DIRECTIONS_ORDER");
        inputVariables.putValue("postEventState", stateName);
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList
                       .get(1).get("taskId"), is("finalOrderIssuedWelshRequest"));
        assertThat(workTypeResultList.get(1).get("processCategories"), is("requestTranslation"));
    }

    @ParameterizedTest
    @CsvSource({
        "CASE_PROGRESSION",
        "All_FINAL_ORDERS_ISSUED",
    })
    void when_final_order_issued_for_cui_defendant_bilingual(String stateName) {

        Map<String, Object> data = new HashMap<>();
        Map<String, Object> respondentResponseLanguage = new HashMap<>();
        respondentResponseLanguage.put("respondent1ResponseLanguage", "BOTH");

        data.put("respondent1LiPResponse", respondentResponseLanguage);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "GENERATE_DIRECTIONS_ORDER");
        inputVariables.putValue("postEventState", stateName);
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList
                       .get(1).get("taskId"), is("finalOrderIssuedWelshRequest"));
        assertThat(workTypeResultList.get(1).get("processCategories"), is("requestTranslation"));
    }

    @Test
    void given_claim_is_settled_create_remove_hearing_task() {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> caseData = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("hearingDate", "22-12-2024");
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "SETTLE_CLAIM");
        inputVariables.putValue("additionalData", caseData);
        inputVariables.putValue("postEventState", "CASE_SETTLED");

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("ClaimSettledRemoveHearing"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("RemoveHearing"));
    }

    @Test
    void given_claim_is_settled_mark_paid_in_full_divergence_create_takeCaseOffline_task() {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("markPaidForAllClaimants", false);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "SETTLE_CLAIM_MARK_PAID_FULL");
        inputVariables.putValue("additionalData", caseData);
        inputVariables.putValue("postEventState", "");
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("ClaimSettledDivergenceTakeCaseOffline"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("paidInFull"));
        assertThat(workTypeResultList.get(0).get("name"),
                   is("ClaimSettledDivergenceTakeCaseOffline"));
    }


    @Test
    void given_claim_is_settled_mark_paid_in_full_create_remove_hearing_task() {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("hearingDate", "22-12-2024");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "SETTLE_CLAIM_MARK_PAID_FULL");
        inputVariables.putValue("additionalData", caseData);
        inputVariables.putValue("postEventState", "CLOSED");
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("ClaimSettledRemoveHearing"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("RemoveHearing"));
    }

    @ParameterizedTest
    @CsvSource({
        "CASE_PROGRESSION",
        "HEARING_READINESS",
        "PREPARE_FOR_HEARING_CONDUCT_HEARING",
        "DECISION_OUTCOME",
        "ALL_FINAL_ORDERS_ISSUED",
    })
    void when_transfer_online_event_create_take_offline_task_if_eaCourt_false(String state) {

        Map<String, Object> data = new HashMap<>();
        data.put("eaCourtLocation", "false");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "TRANSFER_ONLINE_CASE");
        inputVariables.putValue("additionalData", caseData);
        inputVariables.putValue("postEventState", state);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList.get(0).get("taskId"), is("OnlineCaseTransferReceived"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("routineTransfer"));
        assertThat(workTypeResultList.get(0).get("name"), is("Online Case Transfer Received"));

        assertThat(workTypeResultList.get(1).get("taskId"), is("transferCaseOffline"));
        assertThat(workTypeResultList.get(1).get("processCategories"), is("NationalRollout"));
        assertThat(workTypeResultList.get(1).get("name"), is("Transfer Case Offline"));
    }

    @ParameterizedTest
    @CsvSource({
        "CASE_PROGRESSION",
        "HEARING_READINESS",
        "PREPARE_FOR_HEARING_CONDUCT_HEARING",
        "DECISION_OUTCOME",
        "ALL_FINAL_ORDERS_ISSUED",
    })
    void when_transfer_online_event_do_not_create_take_offline_task_if_eaCourt_true(String state) {

        Map<String, Object> data = new HashMap<>();
        data.put("eaCourtLocation", "true");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "TRANSFER_ONLINE_CASE");
        inputVariables.putValue("additionalData", caseData);
        inputVariables.putValue("postEventState", state);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("taskId"), is("OnlineCaseTransferReceived"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("routineTransfer"));
        assertThat(workTypeResultList.get(0).get("name"), is("Online Case Transfer Received"));
    }

    @Test
    void given_2v1_divergent_discontinuance_create_ClaimDiscontinuedDivergenceTakeCaseOffline() {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("courtPermissionNeeded", "NO");
        data.put("selectedClaimantForDiscontinuance", "Mr Joe");
        data.put("typeOfDiscontinuance", "FULL_DISCONTINUANCE");

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "DISCONTINUE_CLAIM_CLAIMANT");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("ClaimDiscontinuedDivergenceTakeCaseOffline"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("discontinued"));
    }

    @Test
    void given_1v2_divergent_discontinuance_create_ClaimDiscontinuedDivergenceTakeCaseOffline() {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("courtPermissionNeeded", "NO");
        data.put("isDiscontinuingAgainstBothDefendants", "NO");
        data.put("typeOfDiscontinuance", "FULL_DISCONTINUANCE");

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "DISCONTINUE_CLAIM_CLAIMANT");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("ClaimDiscontinuedDivergenceTakeCaseOffline")
        );
        assertThat(workTypeResultList.get(0).get("processCategories"), is("discontinued"));
    }

    @Test
    void given_courtPermissionNeededisTrue_createValidateDiscontinuance() {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("courtPermissionNeeded", "YES");

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "DISCONTINUE_CLAIM_CLAIMANT");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("ValidateDiscontinuance")
        );
        assertThat(workTypeResultList.get(0).get("processCategories"), is("discontinued"));
    }

    @Test
    void when_default_judgment_claim_value_over_25000_create_take_case_offline() {
        //TODO : on release of multi intermediate feature, clean up test and DMN to remove
        // current prod version of  summaryJudgmentDirections entry
        // multiOrIntermediateClaim toggled summaryJudgmentDirections version will then be prod
        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "multiOrIntermediateClaim");
        data.put("claimValue", Map.of("statementOfValueInPennies", 2500001));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "NOTIFY_INTERIM_JUDGMENT_DEFENDANT");
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList.get(0).get("taskId"), is("summaryJudgmentDirections"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("defaultJudgment"));
        assertThat(workTypeResultList.get(0).get("name"), is("Directions after Judgment (Damages)"));
        assertThat(workTypeResultList.get(1).get("taskId"), is("transferCaseOffline"));
        assertThat(workTypeResultList.get(1).get("processCategories"), is("multiOrIntermediateClaim"));
        assertThat(workTypeResultList.get(1).get("name"), is("Transfer Case Offline"));
    }

    @Test
    void when_default_judgment_claim_value_less_or_equal_25000_create_summary_judgment_directions() {
        //TODO : on release of multi intermediate feature, clean up test and DMN to remove
        // current prod version of  summaryJudgmentDirections entry
        // multiOrIntermediateClaim toggled summaryJudgmentDirections version will then be prod
        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "multiOrIntermediateClaim");
        data.put("claimValue", Map.of("statementOfValueInPennies", 2500000));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "NOTIFY_INTERIM_JUDGMENT_DEFENDANT");
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList.get(0).get("taskId"), is("summaryJudgmentDirections"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("defaultJudgment"));
        assertThat(workTypeResultList.get(0).get("name"), is("Directions after Judgment (Damages)"));
        assertThat(workTypeResultList.get(1).get("taskId"), is("summaryJudgmentDirections"));
        assertThat(workTypeResultList.get(1).get("processCategories"), is("defaultJudgment"));
        assertThat(workTypeResultList.get(1).get("name"), is("Directions after Judgment (Damages)"));
    }

    @Test
    void retrigger5142() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        List<DmnDecisionTableRuleImpl> duplicateList = logic.getRules().stream()
            .filter(r -> r.getConditions().get(0).getExpression().contains("RETRIGGER_CLAIMANT_RESPONSE"))
            .collect(Collectors.toList());
        for (DmnDecisionTableRuleImpl duplicate : duplicateList) {
            boolean hasOriginal = logic.getRules().stream()
                .anyMatch(rule -> {
                    if (!rule.getConditions().get(0).getExpression().contains("CLAIMANT_RESPONSE")) {
                        return false;
                    }
                    for (int i = 1; i < rule.getConditions().size(); i++) {
                        if (!Objects.equals(
                            rule.getConditions().get(i).getExpression(),
                            duplicate.getConditions().get(i).getExpression()
                        )) {
                            return false;
                        }
                    }
                    return true;
                });
            Assertions.assertTrue(hasOriginal);
        }
        duplicateList = logic.getRules().stream()
            .filter(r -> r.getConditions().get(0).getExpression().contains("RETRIGGER_CLAIMANT_RESPONSE_SPEC"))
            .collect(Collectors.toList());
        for (DmnDecisionTableRuleImpl duplicate : duplicateList) {
            Assertions.assertTrue(
                logic.getRules().stream()
                    .anyMatch(rule -> {
                        if (!rule.getConditions().get(0).getExpression().contains("CLAIMANT_RESPONSE_SPEC")) {
                            return false;
                        }
                        for (int i = 1; i < rule.getConditions().size(); i++) {
                            if (!Objects.equals(
                                rule.getConditions().get(i).getExpression(),
                                duplicate.getConditions().get(i).getExpression()
                            )) {
                                return false;
                            }
                        }
                        return true;
                    }));
            ;
        }
    }

    @Test
    void given_2v1_divergent_validate_discontinuance_create_ClaimDiscontinuedDivergenceTakeCaseOffline() {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("courtPermissionNeeded", "YES");
        data.put("confirmOrderGivesPermission", "YES");
        data.put("selectedClaimantForDiscontinuance", "Mr Joe");
        data.put("typeOfDiscontinuance", "FULL_DISCONTINUANCE");

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "VALIDATE_DISCONTINUE_CLAIM_CLAIMANT");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                .get(0).get("taskId"), is("ClaimDiscontinuedDivergenceTakeCaseOffline"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("discontinued"));
    }

    @Test
    void given_1v2_divergent_validate_discontinuance_create_ClaimDiscontinuedDivergenceTakeCaseOffline() {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("courtPermissionNeeded", "YES");
        data.put("confirmOrderGivesPermission", "YES");
        data.put("isDiscontinuingAgainstBothDefendants", "NO");
        data.put("typeOfDiscontinuance", "FULL_DISCONTINUANCE");

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "VALIDATE_DISCONTINUE_CLAIM_CLAIMANT");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                .get(0).get("taskId"), is("ClaimDiscontinuedDivergenceTakeCaseOffline")
        );
        assertThat(workTypeResultList.get(0).get("processCategories"), is("discontinued"));
    }

    @Test
    void given_1v2_claim_is_discontinued_create_remove_hearing_task_non_divergent_case() {
        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("hearingDate", "22-12-2024");
        data.put("courtPermissionNeeded", "YES");
        data.put("confirmOrderGivesPermission", "YES");
        data.put("isDiscontinuingAgainstBothDefendants", "YES");
        data.put("typeOfDiscontinuance", "FULL_DISCONTINUANCE");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "VALIDATE_DISCONTINUE_CLAIM_CLAIMANT");
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                .get(0).get("taskId"), is("ClaimDiscontinuedRemoveHearing"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("RemoveHearing"));
    }

    @Test
    void given_2v1_claim_is_validate_discontinued_create_remove_hearing_task_non_divergent_case() {
        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("hearingDate", "22-12-2024");
        data.put("courtPermissionNeeded", "YES");
        data.put("confirmOrderGivesPermission", "YES");
        data.put("selectedClaimantForDiscontinuance", "Both");
        data.put("typeOfDiscontinuance", "FULL_DISCONTINUANCE");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "VALIDATE_DISCONTINUE_CLAIM_CLAIMANT");
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                .get(0).get("taskId"), is("ClaimDiscontinuedRemoveHearing"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("RemoveHearing"));
    }

    @Test
    void given_1v1_claim_is_validate_discontinued_create_remove_hearing_task() {
        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("hearingDate", "22-12-2024");
        data.put("courtPermissionNeeded", "YES");
        data.put("confirmOrderGivesPermission", "YES");
        data.put("typeOfDiscontinuance", "FULL_DISCONTINUANCE");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "VALIDATE_DISCONTINUE_CLAIM_CLAIMANT");
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                .get(0).get("taskId"), is("ClaimDiscontinuedRemoveHearing"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("RemoveHearing"));
    }

    @Test
    void given_set_aside_judgment_create_JudgmentOnlineSetAsideTakeCaseOffline() {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "JO");

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "SET_ASIDE_JUDGMENT");
        inputVariables.putValue("postEventState", "All_FINAL_ORDERS_ISSUED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgmentOnlineSetAsideTakeCaseOffline"));
        assertThat(workTypeResultList.get(0).get("name"), is("Set Aside - Take Case Offline"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("setAsideJo"));
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getRules().size(), is(280));
    }

    @Test
    void given_input_should_return_asyncStitchingComplete() {
        Map<String, Object> data = new HashMap<>();
        data.put("bundleError", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "asyncStitchingComplete");
        inputVariables.putValue("postEventState", "PREPARE_FOR_HEARING_CONDUCT_HEARING");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Amend And Restitch Bundle"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("bundlefailedAmendandRestich"));
    }

    @ParameterizedTest
    @CsvSource({
        "100001, , FAST_CLAIM, , Fast Track Directions, FastTrackDirections",
        "100001, , SMALL_CLAIM, , Small Claims Track Directions, SmallClaimsTrackDirections",
        ", 2000, , FAST_CLAIM, Fast Track Directions, FastTrackDirections",
        ", 2000, , SMALL_CLAIM, Small Claims Track Directions, SmallClaimsTrackDirections",
        ", 900, , SMALL_CLAIM, Legal Advisor Small Claims Track Directions, LegalAdvisorSmallClaimsTrackDirections"
    })
    void given_input_should_return_correct_task(String claimValue, String totalClaimAmount,
                                                String allocatedTrack, String responseClaimTrack,
                                                String expectedName, String expectedTaskId) {
        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "CE_B2");
        if (claimValue != null && !claimValue.isEmpty()) {
            data.put("claimValue", Map.of("statementOfValueInPennies", Integer.parseInt(claimValue)));
        }
        if (totalClaimAmount != null && !totalClaimAmount.isEmpty()) {
            data.put("totalClaimAmount", Integer.parseInt(totalClaimAmount));
        }
        if (allocatedTrack != null && !allocatedTrack.isEmpty()) {
            data.put("allocatedTrack", allocatedTrack);
        }
        if (responseClaimTrack != null && !responseClaimTrack.isEmpty()) {
            data.put("responseClaimTrack", responseClaimTrack);
        }
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "MANAGE_STAY");
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is(expectedName));
        assertThat(workTypeResultList.get(0).get("taskId"), is(expectedTaskId));
    }

    @Test
    void manage_stay_wa_event_should_trigger_task() {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "MANAGE_STAY_WA");
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("manageStay"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("manageStay"));
    }

    @ParameterizedTest
    @CsvSource({
        "SMALL_CLAIM, , , , Schedule A Small Claim Hearing, ScheduleAHearing",
        "FAST_CLAIM, , , , Schedule A Fast Track Hearing, ScheduleAHearing",
        ", SMALL_CLAIM, , , Schedule A Small Claim Hearing, ScheduleAHearing",
        ", FAST_CLAIM, , , Schedule A Fast Track Hearing, ScheduleAHearing",
        ", , DISPOSAL, , Schedule A Disposal Hearing, ScheduleAHearing",
        ", , , DISPOSAL_HEARING, Schedule A Disposal Hearing, ScheduleAHearing"
    })
    void given_input_should_return_correct_hearingTask(String allocatedTrack, String responseClaimTrack,
                                                       String orderType, String caseManagementOrderSelection,
                                                       String expectedName, String expectedTaskId) {
        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "CE_B2");
        if (allocatedTrack != null && !allocatedTrack.isEmpty()) {
            data.put("allocatedTrack", allocatedTrack);
        }
        if (responseClaimTrack != null && !responseClaimTrack.isEmpty()) {
            data.put("responseClaimTrack", responseClaimTrack);
        }
        if (orderType != null && !orderType.isEmpty()) {
            data.put("orderType", orderType);
        }
        if (caseManagementOrderSelection != null && !caseManagementOrderSelection.isEmpty()) {
            data.put("caseManagementOrderSelection", caseManagementOrderSelection);
        }
        Map<String, Object> caseData = Map.of("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "MANAGE_STAY");
        inputVariables.putValue("postEventState", "CASE_PROGRESSION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is(expectedName));
        assertThat(workTypeResultList.get(0).get("taskId"), is(expectedTaskId));
    }

    @ParameterizedTest
    @CsvSource({
        ", MULTI_CLAIM, , , Transfer Case Offline, transferCaseOffline",
        ", INTERMEDIATE_CLAIM, , , Transfer Case Offline, transferCaseOffline",
    })
    void minti_cui_claimant_response_trigger_offline_task(String allocatedTrack, String responseClaimTrack,
                                                       String orderType, String caseManagementOrderSelection,
                                                       String expectedName, String expectedTaskId) {
        Map<String, Object> data = new HashMap<>();
        if (allocatedTrack != null && !allocatedTrack.isEmpty()) {
            data.put("allocatedTrack", allocatedTrack);
        }
        if (responseClaimTrack != null && !responseClaimTrack.isEmpty()) {
            data.put("responseClaimTrack", responseClaimTrack);
        }
        if (orderType != null && !orderType.isEmpty()) {
            data.put("orderType", orderType);
        }
        if (caseManagementOrderSelection != null && !caseManagementOrderSelection.isEmpty()) {
            data.put("caseManagementOrderSelection", caseManagementOrderSelection);
        }
        Map<String, Object> caseData = Map.of("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "UPDATE_CLAIMANT_INTENTION_CLAIM_STATE");
        inputVariables.putValue("postEventState", "AWAITING_APPLICANT_INTENTION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is(expectedName));
        assertThat(workTypeResultList.get(0).get("taskId"), is(expectedTaskId));
    }

    @ParameterizedTest
    @CsvSource({
        ", MULTI_CLAIM, , , Transfer Case Offline, transferCaseOffline",
        ", INTERMEDIATE_CLAIM, , , Transfer Case Offline, transferCaseOffline",
    })
    void minti_lr_claimant_response_lip_defendant_trigger_offline_task(String allocatedTrack, String responseClaimTrack,
                                                          String orderType, String caseManagementOrderSelection,
                                                          String expectedName, String expectedTaskId) {
        Map<String, Object> data = new HashMap<>();
        if (allocatedTrack != null && !allocatedTrack.isEmpty()) {
            data.put("allocatedTrack", allocatedTrack);
        }
        if (responseClaimTrack != null && !responseClaimTrack.isEmpty()) {
            data.put("responseClaimTrack", responseClaimTrack);
        }
        if (orderType != null && !orderType.isEmpty()) {
            data.put("orderType", orderType);
        }
        if (caseManagementOrderSelection != null && !caseManagementOrderSelection.isEmpty()) {
            data.put("caseManagementOrderSelection", caseManagementOrderSelection);
        }
        Map<String, Object> caseData = Map.of("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CLAIMANT_RESPONSE_SPEC");
        inputVariables.putValue("postEventState", "AWAITING_APPLICANT_INTENTION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is(expectedName));
        assertThat(workTypeResultList.get(0).get("taskId"), is(expectedTaskId));
    }

    @ParameterizedTest
    @CsvSource({
        "CLAIMANT_RESPONSE_SPEC, ,200000 , Allocate Multi Track",
        "MANAGE_STAY, ,200000 , Allocate Multi Track",
        "TRANSFER_ONLINE_CASE, ,200000 , Allocate Multi Track",
        "CLAIMANT_RESPONSE_SPEC, CLINICAL_NEGLIGENCE, 200000, Allocate Multi Track - Clinical Negligence",
        "MANAGE_STAY, CLINICAL_NEGLIGENCE, 200000, Allocate Multi Track - Clinical Negligence",
        "TRANSFER_ONLINE_CASE, CLINICAL_NEGLIGENCE, 200000, Allocate Multi Track - Clinical Negligence",
        "CLAIMANT_RESPONSE_SPEC, PERSONAL_INJURY, 250001, Allocate Multi Track - Serious Personal Injury",
        "MANAGE_STAY, PERSONAL_INJURY, 250001, Allocate Multi Track - Serious Personal Injury",
        "TRANSFER_ONLINE_CASE, PERSONAL_INJURY, 250001, Allocate Multi Track - Serious Personal Injury",
        "CLAIMANT_RESPONSE_SPEC, PERSONAL_INJURY, 250000, Allocate Multi Track",
        "MANAGE_STAY, PERSONAL_INJURY, 250000, Allocate Multi Track",
        "TRANSFER_ONLINE_CASE, PERSONAL_INJURY, 250000, Allocate Multi Track"
    })
    void given_input_should_return_allocate_minti_multi_claim_task_spec(String eventName, String claimType,
                                                                        Integer claimAmountPounds, String taskDescription) {
        Map<String, Object> data = new HashMap<>();
        data.put("responseClaimTrack", "MULTI_CLAIM");
        data.put("claimType", claimType);
        data.put("totalClaimAmount", claimAmountPounds);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventName);
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        if (Objects.equals(eventName, "TRANSFER_ONLINE_CASE")) {
            assertThat(workTypeResultList.size(), is(2));
            assertThat(workTypeResultList.get(1).get("name"), is(taskDescription));
            assertThat(workTypeResultList.get(1).get("taskId"), is("allocateMultiTrack"));
        } else {
            assertThat(workTypeResultList.size(), is(1));
            assertThat(workTypeResultList.get(0).get("name"), is(taskDescription));
            assertThat(workTypeResultList.get(0).get("taskId"), is("allocateMultiTrack"));
        }
    }

    @ParameterizedTest
    @CsvSource({
        "CLAIMANT_RESPONSE, ,20000000, Allocate Multi Track",
        "MANAGE_STAY, ,20000000, Allocate Multi Track",
        "TRANSFER_ONLINE_CASE, ,20000000, Allocate Multi Track",
        "CLAIMANT_RESPONSE, CLINICAL_NEGLIGENCE, 20000000, Allocate Multi Track - Clinical Negligence",
        "MANAGE_STAY, CLINICAL_NEGLIGENCE, 20000000, Allocate Multi Track - Clinical Negligence",
        "TRANSFER_ONLINE_CASE, CLINICAL_NEGLIGENCE, 20000000, Allocate Multi Track - Clinical Negligence",
        "CLAIMANT_RESPONSE, PERSONAL_INJURY, 25000001, Allocate Multi Track - Serious Personal Injury",
        "MANAGE_STAY, PERSONAL_INJURY, 25000001, Allocate Multi Track - Serious Personal Injury",
        "TRANSFER_ONLINE_CASE, PERSONAL_INJURY, 25000001, Allocate Multi Track - Serious Personal Injury",
        "CLAIMANT_RESPONSE, PERSONAL_INJURY, 25000000, Allocate Multi Track",
        "MANAGE_STAY, PERSONAL_INJURY, 25000000, Allocate Multi Track",
        "TRANSFER_ONLINE_CASE, PERSONAL_INJURY, 25000000, Allocate Multi Track"
    })
    void given_input_should_return_allocate_minti_multi_claim_task_unspec(String eventName, String claimType,
                                                                          String claimAmountPennies, String taskDescription) {
        Map<String, Object> data = new HashMap<>();
        data.put("allocatedTrack", "MULTI_CLAIM");
        data.put("claimType", claimType);
        data.put("claimValue", Map.of(
            "statementOfValueInPennies", Integer.parseInt(claimAmountPennies)));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventName);
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        if (Objects.equals(eventName, "TRANSFER_ONLINE_CASE")) {
            assertThat(workTypeResultList.size(), is(2));
            assertThat(workTypeResultList.get(1).get("name"), is(taskDescription));
            assertThat(workTypeResultList.get(1).get("taskId"), is("allocateMultiTrack"));
        } else {
            assertThat(workTypeResultList.size(), is(1));
            assertThat(workTypeResultList.get(0).get("name"), is(taskDescription));
            assertThat(workTypeResultList.get(0).get("taskId"), is("allocateMultiTrack"));
        }
    }
}
