package uk.gov.hmcts.civil.taskconfiguration.dmn;

import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTable;
import uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTableBaseUnitTest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

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
    void given_input_should_return_schedule_hearing_dj_outcome_dmn_prod() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
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
    void given_input_should_return_schedule_hearing_dj_outcome_dmn_Wacp() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("eaCourtLocation", true);
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
        assertThat(workTypeResultList.get(0).get("name"), is("Schedule A Hearing"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ScheduleAHearing"));
    }

    @Test
    void given_input_should_return_transfer_offline_dj_outcome_dmn() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
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
    void given_input_should_return_schedule_hearing_sdo_outcome_dmn_prod() {

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
    void given_input_should_return_schedule_hearing_sdo_outcome_dmn_Wacp() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("eaCourtLocation", true);
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
        assertThat(workTypeResultList.get(0).get("name"), is("Schedule A Hearing"));
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
        data.put("allocatedTrack", "SMALL_CLAIM");
        data.put("featureToggleWA", "WA3.5");
        data.put("claimType", "Flight delay");
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
        data.put("allocatedTrack", "SMALL_CLAIM");
        data.put("featureToggleWA", "WA3.5");
        data.put("claimType", "Flight delay");
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
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getRules().size(), is(115));
    }
}
