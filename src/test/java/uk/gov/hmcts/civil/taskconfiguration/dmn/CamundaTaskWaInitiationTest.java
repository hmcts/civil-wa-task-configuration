package uk.gov.hmcts.civil.taskconfiguration.dmn;

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
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class CamundaTaskWaInitiationTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = DmnDecisionTable.WA_TASK_INITIATION_CIVIL_DAMAGES;
    }

    @ParameterizedTest
    @MethodSource("scenarioProvider")
    void given_input_should_return_outcome_dmn(String eventId,
                                                      String postEventState,
                                                      Map<String, ? extends Serializable> expectedDmnOutcome) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        inputVariables.putValue("postEventState", postEventState);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        assertThat(dmnDecisionTableResult.getResultList(), is(singletonList(expectedDmnOutcome)));
    }

    public static Stream<Arguments> scenarioProvider() {
        return Stream.of(
            Arguments.of(
                "NOTIFY_INTERIM_JUDGMENT_DEFENDANT", "JUDICIAL_REFERRAL",
                Map.of(
                    "taskId", "summaryJudgmentDirections",
                    "name", "Directions after Judgment (Damages)",
                    "processCategories","defaultJudgment"
                )
            ),
            Arguments.of(
                "STANDARD_DIRECTION_ORDER_DJ", "CASE_PROGRESSION",
                Map.of(
                    "taskId", "transferCaseOffline",
                    "name", "Transfer Case Offline",
                    "processCategories","defaultJudgment"
                )
            )
        );
    }

    @Test
    void when_not_suitable_sdo_change_location_recreate_sdo_task_allocated_smallclaimTrackDirections() {

        Map<String, Object> data = new HashMap<>();
        data.put("claimValue", Map.of(
            "statementOfValueInPennies", 120000
        ));
        data.put("allocatedTrack", "SMALL_CLAIM");
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
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getRules().size(), is(76));
    }
}
