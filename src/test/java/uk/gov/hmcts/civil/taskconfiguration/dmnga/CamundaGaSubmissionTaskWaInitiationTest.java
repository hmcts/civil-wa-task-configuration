package uk.gov.hmcts.civil.taskconfiguration.dmnga;

import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
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

public class CamundaGaSubmissionTaskWaInitiationTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = DmnDecisionTable.WA_TASK_INITIATION_CIVIL_GENERALAPPLICATION;
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE","RESPOND_TO_APPLICATION",
        "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION"
    })
    void when_urgent_ga_with_consent_order_creation_with_case_loc_local_court(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT")
        ));
        data.put("gaWaTrackLabel", " - Fast Track");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application to summary judgement - Fast Track"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE","RESPOND_TO_APPLICATION",
        "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION"
    })
    void when_urgent_ga_with_consent_order_creation_with_case_loc_local_court_lip(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT")
        ));
        data.put("isGaApplicantLip", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application to summary judgement"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "RESPOND_TO_APPLICATION"
    })
    void when_urgent_ga_with_consent_order_uncloaked_referToJudge_judgeRevisit_with_case_local_court(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT")
        ));
        data.put("referToJudge", Map.of(
            "judgeReferAdditionalInfo", "judge refer"
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        data.put("judicialDecision", Map.of(
            "decision", "REQUEST_MORE_INFO"
        ));

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("summary judgement App - revisited request more info"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "RESPOND_TO_APPLICATION"
    })
    void when_urgent_ga_with_consent_order_uncloaked_ReferToJudge_judgeRevisit_with_ccmcc_location(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT")
        ));
        data.put("referToJudge", Map.of(
            "judgeReferAdditionalInfo", "judge refer"
        ));
        data.put("gaWaTrackLabel", " - Multi Track");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        data.put("judicialDecision", Map.of(
            "decision", "REQUEST_MORE_INFO"
        ));

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("summary judgement App - revisited request more info - Multi Track"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "RESPOND_TO_APPLICATION"
    })
    void when_urgent_ga_with_consent_order_uncloaked_judgeRevisit_after_additionalPaymentMade(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", List.of("STRIKE_OUT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        data.put("judicialDecision", Map.of(
            "decision", "REQUEST_MORE_INFO"
        ));

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("strike out App - revisited request more info"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "MODIFY_STATE_AFTER_ADDITIONAL_FEE_PAID"
    })
    void when_non_urgent_ga_with_consent_order_uncloaked_judgeRevisit_after_additionalPaymentMade(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", List.of("STRIKE_OUT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        data.put("judicialDecision", Map.of(
            "decision", "REQUEST_MORE_INFO"
        ));

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("strike out App - revisited request more info"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "RESPOND_TO_APPLICATION"
    })
    void when_urgent_ga_consent_order_uncloaked_ReferLegalAdvisor_LegalAdvisorRevisit_with_local_court(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT")
        ));
        data.put("referToLegalAdvisor", Map.of(
            "judgeReferAdditionalInfo", "judge refer"
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        data.put("judicialDecision", Map.of(
            "decision", "REQUEST_MORE_INFO"
        ));

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("summary judgement App - revisited request more info"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "RESPOND_TO_APPLICATION"
    })
    void when_urgent_ga_with_consent_order_uncloaked_ReferToLegalAdvisor_LaRevisit_with_ccmcc_location(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT")
        ));
        data.put("referToLegalAdvisor", Map.of(
            "judgeReferAdditionalInfo", "judge refer"
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        data.put("judicialDecision", Map.of(
            "decision", "REQUEST_MORE_INFO"
        ));

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("summary judgement App - revisited request more info"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "RESPOND_TO_APPLICATION"
    })
    void when_urgent_ga_with_consent_order_uncloaked_LegalAdvisorRevisit_after_additionalPaymentMade(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", List.of("EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        data.put("judicialDecision", Map.of(
            "decision", "REQUEST_MORE_INFO"
        ));

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("extend time App - revisited request more info"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "MODIFY_STATE_AFTER_ADDITIONAL_FEE_PAID"
    })
    void when_non_urgent_ga_with_consent_order_uncloaked_JudgeRevisit_after_additionalPaymentMade(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", List.of("EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        data.put("judicialDecision", Map.of(
            "decision", "REQUEST_MORE_INFO"
        ));

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("extend time App - revisited request more info"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE","RESPOND_TO_APPLICATION",
        "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION"
    })
    void when_urgent_multiple_ga_with_consent_order_creation_with_case_at_local_court(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT","STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE","RESPOND_TO_APPLICATION",
        "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION"
    })
    void when_urgent_multiple_ga_with_consent_order_creation_with_case_at_local_court_lip(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT","STAY_THE_CLAIM")
        ));
        data.put("isGaApplicantLip", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE","RESPOND_TO_APPLICATION"
    })
    void when_var_judgement_creation_with_case_with_ccmcc_location(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", List.of("VARY_PAYMENT_TERMS_OF_JUDGMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application to vary payment terms of judgment"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE","RESPOND_TO_APPLICATION"
    })
    void when_var_judgement_creation_with_case_loc_local_court(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", List.of("VARY_PAYMENT_TERMS_OF_JUDGMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application to vary payment terms of judgment"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE"
    })
    void when_var_judgement_moved_offline_loc_local_court(String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", List.of("VARY_PAYMENT_TERMS_OF_JUDGMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "PROCEEDS_IN_HERITAGE");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for vary payment terms of judgment"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewOfflineApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE","RESPOND_TO_APPLICATION",
        "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION"
    })
    void when_non_urgent_multiple_ga_with_consent_order_creation_with_case_loc_local_court(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT","STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE","RESPOND_TO_APPLICATION",
        "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION"
    })
    void when_non_urgent_multiple_ga_with_consent_order_creation_with_case_loc_local_court_lip(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT","STAY_THE_CLAIM")
        ));
        data.put("isGaApplicantLip", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE","RESPOND_TO_APPLICATION",
        "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION"
    })
    void when_non_urgent_ga_with_consent_order_creation_with_case_loc_local_court(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", List.of("SETTLE_BY_CONSENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application to settle by consent"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE","RESPOND_TO_APPLICATION",
        "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION"
    })
    void when_non_urgent_ga_with_consent_order_creation_with_case_loc_local_court_lip(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", List.of("SETTLE_BY_CONSENT")
        ));
        data.put("isGaApplicantLip", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application to settle by consent"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC","RESPOND_TO_APPLICATION",
        "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION"
    })
    void when_non_urgent_ga_with_consent_order_creation_with_case_ccmcc_location(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", List.of("SET_ASIDE_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application to set aside judgement"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC","RESPOND_TO_APPLICATION",
        "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION"
    })
    void when_non_urgent_ga_with_consent_order_creation_with_case_ccmcc_location_lip_legal_advisor(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", List.of("SET_ASIDE_JUDGEMENT")
        ));
        data.put("isGaApplicantLip", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application to set aside judgement"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC","RESPOND_TO_APPLICATION",
        "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION"
    })
    void when_non_urgent_ga_with_consent_order_creation_with_case_ccmcc_location_lip_judge(String eventId) {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", List.of("SETTLE_BY_CONSENT")
        ));
        data.put("isGaRespondentOneLip", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application to settle by consent"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC","RESPOND_TO_APPLICATION",
        "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION"
    })
    void when_non_urgent_multiple_ga_with_consent_order_creation_with_case_ccmcc_location(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", List.of("SET_ASIDE_JUDGEMENT","SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC","RESPOND_TO_APPLICATION",
        "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION"
    })
    void when_non_urgent_multiple_ga_with_consent_order_creation_with_case_ccmcc_location_lip_judge(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", List.of("SET_ASIDE_JUDGEMENT","SUMMARY_JUDGEMENT")
        ));
        data.put("isGaRespondentOneLip", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC","RESPOND_TO_APPLICATION",
        "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION"
    })
    void when_non_urgent_multiple_ga_with_consent_order_creation_with_case_ccmcc_location_lip_legal_advisor(
        String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", List.of("SET_ASIDE_JUDGEMENT", "STAY_THE_CLAIM")
        ));
        data.put("isGaRespondentOneLip", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC","RESPOND_TO_APPLICATION",
        "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION"
    })
    void when_urgent_multiple_ga_with_consent_order_creation_with_case_ccmcc_location(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", List.of("SET_ASIDE_JUDGEMENT","SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC","RESPOND_TO_APPLICATION",
        "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION"
    })
    void when_urgent_multiple_ga_with_consent_order_creation_with_case_ccmcc_location_lip_judge(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", List.of("SET_ASIDE_JUDGEMENT","SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        data.put("isGaRespondentOneLip", true);
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC","RESPOND_TO_APPLICATION",
        "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION"
    })
    void when_urgent_multiple_ga_with_consent_order_creation_with_case_ccmcc_location_lip_legal_advisor(
        String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", List.of("SET_ASIDE_JUDGEMENT","STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        data.put("isGaRespondentOneLip", true);
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC","RESPOND_TO_APPLICATION",
        "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION"
    })
    void when_urgent_ga_with_consent_order_creation_with_case_ccmcc_location(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM")
        ));
        data.put("gaWaTrackLabel", " - Intermediate Track");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application to stay the claim - Intermediate Track"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC","RESPOND_TO_APPLICATION",
        "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION"
    })
    void when_urgent_ga_with_consent_order_creation_with_case_ccmcc_location_lip(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", true);
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        data.put("isGaRespondentOneLip", true);
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application to stay the claim"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED", "TRIGGER_LOCATION_UPDATE"
    })
    void when_change_state_to_addln_response_time_expired_nonurgent_consent_order_app_with_case_loc_local_court(
        String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Review summary judgement App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewRevisitedApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED", "TRIGGER_LOCATION_UPDATE"
    })
    void when_change_state_to_addln_response_time_expired_nonurgent_consent_order_app_with_case_loc_local_court_lip(
        String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        data.put("isGaRespondentOneLip", true);
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Review summary judgement App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED", "TRIGGER_LOCATION_UPDATE"
    })
    void when_change_state_to_addln_response_time_expired_urgent_mltple_consent_order_app_with_case_loc_local_court(
        String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT", "STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewRevisitedApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED", "TRIGGER_LOCATION_UPDATE"
    })
    void when_change_state_to_addln_response_time_expired_urgent_mltple_consent_order_app_with_case_loc_local_court_lip(
        String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT", "STAY_THE_CLAIM")
        ));
        data.put("isGaApplicantLip", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED", "TRIGGER_LOCATION_UPDATE"
    })
    void when_change_state_response_nonurgent_multiple_consent_order_app_with_case_loc_local_court(
        String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT", "STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewRevisitedApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED", "TRIGGER_LOCATION_UPDATE"
    })
    void when_change_state_response_nonurgent_multiple_consent_order_app_with_case_loc_local_court_lip(
        String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT", "STAY_THE_CLAIM")
        ));
        data.put("isGaApplicantLip", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED", "TRIGGER_LOCATION_UPDATE"
    })
    void when_change_state_addln_response_urgent_consent_order_app_with_case_loc_local_court(
        String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Review summary judgement App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewRevisitedApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED", "TRIGGER_LOCATION_UPDATE"
    })
    void when_change_state_addln_response_urgent_consent_order_app_with_case_loc_local_court_lip(
        String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT")
        ));
        data.put("isGaApplicantLip", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Review summary judgement App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED", "TRIGGER_LOCATION_UPDATE"
    })
    void when_change_state_addln_response_time_expired_preSdo_referToLa(
        String eventId) {

        Map<String, Object> data = new HashMap<>();
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("EXTEND_TIME")
        ));
        data.put("isGaApplicantLip", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("extend time App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED", "TRIGGER_LOCATION_UPDATE"
    })
    void when_change_state_addln_response_time_expired_preSdo_referToLaMultiApp(
        String eventId) {

        Map<String, Object> data = new HashMap<>();
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("EXTEND_TIME", "AMEND_A_STMT_OF_CASE")
        ));
        data.put("isGaApplicantLip", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorRevisitApplication"));
    }



    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED"
    })
    void when_change_state_addln_response_nonurgent_consent_order_app_with_case_ccmcc_location(
        String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Review summary judgement App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewRevisitedApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED"
    })
    void when_change_state_addln_response_nonurgent_consent_order_app_with_case_ccmcc_location_lip_judge(
        String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT")
        ));
        data.put("isGaApplicantLip", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Review summary judgement App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED"
    })
    void when_change_state_addln_response_nonurgent_consent_order_app_with_case_ccmcc_location_lip_legal_advisor(
        String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM")
        ));
        data.put("isGaApplicantLip", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Review stay the claim App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED"
    })
    void when_change_state_addln_response_urgent_consent_order_app_with_case_ccmmcc_location(
        String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Review summary judgement App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewRevisitedApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED"
    })
    void when_change_state_addln_response_urgent_consent_order_app_with_case_ccmmcc_location_lip_judge(
        String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT")
        ));
        data.put("isGaApplicantLip", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Review summary judgement App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED"
    })
    void when_change_state_addln_response_urgent_consent_order_app_with_case_ccmmcc_location_lip_legal_advisor(
        String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM")
        ));
        data.put("isGaApplicantLip", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Review stay the claim App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED"
    })
    void when_change_state_addln_response_nonurgent_multple_consent_order_app_with_case_ccmcc_locatn(
        String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT", "STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewRevisitedApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED"
    })
    void when_change_state_addln_response_nonurgent_multple_consent_order_app_with_case_ccmcc_locatn_lip_judge(
        String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT", "STAY_THE_CLAIM")
        ));
        data.put("isGaApplicantLip", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED"
    })
    void when_change_state_addln_response_nonurgent_multple_consent_order_app_with_case_ccmcc_locatn_lip_legal_advisor(
        String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("SET_ASIDE_JUDGEMENT", "STAY_THE_CLAIM")
        ));
        data.put("isGaApplicantLip", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED"
    })
    void when_change_state_addln_response_urgent_multple_consent_order_app_with_case_ccmcc_location(
        String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT", "STAY_THE_CLAIM")
        ));
        data.put("gaWaTrackLabel", " - Small Claims");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations - Small Claims"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewRevisitedApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED"
    })
    void when_change_state_addln_response_urgent_multple_consent_order_app_with_case_ccmcc_location_lip_judge(
        String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT", "STAY_THE_CLAIM")
        ));
        data.put("isGaApplicantLip", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED"
    })
    void when_change_state_addln_response_urgent_multple_consent_order_app_with_case_ccmcc_location_lip_legal_advisor(
        String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("SET_ASIDE_JUDGEMENT", "STAY_THE_CLAIM")
        ));
        data.put("isGaApplicantLip", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE"
    })
    void when_urgent_ga_creation_with_case_loc_local_court(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application to summary judgement"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC"
    })
    void when_urgent_ga_creation_with_pre_sdo_refer_legalAdvisor(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application to stay the claim"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE"
    })
    void when_urgent_ga_creation_with_post_sdo_refer_judge(String eventId) {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application to stay the claim"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE"
    })
    void when_urgent_ga_creation_with_case_loc_local_court_multiple_types(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM", "STIKE_OUT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC"
    })

    void when_urgent_ga_creation_with_pre_sdo_multiple_types_refer_legalAdvisor(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppSuperClaimType", "SPEC_CLAIM");
        data.put("generalAppType", Map.of(
            "types", List.of("EXTEND_TIME", "SET_ASIDE_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE"
    })

    void when_urgent_ga_creation_with_post_sdo_multiple_types_refer_judge(String eventId) {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppSuperClaimType", "SPEC_CLAIM");
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", List.of("EXTEND_TIME", "SET_ASIDE_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }


    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE"
    })
    void when_non_urgent_ga_creation_with_case_loc_local_court_single_application(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application to summary judgement"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC"
    })
    void when_non_urgent_ga_creation_with_pre_sdo_single_application_refer_legalAdvisor(String eventId) {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("generalAppConsentOrder", null);
        data.put("isCcmccLocation", true);
        data.put("generalAppSuperClaimType", "SPEC_CLAIM");
        data.put("generalAppType", Map.of(
            "types", List.of("SET_ASIDE_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application to set aside judgement"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE"
    })
    void when_non_urgent_ga_creation_with_post_sdo_single_application_refer_judge(String eventId) {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppSuperClaimType", "SPEC_CLAIM");
        data.put("generalAppType", Map.of(
            "types", List.of("SET_ASIDE_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application to set aside judgement"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE"
    })
    void when_non_urgent_ga_creation_with_case_loc_local_court_multiple_types(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", List.of("STRIKE_OUT", "AMEND_A_STMT_OF_CASE")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE"
    })
    void when_non_urgent_ga_creation_with_post_sdo_multiple_types_refer_judge(String eventId) {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM", "EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC"
    })
    void when_non_urgent_ga_creation_with_pre_sdo_multiple_types_refer_legalAdvisor(String eventId) {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM", "EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_response_nonurgent_app_with_case_loc_local_court_multiple_types() {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", List.of("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "RESPOND_TO_APPLICATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_response_nonurgent_app_with_pre_sdo_multiple_types_refer_legalAdvisor() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM", "EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "RESPOND_TO_APPLICATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_response_nonurgent_app_with_post_sdo_multiple_types_refer_judge() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM", "EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "RESPOND_TO_APPLICATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_response_nonurgent_app_with_case_loc_local_court_single_ga_type() {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppType", Map.of(
            "types", List.of("STRIKE_OUT")
        ));
        data.put("generalAppConsentOrder", null);
        data.put("gaWaTrackLabel", " - Small Claims");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "RESPOND_TO_APPLICATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application to strike out - Small Claims"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }


    @Test
    void when_response_nonurgent_app_with_pre_sdo_single_ga_type_refer_legalAdvisor() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM")
        ));
        data.put("generalAppConsentOrder", null);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "RESPOND_TO_APPLICATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application to stay the claim"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_response_nonurgent_app_with_post_sdo_single_ga_type_refer_judge() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("generalAppConsentOrder", null);
        data.put("isCcmccLocation", false);
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "RESPOND_TO_APPLICATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application to stay the claim"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }


    @Test
    void when_response_urgent_app_with_case_loc_local_court() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("generalAppConsentOrder", null);
        data.put("isCcmccLocation", false);
        data.put("generalAppType", Map.of(
            "types", List.of("STRIKE_OUT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "RESPOND_TO_APPLICATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application to strike out"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_response_urgent_app_with_post_sdo_refer_judge() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "RESPOND_TO_APPLICATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application to stay the claim"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_response_urgent_app_with_pre_sdo_refer_legalAdvisor() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "RESPOND_TO_APPLICATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application to stay the claim"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_response_urgent_app_with_case_loc_local_court_multiple_ga_type() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", List.of("STRIKE_OUT","SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "RESPOND_TO_APPLICATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_response_urgent_app_with_pre_sdo_multiple_ga_type_refer_legalAdvisor() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM","EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "RESPOND_TO_APPLICATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_response_urgent_app_with_post_sdo_multiple_ga_type_refer_judge() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM","EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "RESPOND_TO_APPLICATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_nonurgent_app_with_case_loc_local_court() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", List.of("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_nonurgent_app_with_pre_sdo_multiple_ga_type_refer_legalAdvisor() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM", "EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_nonurgent_with_post_sdo_multiple_ga_type_refer_judge() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM", "EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }


    @Test
    void when_change_state_to_await_judicial_decision_nonurgent_app_with_case_loc_local_court_single_ga_type() {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application to summary judgement"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_nonurgent_app_pre_sdo_single_ga_type_refer_legalAdvisor() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application to stay the claim"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_nonurgent_app_post_sdo_single_ga_type_refer_judge() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application to stay the claim"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_urgent_app_with_case_loc_local_court() {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application to summary judgement"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_urgent_app_with_pre_sdo_refer_legaAdvisor() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application to stay the claim"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_urgent_app_with_post_sdo_refer_judge() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application to stay the claim"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_urgent_app_with_case_loc_local_court_multi_ga_type() {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT","STRIKE_OUT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_urgent_app_with_pre_sdo_multi_ga_type_refer_legalAdvisor() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM","EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_urgent_app_with_post_sdo_multi_ga_type_refer_judge() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM","EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED"
    })
    void when_change_state_to_addln_response_time_expired_urgent_app_with_post_sdo_refer_judge(String eventId) {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("stay the claim App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "TRIGGER_LOCATION_UPDATE"
    })
    void when_change_state_to_trigger_location_update_nonurgent_app_with_case_loc_local_court(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED"
    })
    void when_change_state_to_addln_response_time_expired_nonurgent_app_with_case_loc_local_court(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        data.put("gaWaTrackLabel", " - Fast Track");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations - Fast Track"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED"
    })
    void when_change_state_to_addln_response_time_expired_nonurgent_with_pre_sdo(String eventId) {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM", "EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "TRIGGER_LOCATION_UPDATE"
    })
    void when_change_state_to_trigger_location_update_nonurgent_with_post_sdo_refer_Judge(String eventId) {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM", "EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED"
    })
    void when_change_state_to_addln_response_time_expired_nonurgent_with_post_sdo_refer_Judge(String eventId) {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM", "EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "TRIGGER_LOCATION_UPDATE"
    })
    void when_change_state_to_trigger_location_nonurgent_app_with_case_loc_local_court_single_ga_type(
        String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("STRIKE_OUT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("strike out App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED"
    })
    void when_change_state_to_addln_response_time_expired_nonurgent_app_with_case_loc_local_court_single_ga_type(
        String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("STRIKE_OUT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("strike out App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED"
    })
    void when_change_state_to_addln_response_time_expired_nonurgent_with_post_sdo_single_ga_type_refer_judge(
        String eventId) {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("stay the claim App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "TRIGGER_LOCATION_UPDATE"
    })
    void when_change_state_to_trigger_loc_update_nonurgent_with_post_sdo_single_ga_type_refer_judge(
        String eventId) {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("stay the claim App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED"
    })
    void when_change_state_to_addln_response_time_expired_urgent_app_with_case_loc_local_court(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("summary judgement App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }


    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED"
    })
    void when_change_state_to_addln_response_time_expired_urgent_app_with_pre_sdo_refer_legalAdvisor(String eventId) {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("stay the claim App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "TRIGGER_LOCATION_UPDATE"
    })
    void when_change_state_to_trigger_location_update_urgent_app_with_post_sdo_refer_judge(String eventId) {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("stay the claim App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @Test
    void when_change_state_to_addln_response_time_expired_urgent_app_with_case_loc_local_court_multiple_ga_types() {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT","STRIKE_OUT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @Test
    void when_change_state_to_addln_response_time_expired_urgent_with_pre_sdo_multiple_ga_types_refer_legalAdvisor() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM","EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @Test
    void when_change_state_to_addln_response_time_expired_urgent_with_pre_sdo_multiple_ga_types() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM","EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "TRIGGER_LOCATION_UPDATE"
    })
    void when_taskId_then_return_decision_making_work_for_urgent_listed_for_hearing(String eventId) {



        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT","STRIKE_OUT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED"
    })
    void when_taskId_then_return_decision_making_work_for_urgent_listed_for_hearing_refer_legalAdvisor(String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM","EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "TRIGGER_LOCATION_UPDATE"
    })
    void when_taskId_then_return_decision_making_work_for_urgent_listed_for_hearing_post_sdo_refer_judge(
        String eventId) {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM","EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED"
    })
    void when_taskId_then_return_decision_making_work_for_urgent_listed_for_hearing_post_sdo_refer_judge_addln_time(
        String eventId) {



        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM","EXTEND_TIME")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @Test
    void when_taskId_and_ccmcc_then_return_decision_making_work_for_urgent_listed_for_hearing() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_JUDGE_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "LISTING_FOR_A_HEARING");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
    }

    @Test
    void when_taskId_and_after_ccmcc_then_return_decision_making_work_for_urgent_listed_for_hearing() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_JUDGE_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "LISTING_FOR_A_HEARING");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
    }

    @Test
    void when_taskId_and_after_ccmccC_then_return_decision_making_work_for_nonurgent_listed_for_hearing() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_JUDGE_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "LISTING_FOR_A_HEARING");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
    }

    @Test
    void when_taskId_and_ccmcc_then_return_decision_making_work_for_nonurgent_listed_for_hearing() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_JUDGE_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "LISTING_FOR_A_HEARING");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
    }

    @Test
    void when_taskId_and_ccmcc_then_return_general_order_direction_for_urgent_listed_for_hearing() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "LISTING_FOR_A_HEARING");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
    }

    @Test
    void when_taskId_and_after_ccmcc_then_return_general_order_direction_for_urgent_listed_for_hearing() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "LISTING_FOR_A_HEARING");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
    }

    @Test
    void when_taskId_and_after_ccmcc_then_return_general_order_direction_for_nonurgent_listed_for_hearing() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "LISTING_FOR_A_HEARING");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
    }

    @Test
    void when_taskId_and_ccmcc_then_return_general_order_direction_for_nonurgent_listed_for_hearing() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "LISTING_FOR_A_HEARING");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
    }

    @Test
    void when_taskId_then_return_decision_making_work_for_Order_Made() {


        Map<String, Object> data = new HashMap<>();
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_JUDGE_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "ORDER_MADE");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Review Application Order"));
    }

    @Test
    void when_stayOrder_deadline_ends_taskId_then_return_making_work_review_applicationOrder_withSdo_urgentAppln() {

        Map<String, Object> data = new HashMap<>();
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_SCHEDULER_CHECK_STAY_ORDER_DEADLINE");
        inputVariables.putValue("postEventState", "ORDER_MADE");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Revisit Application for Stay Claim"));
    }

    @Test
    void when_stayOrder_deadline_ends_taskId_then_return_making_work_review_applicationOrder_withOutSdo_urgentAppln() {

        Map<String, Object> data = new HashMap<>();
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_SCHEDULER_CHECK_STAY_ORDER_DEADLINE");
        inputVariables.putValue("postEventState", "ORDER_MADE");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Revisit Application for Stay Claim"));
    }

    @Test
    void when_stayOrder_deadline_ends_taskId_then_return_making_work_review_applicationOrder_withOutSdo_nonUrgent() {

        Map<String, Object> data = new HashMap<>();
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_SCHEDULER_CHECK_STAY_ORDER_DEADLINE");
        inputVariables.putValue("postEventState", "ORDER_MADE");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Revisit Application for Stay Claim"));
    }

    @Test
    void when_stayOrder_deadline_ends_taskId_then_return_making_work_review_applicationOrder_withSdo_nonUrgent() {

        Map<String, Object> data = new HashMap<>();
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false));
        data.put("gaWaTrackLabel", " - Multi Track");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_SCHEDULER_CHECK_STAY_ORDER_DEADLINE");
        inputVariables.putValue("postEventState", "ORDER_MADE");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Revisit Application for Stay Claim - Multi Track"));
    }

    @Test
    void when_unlessOrder_deadline_ends_taskId_then_return_making_work_review_applicationOrder_withSdo_nonUrgentApp() {

        Map<String, Object> data = new HashMap<>();
        data.put("isCcmccLocation", true);

        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);


        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_SCHEDULER_CHECK_UNLESS_ORDER_DEADLINE");
        inputVariables.putValue("postEventState", "ORDER_MADE");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Revisit Application for Unless Order"));
    }

    @Test
    void when_unlessOrder_deadline_ends_taskId_then_return_making_work_review_applicationOrder_withOutSdo_nonUrgent() {

        Map<String, Object> data = new HashMap<>();
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_SCHEDULER_CHECK_UNLESS_ORDER_DEADLINE");
        inputVariables.putValue("postEventState", "ORDER_MADE");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Revisit Application for Unless Order"));
    }

    @Test
    void when_unlessOrder_deadline_ends_taskId_then_return_making_work_review_applicationOrder_withSdo_urgentAppln() {

        Map<String, Object> data = new HashMap<>();
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_SCHEDULER_CHECK_UNLESS_ORDER_DEADLINE");
        inputVariables.putValue("postEventState", "ORDER_MADE");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Revisit Application for Unless Order"));
    }

    @Test
    void when_unlessOrder_taskId_then_return_making_work_review_applicationOrder_withOutSdo_urgentAppln() {
        Map<String, Object> data = new HashMap<>();
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_SCHEDULER_CHECK_UNLESS_ORDER_DEADLINE");
        inputVariables.putValue("postEventState", "ORDER_MADE");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Revisit Application for Unless Order"));
    }

    @Test
    void when_refer_to_judge_urgent_app_for_await_judicial_decision_ccmc_single_appln() {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_JUDGE");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application to summary judgement"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));

    }

    @Test
    void when_refer_to_judge_urgent_app_for_await_judicial_decision_multiple_appln_type() {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);

        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_JUDGE");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_refer_to_judge_non_urgent_app_for_await_judicial_decision_ccmc_single_appln() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_JUDGE");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
        assertThat(workTypeResultList.get(0).get("name"), is("Application to summary judgement"));
    }

    @Test
    void when_refer_to_judge_non_urgent_app_for_await_judicial_decision_multiple_appln_type() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_JUDGE");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
    }

    @Test
    void when_refer_to_judge_urgent_app_for_await_judicial_decision_ccmcc_location_no() {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_JUDGE");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
    }

    @Test
    void when_refer_to_judge_urgent_app_for_addln_response_expired_single_type() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_JUDGE");
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"), is("summary judgement App - "
                                                   + "revisited make order for "
                                                   + "written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @Test
    void when_refer_to_judge_urgent_app_for_addln_response_expired_multiple_type() {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);

        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_JUDGE");
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"), is("Application for multiple types - "
                                                   + "revisited make order for "
                                                   + "written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @Test
    void when_refer_to_judge_nonurgent_app_for_addln_response_expired_single_type() {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_JUDGE");
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"), is("summary judgement App - "
                                                   + "revisited make order for "
                                                   + "written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @Test
    void when_refer_to_judge_non_urgent_app_for_addln_response_expired_multiple_type() {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);

        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_JUDGE");
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"), is("Application for multiple types - "
                                                   + "revisited make order for "
                                                   + "written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @Test
    void when_nonurgent_app_with_pre_sdo_single_ga_type_refer_to_legalAdvisor_event() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_LEGAL_ADVISOR");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application to stay the claim"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_nonurgent_app_with_pre_sdo_multiple_ga_type_refer_to_legalAdvisor_event() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM", "SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_LEGAL_ADVISOR");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_urgent_app_with_pre_sdo_multiple_ga_type_refer_to_legalAdvisor_event() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppType", Map.of(
            "types", List.of("STAY_THE_CLAIM", "SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_LEGAL_ADVISOR");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_urgent_app_with_pre_sdo_single_ga_type_refer_to_legalAdvisor_event() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT")
        ));
        data.put("gaWaTrackLabel", " ");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_LEGAL_ADVISOR");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"), is("Application to summary judgement "));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_refer_to_legalAdvisor_nonurgent_app_for_addln_response_expired_single_type() {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_LEGAL_ADVISOR");
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"), is("summary judgement App - "
                                                   + "revisited make order for "
                                                   + "written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorRevisitApplication"));
    }

    @Test
    void when_refer_to_legalAdvisor_nonurgent_app_for_addln_response_expired_multiple_type() {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT", "STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_LEGAL_ADVISOR");
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"), is("Application for multiple types - revisited "
                                                                      + "make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorRevisitApplication"));
    }

    @Test
    void when_refer_to_legalAdvisor_urgent_app_for_addln_response_expired_single_type() {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_LEGAL_ADVISOR");
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"), is("summary judgement App - "
                                                   + "revisited make order for "
                                                   + "written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorRevisitApplication"));
    }

    @Test
    void when_refer_to_legalAdvisor_urgent_app_for_addln_response_expired_multiple_type() {


        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("SUMMARY_JUDGEMENT", "STAY_THE_CLAIM")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_LEGAL_ADVISOR");
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"), is("Application for multiple types - revisited "
                                                   + "make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorRevisitApplication"));
    }

    @Test
    void when_taskId_and_ccmcc_then_return_decision_making_work_for_urgent_proceed_in_heritage() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_JUDGE_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "PROCEEDS_IN_HERITAGE");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
    }

    @Test
    void when_taskId_and_after_ccmcc_then_return_decision_making_work_for_urgent_proceed_in_heritage() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_JUDGE_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "PROCEEDS_IN_HERITAGE");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
    }

    @Test
    void when_taskId_and_after_ccmccC_then_return_decision_making_work_for_nonurgent_proceed_in_heritage() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppConsentOrder", null);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_JUDGE_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "PROCEEDS_IN_HERITAGE");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
    }

    @Test
    void when_taskId_and_ccmcc_then_return_decision_making_work_for_nonurgent_proceed_in_heritage() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppConsentOrder", null);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_JUDGE_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "PROCEEDS_IN_HERITAGE");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
    }

    @Test
    void when_ga_lip_using_helpWithFee_duringInitiation() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppHelpWithFees", Map.of(
            "helpWithFee", true
        ));

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "NOTIFY_HELP_WITH_FEE");
        inputVariables.putValue("postEventState", "AWAITING_APPLICATION_PAYMENT");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Help With Fees Application Fee"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("HelpWithFeesApplicationFee"));
    }

    @Test
    void when_ga_lip_using_helpWithFee_additionalfee() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppHelpWithFees", Map.of(
            "helpWithFee", true
        ));

        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "NOTIFY_HELP_WITH_FEE");
        inputVariables.putValue("postEventState", "APPLICATION_ADD_PAYMENT");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Help With Fees Additional Application Fee"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("HelpWithFeesAdditionalApplicationFee"));
    }

    @Test
    void given_input_should_return_applicationDocumentsWelshRequest_ifRespondentBilingualAwaitingRespondentResponse() {
        Map<String, Object> data = new HashMap<>();
        data.put("isGaRespondentOneLip", true);
        data.put("respondentBilingualLanguagePreference", true);
        Map<String, Object> informOtherParty = new HashMap<>();
        informOtherParty.put("isWithNotice", "Yes");
        data.put("generalAppInformOtherParty", informOtherParty);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "AWAITING_RESPONDENT_RESPONSE");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"),
                   is("Application Documents Welsh Request - Application Summary document"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("applicationDocumentsWelshRequestAppSum"));
    }

    @Test
    void given_input_should_return_applicationDocumentsWelshRequest_ifApplicantBilingualWithoutNotice() {
        Map<String, Object> data = new HashMap<>();
        data.put("isGaApplicantLip", true);
        data.put("applicantBilingualLanguagePreference", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"),
                   is("Application Documents Welsh Request - Application Summary document"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("applicationDocumentsWelshRequestAppSum"));
    }

    @Test
    void given_input_should_return_applicationDocumentsWelshRequest_ifRespondentResponds() {
        Map<String, Object> data = new HashMap<>();
        data.put("preTranslationGaDocumentType", "RESPOND_TO_APPLICATION_SUMMARY_DOC");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "GENERATE_DRAFT_DOCUMENT");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"),
                   is("Application Documents Welsh Request - Application summary document - Responded"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("applicationDocumentsWelshRequestAppSumResponded"));
    }

    @Test
    void given_input_should_return_applicationDocumentsWelshRequest_ifJudgeMakesOrder() {
        Map<String, Object> data = new HashMap<>();
        data.put("isGaApplicantLip", true);
        data.put("respondentBilingualLanguagePreference", true);
        data.put("applicationIsCloaked", false);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_JUDGE_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "ORDER_MADE");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList.get(1).get("name"), is("Application Documents Welsh Request - General Order"));
        assertThat(workTypeResultList.get(1).get("taskId"), is("applicationDocumentsWelshRequestOrderMade"));
    }

    @Test
    void given_input_should_return_applicationDocumentsWelshRequest_ifListedForHearing() {
        Map<String, Object> data = new HashMap<>();
        data.put("isGaApplicantLip", true);
        data.put("applicantBilingualLanguagePreference", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_JUDGE_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "LISTING_FOR_A_HEARING");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList.get(1).get("name"),
                   is("Application Documents Welsh Request - Hearing Order"));
        assertThat(workTypeResultList.get(1).get("taskId"), is("applicationDocumentsWelshRequestHearingOrder"));
    }

    @Test
    void given_input_should_return_applicationDocumentsWelshRequest_ifAdditionalFeePaid() {
        Map<String, Object> data = new HashMap<>();
        data.put("isGaApplicantLip", true);
        data.put("applicantBilingualLanguagePreference", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_JUDGE_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "APPLICATION_ADD_PAYMENT");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"),
                   is("Application Documents Welsh Request - Without notice to notice document"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("applicationDocumentsWelshRequestWithNotice"));
    }

    @Test
    void given_input_should_return_applicationDocumentsWelshRequest_respondToMoreInfo() {
        Map<String, Object> data = new HashMap<>();
        data.put("isGaApplicantLip", true);
        data.put("isApplicantResponded", true);
        data.put("applicantBilingualLanguagePreference", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "AWAITING_ADDITIONAL_INFORMATION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"),
                   is("Application Documents Welsh Request - Response to the Request for more information - applicant"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("applicationDocumentsWelshRequestRespondToMoreInfo"));
    }

    @Test
    void given_input_should_return_applicationDocumentsWelshRequest_respondToMoreInfoRespondent() {
        Map<String, Object> data = new HashMap<>();
        data.put("isGaApplicantLip", true);
        data.put("isRespondentResponded", true);
        data.put("applicantBilingualLanguagePreference", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "AWAITING_ADDITIONAL_INFORMATION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"),
                   is("Application Documents Welsh Request - Response to the Request for more information - respondent"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("applicationDocumentsWelshRequestRespondToMoreInfoRespondent"));
    }

    @Test
    void given_input_should_return_applicationDocumentsWelshRequest_respondToWrittenRep() {
        Map<String, Object> data = new HashMap<>();
        data.put("isGaApplicantLip", true);
        data.put("isApplicantResponded", true);
        data.put("applicantBilingualLanguagePreference", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "AWAITING_WRITTEN_REPRESENTATIONS");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"),
                   is("Application Documents Welsh Request - Response to Written Representations - applicant"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("applicationDocumentsWelshRequestRespondToWrittenRep"));
    }

    @Test
    void given_input_should_return_applicationDocumentsWelshRequest_respondToWrittenRepRespondent() {
        Map<String, Object> data = new HashMap<>();
        data.put("isGaApplicantLip", true);
        data.put("isRespondentResponded", true);
        data.put("applicantBilingualLanguagePreference", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "AWAITING_WRITTEN_REPRESENTATIONS");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"),
                   is("Application Documents Welsh Request - Response to Written Representations - respondent"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("applicationDocumentsWelshRequestRespondToWrittenRepRespondent"));
    }

    @Test
    void given_input_should_return_applicationDocumentsWelshRequest_respondToJudgeOrder() {
        Map<String, Object> data = new HashMap<>();
        data.put("isGaApplicantLip", true);
        data.put("applicantBilingualLanguagePreference", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "RESPOND_TO_JUDGE_DIRECTIONS");
        inputVariables.putValue("postEventState", "AWAITING_DIRECTIONS_ORDER_DOCS");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"),
                   is("Application Documents Welsh Request - Respond to a Judge Direction Order"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("applicationDocumentsWelshRequestRespondToJudge"));
    }

    @Test
    void given_input_should_return_lipVlr_applicationDocumentsWelshRequest_respondToJudgeOrder() {
        Map<String, Object> data = new HashMap<>();
        data.put("isGaApplicantLip", true);
        data.put("respondentBilingualLanguagePreference", false);
        data.put("applicantBilingualLanguagePreference", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "RESPOND_TO_JUDGE_DIRECTIONS");
        inputVariables.putValue("postEventState", "AWAITING_DIRECTIONS_ORDER_DOCS");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"),
                   is("Application Documents Welsh Request - Respond to a Judge Direction Order"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("applicationDocumentsWelshRequestRespondToJudge"));
    }

    @Test
    void given_input_should_return_lipVlr_WhenFlagIsEnglish_applicationDocumentsWelshRequest_respondToJudgeOrder() {
        Map<String, Object> data = new HashMap<>();
        data.put("isGaApplicantLip", true);
        data.put("respondentBilingualLanguagePreference", false);
        data.put("applicantBilingualLanguagePreference", false);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "RESPOND_TO_JUDGE_DIRECTIONS");
        inputVariables.putValue("postEventState", "AWAITING_DIRECTIONS_ORDER_DOCS");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(0));
    }


    @Test
    void given_input_should_return_applicationDocumentsWelshRequest_uploadAddlDocuments() {
        Map<String, Object> data = new HashMap<>();
        data.put("isGaApplicantLip", true);
        data.put("applicantBilingualLanguagePreference", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_DOC_UPLOAD_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"),
                   is("Application Documents Welsh Request - Uploaded Documents"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("applicationDocumentsWelshRequestAddlDoc"));
    }

    @Test
    void given_input_should_return_applicationDocumentsWelshRequest_judgeReqMoreInfo() {
        Map<String, Object> data = new HashMap<>();
        data.put("isGaApplicantLip", true);
        data.put("applicantBilingualLanguagePreference", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_JUDGE_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "AWAITING_ADDITIONAL_INFORMATION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"),
                   is("Application Documents Welsh Request - Request for More Information"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("applicationDocumentsWelshRequestMoreInfo"));
    }

    @Test
    void given_input_should_return_applicationDocumentsWelshRequest_judgeWrittenRep() {
        Map<String, Object> data = new HashMap<>();
        data.put("isGaApplicantLip", true);
        data.put("applicantBilingualLanguagePreference", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_JUDGE_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "AWAITING_WRITTEN_REPRESENTATIONS");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"),
                   is("Application Documents Welsh Request - Written Representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("applicationDocumentsWelshRequestWrittenResp"));
    }

    @Test
    void given_input_should_return_applicationDocumentsWelshRequest_JudgeMakesHearingNotice() {
        Map<String, Object> data = new HashMap<>();
        data.put("isGaApplicantLip", true);
        data.put("applicantBilingualLanguagePreference", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_HEARING_SCHEDULED_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "HEARING_SCHEDULED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"),
                   is("Application Documents Welsh Request - Hearing Notice"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("applicationDocumentsWelshRequestHearingSchedule"));
    }

    @Test
    void given_input_should_return_applicationDocumentsWelshRequest_judgeMakeDismissalOrder() {
        Map<String, Object> data = new HashMap<>();
        data.put("isGaApplicantLip", true);
        data.put("applicantBilingualLanguagePreference", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_JUDGE_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "APPLICATION_DISMISSED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList.get(0).get("name"),
                   is("Application Documents Welsh Request - Dismissal Order"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("applicationDocumentsWelshRequestAppDismissed"));
        assertThat(workTypeResultList.get(1).get("name"),
                   is("Review application order"));
        assertThat(workTypeResultList.get(1).get("taskId"), is("ReviewApplicationOrder"));
    }

    @Test
    void given_input_should_return_applicationDocumentsWelshRequest_judgeMakesFinalOrder() {
        Map<String, Object> data = new HashMap<>();
        data.put("isGaApplicantLip", true);
        data.put("applicantBilingualLanguagePreference", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "ORDER_MADE");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(2));
        assertThat(workTypeResultList.get(1).get("name"),
                   is("Application Documents Welsh Request - Final Order"));
        assertThat(workTypeResultList.get(1).get("taskId"), is("applicationDocumentsWelshRequestFinalOrder"));
    }

    @Test
    void given_input_should_return_applicationDocumentsWelshRequest_judgeMakesDirectionOrder() {
        Map<String, Object> data = new HashMap<>();
        data.put("isGaApplicantLip", true);
        data.put("applicantBilingualLanguagePreference", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_JUDGE_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "AWAITING_DIRECTIONS_ORDER_DOCS");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"),
                   is("Application Documents Welsh Request - Judges Directions"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("applicationDocumentsWelshRequestJudgeDirection"));
    }

    @Test
    void given_input_should_return_applicationDocumentsWelshRequest_ForApplicationSummaryDocument() {
        Map<String, Object> data = new HashMap<>();
        data.put("preTranslationGaDocumentType", "APPLICATION_SUMMARY_DOC");
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "GENERATE_DRAFT_DOCUMENT");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("name"),
                   is("Upload translated application summary document"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("applicationDocumentsWelshRequestAppSum"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED",
        "TRIGGER_LOCATION_UPDATE"
    })
    void when_response_time_expired_vary_payment_terms_of_judgment_revisit_legal_advisor(
        String eventId) {

        Map<String, Object> data = new HashMap<>();
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", List.of("VARY_PAYMENT_TERMS_OF_JUDGMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Review vary payment terms of judgment App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewRevisitedApplication"));
    }

}
