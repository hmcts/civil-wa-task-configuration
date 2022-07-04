package uk.gov.hmcts.civil.taskconfiguration.dmnGA;

import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTable;
import uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTableBaseUnitTest;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CamundaGASubmissionTaskWaInitiationTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = DmnDecisionTable.WA_TASK_INITIATION_CIVIL_GENERALAPPLICATION;
    }

    @Test
    void when_taskId_then_return_decision_making_work_for_urgent_GA_CREATION() {

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
    void when_taskId_then_return_decision_making_work_for_nonurgent_GA_CREATION() {

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
    void when_taskId_then_return_decision_making_work_for_RESPOND_TO_APP() {

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
    void when_taskId_then_return_decision_making_work_for_urgent_RESPOND_TO_JUDGE_WRITTEN_REPRESENTATION() {

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
    void when_taskId_then_return_decision_making_work_for_nonurgent_RESPOND_TO_JUDGE_WRITTEN_REPRESENTATION() {

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
    void when_taskId_then_return_decision_making_work_for_urgent_RESPOND_TO_JUDGE_DIRECTIONS() {

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
    void when_taskId_then_return_decision_making_work_for_nonurgent_RESPOND_TO_JUDGE_DIRECTIONS() {

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
    void when_taskId_then_return_decision_making_work_for_urgent_RESPOND_TO_JUDGE_ADDITIONAL_INFO() {

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
    void when_taskId_then_return_decision_making_work_for_nonurgent_RESPOND_TO_JUDGE_ADDITIONAL_INFO() {

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
    void when_taskId_then_return_decision_making_work_for_urgent_LISTED_FOR_A_HEARING() {

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
    void when_taskId_then_return_decision_making_work_for_nonurgent_LISTED_FOR_A_HEARING() {

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
    void when_taskId_then_return_decision_making_work_for_urgent_ADDITIONAL_RESPONSE_TIME_EXPIRED() {

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
    void when_taskId_then_return_decision_making_work_for_nonurgent_ADDITIONAL_RESPONSE_TIME_EXPIRED() {

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
    void when_ADDITIONAL_RESPONSE_TIME_EXPIRED_for_urgent_app_post_event_RESPOND_TO_JUDGE_ADDITIONAL_INFO() {

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
    void when_ADDITIONAL_RESPONSE_TIME_EXPIRED_for_nonurgent_app_post_event_RESPOND_TO_JUDGE_ADDITIONAL_INFO() {

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
    void when_ADDITIONAL_RESPONSE_TIME_EXPIRED_for_urgent_app_post_event_RESPOND_TO_JUDGE_DIRECTIONS() {

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
    void when_ADDITIONAL_RESPONSE_TIME_EXPIRED_for_nonurgent_app_post_event_RESPOND_TO_JUDGE_DIRECTIONS() {

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
    void when_ADDITIONAL_RESPONSE_TIME_EXPIRED_for_urgent_app_post_event_RESPOND_TO_JUDGE_WRITTEN_REP() {

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
    void when_ADDITIONAL_RESPONSE_TIME_EXPIRED_for_nonurgent_app_post_event_RESPOND_TO_JUDGE_WRITTEN_REP() {

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
