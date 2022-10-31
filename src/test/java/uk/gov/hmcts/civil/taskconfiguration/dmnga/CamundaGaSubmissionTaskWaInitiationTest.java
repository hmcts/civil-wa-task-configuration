package uk.gov.hmcts.civil.taskconfiguration.dmnga;

import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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

    @Test
    void when_urgent_ga_creation_with_case_loc_ccmcc() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
        assertThat(workTypeResultList.get(0).get("name"), is("Review Application for summary judgement"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @Test
    void when_urgent_ga_creation_with_case_loc_ccmcc_multiple_application() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT", "STRIKE_OUT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
        assertThat(workTypeResultList.get(0).get("name"), is("Review Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @Test
    void when_non_urgent_ga_creation_with_case_loc_ccmcc() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();

        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(10));
        assertThat(workTypeResultList.get(0).get("name"), is("Review Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @Test
    void when_non_urgent_ga_creation_with_case_loc_ccmcc_single_application_type() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();

        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_BUSINESS_PROCESS_GASPEC");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(10));
        assertThat(workTypeResultList.get(0).get("name"), is("Review Application for summary judgement"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @Test
    void when_urgent_ga_creation_with_case_loc_local_court() {

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
        inputVariables.putValue("eventId", "END_BUSINESS_PROCESS_GASPEC");
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
    void when_urgent_ga_creation_with_case_loc_local_court_multiple_types() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT", "STIKE_OUT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "END_BUSINESS_PROCESS_GASPEC");
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
    void when_non_urgent_ga_creation_with_case_loc_local_court_single_application() {

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
        inputVariables.putValue("eventId", "END_BUSINESS_PROCESS_GASPEC");
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
    void when_non_urgent_ga_creation_with_case_loc_local_court() {

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
        inputVariables.putValue("eventId", "END_BUSINESS_PROCESS_GASPEC");
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
    void when_response_nonurgent_app_with_case_loc_ccmcc() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(10));
        assertThat(workTypeResultList.get(0).get("name"), is("Review Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @Test
    void when_response_nonurgent_app_with_case_loc_ccmcc_single_appln_type() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(10));
        assertThat(workTypeResultList.get(0).get("name"), is("Review Application for summary judgement"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @Test
    void when_response_urgent_app_with_case_loc_ccmcc() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT")
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
        assertThat(workTypeResultList.get(0).get("name"), is("Review Application for summary judgement"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @Test
    void when_response_urgent_app_with_case_loc_ccmcc_multiple_ga_types() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT","STIKE_OUT")
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
        assertThat(workTypeResultList.get(0).get("name"), is("Review Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @Test
    void when_response_nonurgent_app_with_case_loc_local_court() {

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
    void when_change_state_to_await_judicial_decision_nonurgent_app_with_case_loc_ccmcc() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(10));
        assertThat(workTypeResultList.get(0).get("name"), is("Review Application for strike out"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_nonurgent_app_with_case_loc_ccmcc_multiple_ga_types() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT","SUMMARY_JUDGEMENT")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(10));
        assertThat(workTypeResultList.get(0).get("name"), is("Review Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_urgent_app_with_case_loc_ccmcc() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
        assertThat(workTypeResultList.get(0).get("name"), is("Review Application for multiple types"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_urgent_app_with_case_loc_ccmcc_single_ga_type() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT")
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
        assertThat(workTypeResultList.get(0).get("name"), is("Review Application for strike out"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_nonurgent_app_with_case_loc_local_court() {

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
    void when_change_state_to_addln_response_time_expired_urgent_app_with_case_loc_ccmcc_single_ga_type() {

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
            "types", asList("STRIKE_OUT")
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
        assertThat(workTypeResultList.get(0).get("name"),
                   is("Review strike out App - "
                          + "revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewRevisitedApplication"));
    }

    @Test
    void when_change_state_to_addln_response_time_expired_urgent_app_with_case_loc_ccmcc() {

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
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
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
        assertThat(workTypeResultList.get(0).get("name"),
                   is("Review Application for multiple types - "
                          + "revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewRevisitedApplication"));
    }

    @Test
    void when_change_state_to_addln_response_time_expired_nonurgent_app_with_case_loc_ccmcc() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();

        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(10));
        assertThat(workTypeResultList.get(0).get("name"),
                   is("Review Application for multiple types "
                          + "- revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewRevisitedApplication"));
    }

    @Test
    void when_change_state_to_addln_response_time_expired_nonurgent_app_with_case_loc_ccmcc_single_ga_type() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();

        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("isCcmccLocation", true);
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(10));
        assertThat(workTypeResultList.get(0).get("name"),
                   is("Review summary judgement App"
                          + " - revisited make order for written representations"));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewRevisitedApplication"));
    }

    @Test
    void when_change_state_to_addln_response_time_expired_nonurgent_app_with_case_loc_local_court() {

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
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED");
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

    @Test
    void when_change_state_to_addln_response_time_expired_nonurgent_app_with_case_loc_local_court_single_ga_type() {

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
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED");
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

    @Test
    void when_change_state_to_addln_response_time_expired_urgent_app_with_case_loc_local_court() {

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
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED");
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
    void when_taskId_then_return_decision_making_work_for_urgent_listed_for_hearing() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
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
    void when_taskId_then_return_decision_making_work_for_nonurgent_listed_for_hearing() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", false
        ));
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
    void when_refer_to_judge_urgent_app_for_await_judicial_decision_ccmc_single_appln() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for summary judgement"));
    }

    @Test
    void when_refer_to_judge_urgent_app_for_await_judicial_decision_multiple_appln_type() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
    }

    @Test
    void when_refer_to_judge_non_urgent_app_for_await_judicial_decision_ccmc_single_appln() {

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
            "types", asList("SUMMARY_JUDGEMENT")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(5));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for summary judgement"));
    }

    @Test
    void when_refer_to_judge_non_urgent_app_for_await_judicial_decision_multiple_appln_type() {

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
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(5));
        assertThat(workTypeResultList.get(0).get("name"), is("Application for multiple types"));
    }

    @Test
    void when_refer_to_judge_urgent_app_for_await_judicial_decision_ccmcc_location_no() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
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
        inputVariables.putValue("eventId", "REFER_TO_JUDGE");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(0));
    }

    @Test
    void when_refer_to_judge_urgent_app_for_addln_response_expired_single_type() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
    }

    @Test
    void when_refer_to_judge_urgent_app_for_addln_response_expired_multiple_type() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
    }

    @Test
    void when_refer_to_judge_nonurgent_app_for_addln_response_expired_single_type() {

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
            "types", asList("SUMMARY_JUDGEMENT")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(5));
    }

    @Test
    void when_refer_to_judge_non_urgent_app_for_addln_response_expired_multiple_type() {

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
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(5));
    }

    @Test
    void when_refer_to_legal_advisor_urgent_app_for_addln_response_expired_single_type() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("SUMMARY_JUDGEMENT")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
    }

    @Test
    void when_refer_to_legal_advisor_urgent_app_for_addln_response_expired_multiple_types() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("isCcmccLocation", true);
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
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
                       .get(0).get("name"), is("Application for multiple types - "
                                                   + "revisited make order for "
                                                   + "written representations"));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
    }

    @Test
    void when_refer_to_legal_advisor_nonurgent_app_for_addln_response_expired() {

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
            "types", asList("SUMMARY_JUDGEMENT")
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
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(5));
    }

    @Test
    void when_refer_to_legal_advisor_nonurgent_app_for_addln_response_expired_multiple_types() {

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
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
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
                       .get(0).get("name"), is("Application for multiple types - "
                                                   + "revisited make order for "
                                                   + "written representations"));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(5));
    }

    @Test
    void when_refer_to_legal_advisor_urgent_app_for_await_judicial_decision_and_ccmcc_location_false() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_LEGAL_ADVISOR");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(0));
    }

    @Test
    void when_refer_to_legal_advisor_urgent_app_for_await_response_time_expired_and_ccmcc_location_false() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> data = new HashMap<>();
        data.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", true
        ));
        data.put("judicialDecision", Map.of(
            "decision", "MAKE_ORDER_FOR_WRITTEN_REPRESENTATIONS"
        ));
        data.put("isCcmccLocation", false);
        data.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("Data", data);

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "REFER_TO_LEGAL_ADVISOR");
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("additionalData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(0));
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

}
