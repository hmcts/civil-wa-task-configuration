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
import uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTableBaseUnitTest;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTable.WA_TASK_CANCELLATION_CIVIL_DAMAGES;

class CamundaTaskWaCancellationTest extends DmnDecisionTableBaseUnitTest {
    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = WA_TASK_CANCELLATION_CIVIL_DAMAGES;
    }

    @ParameterizedTest
    @MethodSource({"scenarioTakesCaseOfflineEventProceedsInHeritageSystem",
        "scenarioTakesCaseOfflineEventProceedsInHeritageSystem_ForReviewCaseFlags",
        "scenarioTakesCaseOfflineEventCaseDismissedSystem", "scenarioProviderRoutineTransfer",
        "scenarioProviderCaseFlags","scenarioTransferCaseOnlineReconfigure","scenarioRetriggerCasesReconfigure",
        "scenarioTakesCaseOfflineEventProceedsInHeritageSystem_ForMci", "scenarioProceedsInHeritageSystem",
        "scenarioUpdateNextHearingDetailsCasesReconfigure", "scenarioUpdateNextHearingInfoCasesReconfigure",
        "scenarioNotSuitableSdoCancelTasks", "scenarioWhenCaseIsStayed"})
    void given_multiple_event_ids_should_evaluate_dmn(String fromState,
                                                      String eventId,
                                                      String state,
                                                      List<Map<String, Object>> expectedDmnOutcome) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("fromState", fromState);
        inputVariables.putValue("event", eventId);
        inputVariables.putValue("state", state);

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertThat(dmnDecisionTableResult.getResultList(), is(expectedDmnOutcome));
    }

    public static Stream<Arguments> scenarioProviderCaseFlags() {
        List<Map<String, String>> outcome = List.of(
            Map.of(
                "action", "ReConfigure"
            )
        );
        return Stream.of(
            Arguments.of(
                "", "CREATE_CASE_FLAGS", "any state",
                outcome
            ),
            Arguments.of(
                "", "MANAGE_CASE_FLAGS", "",
                outcome
            )
        );
    }

    public static Stream<Arguments> scenarioProviderRoutineTransfer() {
        List<Map<String, String>> outcome = List.of(
            Map.of(
                "action", "Cancel",
                "processCategories", "defaultJudgment"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "standardDirectionsOrder"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "routineTransfer"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "decisionOnReconsideration"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "requestTranslation"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "orderJudgmentSetAside"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "helpWithFees"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "claimantIntention"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "paidInFull"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "intentionToProceed"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "updateContactInformation"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "RemoveHearing"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "NationalRollout"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "multiOrIntermediateClaim"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "discontinued"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "setAsideJo"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "sendReplyMessage"
            )
        );
        return Stream.of(
            Arguments.of(
                null, "CASE_PROCEEDS_IN_CASEMAN", null,
                outcome
            )
        );
    }

    public static Stream<Arguments> scenarioTakesCaseOfflineEventProceedsInHeritageSystem() {
        List<Map<String, String>> outcome = List.of(
            Map.of(
                "action", "Cancel",
                "processCategories", "defaultJudgment"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "standardDirectionsOrder"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "routineTransfer"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "decisionOnReconsideration"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "requestTranslation"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "updateContactInformation"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "orderJudgmentSetAside"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "helpWithFees"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "claimantIntention"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "paidInFull"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "intentionToProceed"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "updateContactInformation"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "RemoveHearing"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "NationalRollout"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "multiOrIntermediateClaim"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "discontinued"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "setAsideJo"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "sendReplyMessage"
            )
        );
        return Stream.of(
            Arguments.of(
                "PROCEEDS_IN_HERITAGE_SYSTEM", "CASE_PROCEEDS_IN_CASEMAN", "any state",
                outcome
            ),
            Arguments.of(
                "PROCEEDS_IN_HERITAGE_SYSTEM", "CASE_PROCEEDS_IN_CASEMAN", "",
                outcome
            ),
            Arguments.of(
                "PROCEEDS_IN_HERITAGE_SYSTEM", "CASE_PROCEEDS_IN_CASEMAN", null,
                outcome
            )
        );
    }

    public static Stream<Arguments> scenarioTakesCaseOfflineEventProceedsInHeritageSystem_ForReviewCaseFlags() {
        List<Map<String, String>> outcome = List.of(
            Map.of(
                "action", "Cancel",
                "processCategories", "defaultJudgment"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "standardDirectionsOrder"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "reviewCaseFlags"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "caseProgression"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "routineTransfer"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "decisionOnReconsideration"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "requestTranslation"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "orderJudgmentSetAside"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "helpWithFees"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "claimantIntention"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "paidInFull"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "intentionToProceed"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "updateContactInformation"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "RemoveHearing"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "NationalRollout"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "multiOrIntermediateClaim"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "discontinued"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "setAsideJo"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "sendReplyMessage"
            )
        );
        return Stream.of(
            Arguments.of(
                "any state", "CASE_PROCEEDS_IN_CASEMAN", "PROCEEDS_IN_HERITAGE_SYSTEM",
                outcome
            ),
            Arguments.of(
                null, "CASE_PROCEEDS_IN_CASEMAN", "PROCEEDS_IN_HERITAGE_SYSTEM",
                outcome
            )
        );
    }

    public static Stream<Arguments> scenarioTakesCaseOfflineEventProceedsInHeritageSystem_ForMci() {
        List<Map<String, String>> outcome = List.of(
            Map.of(
                "action", "Cancel",
                "processCategories", "updateContactInformation"
            )
        );
        return Stream.of(
            Arguments.of(
                "PROCEEDS_IN_HERITAGE_SYSTEM", null, null,
                outcome
            )
        );
    }

    public static Stream<Arguments> scenarioTakesCaseOfflineEventCaseDismissedSystem() {
        List<Map<String, String>> outcome = List.of(
            Map.of(
                "action", "Cancel",
                "processCategories", "reviewCaseFlags"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "requestTranslation"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "updateContactInformation"
            )
        );
        return Stream.of(
            Arguments.of(
                "CASE_DISMISSED",
                null,
                null,
                outcome
            )
        );
    }

    public static Stream<Arguments> scenarioTransferCaseOnlineReconfigure() {
        List<Map<String, String>> outcome = List.of(
            Map.of(
                "action", "ReConfigure"
            )
        );
        return Stream.of(
            Arguments.of(
                "",
                "TRANSFER_ONLINE_CASE",
                "",
                outcome
            )
        );
    }

    public static Stream<Arguments> scenarioRetriggerCasesReconfigure() {
        List<Map<String, String>> outcome = List.of(
            Map.of(
                "action", "ReConfigure"
            )
        );
        return Stream.of(
            Arguments.of(
                "",
                "RETRIGGER_CASES",
                "",
                outcome
            )
        );
    }

    public static Stream<Arguments> scenarioUpdateNextHearingDetailsCasesReconfigure() {
        List<Map<String, String>> outcome = List.of(
            Map.of(
                "action", "ReConfigure"
            )
        );
        return Stream.of(
            Arguments.of(
                "",
                "UPDATE_NEXT_HEARING_DETAILS",
                "",
                outcome
            )
        );
    }

    public static Stream<Arguments> scenarioUpdateNextHearingInfoCasesReconfigure() {
        List<Map<String, String>> outcome = List.of(
            Map.of(
                "action", "ReConfigure"
            )
        );
        return Stream.of(
            Arguments.of(
                "",
                "UpdateNextHearingInfo",
                "",
                outcome
            )
        );
    }

    public static Stream<Arguments> scenarioProceedsInHeritageSystem() {
        List<Map<String, String>> outcome = List.of(
            Map.of(
                "action", "Cancel",
                "processCategories", "defaultJudgment"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "standardDirectionsOrder"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "reviewCaseFlags"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "caseProgression"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "routineTransfer"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "decisionOnReconsideration"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "requestTranslation"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "updateContactInformation"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "orderJudgmentSetAside"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "RemoveHearing"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "helpWithFees"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "claimantIntention"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "paidInFull"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "intentionToProceed"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "NationalRollout"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "discontinued"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "multiOrIntermediateClaim"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "setAsideJo"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "sendReplyMessage"
            )
        );
        return Stream.of(
            Arguments.of(
                null, "PROCEEDS_IN_HERITAGE_SYSTEM", null,
                outcome
            )
        );
    }

    public static Stream<Arguments> scenarioNotSuitableSdoCancelTasks() {
        List<Map<String, String>> outcome = List.of(
            Map.of(
                "action", "Cancel",
                "processCategories", "standardDirectionsOrder"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "defaultJudgment"
            )
        );
        return Stream.of(
            Arguments.of(
                "",
                "NotSuitable_SDO",
                "",
                outcome
            )
        );
    }

    public static Stream<Arguments> scenarioWhenCaseIsStayed() {
        List<Map<String, String>> outcome = List.of(
            Map.of(
                "action", "Cancel",
                "processCategories", "standardDirectionsOrder"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "caseProgression"
            ),
            Map.of(
                "action", "Cancel",
                "processCategories", "defaultJudgment"
            )
        );
        return Stream.of(
            Arguments.of(
                null,
                "STAY_CASE",
                null,
                outcome
            )
        );
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getInputs().size(), is(3));
        assertThat(logic.getOutputs().size(), is(4));
        assertThat(logic.getRules().size(), is(54));
    }
}
