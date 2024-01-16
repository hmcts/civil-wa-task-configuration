package uk.gov.hmcts.civil.taskconfiguration.dmn;

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
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "STANDARD_DIRECTION_ORDER_DJ");
        inputVariables.putValue("postEventState", "CASE_PROGRESSION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Schedule a SMALL_CLAIM hearing"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ScheduleAHearing"));
    }

    @Test
    void given_input_should_return_schedule_hearing_dj_fast_claim() {
        Map<String, Object> data = new HashMap<>();
        data.put("eaCourtLocation", true);
        data.put("featureToggleWA", "Prod");
        data.put("allocatedTrack", "FAST_CLAIM");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "STANDARD_DIRECTION_ORDER_DJ");
        inputVariables.putValue("postEventState", "CASE_PROGRESSION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Schedule a FAST_CLAIM hearing"));
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
        assertThat(workTypeResultList.get(0).get("name"), is("Schedule a Disposal hearing"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ScheduleAHearing"));
    }

    @Test
    void given_input_should_return_schedule_hearing_dj_disposal_claim_relisted() {
        Map<String, Object> data = new HashMap<>();
        data.put("eaCourtLocation", true);
        data.put("featureToggleWA", "Prod");
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
        assertThat(workTypeResultList.get(0).get("name"), is("Schedule a Disposal hearing"));
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
        assertThat(workTypeResultList.get(0).get("name"), is("Schedule a SMALL_CLAIM hearing"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ScheduleAHearing"));
        assertThat(workTypeResultList.get(1).get("name"), is("Schedule a SMALL_CLAIM hearing"));
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
        assertThat(workTypeResultList.get(0).get("name"), is("Schedule a FAST_CLAIM hearing"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ScheduleAHearing"));
        assertThat(workTypeResultList.get(1).get("name"), is("Schedule a FAST_CLAIM hearing"));
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
        assertThat(workTypeResultList.get(0).get("name"), is("Schedule a Disposal hearing"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ScheduleAHearing"));
    }

    @Test
    void given_input_should_return_schedule_hearing_small_claim_unspec_and_spec_relisted() {
        Map<String, Object> data = new HashMap<>();
        data.put("eaCourtLocation", true);
        data.put("featureToggleWA", "Prod");
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
        assertThat(workTypeResultList.get(0).get("name"), is("Schedule a SMALL_CLAIM hearing"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ScheduleAHearing"));
        assertThat(workTypeResultList.get(1).get("name"), is("Schedule a SMALL_CLAIM hearing"));
        assertThat(workTypeResultList.get(1).get("taskId"), is("ScheduleAHearing"));
    }

    @Test
    void given_input_should_return_schedule_hearing_fast_claim_unspec_and_spec_relisted() {
        Map<String, Object> data = new HashMap<>();
        data.put("eaCourtLocation", true);
        data.put("featureToggleWA", "Prod");
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
        assertThat(workTypeResultList.get(0).get("name"), is("Schedule a FAST_CLAIM hearing"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ScheduleAHearing"));
        assertThat(workTypeResultList.get(1).get("name"), is("Schedule a FAST_CLAIM hearing"));
        assertThat(workTypeResultList.get(1).get("taskId"), is("ScheduleAHearing"));
    }

    @Test
    void given_input_should_return_schedule_hearing_disposal_relisted() {
        Map<String, Object> data = new HashMap<>();
        data.put("eaCourtLocation", true);
        data.put("featureToggleWA", "Prod");
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
        assertThat(workTypeResultList.get(0).get("name"), is("Schedule a Disposal hearing"));
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
    void when_not_suitable_sdo_change_location_recreate_sdo_task_allocated_smallclaimTrackDirections() {

        Map<String, Object> data = new HashMap<>();
        data.put("claimValue", Map.of(
            "statementOfValueInPennies", 120000
        ));
        data.put("allocatedTrack", "SMALL_CLAIM");
        data.put("featureToggleWA", "WA3.5");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "NotSuitable_SDO");
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("transferOnlineCase"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("routineTransfer"));
        assertThat(workTypeResultList
                       .get(1).get("taskId"), is("SmallClaimsTrackDirections"));
        assertThat(workTypeResultList.get(1).get("processCategories"), is("standardDirectionsOrder"));
    }

    @Test
    void when_not_suitable_sdo_change_location_recreate_sdo_task_response_smallClaimTrackDirections() {

        Map<String, Object> data = new HashMap<>();
        data.put("totalClaimAmount", 2000);
        data.put("responseClaimTrack", "SMALL_CLAIM");
        data.put("featureToggleWA", "WA3.5");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "NotSuitable_SDO");
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("transferOnlineCase"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("routineTransfer"));
        assertThat(workTypeResultList
                       .get(1).get("taskId"), is("SmallClaimsTrackDirections"));
        assertThat(workTypeResultList.get(1).get("processCategories"), is("standardDirectionsOrder"));
    }

    @Test
    void when_not_suitable_sdo_change_location_recreate_sdo_task_allocated_LAsmallclaimTrackDirections() {

        Map<String, Object> data = new HashMap<>();
        data.put("claimValue", Map.of(
            "statementOfValueInPennies", 90000
        ));
        data.put("allocatedTrack", "SMALL_CLAIM");
        data.put("featureToggleWA", "WA3.5");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "NotSuitable_SDO");
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("transferOnlineCase"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("routineTransfer"));
        assertThat(workTypeResultList
                       .get(1).get("taskId"), is("LegalAdvisorSmallClaimsTrackDirections"));
        assertThat(workTypeResultList.get(1).get("processCategories"), is("standardDirectionsOrder"));
    }

    @Test
    void when_not_suitable_sdo_change_location_recreate_sdo_task_response_LAsmallClaimTrackDirections() {

        Map<String, Object> data = new HashMap<>();
        data.put("totalClaimAmount", 900);
        data.put("responseClaimTrack", "SMALL_CLAIM");
        data.put("featureToggleWA", "WA3.5");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "NotSuitable_SDO");
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("transferOnlineCase"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("routineTransfer"));
        assertThat(workTypeResultList
                       .get(1).get("taskId"), is("LegalAdvisorSmallClaimsTrackDirections"));
        assertThat(workTypeResultList.get(1).get("processCategories"), is("standardDirectionsOrder"));
    }

    @Test
    void when_not_suitable_sdo_change_location_recreate_sdo_task_allocated_fastclaimTrackDirections() {

        Map<String, Object> data = new HashMap<>();
        data.put("claimValue", Map.of(
            "statementOfValueInPennies", 120000
        ));
        data.put("allocatedTrack", "FAST_CLAIM");
        data.put("featureToggleWA", "WA3.5");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "NotSuitable_SDO");
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("transferOnlineCase"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("routineTransfer"));
        assertThat(workTypeResultList
                       .get(1).get("taskId"), is("FastTrackDirections"));
        assertThat(workTypeResultList.get(1).get("processCategories"), is("standardDirectionsOrder"));
    }

    @Test
    void when_not_suitable_sdo_change_location_recreate_sdo_task_response_fastClaimTrackDirections() {

        Map<String, Object> data = new HashMap<>();
        data.put("totalClaimAmount", 2000);
        data.put("responseClaimTrack", "FAST_CLAIM");
        data.put("featureToggleWA", "WA3.5");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "NotSuitable_SDO");
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("transferOnlineCase"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("routineTransfer"));
        assertThat(workTypeResultList
                       .get(1).get("taskId"), is("FastTrackDirections"));
        assertThat(workTypeResultList.get(1).get("processCategories"), is("standardDirectionsOrder"));
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
    void when_transfer_online_case_create_online_case_transfer_received_created() {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "WA3.5");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "TRANSFER_ONLINE_CASE");
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
        "100001,0,FAST_CLAIM,",
        "0,1001,,FAST_CLAIM",
    })
    void when_not_suitable_sdo_and_claimType_Nihl_then_FastTrackDirectionsNihl(Integer statementOfValueInPennies,
                                                                  Integer totalClaimAmount,
                                                                  String allocatedTrack,
                                                                  String responseClaimTrack) {

        Map<String, Object> data = new HashMap<>();
        data.put("allocatedTrack", allocatedTrack);
        data.put("responseClaimTrack", responseClaimTrack);
        data.put("featureToggleWA", "WA3.5");
        data.put("claimType", "PERSONAL_INJURY");
        data.put("personalInjuryType", "NOISE_INDUCED_HEARING_LOSS");
        data.put("claimValue", Map.of("statementOfValueInPennies", statementOfValueInPennies));
        data.put("totalClaimAmount", totalClaimAmount);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "NotSuitable_SDO");
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
    void when_request_for_reconsideration_create_judge_decide_on_reconsider_request_unspec() {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "WA3.5");
        data.put("claimValue", Map.of(
            "statementOfValueInPennies", 90000
        ));
        data.put("allocatedTrack", "SMALL_CLAIM");
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
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getRules().size(), is(133));
    }
}
