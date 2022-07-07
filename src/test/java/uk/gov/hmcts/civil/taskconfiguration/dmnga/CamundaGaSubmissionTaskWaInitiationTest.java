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
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "Yes"
        ));
        caseData.put("workAllocationLocation", "CCMCC");
        caseData.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "GENERAL_APPLICATION_CREATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @Test
    void when_non_urgent_ga_creation_with_case_loc_ccmcc() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "No"
        ));
        caseData.put("workAllocationLocation", "CCMCC");
        caseData.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "GENERAL_APPLICATION_CREATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(10));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @Test
    void when_urgent_ga_creation_with_case_loc_local_court() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "Yes"
        ));
        caseData.put("workAllocationLocation", "local court");
        caseData.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "GENERAL_APPLICATION_CREATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_non_urgent_ga_creation_with_case_loc_local_court() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "No"
        ));
        caseData.put("workAllocationLocation", "local court");
        caseData.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "GENERAL_APPLICATION_CREATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(5));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_response_nonurgent_app_with_case_loc_ccmcc() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "No"
        ));
        caseData.put("workAllocationLocation", "CCMCC");
        caseData.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "RESPOND_TO_APPLICATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(10));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @Test
    void when_response_urgent_app_with_case_loc_ccmcc() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "Yes"
        ));
        caseData.put("workAllocationLocation", "CCMCC");
        caseData.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "RESPOND_TO_APPLICATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @Test
    void when_response_nonurgent_app_with_case_loc_local_court() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "No"
        ));
        caseData.put("workAllocationLocation", "local court");
        caseData.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "RESPOND_TO_APPLICATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(5));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_response_urgent_app_with_case_loc_local_court() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "Yes"
        ));
        caseData.put("workAllocationLocation", "local court");
        caseData.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "RESPOND_TO_APPLICATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_nonurgent_app_with_case_loc_ccmcc() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "No"
        ));
        caseData.put("workAllocationLocation", "CCMCC");
        caseData.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(10));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_urgent_app_with_case_loc_ccmcc() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "Yes"
        ));
        caseData.put("workAllocationLocation", "CCMCC");
        caseData.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_nonurgent_app_with_case_loc_local_court() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "No"
        ));
        caseData.put("workAllocationLocation", "local court");
        caseData.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(5));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    @Test
    void when_change_state_to_await_judicial_decision_urgent_app_with_case_loc_local_court() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "Yes"
        ));
        caseData.put("workAllocationLocation", "local court");
        caseData.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeDecideOnApplication"));
    }

    //----------------------------------------------------------

    @Test
    void when_change_state_to_addln_response_time_expired_nonurgent_app_with_case_loc_ccmcc() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "No"
        ));
        caseData.put("workAllocationLocation", "CCMCC");
        caseData.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(10));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewRevisitedApplication"));
    }

    @Test
    void when_change_state_to_addln_response_time_expired_urgent_app_with_case_loc_ccmcc() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "Yes"
        ));
        caseData.put("workAllocationLocation", "CCMCC");
        caseData.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
        assertThat(workTypeResultList.get(0).get("taskId"), is("ReviewRevisitedApplication"));
    }

    @Test
    void when_change_state_to_addln_response_time_expired_nonurgent_app_with_case_loc_local_court() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "No"
        ));
        caseData.put("workAllocationLocation", "local court");
        caseData.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(5));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @Test
    void when_change_state_to_addln_response_time_expired_urgent_app_with_case_loc_local_court() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "Yes"
        ));
        caseData.put("workAllocationLocation", "local court");
        caseData.put("generalAppType", Map.of(
            "types", asList("STRIKE_OUT", "SUMMARY_JUDGEMENT")
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
        assertThat(workTypeResultList.get(0).get("taskId"), is("JudgeRevisitApplication"));
    }

    @Test
    void when_taskId_then_return_decision_making_work_for_urgent_listed_for_hearing() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "Yes"
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "JUDGE_MAKES_DECISION");
        inputVariables.putValue("postEventState", "LISTED_FOR_A_HEARING");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
    }

    @Test
    void when_taskId_then_return_decision_making_work_for_nonurgent_listed_for_hearing() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "No"
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "JUDGE_MAKES_DECISION");
        inputVariables.putValue("postEventState", "LISTED_FOR_A_HEARING");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(10));
    }

}
