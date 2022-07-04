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

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class CamundaGaSubmissionTaskWaInitiationTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = DmnDecisionTable.WA_TASK_INITIATION_CIVIL_GENERALAPPLICATION;
    }

    @Test
    void when_taskId_then_return_decision_making_work_for_urgent_ga_creation() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "Yes"
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "GENERAL_APPLICATION_CREATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
    }

    @Test
    void when_taskId_then_return_decision_making_work_for_nonurgent_ga_creation() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "No"
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "GENERAL_APPLICATION_CREATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(10));
    }

    @Test
    void when_taskId_then_return_decision_making_work_for_respond_to_app() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> caseData = new HashMap<>();

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "RESPOND_TO_APPLICATION");
        inputVariables.putValue("postEventState", "APPLICATION_SUBMITTED_AWAITING_JUDICIAL_DECISION");
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(10));
    }

    @Test
    void when_taskId_then_return_decision_making_work_for_urgent_respond_to_judge_written_rep() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "Yes"
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "JUDGE_MAKES_DECISION");
        inputVariables.putValue("postEventState", "RESPOND_TO_JUDGE_WRITTEN_REPRESENTATION");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
    }

    @Test
    void when_taskId_then_return_decision_making_work_for_nonurgent_respond_to_judge_written_rep() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "No"
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "JUDGE_MAKES_DECISION");
        inputVariables.putValue("postEventState", "RESPOND_TO_JUDGE_WRITTEN_REPRESENTATION");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(5));
    }

    @Test
    void when_taskId_then_return_decision_making_work_for_urgent_respond_to_judge_directions() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "Yes"
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "JUDGE_MAKES_DECISION");
        inputVariables.putValue("postEventState", "RESPOND_TO_JUDGE_DIRECTIONS");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
    }

    @Test
    void when_taskId_then_return_decision_making_work_for_nonurgent_respond_to_judge_directions() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "No"
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "JUDGE_MAKES_DECISION");
        inputVariables.putValue("postEventState", "RESPOND_TO_JUDGE_DIRECTIONS");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(5));
    }

    @Test
    void when_taskId_then_return_decision_making_work_for_urgent_respond_to_judge_addln_info() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "Yes"
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "JUDGE_MAKES_DECISION");
        inputVariables.putValue("postEventState", "RESPOND_TO_JUDGE_ADDITIONAL_INFO");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
    }

    @Test
    void when_taskId_then_return_decision_making_work_for_nonurgent_respond_to_judge_addln_info() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "No"
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "JUDGE_MAKES_DECISION");
        inputVariables.putValue("postEventState", "RESPOND_TO_JUDGE_ADDITIONAL_INFO");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(5));
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

        System.out.println(workTypeResultList);
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

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(10));
    }

    @Test
    void when_taskId_then_return_decision_making_work_for_urgent_addln_response_time_expired() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "Yes"
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
    }

    @Test
    void when_taskId_then_return_decision_making_work_for_nonurgent_addln_response_time_expired() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 5*/
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "No"
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "CHANGE_STATE_TO_ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("postEventState", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(5));
    }

    @Test
    void when_addln_respose_time_expired_for_urgent_app_post_event_respond_to_judge_addln_info() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "Yes"
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("postEventState", "RESPOND_TO_JUDGE_ADDITIONAL_INFO");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
    }

    @Test
    void when_addln_respose_time_expired_for_nonurgent_app_post_event_respond_to_judge_addln_info() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "No"
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("postEventState", "RESPOND_TO_JUDGE_ADDITIONAL_INFO");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(10));
    }

    @Test
    void when_addln_respose_time_expired_for_urgent_app_post_event_respond_judge_directions() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "Yes"
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("postEventState", "RESPOND_TO_JUDGE_DIRECTIONS");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
    }

    @Test
    void when_addln_respose_time_expired_for_nonurgent_app_post_event_respond_judge_directions() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "No"
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("postEventState", "RESPOND_TO_JUDGE_DIRECTIONS");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(10));
    }

    @Test
    void when_addln_respose_time_expired_for_urgent_app_post_event_respond_to_judege_written_rep() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "Yes"
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("postEventState", "RESPOND_TO_JUDGE_WRITTEN_REPRESENTATION");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(2));
    }

    @Test
    void when_addln_respose_time_expired_for_nonurgent_app_post_event_respond_to_judege_written_rep() {

        /*if(caseData.generalAppUrgencyRequirement.generalAppUrgency != "Yes") then 2 else 10*/
        Map<String, Object> caseData = new HashMap<>();
        caseData.put("generalAppUrgencyRequirement", Map.of(
            "generalAppUrgency", "No"
        ));
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", "ADDITIONAL_RESPONSE_TIME_EXPIRED");
        inputVariables.putValue("postEventState", "RESPOND_TO_JUDGE_WRITTEN_REPRESENTATION");
        inputVariables.putValue("caseData", caseData);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        List<Map<String, Object>> workTypeResultList = dmnDecisionTableResult.getResultList();

        System.out.println(workTypeResultList);
        assertThat(workTypeResultList.size(), is(1));
        assertThat(workTypeResultList.get(0).get("workingDaysAllowed"), is(10));
    }

}
