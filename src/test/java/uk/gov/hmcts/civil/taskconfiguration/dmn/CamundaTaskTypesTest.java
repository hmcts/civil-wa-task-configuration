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
                "taskTypeId", "ClaimSettledDivergenceTakeCaseOffline",
                "taskTypeName", "Claim Settled Divergence - Take Case Offline"
            ),
            Map.of(
                "taskTypeId", "ClaimDiscontinuedDivergenceTakeCaseOffline",
                "taskTypeName", "Claim Discontinued Divergence - Take Case Offline"
            ),
            Map.of(
                "taskTypeId", "ClaimDiscontinuedRemoveHearing",
                "taskTypeName", "Claim Discontinued - Remove Hearing"
            ),
            Map.of(
                "taskTypeId", "ValidateDiscontinuanceCTSC",
                "taskTypeName", "Claim Discontinued - Validate Discontinuance"
            ),
            Map.of(
                "taskTypeId", "ValidateDiscontinuanceAdmin",
                "taskTypeName", "Claim Discontinued - Validate Discontinuance"
            ),
            Map.of(
                "taskTypeId", "JudgmentOnlineSetAsideTakeCaseOffline",
                "taskTypeName", "Set Aside - Take Case Offline"
            ),
            Map.of(
                "taskTypeId", "claimantWelshRequest",
                "taskTypeName", "Claimant Welsh Request"
            ),
            Map.of(
                "taskTypeId", "HelpWithFeesClaimIssue",
                "taskTypeName", "Help With Fees Claim Issue"
            ),
            Map.of(
                "taskTypeId", "transferCaseOfflineLiP",
                "taskTypeName", "Transfer Case Offline"
            ),
            Map.of(
                "taskTypeId", "applicationDocumentsWelshRequestAppSum",
                "taskTypeName", "Application Documents Welsh Request - Application Summary document"
            ),
            Map.of(
                "taskTypeId", "applicationDocumentsWelshRequestOrderMade",
                "taskTypeName", "Application Documents Welsh Request - General Order"
            ),
            Map.of(
                "taskTypeId", "applicationDocumentsWelshRequestHearingOrder",
                "taskTypeName", "Application Documents Welsh Request - Hearing Order"
            ),
            Map.of(
                "taskTypeId", "applicationDocumentsWelshRequestWithNotice",
                "taskTypeName", "Application Documents Welsh Request - Without notice to notice document"
            ),
            Map.of(
                "taskTypeId", "applicationDocumentsWelshRequestRespondToMoreInfo",
                "taskTypeName", "Application Documents Welsh Request - Response to the Request for more information"
            ),
            Map.of(
                "taskTypeId", "applicationDocumentsWelshRequestRespondToWrittenRep",
                "taskTypeName", "Application Documents Welsh Request - Response to Written Representations"
            ),
            Map.of(
                "taskTypeId", "applicationDocumentsWelshRequestAddlDoc",
                "taskTypeName", "Application Documents Welsh Request - Uploaded Documents"
            ),
            Map.of(
                "taskTypeId", "applicationDocumentsWelshRequestRespondToJudge",
                "taskTypeName", "Application Documents Welsh Request - Respond to a Judge Direction Order"
            ),
            Map.of(
                "taskTypeId", "HelpWithFeesAdditionalApplicationFee",
                "taskTypeName", "Help With Fees Additional Application Fee"
            ),
            Map.of(
                "taskTypeId", "HelpWithFeesApplicationFee",
                "taskTypeName", "Help With Fees Application Fee"
            ),
            Map.of(
                "taskTypeId", "applicationDocumentsWelshRequestMoreInfo",
                "taskTypeName", "Application Documents Welsh Request - Request for More Information"
            ),
            Map.of(
                "taskTypeId", "applicationDocumentsWelshRequestWrittenResp",
                "taskTypeName", "Application Documents Welsh Request - Written Representations"
            ),
            Map.of(
                "taskTypeId", "applicationDocumentsWelshRequestHearingSchedule",
                "taskTypeName", "Application Documents Welsh Request - Hearing Notice"
            ),
            Map.of(
                "taskTypeId", "applicationDocumentsWelshRequestAppDismissed",
                "taskTypeName", "Application Documents Welsh Request - Dismissal Order"
            ),
            Map.of(
                "taskTypeId", "applicationDocumentsWelshRequestJudgeDirection",
                "taskTypeName", "Application Documents Welsh Request - Judges Directions"
            ),
            Map.of(
                "taskTypeId", "applicationDocumentsWelshRequestFinalOrder",
                "taskTypeName", "Application Documents Welsh Request - Final Order"
            ),
            Map.of(
                "taskTypeId", "manageStay",
                "taskTypeName", "Manage Stay"
            ),
            Map.of(
                "taskTypeId", "createHearingNoticeMT",
                "taskTypeName", "Create a hearing notice"
            ),
            Map.of(
                "taskTypeId", "createHearingNoticeInt",
                "taskTypeName", "Create a hearing notice"
            ),
            Map.of(
                "taskTypeId", "allocateMultiTrack",
                "taskTypeName", "Multi Track Directions"
            ),
            Map.of(
                "taskTypeId", "allocateMultiTrack",
                "taskTypeName", "Multi Track Directions - Clinical Negligence"
            ),
            Map.of(
                "taskTypeId", "allocateMultiTrack",
                "taskTypeName", "Multi Track Directions - Serious Personal Injury"
            ),
            Map.of(
                "taskTypeId", "allocateIntermediateTrack",
                "taskTypeName", "Intermediate Track Directions"
            ),
            Map.of(
                "taskTypeId", "allocateIntermediateTrack",
                "taskTypeName", "Intermediate Track Directions - Clinical Negligence"
            ),
            Map.of(
                "taskTypeId", "damagesListCMCMulti",
                "taskTypeName", "List a CMC"
            ),
            Map.of(
                "taskTypeId", "damagesListCMCMulti",
                "taskTypeName", "List a Multi Track hearing"
            ),
            Map.of(
                "taskTypeId", "damagesListCCMCMulti",
                "taskTypeName", "List a CCMC"
            ),
            Map.of(
                "taskTypeId", "damagesListPtrMulti",
                "taskTypeName", "List a PTR"
            ),
            Map.of(
                "taskTypeId", "damagesListTrialMulti",
                "taskTypeName", "List a Trial"
            ),
            Map.of(
                "taskTypeId", "damagesListCMCInt",
                "taskTypeName", "List a CMC"
            ),
            Map.of(
                "taskTypeId", "damagesListCMCInt",
                "taskTypeName", "List a Intermediate Track hearing"
            ),
            Map.of(
                "taskTypeId", "damagesListPtrInt",
                "taskTypeName", "List a PTR"
            ),
            Map.of(
                "taskTypeId", "damagesListTrialInt",
                "taskTypeName", "List a Trial"
            ),
            Map.of(
                "taskTypeId", "specifiedListCMCMulti",
                "taskTypeName", "List a CMC"
            ),
            Map.of(
                "taskTypeId", "specifiedListCMCMulti",
                "taskTypeName", "List a Multi Track hearing"
            ),
            Map.of(
                "taskTypeId", "specifiedListCCMCMulti",
                "taskTypeName", "List a CCMC"
            ),
            Map.of(
                "taskTypeId", "specifiedListPTRMulti",
                "taskTypeName", "List a PTR"
            ),
            Map.of(
                "taskTypeId", "specifiedListTrialMulti",
                "taskTypeName", "List a Trial"
            ),
            Map.of(
                "taskTypeId", "specifiedListCMCInt",
                "taskTypeName", "List a CMC"
            ),
            Map.of(
                "taskTypeId", "specifiedListCMCInt",
                "taskTypeName", "List a Intermediate Track hearing"
            ),
            Map.of(
                "taskTypeId", "specifiedListPTRInt",
                "taskTypeName", "List a PTR"
            ),
            Map.of(
                "taskTypeId", "specifiedListTrialInt",
                "taskTypeName", "List a Trial"
            ),
            Map.of(
                "taskTypeId", "ScheduleHMCHearing",
                "taskTypeName", "Schedule HMC Hearing"
            ),
            Map.of(
                "taskTypeId", "RemoveHMCHearing",
                "taskTypeName", "Remove HMC Hearing"
            ),
            Map.of(
                "taskTypeId", "removeHearing",
                "taskTypeName", "Remove Hearing"
            ),
            Map.of(
                "taskTypeId", "transferCaseOfflineMinti",
                "taskTypeName", "Transfer Case Offline"
            ),
            Map.of(
                "taskTypeId", "LegalAdvisorRevisitApplication",
                "taskTypeName", "Legal Advisor Revisit Application"
            ),
            Map.of(
                "taskTypeId", "JudgeRevisitApplication",
                "taskTypeName", "Judge Revisit Application"
            ),
            Map.of(
                "taskTypeId", "JudgeDecideOnApplication",
                "taskTypeName", "Judge Decide On Application"
            ),
            Map.of(
            "taskTypeId", "ReviewApplication",
            "taskTypeName", "Review Application"
            ),
            Map.of(
                "taskTypeId", "ReviewRevisitedApplication",
                "taskTypeName", "Review Revisited Application"
            ),
            Map.of(
                "taskTypeId", "ScheduleApplicationHearing",
                "taskTypeName", "Schedule Application Hearing"
            ),
            Map.of(
                "taskTypeId", "ReviewApplicationOrder",
                "taskTypeName", "Review Application Order"
            ),
            Map.of(
                "taskTypeId", "ReviewStayTheClaimApplicationOrder",
                "taskTypeName", "Review Stay The Claim Application Order"
            ),
            Map.of(
                "taskTypeId", "ReviewUnlessOrderApplication",
                "taskTypeName", "Review Unless Order Application"
            ),
            Map.of(
                "taskTypeId", "LegalAdvisorDecideOnApplication",
                "taskTypeName", "Legal Advisor Decide On Application"
            ),
            Map.of(
                "taskTypeId", "respondToQueryCTSC",
                "taskTypeName", "Respond to a query"
            ),
            Map.of(
                "taskTypeId", "respondToQueryAdmin",
                "taskTypeName", "Respond to a hearing related query"
            ),
            Map.of(
                "taskTypeId", "reviewMessageJudicial",
                "taskTypeName", "Review message"
            ),
            Map.of(
                "taskTypeId", "reviewMessageCW",
                "taskTypeName", "Review message"
            ),
            Map.of(
                "taskTypeId", "reviewMessageLA",
                "taskTypeName", "Review message"
            ),
            Map.of(
                "taskTypeId", "reviewMessageWLU",
                "taskTypeName", "Review message"
            ),
            Map.of(
                "taskTypeId", "uploadTranslatedOrderDocument",
                "taskTypeName", "Upload Translated Order Document"
            ),
            Map.of(
                "taskTypeId", "finalOrderIssuedWelshRequest",
                "taskTypeName", "Final Order Issued Welsh Request"
            ),
            Map.of(
                "taskTypeId", "respondToQueryWLU",
                "taskTypeName", "Respond to a query - Welsh"
            ),
            Map.of(
                "taskTypeId", "respondToHearingQueryWLU",
                "taskTypeName", "Respond to a hearing related query - Welsh"
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

        assertThat(dmnDecisionTableResult.getResultList().size(), is(104));
    }

}
