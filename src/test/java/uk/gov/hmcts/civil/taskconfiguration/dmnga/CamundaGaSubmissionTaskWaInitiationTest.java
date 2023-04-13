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

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CamundaGaSubmissionTaskWaInitiationTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = DmnDecisionTable.WA_TASK_INITIATION_CIVIL_GENERALAPPLICATION;
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE"
    })
    void when_urgent_ga_creation_with_case_loc_local_court(String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for summary judgement"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE"
    })
    void when_urgent_ga_creation_with_pre_sdo_refer_legalAdvisor(String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for stay the claim"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE"
    })
    void when_urgent_ga_creation_with_case_loc_local_court_multiple_types(String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM", "STIKE_OUT")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE"
    })

    void when_urgent_ga_creation_with_pre_sdo_multiple_types_refer_legalAdvisor(String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppSuperClaimType", "SPEC_CLAIM");
        data.put("generalAppType", Map.of(
            "types", asList("EXTEND_TIME", "SET_ASIDE_JUDGEMENT")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE"
    })
    void when_non_urgent_ga_creation_with_case_loc_local_court_single_application(String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(5));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for summary judgement"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE"
    })
    void when_non_urgent_ga_creation_with_pre_sdo_single_application_refer_legalAdvisor(String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppSuperClaimType", "SPEC_CLAIM");
        data.put("generalAppType", Map.of(
            "types", asList("SET_ASIDE_JUDGEMENT")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(5));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for set aside judgement"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE"
    })
    void when_non_urgent_ga_creation_with_case_loc_local_court_multiple_types(String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "AMEND_A_STMT_OF_CASE")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(5));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "END_BUSINESS_PROCESS_GASPEC", "TRIGGER_LOCATION_UPDATE"
    })
    void when_non_urgent_ga_creation_with_pre_sdo_multiple_types_refer_legalAdvisor(String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM", "EXTEND_TIME")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(5));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_response_nonurgent_app_with_case_loc_local_court_multiple_types() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(5));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_response_nonurgent_app_with_pre_sdo_multiple_types_refer_legalAdvisor() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM", "EXTEND_TIME")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(5));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_response_nonurgent_app_with_case_loc_local_court_single_ga_type() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(5));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for strike out"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }


    @Test
    void when_response_nonurgent_app_with_pre_sdo_single_ga_type_refer_legalAdvisor() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(5));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for stay the claim"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_response_urgent_app_with_case_loc_local_court() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for strike out"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_response_urgent_app_with_pre_sdo_refer_legalAdvisor() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for stay the claim"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_response_urgent_app_with_case_loc_local_court_multiple_ga_type() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT","SUMMARY_JUDGEMENT")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_response_urgent_app_with_pre_sdo_multiple_ga_type_refer_legalAdvisor() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM","EXTEND_TIME")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_nonurgent_app_with_case_loc_local_court() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(5));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_nonurgent_app_with_pre_sdo_multiple_ga_type_refer_legalAdvisor() {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM", "EXTEND_TIME")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(5));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_nonurgent_app_with_case_loc_local_court_single_ga_type() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(5));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for summary judgement"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_nonurgent_app_pre_sdo_single_ga_type_refer_legalAdvisor() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(5));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for summary judgement"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_urgent_app_with_case_loc_local_court() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for summary judgement"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_urgent_app_with_pre_sdo_refer_legaAdvisor() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
        assertThat(workTypeResultList.get(0).get("name"), is("AApplication for stay the claim"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_urgent_app_with_case_loc_local_court_multi_ga_type() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT","STRIKE_OUT")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_urgent_app_with_pre_sdo_multi_ga_type_refer_legalAdvisor() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM","EXTEND_TIME")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorDecideOnApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED", "TRIGGER_LOCATION_UPDATE"
    })
    void when_change_state_to_addln_response_time_expired_nonurgent_app_with_case_loc_local_court(String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(5));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED", "TRIGGER_LOCATION_UPDATE"
    })
    void when_change_state_to_addln_response_time_expired_nonurgent_with_pre_sdo_refer_legalAdvisor(String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM", "EXTEND_TIME")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(5));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED", "TRIGGER_LOCATION_UPDATE"
    })
    void when_change_state_to_addln_response_time_expired_nonurgent_app_with_case_loc_local_court_single_ga_type(
        String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(5));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("strike out App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED", "TRIGGER_LOCATION_UPDATE"
    })
    void when_change_state_to_addln_response_time_expired_nonurgent_app_with_pre_sdo_single_ga_type_refer_LegalAdvior(
        String eventId) {

        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(5));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("stay the claim App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED", "TRIGGER_LOCATION_UPDATE"
    })
    void when_change_state_to_addln_response_time_expired_urgent_app_with_case_loc_local_court(String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("summary judgement App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }


    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED", "TRIGGER_LOCATION_UPDATE"
    })
    void when_change_state_to_addln_response_time_expired_urgent_app_with_pre_sdo_refer_legalAdvisor(String eventId) {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("stay the claim App - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorRevisitApplication"));
    }

    @Test
    void when_change_state_to_addln_response_time_expired_urgent_app_with_case_loc_local_court_multiple_ga_types() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT","STRIKE_OUT")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
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
        data.put("isCcmccLocation", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM","EXTEND_TIME")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED", "TRIGGER_LOCATION_UPDATE"
    })
    void when_taskId_then_return_decision_making_work_for_urgent_listed_for_hearing(String eventId) {


        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT","STRIKE_OUT")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED", "TRIGGER_LOCATION_UPDATE"
    })
    void when_taskId_then_return_decision_making_work_for_urgent_listed_for_hearing_refer_legarAdvisor(String eventId) {


        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("STAY_THE_CLAIM","EXTEND_TIME")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
        assertThat(workTypeResultList
                       .get(0).get("name"),
                   is("Application for multiple types - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("LegalAdvisorRevisitApplication"));
    }

    @Test
    void when_taskId_and_ccmcc_then_return_decision_making_work_for_urgent_listed_for_hearing() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_JUDGE_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "LISTING_FOR_A_HEARING");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
    }

    @Test
    void when_taskId_and_after_ccmcc_then_return_decision_making_work_for_urgent_listed_for_hearing() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_JUDGE_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "LISTING_FOR_A_HEARING");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
    }

    @Test
    void when_taskId_and_after_ccmccC_then_return_decision_making_work_for_nonurgent_listed_for_hearing() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", false);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_JUDGE_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "LISTING_FOR_A_HEARING");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(10));
    }

    @Test
    void when_taskId_and_ccmcc_then_return_decision_making_work_for_nonurgent_listed_for_hearing() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_JUDGE_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "LISTING_FOR_A_HEARING");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(10));
    }

    @Test
    void when_taskId_then_return_decision_making_work_for_Order_Made() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("isCcmccLocation", true);
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(10));
    }

    @Test
    void when_stayOrder_deadline_ends_taskId_then_return_making_work_review_applicationOrder_withSdo_urgentAppln() {

        Map<String, Object> data = new HashMap<>();
        data.put("isCcmccLocation", true);
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
    }

    @Test
    void when_stayOrder_deadline_ends_taskId_then_return_making_work_review_applicationOrder_withOutSdo_urgentAppln() {

        Map<String, Object> data = new HashMap<>();
        data.put("isCcmccLocation", false);
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
    }

    @Test
    void when_stayOrder_deadline_ends_taskId_then_return_making_work_review_applicationOrder_withOutSdo_nonUrgent() {

        Map<String, Object> data = new HashMap<>();
        data.put("isCcmccLocation", false);
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(10));
    }

    @Test
    void when_stayOrder_deadline_ends_taskId_then_return_making_work_review_applicationOrder_withSdo_nonUrgent() {

        Map<String, Object> data = new HashMap<>();
        data.put("isCcmccLocation", true);
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(10));
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(10));
    }

    @Test
    void when_unlessOrder_deadline_ends_taskId_then_return_making_work_review_applicationOrder_withOutSdo_nonUrgent() {

        Map<String, Object> data = new HashMap<>();
        data.put("isCcmccLocation", false);
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(10));
    }

    @Test
    void when_unlessOrder_deadline_ends_taskId_then_return_making_work_review_applicationOrder_withSdo_urgentAppln() {

        Map<String, Object> data = new HashMap<>();
        data.put("isCcmccLocation", true);
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
    }

    @Test
    void when_unlessOrder_taskId_then_return_making_work_review_applicationOrder_withOutSdo_urgentAppln() {
        Map<String, Object> data = new HashMap<>();
        data.put("isCcmccLocation", false);
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
    }

}
