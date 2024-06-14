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
        assertThat(workTypeResultList.get(0).get("processCategories"), is("standardDirectionsOrder"));
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
                       .get(0).get("taskId"), is("LegalAdvisorSmallClaimsTrackDirections"));
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
        assertThat(workTypeResultList.get(0).get("processCategories"), is("standardDirectionsOrder"));
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

    @Test
    void when_request_for_reconsideration_create_judge_decide_on_reconsider_request_spec() {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "WA3.5");
        data.put("totalClaimAmount", 900);
        data.put("responseClaimTrack", "SMALL_CLAIM");
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

    @Test
    void when_manage_contact_information_created() {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "MCI");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

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
        data.put("featureToggleWA", "HMC");
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
        data.put("featureToggleWA", "HMC");
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
        data.put("featureToggleWA", "CUIR2");

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "UPDATE_CLAIMANT_INTENTION_CLAIM_STATE");
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
    void given_input_should_return_review_claimant_welsh_request_decision_during_claimIssue() {
        Map<String, Object> data = new HashMap<>();
        data.put("claimantBilingualLanguagePreference", "BOTH");
        data.put("featureToggleWA", "CUIR2");

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
        data.put("featureToggleWA", "CUIR2");

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
        data.put("featureToggleWA", "CUIR2");

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
        data.put("featureToggleWA", "CUIR2");

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
        data.put("featureToggleWA", "CUIR2");

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
        data.put("featureToggleWA", "CUIR2");

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
        data.put("featureToggleWA", "CUIR2");

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
        data.put("featureToggleWA", "CUIR2");

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
        data.put("featureToggleWA", "CUIR2");

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
        data.put("featureToggleWA", "CUIR2");

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
        data.put("featureToggleWA", "CUIR2");
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

    @Test
    void when_paid_in_full_received_create_ClaimSettledDivergenceTakeCaseOffline() {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("totalClaimAmount", 900);
        data.put("responseClaimTrack", "SMALL_CLAIM");
        data.put("markPaidForAllClaimants", "Yes");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "SETTLE_CLAIM_MARK_PAID_FULL");
        inputVariables.putValue("additionalData", caseData);
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
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getRules().size(), is(219));
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
}
