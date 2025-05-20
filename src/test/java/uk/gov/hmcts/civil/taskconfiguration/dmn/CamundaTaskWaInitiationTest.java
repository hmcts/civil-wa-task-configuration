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

import static java.util.Objects.nonNull;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SuppressWarnings("checkstyle:LineLength")
class CamundaTaskWaInitiationTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = DmnDecisionTable.WA_TASK_INITIATION_CIVIL_DAMAGES;
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
                       .get(0).get("taskId"), is("LegalAdvisorSmallClaimsTrackDirections"));
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
        data.put("responseClaimTrack", "SMALL_CLAIM");
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
                       .get(0).get("taskId"), is("LegalAdvisorSmallClaimsTrackDirections"));
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

    @ParameterizedTest
    @CsvSource({
        "BOTH, WELSH, ENGLISH, ENGLISH",
        "ENGLISH, WELSH, ENGLISH, ENGLISH",
        "BOTH, ENGLISH, ENGLISH, ENGLISH",
        "ENGLISH, ENGLISH, WELSH, ENGLISH",
        "ENGLISH, ENGLISH, ENGLISH, BOTH"
    })
    void given_english_to_welsh_input_should_return_review_claimant_welsh_request_decision(String respondentLang,
                                                                                           String respondentDqDocLang,
                                                                                           String claimantLang,
                                                                                           String claimantDqDocLang) {
        Map<String, Object> data = new HashMap<>();

        data.put("respondent1LiPResponse", Map.of("respondent1ResponseLanguage", respondentLang));
        data.put("respondent1DQLanguage",Map.of("documents", respondentDqDocLang));

        data.put("claimantBilingualLanguagePreference", claimantLang);
        data.put("applicant1DQLanguage", Map.of("documents", claimantDqDocLang));
        data.put("applicant1Represented", false);
        data.put("respondent1Represented", false);

        data.put("featureToggleWA", "CUI_WELSH");

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

    @ParameterizedTest
    @CsvSource({
        "WELSH, BOTH, ENGLISH",
        "ENGLISH, WELSH, ENGLISH",
        "WELSH, ENGLISH, ENGLISH",
        "ENGLISH, ENGLISH, WELSH"
    })
    void given_english_to_welsh_input_should_return_review_respondent_welsh_request_decision(String respondentLang,
                                                                                             String respondentDqDocLang,
                                                                                             String claimantLang) {
        Map<String, Object> data = new HashMap<>();
        data.put("respondent1LiPResponse", Map.of("respondent1ResponseLanguage", respondentLang));
        data.put("respondent1DQLanguage",Map.of("documents", respondentDqDocLang));
        data.put("claimantBilingualLanguagePreference", claimantLang);
        data.put("respondent1Represented", false);

        data.put("featureToggleWA", "CUI_WELSH");

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "DEFENDANT_RESPONSE_CUI");
        inputVariables.putValue("additionalData", caseData);
        inputVariables.putValue("postEventState", "AWAITING_RESPONDENT_ACKNOWLEDGEMENT");

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("defendantWelshRequest"));
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
                       .get(0).get("taskId"), is("LegalAdvisorSmallClaimsTrackDirections"));
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
                       .get(0).get("taskId"), is("LegalAdvisorSmallClaimsTrackDirections"));
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
        data.put("responseClaimTrack", "SMALL_CLAIM");

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
    void when_final_order_issued_for_cui_claimant_and_defendant_bilingual_caseProgression() {

        Map<String, Object> data = new HashMap<>();
        Map<String, Object> respondentResponseLanguage = new HashMap<>();
        respondentResponseLanguage.put("respondent1ResponseLanguage", "BOTH");

        data.put("claimantBilingualLanguagePreference", "BOTH");
        data.put("respondent1LiPResponse", respondentResponseLanguage);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "GENERATE_DIRECTIONS_ORDER");
        inputVariables.putValue("postEventState", "CASE_PROGRESSION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList
                       .get(1).get("taskId"), is("finalOrderIssuedWelshRequest"));
        assertThat(workTypeResultList.get(1).get("processCategories"), is("requestTranslation"));
    }

    @Test
    void when_final_order_issued_for_cui_claimant_and_defendant_bilingual_caseProgression_allFinalOrdersIssued() {

        Map<String, Object> data = new HashMap<>();
        Map<String, Object> respondentResponseLanguage = new HashMap<>();
        respondentResponseLanguage.put("respondent1ResponseLanguage", "BOTH");

        data.put("claimantBilingualLanguagePreference", "BOTH");
        data.put("respondent1LiPResponse", respondentResponseLanguage);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "GENERATE_DIRECTIONS_ORDER");
        inputVariables.putValue("postEventState", "All_FINAL_ORDERS_ISSUED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList
                       .get(1).get("taskId"), is("finalOrderIssuedWelshRequest"));
        assertThat(workTypeResultList.get(1).get("processCategories"), is("requestTranslation"));
    }

    @Test
    void when_final_order_issued_for_cui_claimant_bilingual_allFinalOrdersIssued() {

        Map<String, Object> data = new HashMap<>();

        data.put("claimantBilingualLanguagePreference", "BOTH");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "GENERATE_DIRECTIONS_ORDER");
        inputVariables.putValue("postEventState", "All_FINAL_ORDERS_ISSUED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList
                       .get(1).get("taskId"), is("finalOrderIssuedWelshRequest"));
        assertThat(workTypeResultList.get(1).get("processCategories"), is("requestTranslation"));
    }

    @Test
    void when_final_order_issued_for_cui_claimant_bilingual_caseProgression() {

        Map<String, Object> data = new HashMap<>();

        data.put("claimantBilingualLanguagePreference", "BOTH");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "GENERATE_DIRECTIONS_ORDER");
        inputVariables.putValue("postEventState", "CASE_PROGRESSION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList
                       .get(1).get("taskId"), is("finalOrderIssuedWelshRequest"));
        assertThat(workTypeResultList.get(1).get("processCategories"), is("requestTranslation"));
    }

    @Test
    void when_final_order_issued_for_cui_defendant_bilingual_CaseProgression() {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> respondentResponseLanguage = new HashMap<>();
        respondentResponseLanguage.put("respondent1ResponseLanguage", "BOTH");

        data.put("respondent1LiPResponse", respondentResponseLanguage);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "GENERATE_DIRECTIONS_ORDER");
        inputVariables.putValue("postEventState", "CASE_PROGRESSION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("reviewOrder"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("caseProgression"));
        assertThat(workTypeResultList
                       .get(1).get("taskId"), is("finalOrderIssuedWelshRequest"));
        assertThat(workTypeResultList.get(1).get("processCategories"), is("requestTranslation"));
    }

    @Test
    void when_final_order_issued_for_cui_defendant_bilingual_AllFinalOrdersIssued() {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> respondentResponseLanguage = new HashMap<>();
        respondentResponseLanguage.put("respondent1ResponseLanguage", "BOTH");

        data.put("respondent1LiPResponse", respondentResponseLanguage);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "GENERATE_DIRECTIONS_ORDER");
        inputVariables.putValue("postEventState", "All_FINAL_ORDERS_ISSUED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList
                       .get(1).get("taskId"), is("finalOrderIssuedWelshRequest"));
        assertThat(workTypeResultList.get(1).get("processCategories"), is("requestTranslation"));
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
        data.put("applicant1Represented", false);
        data.put("hearingDate", "22-12-2024");
        data.put("preStayState", "AWAITING_RESPONDENT_ACKNOWLEDGEMENT");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "SETTLE_CLAIM_MARK_PAID_FULL");
        inputVariables.putValue("additionalData", caseData);
        inputVariables.putValue("postEventState", "CASE_STAYED");
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("RemoveHMCHearing"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("caseProgression"));
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
    void given_courtPermissionNeededisTrue_preSdo_True_createValidateDiscontinuance() {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("courtPermissionNeeded", "YES");
        data.put("preStayState", "AWAITING_APPLICANT_INTENTION");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "DISCONTINUE_CLAIM_CLAIMANT");
        inputVariables.putValue("postEventState", "CASE_STAYED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("ValidateDiscontinuanceCTSC")
        );
        assertThat(workTypeResultList.get(0).get("processCategories"), is("discontinued"));
    }

    @Test
    void given_courtPermissionNeededisTrue_preSdo_false_createValidateDiscontinuance() {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("courtPermissionNeeded", "YES");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "DISCONTINUE_CLAIM_CLAIMANT");
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("ValidateDiscontinuanceAdmin")
        );
        assertThat(workTypeResultList.get(0).get("processCategories"), is("discontinued"));
    }

    @Test
    void when_default_judgment_claim_value_over_25000_create_take_case_offline() {
        Map<String, Object> data = new HashMap<>();
        data.put("claimValue", Map.of("statementOfValueInPennies", 2500001));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "NOTIFY_INTERIM_JUDGMENT_DEFENDANT");
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("taskId"), is("transferCaseOffline"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("multiOrIntermediateClaim"));
        assertThat(workTypeResultList.get(0).get("name"), is("Transfer Case Offline"));
    }

    @Test
    void when_default_judgment_claim_value_less_or_equal_25000_create_summary_judgment_directions() {
        Map<String, Object> data = new HashMap<>();
        data.put("claimValue", Map.of("statementOfValueInPennies", 2500000));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "NOTIFY_INTERIM_JUDGMENT_DEFENDANT");
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("taskId"), is("summaryJudgmentDirections"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("defaultJudgment"));
        assertThat(workTypeResultList.get(0).get("name"), is("Directions after Judgment (Damages)"));
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

        }
    }

    @Test
    void given_2v1_divergent_validate_discontinuance_create_ClaimDiscontinuedDivergenceTakeCaseOffline() {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("applicant1Represented", false);
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
        data.put("applicant1Represented", false);
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
                       .get(0).get("taskId"), is("RemoveHMCHearing"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("caseProgression"));
    }

    @Test
    void given_2v1_claim_is_validate_discontinued_create_remove_hearing_task_non_divergent_case() {
        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("applicant1Represented", false);
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
                       .get(0).get("taskId"), is("RemoveHMCHearing"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("caseProgression"));
    }

    @Test
    void given_1v1_claim_is_validate_discontinued_create_remove_hearing_task() {
        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("applicant1Represented", false);
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
                       .get(0).get("taskId"), is("RemoveHMCHearing"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("caseProgression"));
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
        ", 10001, , FAST_CLAIM, Fast Track Directions, FastTrackDirections",
        ", 10000, , FAST_CLAIM, Fast Track Directions, FastTrackDirections",
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
        "JUDICIAL, reviewMessageJudicial",
        "JUDICIAL_DISTRICT, reviewMessageJudicial",
        "JUDICIAL_CIRCUIT, reviewMessageJudicial",
        "LEGAL_OPERATIONS, reviewMessageLA",
        "ADMIN, reviewMessageCW"
    })
    void given_input_rolePool_should_return_correct_review_message_task(String rolePool, String expectedTaskId) {
        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "CE_B2");

        data.put("lastMessage", Map.of("recipientRoleType", rolePool));

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "SEND_AND_REPLY");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Review message"));
        assertThat(workTypeResultList.get(0).get("taskId"), is(expectedTaskId));
    }

    @ParameterizedTest
    @CsvSource({
        "SMALL_CLAIM, , , , Schedule a Small Claims Hearing - HMC, ScheduleHMCHearing",
        "FAST_CLAIM, , , , Schedule a Fast Track Hearing - HMC, ScheduleHMCHearing",
        ", SMALL_CLAIM, , , Schedule a Small Claims Hearing - HMC, ScheduleHMCHearing",
        ", FAST_CLAIM, , , Schedule a Fast Track Hearing - HMC, ScheduleHMCHearing",
        "SMALL_CLAIM, , DISPOSAL, , Schedule a Disposal Hearing - HMC, ScheduleHMCHearing",
        "FAST_CLAIM, , , DISPOSAL_HEARING, Schedule a Disposal Hearing - HMC, ScheduleHMCHearing"
    })
    void given_input_should_return_correct_hearingTask(String allocatedTrack, String responseClaimTrack,
                                                       String orderType, String caseManagementOrderSelection,
                                                       String expectedName, String expectedTaskId) {
        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "CE_B2");
        data.put("applicant1Represented", false);
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
        "MULTI_CLAIM, Transfer Case Offline, transferCaseOfflineMinti, CLAIMANT_RESPONSE_SPEC, AWAITING_APPLICANT_INTENTION",
        "INTERMEDIATE_CLAIM, Transfer Case Offline, transferCaseOfflineMinti, CLAIMANT_RESPONSE_SPEC, AWAITING_APPLICANT_INTENTION",
        "MULTI_CLAIM, Transfer Case Offline, transferCaseOfflineMinti, UPDATE_CLAIMANT_INTENTION_CLAIM_STATE, AWAITING_APPLICANT_INTENTION",
        "INTERMEDIATE_CLAIM, Transfer Case Offline, transferCaseOfflineMinti, UPDATE_CLAIMANT_INTENTION_CLAIM_STATE, AWAITING_APPLICANT_INTENTION",
    })
    void minti_lr_claimant_response_lip_defendant_trigger_offline_task_spec(String responseClaimTrack,
                                                                       String expectedName, String expectedTaskId, String eventId, String postState) {

        Map<String, Object> data = new HashMap<>();
        data.put("responseClaimTrack", responseClaimTrack);

        Map<String, Object> caseData = Map.of("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", postState);
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is(expectedName));
        assertThat(workTypeResultList.get(0).get("taskId"), is(expectedTaskId));
    }

    @ParameterizedTest
    @CsvSource({
        ", INTERMEDIATE_CLAIM, OTHER, Create a hearing notice - Other, createHearingNoticeInt",
        "INTERMEDIATE_CLAIM, , CASE_MANAGEMENT_CONFERENCE, Create a hearing notice - CMC, createHearingNoticeInt",
        ", INTERMEDIATE_CLAIM, TRIAL, Create a hearing notice - Trial, createHearingNoticeInt",
        "INTERMEDIATE_CLAIM, , PRE_TRIAL_REVIEW, Create a hearing notice - PTR, createHearingNoticeInt"
    })
    void minti_hearing_notice_intermediate_track(String allocatedTrack, String responseClaimTrack, String hearingTypeConfirmed,
                                                 String expectedName, String expectedTaskId) {
        Map<String, Object> data = new HashMap<>();

        Map<String, Object> hearingTypeValue = new HashMap<>();
        hearingTypeValue.put("code", hearingTypeConfirmed);
        Map<String, Object> hearingListingConfirmedDynamicList = new HashMap<>();
        hearingListingConfirmedDynamicList.put("value", hearingTypeValue);
        data.put("hearingListedDynamicList", hearingListingConfirmedDynamicList);

        if (allocatedTrack != null && !allocatedTrack.isEmpty()) {
            data.put("allocatedTrack", allocatedTrack);
        }
        if (responseClaimTrack != null && !responseClaimTrack.isEmpty()) {
            data.put("responseClaimTrack", responseClaimTrack);
        }
        Map<String, Object> caseData = Map.of("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CONFIRM_LISTING_COMPLETED");
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
        ", MULTI_CLAIM, OTHER, Create a hearing notice - Other, createHearingNoticeMT",
        "MULTI_CLAIM, , COSTS_CASE_MANAGEMENT_CONFERENCE, Create a hearing notice - CCMC, createHearingNoticeMT",
        ", MULTI_CLAIM, TRIAL, Create a hearing notice - Trial, createHearingNoticeMT",
        "MULTI_CLAIM, , PRE_TRIAL_REVIEW, Create a hearing notice - PTR, createHearingNoticeMT"
    })
    void minti_hearing_notice_multi_track(String allocatedTrack, String responseClaimTrack, String hearingTypeConfirmed,
                                          String expectedName, String expectedTaskId) {
        Map<String, Object> data = new HashMap<>();

        Map<String, Object> hearingTypeValue = new HashMap<>();
        hearingTypeValue.put("code", hearingTypeConfirmed);
        Map<String, Object> hearingListingConfirmedDynamicList = new HashMap<>();
        hearingListingConfirmedDynamicList.put("value", hearingTypeValue);
        data.put("hearingListedDynamicList", hearingListingConfirmedDynamicList);

        if (allocatedTrack != null && !allocatedTrack.isEmpty()) {
            data.put("allocatedTrack", allocatedTrack);
        }
        if (responseClaimTrack != null && !responseClaimTrack.isEmpty()) {
            data.put("responseClaimTrack", responseClaimTrack);
        }
        Map<String, Object> caseData = Map.of("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CONFIRM_LISTING_COMPLETED");
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
        "CLAIMANT_RESPONSE_SPEC, ,200000 , Multi Track Directions",
        "MANAGE_STAY, ,200000 , Multi Track Directions",
        "TRANSFER_ONLINE_CASE, ,200000 , Multi Track Directions",
        "CLAIMANT_RESPONSE_SPEC, CLINICAL_NEGLIGENCE, 200000, Multi Track Directions - Clinical Negligence",
        "MANAGE_STAY, CLINICAL_NEGLIGENCE, 200000, Multi Track Directions - Clinical Negligence",
        "TRANSFER_ONLINE_CASE, CLINICAL_NEGLIGENCE, 200000, Multi Track Directions - Clinical Negligence",
        "CLAIMANT_RESPONSE_SPEC, PERSONAL_INJURY, 250001, Multi Track Directions - Serious Personal Injury",
        "MANAGE_STAY, PERSONAL_INJURY, 250001, Multi Track Directions - Serious Personal Injury",
        "TRANSFER_ONLINE_CASE, PERSONAL_INJURY, 250001, Multi Track Directions - Serious Personal Injury",
        "CLAIMANT_RESPONSE_SPEC, PERSONAL_INJURY, 250000, Multi Track Directions",
        "MANAGE_STAY, PERSONAL_INJURY, 250000, Multi Track Directions",
        "TRANSFER_ONLINE_CASE, PERSONAL_INJURY, 250000, Multi Track Directions"
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
        "CLAIMANT_RESPONSE, ,20000000, Multi Track Directions",
        "MANAGE_STAY, ,20000000, Multi Track Directions",
        "TRANSFER_ONLINE_CASE, ,20000000, Multi Track Directions",
        "CLAIMANT_RESPONSE, CLINICAL_NEGLIGENCE, 20000000, Multi Track Directions - Clinical Negligence",
        "MANAGE_STAY, CLINICAL_NEGLIGENCE, 20000000, Multi Track Directions - Clinical Negligence",
        "TRANSFER_ONLINE_CASE, CLINICAL_NEGLIGENCE, 20000000, Multi Track Directions - Clinical Negligence",
        "CLAIMANT_RESPONSE, PERSONAL_INJURY, 25000001, Multi Track Directions - Serious Personal Injury",
        "MANAGE_STAY, PERSONAL_INJURY, 25000001, Multi Track Directions - Serious Personal Injury",
        "TRANSFER_ONLINE_CASE, PERSONAL_INJURY, 25000001, Multi Track Directions - Serious Personal Injury",
        "CLAIMANT_RESPONSE, PERSONAL_INJURY, 25000000, Multi Track Directions",
        "MANAGE_STAY, PERSONAL_INJURY, 25000000, Multi Track Directions",
        "TRANSFER_ONLINE_CASE, PERSONAL_INJURY, 25000000, Multi Track Directions"
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

    @ParameterizedTest
    @CsvSource({
        "CLAIMANT_RESPONSE_SPEC, , 100000 , Intermediate Track Directions",
        "MANAGE_STAY, , 100000 , Intermediate Track Directions",
        "TRANSFER_ONLINE_CASE, , 100000 , Intermediate Track Directions",
        "CLAIMANT_RESPONSE_SPEC, CLINICAL_NEGLIGENCE, 100000, Intermediate Track Directions - Clinical Negligence",
        "MANAGE_STAY, CLINICAL_NEGLIGENCE, 100000, Intermediate Track Directions - Clinical Negligence",
        "TRANSFER_ONLINE_CASE, CLINICAL_NEGLIGENCE, 100000, Intermediate Track Directions - Clinical Negligence",
    })
    void given_input_should_return_allocate_minti_intermediate_claim_task_spec(String eventName, String claimType,
                                                                               Integer claimAmountPounds, String taskDescription) {
        Map<String, Object> data = new HashMap<>();
        data.put("responseClaimTrack", "INTERMEDIATE_CLAIM");
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
            assertThat(workTypeResultList.get(1).get("taskId"), is("allocateIntermediateTrack"));
        } else {
            assertThat(workTypeResultList.size(), is(1));
            assertThat(workTypeResultList.get(0).get("name"), is(taskDescription));
            assertThat(workTypeResultList.get(0).get("taskId"), is("allocateIntermediateTrack"));
        }
    }

    @ParameterizedTest
    @CsvSource({
        "CLAIMANT_RESPONSE, , 100000 , Intermediate Track Directions",
        "MANAGE_STAY, , 100000 , Intermediate Track Directions",
        "TRANSFER_ONLINE_CASE, , 100000 , Intermediate Track Directions",
        "CLAIMANT_RESPONSE, CLINICAL_NEGLIGENCE, 100000, Intermediate Track Directions - Clinical Negligence",
        "MANAGE_STAY, CLINICAL_NEGLIGENCE, 100000, Intermediate Track Directions - Clinical Negligence",
        "TRANSFER_ONLINE_CASE, CLINICAL_NEGLIGENCE, 100000, Intermediate Track Directions - Clinical Negligence",
    })
    void given_input_should_return_allocate_minti_intermediate_claim_task_unspec(String eventName, String claimType,
                                                                                 String claimAmountPennies, String taskDescription) {
        Map<String, Object> data = new HashMap<>();
        data.put("allocatedTrack", "INTERMEDIATE_CLAIM");
        data.put("claimType", claimType);
        data.put(
            "claimValue", Map.of(
                "statementOfValueInPennies", Integer.parseInt(claimAmountPennies))
        );
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
            assertThat(workTypeResultList.get(1).get("taskId"), is("allocateIntermediateTrack"));
        } else {
            assertThat(workTypeResultList.size(), is(1));
            assertThat(workTypeResultList.get(0).get("name"), is(taskDescription));
            assertThat(workTypeResultList.get(0).get("taskId"), is("allocateIntermediateTrack"));
        }
    }

    @ParameterizedTest
    @CsvSource({
        "HEARING_SCHEDULED_RETRIGGER, CASE_MANAGEMENT_CONFERENCE, List a CMC, damagesListCMCMulti",
        "HEARING_SCHEDULED_RETRIGGER, OTHER, List a Multi Track hearing, damagesListCMCMulti",
        "HEARING_SCHEDULED_RETRIGGER, COSTS_CASE_MANAGEMENT_CONFERENCE, List a CCMC, damagesListCCMCMulti",
        "HEARING_SCHEDULED_RETRIGGER, PRE_TRIAL_REVIEW, List a PTR, damagesListPTRMulti",
        "HEARING_SCHEDULED_RETRIGGER, TRIAL, List a Trial, damagesListTrialMulti"})
    void given_input_should_return_allocate_multi_damages_listing_task_unspec(String eventName, String hearingType,
                                                                              String taskDescription, String expectedTask) {
        Map<String, Object> data = new HashMap<>();
        data.put("allocatedTrack", "MULTI_CLAIM");
        Map<String, Object> hearingTypeValue = new HashMap<>();
        hearingTypeValue.put("code", hearingType);
        Map<String, Object> requestHearingNoticeDynamic = new HashMap<>();
        requestHearingNoticeDynamic.put("value", hearingTypeValue);
        data.put("requestHearingNoticeDynamic", requestHearingNoticeDynamic);

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventName);
        inputVariables.putValue("postEventState", "CASE_PROGRESSION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is(taskDescription));
        assertThat(workTypeResultList.get(0).get("taskId"), is(expectedTask));
    }

    @ParameterizedTest
    @CsvSource({
        "HEARING_SCHEDULED_RETRIGGER, CASE_MANAGEMENT_CONFERENCE, List a CMC, damagesListCMCInt",
        "HEARING_SCHEDULED_RETRIGGER, OTHER, List a Intermediate Track hearing, damagesListCMCInt",
        "HEARING_SCHEDULED_RETRIGGER, PRE_TRIAL_REVIEW, List a PTR, damagesListPTRInt",
        "HEARING_SCHEDULED_RETRIGGER, TRIAL, List a Trial, damagesListTrialInt"})
    void given_input_should_return_allocate_intermediate_damages_listing_task_unspec(String eventName, String hearingType,
                                                                                     String taskDescription, String expectedTask) {
        Map<String, Object> data = new HashMap<>();
        data.put("allocatedTrack", "INTERMEDIATE_CLAIM");
        Map<String, Object> hearingTypeValue = new HashMap<>();
        hearingTypeValue.put("code", hearingType);
        Map<String, Object> requestHearingNoticeDynamic = new HashMap<>();
        requestHearingNoticeDynamic.put("value", hearingTypeValue);
        data.put("requestHearingNoticeDynamic", requestHearingNoticeDynamic);

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventName);
        inputVariables.putValue("postEventState", "CASE_PROGRESSION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is(taskDescription));
        assertThat(workTypeResultList.get(0).get("taskId"), is(expectedTask));
    }

    @ParameterizedTest
    @CsvSource({
        "HEARING_SCHEDULED_RETRIGGER, CASE_MANAGEMENT_CONFERENCE, List a CMC, specifiedListCMCMulti",
        "HEARING_SCHEDULED_RETRIGGER, OTHER, List a Multi Track hearing, specifiedListCMCMulti",
        "HEARING_SCHEDULED_RETRIGGER, COSTS_CASE_MANAGEMENT_CONFERENCE, List a CCMC, specifiedListCCMCMulti",
        "HEARING_SCHEDULED_RETRIGGER, PRE_TRIAL_REVIEW, List a PTR, specifiedListPTRMulti",
        "HEARING_SCHEDULED_RETRIGGER, TRIAL, List a Trial, specifiedListTrialMulti"})
    void given_input_should_return_allocate_multi_listing_task_spec(String eventName, String hearingType,
                                                                    String taskDescription, String expectedTask) {
        Map<String, Object> data = new HashMap<>();
        data.put("responseClaimTrack", "MULTI_CLAIM");
        Map<String, Object> hearingTypeValue = new HashMap<>();
        hearingTypeValue.put("code", hearingType);
        Map<String, Object> requestHearingNoticeDynamic = new HashMap<>();
        requestHearingNoticeDynamic.put("value", hearingTypeValue);
        data.put("requestHearingNoticeDynamic", requestHearingNoticeDynamic);

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventName);
        inputVariables.putValue("postEventState", "CASE_PROGRESSION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        System.out.println("ttttt " + workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is(taskDescription));
        assertThat(workTypeResultList.get(0).get("taskId"), is(expectedTask));
    }

    @ParameterizedTest
    @CsvSource({
        "HEARING_SCHEDULED_RETRIGGER, CASE_MANAGEMENT_CONFERENCE, List a CMC, specifiedListCMCInt",
        "HEARING_SCHEDULED_RETRIGGER, OTHER, List a Intermediate Track hearing, specifiedListCMCInt",
        "HEARING_SCHEDULED_RETRIGGER, PRE_TRIAL_REVIEW, List a PTR, specifiedListPTRInt",
        "HEARING_SCHEDULED_RETRIGGER, TRIAL, List a Trial, specifiedListTrialInt"})
    void given_input_should_return_allocate_intermediate_listing_task_spec(String eventName, String hearingType,
                                                                           String taskDescription, String expectedTask) {
        Map<String, Object> data = new HashMap<>();
        data.put("responseClaimTrack", "INTERMEDIATE_CLAIM");
        Map<String, Object> hearingTypeValue = new HashMap<>();
        hearingTypeValue.put("code", hearingType);
        Map<String, Object> requestHearingNoticeDynamic = new HashMap<>();
        requestHearingNoticeDynamic.put("value", hearingTypeValue);
        data.put("requestHearingNoticeDynamic", requestHearingNoticeDynamic);

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventName);
        inputVariables.putValue("postEventState", "CASE_PROGRESSION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is(taskDescription));
        assertThat(workTypeResultList.get(0).get("taskId"), is(expectedTask));
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getRules().size(), is(284));
    }

    @ParameterizedTest
    @CsvSource({
        "UNLESS_ORDER, confirmOrderReviewUnlessOrder",
        "PRE_TRIAL_CHECKLIST, confirmOrderReviewFreeTrial",
        "GENERAL_ORDER, confirmOrderReviewGeneralOrder",
        "RESERVE_JUDGMENT, confirmOrderReviewReserveJudgement",
        "OTHER, confirmOrderReviewOther",
        "STAY_A_CASE, confirmOrderReviewStayCase",
        "LIFT_A_STAY, confirmOrderReviewManageStay",
        "DISMISS_CASE, confirmOrderReviewDismissCase"
    })
    void given_input_should_return_correct_reviewCaseTask(String waFlag, String expectedTaskId) {
        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "CE_B2");
        data.put("obligationWAFlag", Map.of("obligationReason", waFlag));

        Map<String, Object> caseData = Map.of("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "ORDER_REVIEW_OBLIGATION_CHECK");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Review case"));
        assertThat(workTypeResultList.get(0).get("taskId"), is(expectedTaskId));
    }

    @Test
    void given_lip_claim_settled_create_remove_hearing_task_smallTrack() {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("hearingDate", "22-12-2024");
        data.put("responseClaimTrack", "SMALL_CLAIM");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        data.put("respondent1Represented", false);
        inputVariables.putValue("eventId", "LIP_CLAIM_SETTLED");
        inputVariables.putValue("additionalData", caseData);
        inputVariables.putValue("postEventState", "CASE_SETTLED");
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(
            workTypeResultList
                .get(0).get("taskId"), is("RemoveHMCHearing")
        );
        assertThat(workTypeResultList.get(0).get("processCategories"), is("caseProgression"));
    }

    @Test
    void given_lip_claim_settled_create_remove_hearing_task_fastTrack() {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("hearingDate", "22-12-2024");
        data.put("responseClaimTrack", "FAST_CLAIM");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        data.put("applicant1Represented", false);
        inputVariables.putValue("eventId", "LIP_CLAIM_SETTLED");
        inputVariables.putValue("additionalData", caseData);
        inputVariables.putValue("postEventState", "CASE_SETTLED");
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(
            workTypeResultList
                .get(0).get("taskId"), is("RemoveHMCHearing")
        );
        assertThat(workTypeResultList.get(0).get("processCategories"), is("caseProgression"));
    }

    @ParameterizedTest
    @CsvSource({
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION, FAST_CLAIM,, true, true,,, Reschedule a Fast Track Hearing - HMC",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION, SMALL_CLAIM,, true ,true,,, Reschedule a Small Claims Hearing - HMC",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,,, true, true, DISPOSAL,, Reschedule a Disposal Hearing - HMC",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,,, true, true,, DISPOSAL_HEARING, Reschedule a Disposal Hearing - HMC",
        "CREATE_SDO, CASE_PROGRESSION, FAST_CLAIM,,true,true,,, Schedule a Fast Track Hearing - HMC",
        "CREATE_SDO, CASE_PROGRESSION, SMALL_CLAIM,,true,true,,, Schedule a Small Claims Hearing - HMC",
        "CREATE_SDO, CASE_PROGRESSION,,,true,true,DISPOSAL,, Schedule a Disposal Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION, FAST_CLAIM,,true,true,,, Reschedule a Fast Track Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION, SMALL_CLAIM,,true,true,,, Reschedule a Small Claims Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION,,,true,true, DISPOSAL,, Reschedule a Disposal Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION,,,true,true,, DISPOSAL_HEARING, Reschedule a Disposal Hearing - HMC",
        "STANDARD_DIRECTION_ORDER_DJ, CASE_PROGRESSION, FAST_CLAIM,,true,true,,TRIAL_HEARING, "
            + "Schedule a Fast Track Hearing - HMC",
        "STANDARD_DIRECTION_ORDER_DJ, CASE_PROGRESSION, SMALL_CLAIM,,true,true,,TRIAL_HEARING, "
            + "Schedule a Small Claims Hearing - HMC",
        "STANDARD_DIRECTION_ORDER_DJ, CASE_PROGRESSION,,,true,true,, DISPOSAL_HEARING, Schedule a Disposal Hearing - HMC, 1",
        "COURT_OFFICER_ORDER, CASE_PROGRESSION,,,true,true,,, Reschedule a Hearing - HMC",
    })
    void given_input_should_return_correct_scheduleHmcHearingTask_noLip(
        String eventId,
        String postEventState,
        String allocatedTrack,
        String responseClaimTrack,
        boolean applicant1Represented,
        boolean respondent1Represented,
        String orderType,
        String caseManagementOrderSelection,
        String expectedTaskName
    ) {

        Map<String, Object> data = new HashMap<>();

        boolean directionsOrderEvent = eventId.equals("GENERATE_DIRECTIONS_ORDER");
        if (directionsOrderEvent) {
            addNonNullField(data, "finalOrderFurtherHearingToggle", "SHOW");
        }

        addNonNullField(data, "applicant1Represented", applicant1Represented);
        addNonNullField(data, "respondent1Represented", respondent1Represented);
        addNonNullField(data, "allocatedTrack", allocatedTrack);
        addNonNullField(data, "responseClaimTrack", responseClaimTrack);
        addNonNullField(data, "orderType", orderType);
        addNonNullField(data, "caseManagementOrderSelection", caseManagementOrderSelection);

        Map<String, Object> caseData = Map.of("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", postEventState);
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        int listSize = directionsOrderEvent ? 2 : 1;
        assertThat(workTypeResultList.size(), is(listSize));
        assertThat(workTypeResultList.get(listSize - 1).get("name"), is(expectedTaskName));
        assertThat(workTypeResultList.get(listSize - 1).get("taskId"), is("ScheduleHMCHearing"));
    }

    @ParameterizedTest
    @CsvSource({
        "MANAGE_STAY, CASE_PROGRESSION, FAST_CLAIM,,true,true,,, Schedule a Fast Track Hearing - HMC, 1",
        "MANAGE_STAY, CASE_PROGRESSION, SMALL_CLAIM,,true,true,,, Schedule a Small Claims Hearing - HMC, 1",
        "MANAGE_STAY, CASE_PROGRESSION,,,true,true, DISPOSAL,, Schedule a Disposal Hearing - HMC, 1",
        "MANAGE_STAY, CASE_PROGRESSION,,,true,true,, DISPOSAL_HEARING, Schedule a Disposal Hearing - HMC, 1"
    })
    void given_input_should_return_correct_scheduleHmcHearingTask_manageStay_noLip(
        String eventId,
        String postEventState,
        String allocatedTrack,
        String responseClaimTrack,
        boolean applicant1Represented,
        boolean respondent1Represented,
        String orderType,
        String caseManagementOrderSelection,
        String expectedTaskName,
        int expectedNumberOfTasks
    ) {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "CE_B2");
        addNonNullField(data, "applicant1Represented", applicant1Represented);
        addNonNullField(data, "respondent1Represented", respondent1Represented);
        addNonNullField(data, "allocatedTrack", allocatedTrack);
        addNonNullField(data, "responseClaimTrack", responseClaimTrack);
        addNonNullField(data, "orderType", orderType);
        addNonNullField(data, "caseManagementOrderSelection", caseManagementOrderSelection);

        Map<String, Object> caseData = Map.of("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", postEventState);
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        int lastIndex = expectedNumberOfTasks - 1;
        assertThat(workTypeResultList.size(), is(expectedNumberOfTasks));
        assertThat(workTypeResultList.get(lastIndex).get("name"), is(expectedTaskName));
        assertThat(workTypeResultList.get(lastIndex).get("taskId"), is("ScheduleHMCHearing"));
    }

    // Temp may return 2 tasks until HMC NRO goes live and legacy tasks are removed.
    @Test
    void given_HearingFeeUnpaid_should_return_correct_removeHmcHearingTask_noLipSmallClaimUnspec() {

        Map<String, Object> data = new HashMap<>();

        data.put("allocatedTrack", "SMALL_CLAIM");
        addNonNullField(data, "hearingDate", "22-12-2024");

        Map<String, Object> caseData = Map.of("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "HEARING_FEE_UNPAID");
        inputVariables.putValue("postEventState", "CASE_DISMISSED");
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Remove Hearing - HMC"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("RemoveHMCHearing"));
    }

    @Test
    void given_HearingFeeUnpaid_should_return_correct_removeHmcHearingTask_noLipFastClaimUnspec() {

        Map<String, Object> data = new HashMap<>();

        data.put("allocatedTrack", "FAST_CLAIM");
        addNonNullField(data, "hearingDate", "22-12-2024");

        Map<String, Object> caseData = Map.of("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "HEARING_FEE_UNPAID");
        inputVariables.putValue("postEventState", "CASE_DISMISSED");
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Remove Hearing - HMC"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("RemoveHMCHearing"));
    }

    @Test
    void given_HearingFeeUnpaid_should_return_correct_removeHmcHearingTask_noLipSmallClaimSpec() {

        Map<String, Object> data = new HashMap<>();

        data.put("responseClaimTrack", "SMALL_CLAIM");
        addNonNullField(data, "hearingDate", "22-12-2024");

        Map<String, Object> caseData = Map.of("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "HEARING_FEE_UNPAID");
        inputVariables.putValue("postEventState", "CASE_DISMISSED");
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Remove Hearing - HMC"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("RemoveHMCHearing"));
    }

    @Test
    void given_HearingFeeUnpaid_should_return_correct_removeHmcHearingTask_noLipFastClaimSpec() {

        Map<String, Object> data = new HashMap<>();

        data.put("responseClaimTrack", "FAST_CLAIM");
        addNonNullField(data, "hearingDate", "22-12-2024");

        Map<String, Object> caseData = Map.of("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "HEARING_FEE_UNPAID");
        inputVariables.putValue("postEventState", "CASE_DISMISSED");
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Remove Hearing - HMC"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("RemoveHMCHearing"));
    }

    @ParameterizedTest
    @CsvSource({
        "SETTLE_CLAIM_MARK_PAID_FULL, CASE_STAYED, 22-12-2024",
        "SETTLE_CLAIM, CASE_SETTLED, 22-12-2024"
    })
    void given_input_should_return_correct_removeHmcHearingTask_noLipSmallClaimUnspec(
        String eventId,
        String postEventState,
        String hearingDate
    ) {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("allocatedTrack", "SMALL_CLAIM");
        addNonNullField(data, "hearingDate", hearingDate);
        addNonNullField(data,"preStayState", "AWAITING_RESPONDENT_ACKNOWLEDGEMENT");

        Map<String, Object> caseData = Map.of("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", postEventState);
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Remove Hearing - HMC"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("RemoveHMCHearing"));
    }

    @ParameterizedTest
    @CsvSource({
        "SETTLE_CLAIM_MARK_PAID_FULL, CASE_STAYED, 22-12-2024, 1",
        "SETTLE_CLAIM, CASE_SETTLED, 22-12-2024, 1"
    })
    void given_input_should_return_correct_removeHmcHearingTask_noLipFastClaimUnspec(
        String eventId,
        String postEventState,
        String hearingDate,
        int expectedNumberOfTasks
    ) {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("allocatedTrack", "FAST_CLAIM");
        addNonNullField(data, "hearingDate", hearingDate);
        addNonNullField(data,"preStayState", "AWAITING_RESPONDENT_ACKNOWLEDGEMENT");

        Map<String, Object> caseData = Map.of("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", postEventState);
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(expectedNumberOfTasks));
        assertThat(workTypeResultList.get(0).get("name"), is("Remove Hearing - HMC"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("RemoveHMCHearing"));
    }

    @ParameterizedTest
    @CsvSource({
        "SETTLE_CLAIM_MARK_PAID_FULL, CASE_STAYED, 22-12-2024, 1",
        "SETTLE_CLAIM, CASE_SETTLED, 22-12-2024, 1"
    })
    void given_input_should_return_correct_removeHmcHearingTask_noLipSmallClaimSpec(
        String eventId,
        String postEventState,
        String hearingDate,
        int expectedNumberOfTasks
    ) {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("responseClaimTrack", "SMALL_CLAIM");
        addNonNullField(data, "hearingDate", hearingDate);
        addNonNullField(data,"preStayState", "AWAITING_RESPONDENT_ACKNOWLEDGEMENT");

        Map<String, Object> caseData = Map.of("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", postEventState);
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(expectedNumberOfTasks));
        assertThat(workTypeResultList.get(0).get("name"), is("Remove Hearing - HMC"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("RemoveHMCHearing"));
    }

    @ParameterizedTest
    @CsvSource({
        "SETTLE_CLAIM_MARK_PAID_FULL, CASE_STAYED, 22-12-2024, 1",
        "SETTLE_CLAIM, CASE_SETTLED, 22-12-2024, 1"
    })
    void given_input_should_return_correct_removeHmcHearingTask_noLipFastClaimSpec(
        String eventId,
        String postEventState,
        String hearingDate,
        int expectedNumberOfTasks
    ) {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("responseClaimTrack", "FAST_CLAIM");
        addNonNullField(data, "hearingDate", hearingDate);
        addNonNullField(data,"preStayState", "AWAITING_RESPONDENT_ACKNOWLEDGEMENT");

        Map<String, Object> caseData = Map.of("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", postEventState);
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(expectedNumberOfTasks));
        assertThat(workTypeResultList.get(0).get("name"), is("Remove Hearing - HMC"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("RemoveHMCHearing"));
    }

    @Test
    void claim_is_discontinued_create_removeHmcHearingTask_noLipSmallClaimUnspec() {
        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("hearingDate", "22-12-2024");
        data.put("courtPermissionNeeded", "YES");
        data.put("confirmOrderGivesPermission", "YES");
        data.put("isDiscontinuingAgainstBothDefendants", "YES");
        data.put("typeOfDiscontinuance", "FULL_DISCONTINUANCE");
        data.put("allocatedTrack", "SMALL_CLAIM");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "VALIDATE_DISCONTINUE_CLAIM_CLAIMANT");
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("RemoveHMCHearing"));
        assertThat(workTypeResultList
                       .get(0).get("name"), is("Remove Hearing - HMC"));
    }

    @Test
    void claim_is_discontinued_create_removeHmcHearingTask_noLipFastClaimUnspec() {
        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("hearingDate", "22-12-2024");
        data.put("courtPermissionNeeded", "YES");
        data.put("confirmOrderGivesPermission", "YES");
        data.put("isDiscontinuingAgainstBothDefendants", "YES");
        data.put("typeOfDiscontinuance", "FULL_DISCONTINUANCE");
        data.put("allocatedTrack", "FAST_CLAIM");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "VALIDATE_DISCONTINUE_CLAIM_CLAIMANT");
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("RemoveHMCHearing"));
        assertThat(workTypeResultList
                       .get(0).get("name"), is("Remove Hearing - HMC"));
    }

    @Test
    void claim_is_discontinued_create_removeHmcHearingTask_noLipSmallClaimSpec() {
        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("hearingDate", "22-12-2024");
        data.put("courtPermissionNeeded", "YES");
        data.put("confirmOrderGivesPermission", "YES");
        data.put("isDiscontinuingAgainstBothDefendants", "YES");
        data.put("typeOfDiscontinuance", "FULL_DISCONTINUANCE");
        data.put("responseClaimTrack", "SMALL_CLAIM");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "VALIDATE_DISCONTINUE_CLAIM_CLAIMANT");
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("RemoveHMCHearing"));
        assertThat(workTypeResultList
                       .get(0).get("name"), is("Remove Hearing - HMC"));
    }

    @Test
    void claim_is_discontinued_create_removeHmcHearingTask_noLipFastClaimSpec() {
        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("hearingDate", "22-12-2024");
        data.put("courtPermissionNeeded", "YES");
        data.put("confirmOrderGivesPermission", "YES");
        data.put("isDiscontinuingAgainstBothDefendants", "YES");
        data.put("typeOfDiscontinuance", "FULL_DISCONTINUANCE");
        data.put("responseClaimTrack", "FAST_CLAIM");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "VALIDATE_DISCONTINUE_CLAIM_CLAIMANT");
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("RemoveHMCHearing"));
        assertThat(workTypeResultList
                       .get(0).get("name"), is("Remove Hearing - HMC"));
    }

    @Test
    void discontinuance_create_RemoveHmcHearingTask_noLipSmallClaimUnspec() {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("hearingDate", "22-12-2024");
        data.put("isDiscontinuingAgainstBothDefendants", "YES");
        data.put("allocatedTrack", "SMALL_CLAIM");
        data.put("courtPermissionNeeded", "NO");
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
                       .get(0).get("taskId"), is("RemoveHMCHearing"));
        assertThat(workTypeResultList
                       .get(0).get("name"), is("Remove Hearing - HMC"));
    }

    @Test
    void discontinuance_create_RemoveHmcHearingTask_noLipFastClaimUnspec() {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("hearingDate", "22-12-2024");
        data.put("isDiscontinuingAgainstBothDefendants", "YES");
        data.put("allocatedTrack", "FAST_CLAIM");
        data.put("courtPermissionNeeded", "NO");
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
                       .get(0).get("taskId"), is("RemoveHMCHearing"));
        assertThat(workTypeResultList
                       .get(0).get("name"), is("Remove Hearing - HMC"));
    }

    @Test
    void discontinuance_create_RemoveHmcHearingTask_noLipSmallClaimSpec() {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("hearingDate", "22-12-2024");
        data.put("isDiscontinuingAgainstBothDefendants", "YES");
        data.put("responseClaimTrack", "SMALL_CLAIM");
        data.put("courtPermissionNeeded", "NO");
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
                       .get(0).get("taskId"), is("RemoveHMCHearing"));
        assertThat(workTypeResultList
                       .get(0).get("name"), is("Remove Hearing - HMC"));
    }

    @Test
    void discontinuance_create_RemoveHmcHearingTask_noLipFastClaimSpec() {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("hearingDate", "22-12-2024");
        data.put("isDiscontinuingAgainstBothDefendants", "YES");
        data.put("responseClaimTrack", "FAST_CLAIM");
        data.put("courtPermissionNeeded", "NO");
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
                       .get(0).get("taskId"), is("RemoveHMCHearing"));
        assertThat(workTypeResultList
                       .get(0).get("name"), is("Remove Hearing - HMC"));
    }

    @Test
    void generateDirectionsOrder_createOrderReviewTask_anyEndState() {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "CE_B2");

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "GENERATE_DIRECTIONS_ORDER");
        inputVariables.putValue("postEventState", "HEARING_READINESS");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("reviewOrder"));
        assertThat(workTypeResultList
                       .get(0).get("name"), is("Order Made - Review case"));
    }

    @ParameterizedTest
    @CsvSource({
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION, FAST_CLAIM,, false, true,,, Reschedule a Fast Track Hearing - HMC",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION, FAST_CLAIM,, true, false,,, Reschedule a Fast Track Hearing - HMC",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION, FAST_CLAIM,, false, false,,, Reschedule a Fast Track Hearing - HMC",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,, FAST_CLAIM, false, true,,, Reschedule a Fast Track Hearing - HMC",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,, FAST_CLAIM, true, false,,, Reschedule a Fast Track Hearing - HMC",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,, FAST_CLAIM, false, false,,, Reschedule a Fast Track Hearing - HMC",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION, SMALL_CLAIM,, false, true,,, Reschedule a Small Claims Hearing - HMC",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION, SMALL_CLAIM,, true, false,,, Reschedule a Small Claims Hearing - HMC",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION, SMALL_CLAIM,, false, false,,, Reschedule a Small Claims Hearing - HMC",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,, SMALL_CLAIM, false, true,,, Reschedule a Small Claims Hearing - HMC",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,, SMALL_CLAIM, true, false,,, Reschedule a Small Claims Hearing - HMC",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,, SMALL_CLAIM, false, false,,, Reschedule a Small Claims Hearing - HMC",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,,, false, true, DISPOSAL,, Reschedule a Disposal Hearing - HMC",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,,, true, false, DISPOSAL,, Reschedule a Disposal Hearing - HMC",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,,, false, false, DISPOSAL,, Reschedule a Disposal Hearing - HMC",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,,, false, true,, DISPOSAL_HEARING, Reschedule a Disposal Hearing - HMC",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,,, true, false,, DISPOSAL_HEARING, Reschedule a Disposal Hearing - HMC",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,,, false, false,, DISPOSAL_HEARING, Reschedule a Disposal Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION, FAST_CLAIM,, false, true,,, Reschedule a Fast Track Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION, FAST_CLAIM,, true, false,,, Reschedule a Fast Track Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION, FAST_CLAIM,, false, false,,, Reschedule a Fast Track Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION,, FAST_CLAIM, false, true,,, Reschedule a Fast Track Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION,, FAST_CLAIM, true, false,,, Reschedule a Fast Track Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION,, FAST_CLAIM, false, false,,, Reschedule a Fast Track Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION, SMALL_CLAIM,, false, true,,, Reschedule a Small Claims Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION, SMALL_CLAIM,, true, false,,, Reschedule a Small Claims Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION, SMALL_CLAIM,, false, false,,, Reschedule a Small Claims Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION,, SMALL_CLAIM, false, true,,, Reschedule a Small Claims Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION,, SMALL_CLAIM, true, false,,, Reschedule a Small Claims Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION,, SMALL_CLAIM, false, false,,, Reschedule a Small Claims Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION,,, false, true, DISPOSAL,, Reschedule a Disposal Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION,,, true, false, DISPOSAL,, Reschedule a Disposal Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION,,, false, false, DISPOSAL,, Reschedule a Disposal Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION,,, false, true,, DISPOSAL_HEARING, Reschedule a Disposal Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION,,, true, false,, DISPOSAL_HEARING, Reschedule a Disposal Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION,,, false, false,, DISPOSAL_HEARING, Reschedule a Disposal Hearing - HMC"
    })
    void given_input_should_return_correct_scheduleHmcHearingTask_createSdoEvent_ea_lip(
        String eventId,
        String postEventState,
        String allocatedTrack,
        String responseClaimTrack,
        boolean applicant1Represented,
        boolean respondent1Represented,
        String orderType,
        String caseManagementOrderSelection,
        String expectedTaskName
    ) {

        Map<String, Object> data = new HashMap<>();
        boolean generateDirectionsOrder = eventId.equals("GENERATE_DIRECTIONS_ORDER");
        if (generateDirectionsOrder) {
            addNonNullField(data, "finalOrderFurtherHearingToggle", "SHOW");
        }

        addNonNullField(data, "applicant1Represented", applicant1Represented);
        addNonNullField(data, "respondent1Represented", respondent1Represented);
        addNonNullField(data, "allocatedTrack", allocatedTrack);
        addNonNullField(data, "responseClaimTrack", responseClaimTrack);
        addNonNullField(data, "orderType", orderType);
        addNonNullField(data, "caseManagementOrderSelection", caseManagementOrderSelection);

        Map<String, Object> caseData = Map.of("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", postEventState);
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        int resultSizeList = generateDirectionsOrder ? 2 : 1;
        assertThat(workTypeResultList.size(), is(resultSizeList));
        assertThat(workTypeResultList.get(resultSizeList - 1).get("name"), is(expectedTaskName));
        assertThat(workTypeResultList.get(resultSizeList - 1).get("taskId"), is("ScheduleHMCHearing"));
    }

    @ParameterizedTest
    @CsvSource({
        "CREATE_SDO, CASE_PROGRESSION, FAST_CLAIM,, false, true,,, Schedule a Fast Track Hearing - HMC",
        "CREATE_SDO, CASE_PROGRESSION, FAST_CLAIM,, true, false,,, Schedule a Fast Track Hearing - HMC",
        "CREATE_SDO, CASE_PROGRESSION, FAST_CLAIM,, false, false,,, Schedule a Fast Track Hearing - HMC",
        "CREATE_SDO, CASE_PROGRESSION,, FAST_CLAIM, false, true,,, Schedule a Fast Track Hearing - HMC",
        "CREATE_SDO, CASE_PROGRESSION,, FAST_CLAIM, false, true,,, Schedule a Fast Track Hearing - HMC",
        "CREATE_SDO, CASE_PROGRESSION,, FAST_CLAIM, false, false,,, Schedule a Fast Track Hearing - HMC",
        "CREATE_SDO, CASE_PROGRESSION, SMALL_CLAIM,, false, true,,, Schedule a Small Claims Hearing - HMC",
        "CREATE_SDO, CASE_PROGRESSION, SMALL_CLAIM,, true, false,,, Schedule a Small Claims Hearing - HMC",
        "CREATE_SDO, CASE_PROGRESSION, SMALL_CLAIM,, false, false,,, Schedule a Small Claims Hearing - HMC",
        "CREATE_SDO, CASE_PROGRESSION,, SMALL_CLAIM, false, true,,, Schedule a Small Claims Hearing - HMC",
        "CREATE_SDO, CASE_PROGRESSION,, SMALL_CLAIM, false, true,,, Schedule a Small Claims Hearing - HMC",
        "CREATE_SDO, CASE_PROGRESSION,, SMALL_CLAIM, false, false,,, Schedule a Small Claims Hearing - HMC",
        "CREATE_SDO, CASE_PROGRESSION,,, false, true, DISPOSAL,, Schedule a Disposal Hearing - HMC",
        "CREATE_SDO, CASE_PROGRESSION,,, false, true, DISPOSAL,, Schedule a Disposal Hearing - HMC",
        "CREATE_SDO, CASE_PROGRESSION,,, false, false, DISPOSAL,, Schedule a Disposal Hearing - HMC",
    })
    void given_input_should_return_correct_scheduleHmcHearingTask_lip(
        String eventId,
        String postEventState,
        String allocatedTrack,
        String responseClaimTrack,
        boolean applicant1Represented,
        boolean respondent1Represented,
        String orderType,
        String caseManagementOrderSelection,
        String expectedTaskName
    ) {

        Map<String, Object> data = new HashMap<>();

        addNonNullField(data, "applicant1Represented", applicant1Represented);
        addNonNullField(data, "respondent1Represented", respondent1Represented);
        addNonNullField(data, "allocatedTrack", allocatedTrack);
        addNonNullField(data, "responseClaimTrack", responseClaimTrack);
        addNonNullField(data, "orderType", orderType);
        addNonNullField(data, "caseManagementOrderSelection", caseManagementOrderSelection);
        data.put("eaCourtLocation", "true");

        Map<String, Object> caseData = Map.of("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", postEventState);
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is(expectedTaskName));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ScheduleHMCHearing"));
    }

    @ParameterizedTest
    @CsvSource({
        "MANAGE_STAY, CASE_PROGRESSION, FAST_CLAIM,, false, true,,, Schedule a Fast Track Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION, FAST_CLAIM,, true, false,,, Schedule a Fast Track Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION, FAST_CLAIM,, false, false,,, Schedule a Fast Track Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION,, FAST_CLAIM, false, true,,, Schedule a Fast Track Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION,, FAST_CLAIM, true,false,,, Schedule a Fast Track Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION,, FAST_CLAIM, false, false,,, Schedule a Fast Track Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION, SMALL_CLAIM,, false, true,,, Schedule a Small Claims Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION, SMALL_CLAIM,, true, false,,, Schedule a Small Claims Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION, SMALL_CLAIM,, false, false,,, Schedule a Small Claims Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION,, SMALL_CLAIM, false, true,,, Schedule a Small Claims Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION,, SMALL_CLAIM, true, false,,, Schedule a Small Claims Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION,, SMALL_CLAIM, false, false,,, Schedule a Small Claims Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION, FAST_CLAIM,,false, true, DISPOSAL,, Schedule a Disposal Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION, SMALL_CLAIM,,false, true, DISPOSAL,, Schedule a Disposal Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION, FAST_CLAIM,, true, false, DISPOSAL,, Schedule a Disposal Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION, SMALL_CLAIM,, true, false, DISPOSAL,, Schedule a Disposal Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION, FAST_CLAIM,, false, false, DISPOSAL,, Schedule a Disposal Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION, SMALL_CLAIM,, false, false, DISPOSAL,, Schedule a Disposal Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION,, FAST_CLAIM, false, true,, DISPOSAL_HEARING, Schedule a Disposal Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION,, SMALL_CLAIM, false, true,, DISPOSAL_HEARING, Schedule a Disposal Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION,, FAST_CLAIM, true, false,, DISPOSAL_HEARING, Schedule a Disposal Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION,, SMALL_CLAIM, true, false,, DISPOSAL_HEARING, Schedule a Disposal Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION,, SMALL_CLAIM, false, false,, DISPOSAL_HEARING, Schedule a Disposal Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION,, FAST_CLAIM, false, false,, DISPOSAL_HEARING, Schedule a Disposal Hearing - HMC"
    })
    void given_input_should_return_correct_scheduleHmcHearingTask_manageStay_lip(
        String eventId,
        String postEventState,
        String allocatedTrack,
        String responseClaimTrack,
        boolean applicant1Represented,
        boolean respondent1Represented,
        String orderType,
        String caseManagementOrderSelection,
        String expectedTaskName
    ) {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "CE_B2");
        addNonNullField(data, "applicant1Represented", applicant1Represented);
        addNonNullField(data, "respondent1Represented", respondent1Represented);
        addNonNullField(data, "allocatedTrack", allocatedTrack);
        addNonNullField(data, "responseClaimTrack", responseClaimTrack);
        addNonNullField(data, "orderType", orderType);
        addNonNullField(data, "caseManagementOrderSelection", caseManagementOrderSelection);

        Map<String, Object> caseData = Map.of("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", postEventState);
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is(expectedTaskName));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ScheduleHMCHearing"));
    }

    @ParameterizedTest
    @CsvSource({
        "SETTLE_CLAIM, CASE_SETTLED, true, false, 22-12-2024"
    })
    void given_input_should_return_correct_removeHmcHearingTask_lip(
        String eventId,
        String postEventState,
        boolean applicant1Represented,
        boolean respondent1Represented,
        String hearingDate
    ) {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        addNonNullField(data, "hearingDate", hearingDate);
        addNonNullField(data, "applicant1Represented", applicant1Represented);
        addNonNullField(data, "respondent1Represented", respondent1Represented);
        addNonNullField(data,"preStayState", "AWAITING_RESPONDENT_ACKNOWLEDGEMENT");
        Map<String, Object> caseData = Map.of("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", postEventState);
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Remove Hearing - HMC"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("RemoveHMCHearing"));

    }

    @ParameterizedTest
    @CsvSource({
        "SETTLE_CLAIM_MARK_PAID_FULL, CASE_STAYED, false, true, 22-12-2024",
        "SETTLE_CLAIM_MARK_PAID_FULL, CASE_STAYED, true, false, 22-12-2024",
        "SETTLE_CLAIM_MARK_PAID_FULL, CASE_STAYED, false, false, 22-12-2024"
    })
    void given_input_should_return_correct_removeHearingTask(
        String eventId,
        String postEventState,
        boolean applicant1Represented,
        boolean respondent1Represented,
        String hearingDate
    ) {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        addNonNullField(data, "hearingDate", hearingDate);
        addNonNullField(data, "applicant1Represented", applicant1Represented);
        addNonNullField(data, "respondent1Represented", respondent1Represented);
        addNonNullField(data,"preStayState", "AWAITING_RESPONDENT_ACKNOWLEDGEMENT");
        Map<String, Object> caseData = Map.of("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", postEventState);
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Remove Hearing - HMC"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("RemoveHMCHearing"));

    }

    @ParameterizedTest
    @CsvSource({
        "false, true",
        "true, false",
        "false, false",
    })
    void claim_is_discontinued_create_removeHmcHearingTask_lip(
        boolean applicant1Represented,
        boolean respondent1Represented) {
        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("hearingDate", "22-12-2024");
        data.put("courtPermissionNeeded", "YES");
        data.put("confirmOrderGivesPermission", "YES");
        data.put("isDiscontinuingAgainstBothDefendants", "YES");
        data.put("typeOfDiscontinuance", "FULL_DISCONTINUANCE");
        addNonNullField(data, "applicant1Represented", applicant1Represented);
        addNonNullField(data, "respondent1Represented", respondent1Represented);

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "VALIDATE_DISCONTINUE_CLAIM_CLAIMANT");
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("RemoveHMCHearing"));
        assertThat(workTypeResultList
                       .get(0).get("name"), is("Remove Hearing - HMC"));
    }

    @ParameterizedTest
    @CsvSource({
        "false, true",
        "true, false",
        "false, false",
    })
    void discontinuance_create_RemoveHmcHearingTask_lip(
        boolean applicant1Represented,
        boolean respondent1Represented) {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("hearingDate", "22-12-2024");
        data.put("isDiscontinuingAgainstBothDefendants", "YES");
        data.put("courtPermissionNeeded", "NO");
        data.put("typeOfDiscontinuance", "FULL_DISCONTINUANCE");
        addNonNullField(data, "applicant1Represented", applicant1Represented);
        addNonNullField(data, "respondent1Represented", respondent1Represented);

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "DISCONTINUE_CLAIM_CLAIMANT");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("RemoveHMCHearing"));
        assertThat(workTypeResultList
                       .get(0).get("name"), is("Remove Hearing - HMC"));
    }

    @ParameterizedTest
    @CsvSource({
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION, FAST_CLAIM,, false, true,,, Reschedule a Fast Track Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION, FAST_CLAIM,, true, false,,, Reschedule a Fast Track Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION, FAST_CLAIM,, false, false,,, Reschedule a Fast Track Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION,, FAST_CLAIM, false, true,,, Reschedule a Fast Track Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION,, FAST_CLAIM, true, false,,, Reschedule a Fast Track Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION,, FAST_CLAIM, false, false,,, Reschedule a Fast Track Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION, SMALL_CLAIM,, false, true,,, Reschedule a Small Claims Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION, SMALL_CLAIM,, true, false,,, Reschedule a Small Claims Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION, SMALL_CLAIM,, false, false,,, Reschedule a Small Claims Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION,, SMALL_CLAIM, false, true,,, Reschedule a Small Claims Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION,, SMALL_CLAIM, true, false,,, Reschedule a Small Claims Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION,, SMALL_CLAIM, false, false,,, Reschedule a Small Claims Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION,,, false, true, DISPOSAL,, Reschedule a Disposal Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION,,, true, false, DISPOSAL,, Reschedule a Disposal Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION,,, false, false, DISPOSAL,, Reschedule a Disposal Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION,,, false, true,, DISPOSAL_HEARING, Reschedule a Disposal Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION,,, true, false,, DISPOSAL_HEARING, Reschedule a Disposal Hearing - HMC",
        "HEARING_SCHEDULED_RETRIGGER, CASE_PROGRESSION,,, false, false,, DISPOSAL_HEARING, Reschedule a Disposal Hearing - HMC"
    })
    void given_input_should_return_correct_scheduleHmcHearingTask_hearingScheduledRetrigger_lipEnabled(
        String eventId,
        String postEventState,
        String allocatedTrack,
        String responseClaimTrack,
        boolean applicant1Represented,
        boolean respondent1Represented,
        String orderType,
        String caseManagementOrderSelection,
        String expectedTaskName
    ) {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "HMC_LIP");
        addNonNullField(data, "applicant1Represented", applicant1Represented);
        addNonNullField(data, "respondent1Represented", respondent1Represented);
        addNonNullField(data, "allocatedTrack", allocatedTrack);
        addNonNullField(data, "responseClaimTrack", responseClaimTrack);
        addNonNullField(data, "orderType", orderType);
        addNonNullField(data, "caseManagementOrderSelection", caseManagementOrderSelection);

        Map<String, Object> caseData = Map.of("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", postEventState);
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is(expectedTaskName));

        assertThat(workTypeResultList.get(0).get("taskId"), is("ScheduleHMCHearing"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION, FAST_CLAIM,, true, true,,, Reschedule a Fast Track Hearing - HMC, SHOW",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION, FAST_CLAIM,, false, true,,, Reschedule a Fast Track Hearing - HMC, SHOW",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION, FAST_CLAIM,, false, false,,, Reschedule a Fast Track Hearing - HMC, SHOW",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,, FAST_CLAIM, true, true,,, Reschedule a Fast Track Hearing - HMC, SHOW",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,, FAST_CLAIM, false, true,,, Reschedule a Fast Track Hearing - HMC, SHOW",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,, FAST_CLAIM, false, false,,, Reschedule a Fast Track Hearing - HMC, SHOW",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION, SMALL_CLAIM,, true, true,,, Reschedule a Small Claims Hearing - HMC, SHOW",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION, SMALL_CLAIM,, false, true,,, Reschedule a Small Claims Hearing - HMC, SHOW",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION, SMALL_CLAIM,, false, false,,, Reschedule a Small Claims Hearing - HMC, SHOW",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,, SMALL_CLAIM, true, true,,, Reschedule a Small Claims Hearing - HMC, SHOW",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,, SMALL_CLAIM, false, true,,, Reschedule a Small Claims Hearing - HMC, SHOW",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,, SMALL_CLAIM, false, false,,, Reschedule a Small Claims Hearing - HMC, SHOW",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,,, true, true, DISPOSAL,, Reschedule a Disposal Hearing - HMC, SHOW",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,,, false, true, DISPOSAL,, Reschedule a Disposal Hearing - HMC, SHOW",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,,, false, false, DISPOSAL,, Reschedule a Disposal Hearing - HMC, SHOW",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,,, true, true,, DISPOSAL_HEARING, Reschedule a Disposal Hearing - HMC, SHOW",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,,, false, true,, DISPOSAL_HEARING, Reschedule a Disposal Hearing - HMC, SHOW",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,,, false, false,, DISPOSAL_HEARING, Reschedule a Disposal Hearing - HMC, SHOW",

        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION, FAST_CLAIM,, true, true,,, Reschedule a Fast Track Hearing - HMC, ",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION, FAST_CLAIM,, false, true,,, Reschedule a Fast Track Hearing - HMC, ",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION, FAST_CLAIM,, false, false,,, Reschedule a Fast Track Hearing - HMC, ",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,, FAST_CLAIM, true, true,,, Reschedule a Fast Track Hearing - HMC, ",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,, FAST_CLAIM, false, true,,, Reschedule a Fast Track Hearing - HMC, ",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,, FAST_CLAIM, false, false,,, Reschedule a Fast Track Hearing - HMC, ",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION, SMALL_CLAIM,, true, true,,, Reschedule a Small Claims Hearing - HMC, ",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION, SMALL_CLAIM,, false, true,,, Reschedule a Small Claims Hearing - HMC, ",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION, SMALL_CLAIM,, false, false,,, Reschedule a Small Claims Hearing - HMC, ",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,, SMALL_CLAIM, true, true,,, Reschedule a Small Claims Hearing - HMC, ",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,, SMALL_CLAIM, false, true,,, Reschedule a Small Claims Hearing - HMC, ",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,, SMALL_CLAIM, false, false,,, Reschedule a Small Claims Hearing - HMC, ",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,,, true, true, DISPOSAL,, Reschedule a Disposal Hearing - HMC,",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,,, false, true, DISPOSAL,, Reschedule a Disposal Hearing - HMC,",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,,, false, false, DISPOSAL,, Reschedule a Disposal Hearing - HMC,",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,,, true, true,, DISPOSAL_HEARING, Reschedule a Disposal Hearing - HMC, ",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,,, false, true,, DISPOSAL_HEARING, Reschedule a Disposal Hearing - HMC, ",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,,, false, false,, DISPOSAL_HEARING, Reschedule a Disposal Hearing - HMC, "
    })
    void given_input_should_return_correct_rescheduleHmcHearingTask_generateDirectionsOrder_caseEventBatch2(
        String eventId,
        String postEventState,
        String allocatedTrack,
        String responseClaimTrack,
        boolean applicant1Represented,
        boolean respondent1Represented,
        String orderType,
        String caseManagementOrderSelection,
        String expectedTaskName,
        String furtherHearingRequired
    ) {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "CE_B2");
        addNonNullField(data, "applicant1Represented", applicant1Represented);
        addNonNullField(data, "respondent1Represented", respondent1Represented);
        addNonNullField(data, "allocatedTrack", allocatedTrack);
        addNonNullField(data, "responseClaimTrack", responseClaimTrack);
        addNonNullField(data, "orderType", orderType);
        addNonNullField(data, "caseManagementOrderSelection", caseManagementOrderSelection);
        addNonNullField(data, "finalOrderFurtherHearingToggle", furtherHearingRequired);

        Map<String, Object> caseData = Map.of("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", postEventState);
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        String taskId = "ScheduleHMCHearing";
        if (nonNull(furtherHearingRequired)) {
            assertThat(workTypeResultList.size(), is(2));
            assertThat(workTypeResultList.get(0).get("name"), is("Order Made - Review case"));
            assertThat(workTypeResultList.get(0).get("taskId"), is("reviewOrder"));
            assertThat(workTypeResultList.get(1).get("name"), is(expectedTaskName));
            assertThat(workTypeResultList.get(1).get("taskId"), is(taskId));
        } else {
            assertThat(workTypeResultList.size(), is(1));
            assertThat(workTypeResultList.get(0).get("name"), is("Order Made - Review case"));
            assertThat(workTypeResultList.get(0).get("taskId"), is("reviewOrder"));
        }
    }

    @ParameterizedTest
    @CsvSource({
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION, FAST_CLAIM,, false, true,,, Reschedule a Fast Track Hearing - HMC, SHOW",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION, FAST_CLAIM,, true, false,,, Reschedule a Fast Track Hearing - HMC, SHOW",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION, FAST_CLAIM,, false, false,,, Reschedule a Fast Track Hearing - HMC, SHOW",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,, FAST_CLAIM, false, true,,, Reschedule a Fast Track Hearing - HMC, SHOW",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,, FAST_CLAIM, true, false,,, Reschedule a Fast Track Hearing - HMC, SHOW",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,, FAST_CLAIM, false, false,,, Reschedule a Fast Track Hearing - HMC, SHOW",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION, SMALL_CLAIM,, false, true,,, Reschedule a Small Claims Hearing - HMC, SHOW",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION, SMALL_CLAIM,, true, false,,, Reschedule a Small Claims Hearing - HMC, SHOW",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION, SMALL_CLAIM,, false, false,,, Reschedule a Small Claims Hearing - HMC, SHOW",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,, SMALL_CLAIM, false, true,,, Reschedule a Small Claims Hearing - HMC, SHOW",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,, SMALL_CLAIM, true, false,,, Reschedule a Small Claims Hearing - HMC, SHOW",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,, SMALL_CLAIM, false, false,,, Reschedule a Small Claims Hearing - HMC, SHOW",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,,, false, true, DISPOSAL,, Reschedule a Disposal Hearing - HMC, true",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,,, true, false, DISPOSAL,, Reschedule a Disposal Hearing - HMC, true",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,,, false, false, DISPOSAL,, Reschedule a Disposal Hearing - HMC, true",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,,, false, true,, DISPOSAL_HEARING, Reschedule a Disposal Hearing - HMC, true",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,,, true, false,, DISPOSAL_HEARING, Reschedule a Disposal Hearing - HMC, true",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,,, false, false,, DISPOSAL_HEARING, Reschedule a Disposal Hearing - HMC, true",

        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION, FAST_CLAIM,, false, true,,, Reschedule a Fast Track Hearing, ",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION, FAST_CLAIM,, true, false,,, Reschedule a Fast Track Hearing, ",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION, FAST_CLAIM,, false, false,,, Reschedule a Fast Track Hearing, ",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,, FAST_CLAIM, false, true,,, Reschedule a Fast Track Hearing, ",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,, FAST_CLAIM, true, false,,, Reschedule a Fast Track Hearing, ",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,, FAST_CLAIM, false, false,,, Reschedule a Fast Track Hearing, ",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION, SMALL_CLAIM,, false, true,,, Reschedule a Small Claims Hearing, ",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION, SMALL_CLAIM,, true, false,,, Reschedule a Small Claims Hearing, ",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION, SMALL_CLAIM,, false, false,,, Reschedule a Small Claims Hearing, ",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,, SMALL_CLAIM, false, true,,, Reschedule a Small Claims Hearing, ",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,, SMALL_CLAIM, true, false,,, Reschedule a Small Claims Hearing, ",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,, SMALL_CLAIM, false, false,,, Reschedule a Small Claims Hearing, ",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,,, false, true, DISPOSAL,, Reschedule a Disposal Hearing,",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,,, true, false, DISPOSAL,, Reschedule a Disposal Hearing,",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,,, false, false, DISPOSAL,, Reschedule a Disposal Hearing,",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,,, false, true,, DISPOSAL_HEARING, Reschedule a Disposal Hearing, ",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,,, true, false,, DISPOSAL_HEARING, Reschedule a Disposal Hearing, ",
        "GENERATE_DIRECTIONS_ORDER, CASE_PROGRESSION,,, false, false,, DISPOSAL_HEARING, Reschedule a Disposal Hearing, "
    })
    void given_input_should_return_correct_rescheduleHmcHearingTask_generateDirectionsOrder_lipEnabled(
        String eventId,
        String postEventState,
        String allocatedTrack,
        String responseClaimTrack,
        boolean applicant1Represented,
        boolean respondent1Represented,
        String orderType,
        String caseManagementOrderSelection,
        String expectedTaskName,
        String furtherHearingRequired
    ) {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "HMC_LIP");
        addNonNullField(data, "applicant1Represented", applicant1Represented);
        addNonNullField(data, "respondent1Represented", respondent1Represented);
        addNonNullField(data, "allocatedTrack", allocatedTrack);
        addNonNullField(data, "responseClaimTrack", responseClaimTrack);
        addNonNullField(data, "orderType", orderType);
        addNonNullField(data, "caseManagementOrderSelection", caseManagementOrderSelection);
        addNonNullField(data, "finalOrderFurtherHearingToggle", furtherHearingRequired);

        Map<String, Object> caseData = Map.of("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", postEventState);
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();
        //This will be 3 until Hmc Lip goes live and pre-existing tasks gets removed.
        if (nonNull(furtherHearingRequired)) {
            assertThat(workTypeResultList.size(), is(2));
            assertThat(workTypeResultList.get(0).get("name"), is("Order Made - Review case"));
            assertThat(workTypeResultList.get(0).get("taskId"), is("reviewOrder"));
            assertThat(workTypeResultList.get(1).get("name"), is(expectedTaskName));
            assertThat(workTypeResultList.get(1).get("taskId"), is("ScheduleHMCHearing"));
        } else {
            assertThat(workTypeResultList.size(), is(1));
            assertThat(workTypeResultList.get(0).get("name"), is("Order Made - Review case"));
            assertThat(workTypeResultList.get(0).get("taskId"), is("reviewOrder"));
        }
    }

    @ParameterizedTest
    @CsvSource({
        "CREATE_SDO, CASE_PROGRESSION, FAST_CLAIM,, false, true,,, Schedule a Fast Track Hearing - HMC, true",
        "CREATE_SDO, CASE_PROGRESSION, FAST_CLAIM,, true, false,,, Schedule a Fast Track Hearing - HMC, true",
        "CREATE_SDO, CASE_PROGRESSION, FAST_CLAIM,, false, false,,, Schedule a Fast Track Hearing - HMC, true",
        "CREATE_SDO, CASE_PROGRESSION,, FAST_CLAIM, false, true,,, Schedule a Fast Track Hearing - HMC, true",
        "CREATE_SDO, CASE_PROGRESSION,, FAST_CLAIM, false, true,,, Schedule a Fast Track Hearing - HMC, true",
        "CREATE_SDO, CASE_PROGRESSION,, FAST_CLAIM, false, false,,, Schedule a Fast Track Hearing - HMC, true",
        "CREATE_SDO, CASE_PROGRESSION, SMALL_CLAIM,, false, true,,, Schedule a Small Claims Hearing - HMC, true",
        "CREATE_SDO, CASE_PROGRESSION, SMALL_CLAIM,, true, false,,, Schedule a Small Claims Hearing - HMC, true",
        "CREATE_SDO, CASE_PROGRESSION, SMALL_CLAIM,, false, false,,, Schedule a Small Claims Hearing - HMC, true",
        "CREATE_SDO, CASE_PROGRESSION,, SMALL_CLAIM, false, true,,, Schedule a Small Claims Hearing - HMC, true",
        "CREATE_SDO, CASE_PROGRESSION,, SMALL_CLAIM, false, true,,, Schedule a Small Claims Hearing - HMC, true",
        "CREATE_SDO, CASE_PROGRESSION,, SMALL_CLAIM, false, false,,, Schedule a Small Claims Hearing - HMC, true",
        "CREATE_SDO, CASE_PROGRESSION,,, false, true, DISPOSAL,, Schedule a Disposal Hearing - HMC, true",
        "CREATE_SDO, CASE_PROGRESSION,,, false, true, DISPOSAL,, Schedule a Disposal Hearing - HMC, true",
        "CREATE_SDO, CASE_PROGRESSION,,, false, false, DISPOSAL,, Schedule a Disposal Hearing - HMC, true",
        "CREATE_SDO, CASE_PROGRESSION, FAST_CLAIM,, false, true,,, Transfer Case Offline, false",
        "CREATE_SDO, CASE_PROGRESSION, FAST_CLAIM,, true, false,,, Transfer Case Offline, false",
        "CREATE_SDO, CASE_PROGRESSION, FAST_CLAIM,, false, false,,, Transfer Case Offline, false",
        "CREATE_SDO, CASE_PROGRESSION,, FAST_CLAIM, false, true,,, Transfer Case Offline, false",
        "CREATE_SDO, CASE_PROGRESSION,, FAST_CLAIM, false, true,,, Transfer Case Offline, false",
        "CREATE_SDO, CASE_PROGRESSION,, FAST_CLAIM, false, false,,, Transfer Case Offline, false",
        "CREATE_SDO, CASE_PROGRESSION, SMALL_CLAIM,, false, true,,, Transfer Case Offline, false",
        "CREATE_SDO, CASE_PROGRESSION, SMALL_CLAIM,, true, false,,, Transfer Case Offline, false",
        "CREATE_SDO, CASE_PROGRESSION, SMALL_CLAIM,, false, false,,, Transfer Case Offline, false",
        "CREATE_SDO, CASE_PROGRESSION,, SMALL_CLAIM, false, true,,, Transfer Case Offline, false",
        "CREATE_SDO, CASE_PROGRESSION,, SMALL_CLAIM, false, true,,, Transfer Case Offline, false",
        "CREATE_SDO, CASE_PROGRESSION,, SMALL_CLAIM, false, false,,, Transfer Case Offline, false",
        "CREATE_SDO, CASE_PROGRESSION,,, false, true, DISPOSAL,, Transfer Case Offline, false",
        "CREATE_SDO, CASE_PROGRESSION,,, false, true, DISPOSAL,, Transfer Case Offline, false",
        "CREATE_SDO, CASE_PROGRESSION,,, false, false, DISPOSAL,, Transfer Case Offline, false",
    })
    void given_input_should_return_correct_scheduleHmcHearingTaskForSdo_EaCourt(
        String eventId,
        String postEventState,
        String allocatedTrack,
        String responseClaimTrack,
        boolean applicant1Represented,
        boolean respondent1Represented,
        String orderType,
        String caseManagementOrderSelection,
        String expectedTaskName,
        boolean isEaCourt
    ) {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "HMC_LIP");
        addNonNullField(data, "applicant1Represented", applicant1Represented);
        addNonNullField(data, "respondent1Represented", respondent1Represented);
        addNonNullField(data, "allocatedTrack", allocatedTrack);
        addNonNullField(data, "responseClaimTrack", responseClaimTrack);
        addNonNullField(data, "orderType", orderType);
        addNonNullField(data, "caseManagementOrderSelection", caseManagementOrderSelection);
        addNonNullField(data, "eaCourtLocation", isEaCourt);

        Map<String, Object> caseData = Map.of("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", postEventState);
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        int expectedTasks = 1;
        int taskToCheck = 0;
        assertThat(workTypeResultList.size(), is(expectedTasks));
        assertThat(workTypeResultList.get(taskToCheck).get("name"), is(expectedTaskName));

        if (isEaCourt) {
            assertThat(workTypeResultList.get(taskToCheck).get("taskId"), is("ScheduleHMCHearing"));
        } else {
            assertThat(workTypeResultList.get(taskToCheck).get("taskId"), is("transferCaseOffline"));
        }
    }

    @ParameterizedTest
    @CsvSource({
        "MANAGE_STAY, CASE_PROGRESSION, FAST_CLAIM,, false, true,,, Schedule a Fast Track Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION, FAST_CLAIM,, true, false,,, Schedule a Fast Track Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION, FAST_CLAIM,, false, false,,, Schedule a Fast Track Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION,, FAST_CLAIM, false, true,,, Schedule a Fast Track Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION,, FAST_CLAIM, true,false,,, Schedule a Fast Track Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION,, FAST_CLAIM, false, false,,, Schedule a Fast Track Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION, SMALL_CLAIM,, false, true,,, Schedule a Small Claims Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION, SMALL_CLAIM,, true, false,,, Schedule a Small Claims Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION, SMALL_CLAIM,, false, false,,, Schedule a Small Claims Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION,, SMALL_CLAIM, false, true,,, Schedule a Small Claims Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION,, SMALL_CLAIM, true, false,,, Schedule a Small Claims Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION,, SMALL_CLAIM, false, false,,, Schedule a Small Claims Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION, FAST_CLAIM,,false, true, DISPOSAL,, Schedule a Disposal Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION, SMALL_CLAIM,, true, false, DISPOSAL,, Schedule a Disposal Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION,, FAST_CLAIM, false, false, DISPOSAL,, Schedule a Disposal Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION, FAST_CLAIM,, false, true,, DISPOSAL_HEARING, Schedule a Disposal Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION, SMALL_CLAIM,, true, false,, DISPOSAL_HEARING, Schedule a Disposal Hearing - HMC",
        "MANAGE_STAY, CASE_PROGRESSION,,SMALL_CLAIM, false, false,, DISPOSAL_HEARING, Schedule a Disposal Hearing - HMC"
    })
    void given_input_should_return_correct_scheduleHmcHearingTaskWhen_manageStay_and_hmcLipEnabled(
        String eventId,
        String postEventState,
        String allocatedTrack,
        String responseClaimTrack,
        boolean applicant1Represented,
        boolean respondent1Represented,
        String orderType,
        String caseManagementOrderSelection,
        String expectedTaskName
    ) {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "HMC_LIP");
        addNonNullField(data, "applicant1Represented", applicant1Represented);
        addNonNullField(data, "respondent1Represented", respondent1Represented);
        addNonNullField(data, "allocatedTrack", allocatedTrack);
        addNonNullField(data, "responseClaimTrack", responseClaimTrack);
        addNonNullField(data, "orderType", orderType);
        addNonNullField(data, "caseManagementOrderSelection", caseManagementOrderSelection);

        Map<String, Object> caseData = Map.of("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", postEventState);
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        int resultList = 1;
        assertThat(workTypeResultList.size(), is(resultList));
        assertThat(workTypeResultList.get(resultList - 1).get("name"), is(expectedTaskName));

        assertThat(workTypeResultList.get(resultList - 1).get("taskId"), is("ScheduleHMCHearing"));
    }

    @ParameterizedTest
    @CsvSource({
        "false, false",
        "true, false",
        "false, true"
    })
    void given_HearingFeeUnpaid_should_return_correct_removeHmcHearingTask_whenlipHmcEnabled(boolean applicant1Represented,
                                                                                             boolean respondent1Represented) {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "HMC_LIP");
        addNonNullField(data, "hearingDate", "22-12-2024");
        addNonNullField(data, "applicant1Represented", applicant1Represented);
        addNonNullField(data, "respondent1Represented", respondent1Represented);

        Map<String, Object> caseData = Map.of("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "HEARING_FEE_UNPAID");
        inputVariables.putValue("postEventState", "CASE_DISMISSED");
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();
        assertThat(workTypeResultList.size(), is(1));

        assertThat(workTypeResultList.get(0).get("name"), is("Remove Hearing - HMC"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("RemoveHMCHearing"));
    }

    @ParameterizedTest
    @CsvSource({
        "SETTLE_CLAIM_MARK_PAID_FULL, CASE_STAYED, false, true, 22-12-2024",
        "SETTLE_CLAIM_MARK_PAID_FULL, CASE_STAYED, true, false, 22-12-2024",
        "SETTLE_CLAIM_MARK_PAID_FULL, CASE_STAYED, false, false, 22-12-2024",
        "LIP_CLAIM_SETTLED, CASE_SETTLED, false, true, 22-12-2024",
        "SETTLE_CLAIM, CASE_SETTLED, true, false, 22-12-2024",
        "LIP_CLAIM_SETTLED, CASE_SETTLED, false, false, 22-12-2024"
    })
    void given_input_should_return_correct_removeHmcHearingTask_whenHmcLiPEnabled(
        String eventId,
        String postEventState,
        boolean applicant1Represented,
        boolean respondent1Represented,
        String hearingDate
    ) {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        addNonNullField(data, "hearingDate", hearingDate);
        addNonNullField(data, "applicant1Represented", applicant1Represented);
        addNonNullField(data, "respondent1Represented", respondent1Represented);
        addNonNullField(data, "responseClaimTrack", "FAST_CLAIM");
        addNonNullField(data, "preStayState", "AWAITING_RESPONDENT_ACKNOWLEDGEMENT");
        Map<String, Object> caseData = Map.of("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", postEventState);
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));

        assertThat(workTypeResultList.get(0).get("name"), is("Remove Hearing - HMC"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("RemoveHMCHearing"));
    }

    @ParameterizedTest
    @CsvSource({
        "false, true",
        "true, false",
        "false, false"
    })
    void claim_is_discontinued_create_removeHmcHearingTask_hmcLiPEnabled(
        boolean applicant1Represented,
        boolean respondent1Represented) {
        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("hearingDate", "22-12-2024");
        data.put("courtPermissionNeeded", "YES");
        data.put("confirmOrderGivesPermission", "YES");
        data.put("isDiscontinuingAgainstBothDefendants", "YES");
        data.put("typeOfDiscontinuance", "FULL_DISCONTINUANCE");
        addNonNullField(data, "applicant1Represented", applicant1Represented);
        addNonNullField(data, "respondent1Represented", respondent1Represented);

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "VALIDATE_DISCONTINUE_CLAIM_CLAIMANT");
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Remove Hearing - HMC"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("RemoveHMCHearing"));
    }

    @ParameterizedTest
    @CsvSource({
        "HEARING_FEE_UNPAID, CASE_DISMISSED, MULTI_CLAIM, , , removeHearing",
        "VALIDATE_DISCONTINUE_CLAIM_CLAIMANT, , MULTI_CLAIM, , validateDiscontinuance, removeHearing",
        "DISCONTINUE_CLAIM_CLAIMANT, , MULTI_CLAIM, discontinuanceType, , removeHearing",
        "SETTLE_CLAIM_MARK_PAID_FULL, CASE_STAYED, MULTI_CLAIM, , , removeHearing",
        "SETTLE_CLAIM, CASE_SETTLED, MULTI_CLAIM, , , removeHearing",
    })
    void given_input_should_return_remove_hearing_minti_unspec(String eventName, String postState, String multiOrIntermediate,
                                                               String discontinuanceType, String validateDiscontinuance, String taskId) {
        Map<String, Object> data = new HashMap<>();
        data.put("hearingDate", "22-12-2024");
        data.put("allocatedTrack", multiOrIntermediate);
        data.put("preStayState", "AWAITING_RESPONDENT_ACKNOWLEDGEMENT");

        if (nonNull(discontinuanceType)) {
            data.put("isDiscontinuingAgainstBothDefendants", "YES");
            data.put("courtPermissionNeeded", "NO");
            data.put("typeOfDiscontinuance", "FULL_DISCONTINUANCE");
        }
        if (nonNull(validateDiscontinuance)) {
            data.put("courtPermissionNeeded", "YES");
            data.put("confirmOrderGivesPermission", "YES");
            data.put("isDiscontinuingAgainstBothDefendants", "YES");
            data.put("typeOfDiscontinuance", "FULL_DISCONTINUANCE");
        }

        Map<String, Object> caseData = Map.of("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventName);
        if (nonNull(postState)) {
            inputVariables.putValue("postEventState", postState);
        }
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("removeHearing"));
        assertThat(workTypeResultList
                       .get(0).get("name"), is("Remove Hearing"));
    }

    @ParameterizedTest
    @CsvSource({
        "false, true",
        "true, false",
        "false, false"
    })
    void discontinuance_create_RemoveHmcHearingTask_hmcLiPEnabled(
        boolean applicant1Represented,
        boolean respondent1Represented) {

        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "SD");
        data.put("hearingDate", "22-12-2024");
        data.put("isDiscontinuingAgainstBothDefendants", "YES");
        data.put("courtPermissionNeeded", "NO");
        data.put("typeOfDiscontinuance", "FULL_DISCONTINUANCE");
        addNonNullField(data, "applicant1Represented", applicant1Represented);
        addNonNullField(data, "respondent1Represented", respondent1Represented);

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "DISCONTINUE_CLAIM_CLAIMANT");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("RemoveHMCHearing"));
        assertThat(workTypeResultList
                       .get(0).get("name"), is("Remove Hearing - HMC"));

        assertThat(workTypeResultList.size(), is(1));
    }

    private void addNonNullField(Map<String, Object> inputVars, String key, Object value) {
        if (value == null || (value instanceof String && ((String) value).trim().isEmpty())) {
            return;
        }
        inputVars.put(key, value);
    }

    @Test
    void given_input_should_return_take_case_offline_for_caseworker() {

        VariableMap inputVariables = new VariableMapImpl();
        Map<String, Object> data = new HashMap<>();
        data.put("gaEaCourtLocation", "true");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        inputVariables.putValue("eventId", "TRIGGER_UPDATE_GA_LOCATION");
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();
        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Take Case Offline - Non EA Court"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("takeCaseOfflineApplicationNonEA"));
    }

    @Test
    void given_input_should_return_take_case_offline_for_caseworker_when_Transfer_case_online_event() {

        VariableMap inputVariables = new VariableMapImpl();
        Map<String, Object> data = new HashMap<>();
        data.put("gaEaCourtLocation", "true");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        inputVariables.putValue("eventId", "TRIGGER_TASK_RECONFIG_GA");
        inputVariables.putValue("postEventState", "CASE_PROGRESSION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();
        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Take Case Offline - Non EA Court"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("takeCaseOfflineApplicationNonEA"));
    }

    @Test
    void given_input_should_return_take_case_offline_for_caseworker_whenGaIsVaryJudgement() {

        VariableMap inputVariables = new VariableMapImpl();

        inputVariables.putValue("eventId", "TRIGGER_MAIN_CASE_FROM_GA");
        inputVariables.putValue("postEventState", "JUDICIAL_REFERRAL");
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();
        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Take Case Offline - determination to be completed offline"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("takeCaseOfflineVaryJudgmentApplication"));
    }

    @ParameterizedTest
    @CsvSource({
        "CASE_ISSUED,false,respondToQueryCTSC,Respond to a query",
        "AWAITING_CASE_DETAILS,false,respondToQueryCTSC,Respond to a query",
        "AWAITING_RESPONDENT_ACKNOWLEDGEMENT,false,respondToQueryCTSC,Respond to a query",
        "AWAITING_APPLICANT_INTENTION,false,respondToQueryCTSC,Respond to a query",
        "IN_MEDIATION,false,respondToQueryCTSC,Respond to a query",
        "JUDICIAL_REFERRAL,false,respondToQueryCTSC,Respond to a query",
        "CASE_SETTLED,false,respondToQueryCTSC,Respond to a query",
        "CASE_DISCONTINUED,false,respondToQueryCTSC,Respond to a query",
        "JUDICIAL_REFERRAL,false,respondToQueryCTSC,Respond to a query",
        "JUDICIAL_REFERRAL,true,respondToQueryAdmin,Respond to a hearing related query",
        "CASE_PROGRESSION,false,respondToQueryCTSC,Respond to a query",
        "CASE_PROGRESSION,true,respondToQueryAdmin,Respond to a hearing related query",
        "HEARING_READINESS,false,respondToQueryCTSC,Respond to a query",
        "HEARING_READINESS,true,respondToQueryAdmin,Respond to a hearing related query",
        "PREPARE_FOR_HEARING_CONDUCT_HEARING,false,respondToQueryCTSC,Respond to a query",
        "PREPARE_FOR_HEARING_CONDUCT_HEARING,true,respondToQueryAdmin,Respond to a hearing related query",
        "DECISION_OUTCOME,false,respondToQueryCTSC,Respond to a query",
        "DECISION_OUTCOME,true,respondToQueryAdmin,Respond to a hearing related query",
        "All_FINAL_ORDERS_ISSUED,false,respondToQueryCTSC,Respond to a query",
        "All_FINAL_ORDERS_ISSUED,true,respondToQueryAdmin,Respond to a hearing related query",
    })
    void given_input_should_return_expected_qm(String state, boolean isHearingRelated, String expectedTaskId, String expectedTaskName) {

        VariableMap inputVariables = new VariableMapImpl();
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> caseData = new HashMap<>();
        String queryId = "query-id";
        data.put("qmLatestQuery", Map.of(
            "queryId", queryId,
            "isHearingRelated", isHearingRelated
        ));
        caseData.put("Data", data);

        inputVariables.putValue("eventId", "queryManagementRaiseQuery");
        inputVariables.putValue("postEventState", state);
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("taskId"), is(expectedTaskId));
        assertThat(workTypeResultList.get(0).get("name"), is(expectedTaskName));
    }

    @ParameterizedTest
    @CsvSource({
        "JUDICIAL_REFERRAL,false,respondToQueryCTSC,Respond to a query",
        "CASE_PROGRESSION,false,respondToQueryCTSC,Respond to a query",
        "CASE_PROGRESSION,true,respondToQueryAdmin,Respond to a hearing related query",
    })
    void given_input_should_return_expected_qm_task_for_stayed_case(String preStayState, boolean isHearingRelated, String expectedTaskId, String expectedTaskName) {

        Map<String, Object> data = new HashMap<>();
        Map<String, Object> caseData = new HashMap<>();
        String queryId = "query-id";
        data.put("preStayState", preStayState);
        data.put("qmLatestQuery", Map.of(
            "queryId", queryId,
            "isHearingRelated", isHearingRelated
        ));
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "queryManagementRaiseQuery");
        inputVariables.putValue("postEventState", "CASE_STAYED");
        inputVariables.putValue("additionalData", caseData);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("taskId"), is(expectedTaskId));
        assertThat(workTypeResultList.get(0).get("name"), is(expectedTaskName));
    }

    @ParameterizedTest
    @CsvSource({
        "CREATE_SDO, ENGLISH, BOTH",
        "CREATE_SDO, BOTH, ENGLISH",
        "CREATE_SDO, WELSH, WELSH",
        "REQUEST_FOR_RECONSIDERATION, ENGLISH, BOTH",
        "REQUEST_FOR_RECONSIDERATION, BOTH, ENGLISH",
        "REQUEST_FOR_RECONSIDERATION, WELSH, WELSH"
    })
    void given_input_should_return_upload_translated_order_document(
        String eventId, String dqLanguage, String lipBilingual) {
        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "CUI_WELSH");
        data.put("claimantBilingualLanguagePreference", lipBilingual);
        data.put("applicant1DQLanguage", Map.of("documents", dqLanguage));
        data.put("applicant1Represented", false);

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("additionalData", caseData);
        inputVariables.putValue("postEventState", "CASE_PROGRESSION");

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("uploadTranslatedOrderDocument"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("requestTranslation"));
    }

    @ParameterizedTest
    @CsvSource({
        "ENGLISH, BOTH, GENERATE_INTERLOCUTORY_JUDGEMENT_DOCUMENT, INTERLOCUTORY_JUDGMENT",
        "BOTH, ENGLISH, GENERATE_INTERLOCUTORY_JUDGEMENT_DOCUMENT, INTERLOCUTORY_JUDGMENT",
        "WELSH, WELSH, GENERATE_INTERLOCUTORY_JUDGEMENT_DOCUMENT, INTERLOCUTORY_JUDGMENT",
        "ENGLISH, BOTH, GENERATE_LIP_CLAIMANT_MANUAL_DETERMINATION, MANUAL_DETERMINATION_DOCUMENT",
        "BOTH, ENGLISH, GENERATE_LIP_CLAIMANT_MANUAL_DETERMINATION, MANUAL_DETERMINATION_DOCUMENT",
        "WELSH, WELSH, GENERATE_LIP_CLAIMANT_MANUAL_DETERMINATION, MANUAL_DETERMINATION_DOCUMENT",
    })
    void given_input_should_return_upload_interlocutory_judgment_or_manual_determination_document(
        String claimantLanguage, String documentLanguage, String event, String documentName) {
        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "CUI_WELSH");
        data.put("claimantBilingualLanguagePreference", claimantLanguage);
        data.put("applicant1DQLanguage", Map.of("documents", documentLanguage));
        data.put("preTranslationDocumentType", documentName);
        data.put("applicant1Represented", false);

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", event);
        inputVariables.putValue("additionalData", caseData);
        inputVariables.putValue("postEventState", "AWAITING_APPLICANT_INTENTION");

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("uploadTranslatedOrderDocument"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("requestTranslation"));
    }

    @ParameterizedTest
    @CsvSource({
        "CREATE_SDO, ENGLISH, ENGLISH",
        "REQUEST_FOR_RECONSIDERATION, ENGLISH, ENGLISH"
    })
    void given_input_should_not_return_upload_translated_order_document(
        String eventId, String dqLanguage, String lipBilingual) {
        Map<String, Object> data = new HashMap<>();
        data.put("claimantBilingualLanguagePreference", lipBilingual);
        data.put("applicant1DQLanguage", Map.of("documents", dqLanguage));

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("additionalData", caseData);
        inputVariables.putValue("postEventState", "CASE_PROGRESSION");

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(0));
    }

    @ParameterizedTest
    @CsvSource({
        "ENGLISH, BOTH",
        "BOTH, ENGLISH",
        "WELSH, WELSH"
    })
    void given_input_should_return_upload_settlement_agreement_document(
        String claimantLanguage, String documentLanguage) {
        Map<String, Object> data = new HashMap<>();
        data.put("featureToggleWA", "CUI_WELSH");
        data.put("claimantBilingualLanguagePreference", claimantLanguage);
        data.put("applicant1DQLanguage", Map.of("documents", documentLanguage));

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "GENERATE_LIP_SIGN_SETTLEMENT_AGREEMENT_FORM");
        inputVariables.putValue("additionalData", caseData);
        inputVariables.putValue("postEventState", "AWAITING_APPLICANT_INTENTION");

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("taskId"), is("uploadTranslatedOrderDocument"));
        assertThat(workTypeResultList.get(0).get("processCategories"), is("requestTranslation"));
    }
}
