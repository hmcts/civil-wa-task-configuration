package uk.gov.hmcts.civil.taskconfiguration.dmn;

import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTable;
import uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTableBaseUnitTest;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class CamundaTaskTypesTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = DmnDecisionTable.WA_TASK_TYPES_CIVIL_DAMAGES;
    }

    @ParameterizedTest
    @MethodSource({"scenarioProvider"})
    void should_evaluate_dmn_and_verify_task_types(List<Map<String, Object>> expectedDmnOutcome) {
        VariableMap inputVariables = new VariableMapImpl();

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertThat(dmnDecisionTableResult.getResultList(), is(expectedDmnOutcome));
    }

    public static Stream<Arguments> scenarioProvider() {
        List<Map<String, String>> outcome = List.of(
            Map.of(
                "taskTypeId", "summaryJudgmentDirections",
                "taskTypeName", "Directions after Judgment"
            ),
            Map.of(
                "taskTypeId", "FastTrackDirections",
                "taskTypeName", "Fast Track Directions"
            ),
            Map.of(
                "taskTypeId", "transferCaseOffline",
                "taskTypeName", "Transfer Case Offline"
            ),
            Map.of(
                "taskTypeId", "transferCaseOffline",
                "taskTypeName", "Transfer Case Offline"
            ),
            Map.of(
                "taskTypeId", "SmallClaimsTrackDirections",
                "taskTypeName", "Small Claims Track Directions"
            ),
            Map.of(
                "taskTypeId", "LegalAdvisorSmallClaimsTrackDirections",
                "taskTypeName", "Small Claims Track Directions - Legal Advisor"
            ),
            Map.of(
                "taskTypeId", "SmallClaimsTrackDirectionsReferral",
                "taskTypeName", "Small Claims Track Directions - Referral"
            ),
            Map.of(
                "taskTypeId", "transferCaseOfflineNotSuitableSDO",
                "taskTypeName", "Confirm Case Offline"
            ),
            Map.of(
                "taskTypeId", "reviewSpecificAccessRequestJudiciary",
                "taskTypeName", "Specific Access Request - Judiciary"
            ),
            Map.of(
                "taskTypeId", "reviewSpecificAccessRequestLegalOps",
                "taskTypeName", "Specific Access Request - LegalOps"
            ),
            Map.of(
                "taskTypeId", "reviewSpecificAccessRequestAdmin",
                "taskTypeName", "Specific Access Request - Admin"
            ),
            Map.of(
                "taskTypeId", "reviewSpecificAccessRequestCTSC",
                "taskTypeName", "Specific Access Request - CTSC"
            ),
            Map.of(
                "taskTypeId", "ReviewCaseFlagsForClaimant",
                "taskTypeName", "Review Case Flags Claimant"
            ),
            Map.of(
                "taskTypeId", "ReviewCaseFlagsForDefendant",
                "taskTypeName", "Review Case Flags Defendant"
            ),
            Map.of(
                "taskTypeId", "ScheduleAHearing",
                "taskTypeName", "Schedule a Hearing"
            ),
            Map.of(
                "taskTypeId", "removeHearing",
                "taskTypeName", "Remove a Scheduled Hearing"
            ),
            Map.of(
                "taskTypeId", "preHearingContact",
                "taskTypeName", "Hearing/Trial Readiness - Pre hearing"
            ),
            Map.of(
                "taskTypeId", "adjournedReList",
                "taskTypeName", "Hearing/Trial Readiness - Adjourned"
            ),
            Map.of(
                "taskTypeId", "transferOnlineCase",
                "taskTypeName", "Request for Transfer Online Case"
            ),
            Map.of(
                "taskTypeId", "manualDetermination",
                "taskTypeName", "Manual Determination"
            ),
            Map.of(
                "taskTypeId", "defendantWelshRequest",
                "taskTypeName", "Defendant Welsh Request"
            ),
            Map.of(
                "taskTypeId", "OnlineCaseTransferReceived",
                "taskTypeName", "Online Case Transfer Received"
            ),
            Map.of(
                "taskTypeId", "InitialDirectionFlightDelay",
                "taskTypeName", "Initial Directions (Flight Delay)"
            ),
            Map.of(
                "taskTypeId", "JudgeDecideOnReconsiderRequest",
                "taskTypeName", "Decision on Reconsideration Request"
            ),
            Map.of(
                "taskTypeId", "NIHLFastTrackDirections",
                "taskTypeName", "Fast Track Directions - Noise induced hearing loss"
            ),
            Map.of(
                "taskTypeId", "HelpWithFeesHearingFee",
                "taskTypeName", "Help With Fees Hearing Fee"
            ),
            Map.of(
                "taskTypeId", "reviewHearingException",
                "taskTypeName", "Review Hearing Exception"
            ),
            Map.of(
                "taskTypeId", "sendCvpHearingLink",
                "taskTypeName", "Send CVP Hearing Link"
            ),
            Map.of(
                "taskTypeId", "UpdateDetailsInCasemanSystem",
                "taskTypeName", "Update details in caseman"
            ),
            Map.of(
                "taskTypeId", "OrderToSetAsideDefendedClaim",
                "taskTypeName", "Defence received in time - order that judgment is set aside"
            ),
            Map.of(
                "taskTypeId", "ClaimSettledRemoveHearing",
                "taskTypeName", "Claim Settled - Remove Hearing"
            )
        );

        return Stream.of(
            Arguments.of(
                outcome
            )
        );
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        VariableMap inputVariables = new VariableMapImpl();

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);

        assertThat(dmnDecisionTableResult.getResultList().size(), is(31));
    }

}
