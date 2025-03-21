package uk.gov.hmcts.civil.taskconfiguration.dmn;

import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.hamcrest.MatcherAssert;
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

import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class CamundaTaskCompletionTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = DmnDecisionTable.WA_TASK_COMPLETION_CIVIL_DAMAGES;
    }

    static Stream<Arguments> scenarioProvider() {

        return Stream.of(
            Arguments.of(
                "STANDARD_DIRECTION_ORDER_DJ",
                asList(
                    Map.of(
                        "taskType", "summaryJudgmentDirections",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "TAKE_CASE_OFFLINE",
                asList(
                    Map.of(
                        "taskType", "transferCaseOffline"
                    ),
                    Map.of(
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "CASE_PROCEEDS_IN_CASEMAN",
                asList(
                    Map.of(
                        "taskType", "transferCaseOffline",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "transferCaseOfflineMinti",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "manualDetermination",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "ClaimSettledDivergenceTakeCaseOffline",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "JudgmentOnlineSetAsideTakeCaseOffline",
                        "completionMode", "Auto"
                    )
                )
            )
        );
    }

    static Stream<Arguments> scenarioProviderSdo() {
        return Stream.of(
            Arguments.of(
                "CREATE_SDO",
                asList(
                    Map.of(
                        "taskType", "FastTrackDirections",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "SmallClaimsTrackDirections",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "LegalAdvisorSmallClaimsTrackDirections",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "SmallClaimsTrackDirectionsReferral",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "InitialDirectionFlightDelay",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "NIHLFastTrackDirections",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "allocateMultiTrack",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "allocateIntermediateTrack",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "NotSuitable_SDO",
                asList(
                    Map.of(
                        "taskType", "summaryJudgmentDirections",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "FastTrackDirections",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "SmallClaimsTrackDirections",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "SmallClaimsTrackDirectionsReferral",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "InitialDirectionFlightDelay",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "NIHLFastTrackDirections",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "allocateMultiTrack",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "allocateIntermediateTrack",
                        "completionMode", "Auto"
                    )
                )
            ),
            Arguments.of(
                "REFER_TO_JUDGE",
                asList(
                    Map.of(
                        "taskType", "LegalAdvisorSmallClaimsTrackDirections",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "completionMode", "Auto"
                    )
                )
            )
        );
    }

    static Stream<Arguments> scenarioProviderHearingNotice() {
        return Stream.of(
            Arguments.of(
                "HEARING_SCHEDULED",
                asList(
                    Map.of(
                        "taskType", "ScheduleAHearing",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "ScheduleHMCHearing",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "completionMode", "Auto"

                    ),
                    Map.of(
                        "taskType", "createHearingNoticeMT",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "createHearingNoticeInt",
                        "completionMode", "Auto"
                    )
                )
            )
        );
    }

    static Stream<Arguments> scenarioProviderCaseNote() {
        return Stream.of(
            Arguments.of(
                "ADD_CASE_NOTE",
                asList(
                    Map.of(
                        "taskType", "removeHearing",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "preHearingContact",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "reviewOrder",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "sendCvpHearingLink",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "reviewHearingException",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "UpdateDetailsInCasemanSystem",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "ClaimDiscontinuedRemoveHearing",
                        "completionMode", "Auto"
                    )
                )
            )
        );
    }

    static Stream<Arguments> scenarioProviderToC() {

        return Stream.of(
            Arguments.of(
                "TRANSFER_ONLINE_CASE",
                asList(
                    Map.of(
                        "taskType", "transferOnlineCase",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "completionMode", "Auto"
                    )
                )
            )
        );
    }

    static Stream<Arguments> scenarioProviderTDoRR() {

        return Stream.of(
            Arguments.of(
                "DECISION_ON_RECONSIDERATION_REQUEST",
                asList(
                    Map.of(
                        "taskType", "JudgeDecideOnReconsiderRequest",
                        "completionMode", "Auto"
                    )
                )
            )
        );
    }

    static Stream<Arguments> scenarioProviderUpTD() {
        return Stream.of(
            Arguments.of(
                "UPLOAD_TRANSLATED_DOCUMENT",
                asList(
                    Map.of(
                        "taskType", "defendantWelshRequest",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "claimantWelshRequest",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "finalOrderIssuedWelshRequest",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "uploadTranslatedOrderDocument",
                        "completionMode", "Auto"
                    )
                )
            )
        );
    }

    static Stream<Arguments> scenarioProviderReferJudge() {

        return Stream.of(
            Arguments.of(
                "GENERATE_DIRECTIONS_ORDER",
                    List.of(
                            Map.of(
                                "taskType", "OrderToSetAsideDefendedClaim",
                                "completionMode", "Auto"
                            ),
                            Map.of(
                                "completionMode", "Auto"
                            ),
                            Map.of(
                                "taskType", "allocateMultiTrack",
                                "completionMode", "Auto"
                            ),
                            Map.of(
                                "taskType", "allocateIntermediateTrack",
                                "completionMode", "Auto"
                            )
                    )
            )
        );
    }

    static Stream<Arguments> scenarioHelpWithFeeEvent() {

        return Stream.of(
            Arguments.of(
                "FEE_PAYMENT_OUTCOME",
                asList(
                    Map.of(
                        "taskType", "HelpWithFeesHearingFee",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "HelpWithFeesClaimIssue",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "completionMode", "Auto"
                    )))
        );
    }

    static Stream<Arguments> scenarioValidateDiscontinuation() {

        return Stream.of(
            Arguments.of(
                "VALIDATE_DISCONTINUE_CLAIM_CLAIMANT",
                asList(
                    Map.of(
                        "taskType", "ValidateDiscontinuance",
                        "completionMode", "Auto"
                    )
                )
            )
        );
    }

    static Stream<Arguments> bundlefailedAmendandRestich() {

        return Stream.of(
            Arguments.of(
                "AMEND_RESTITCH_BUNDLE",
                asList(
                    Map.of(
                        "taskType", "bundlefailedAmendandRestich",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "completionMode", "Auto"
                    )
                )
            )
        );
    }


    static Stream<Arguments> manageStay() {

        return Stream.of(
            Arguments.of(
                "MANAGE_STAY",
                asList(
                    Map.of(
                        "taskType", "manageStay",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "confirmOrderReviewManageStay",
                        "completionMode", "Auto"
                    )
                )
            )
        );
    }

    static Stream<Arguments> confirmOrderReview() {

        return Stream.of(
            Arguments.of(
                "CONFIRM_ORDER_REVIEW",
                asList(
                    Map.of(
                        "taskType", "reviewOrder",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "completionMode", "Auto"
                    )
                )
            )
        );
    }

    static Stream<Arguments> confirmListingScenarioProvider() {

        return Stream.of(
            Arguments.of(
                "CONFIRM_LISTING_COMPLETED",
                asList(
                    Map.of(
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "damagesListCMCMulti",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "damagesListCCMCMulti",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "damagesListPTRMulti",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "damagesListTrialMulti",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "damagesListCMCInt",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "damagesListPTRInt",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "damagesListTrialInt",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "specifiedListCMCMulti",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "specifiedListCCMCMulti",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "specifiedListPTRMulti",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "specifiedListTrialMulti",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "specifiedListCMCInt",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "specifiedListPTRInt",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "specifiedListTrialInt",
                        "completionMode", "Auto"
                    )
                )
            )
        );
    }

    static Stream<Arguments> dismissCase() {

        return Stream.of(
            Arguments.of(
                "DISMISS_CASE",
                asList(
                    Map.of(
                        "taskType", "confirmOrderReviewDismissCase",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "completionMode", "Auto"
                    )
                )
            )
        );
    }

    static Stream<Arguments> stayCase() {

        return Stream.of(
            Arguments.of(
                "STAY_CASE",
                asList(
                    Map.of(
                        "taskType", "confirmOrderReviewStayCase",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "completionMode", "Auto"
                    )
                )
            )
        );
    }

    static Stream<Arguments> scenarioRespondToQuery() {

        return Stream.of(
            Arguments.of(
                "queryManagementRespondQuery",
                asList(
                    Map.of(
                        "taskType", "respondToQueryCTSC",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "respondToQueryAdmin",
                        "completionMode", "Auto"
                    )
                )
            )
        );
    }

    @ParameterizedTest(name = "event id: {0}")
    @MethodSource({"dismissCase"})
    void given_event_ids_should_evaluate_dismiss_case_dmn(String eventId,
                                                          List<Map<String, String>> expectation) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @ParameterizedTest(name = "event id: {0}")
    @MethodSource({"stayCase"})
    void given_event_ids_should_evaluate_stay_case_dmn(String eventId,
                                                       List<Map<String, String>> expectation) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }


    @ParameterizedTest(name = "event id: {0}")
    @MethodSource({"confirmOrderReview"})
    void given_event_ids_should_evaluate_confirm_order_review_dmn(String eventId,
                                                                  List<Map<String, String>> expectation) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @ParameterizedTest(name = "event id: {0}")
    @MethodSource({"scenarioProvider"})
    void given_event_ids_should_evaluate_dmn(String eventId, List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @ParameterizedTest(name = "event id: {0}")
    @MethodSource({"scenarioProviderSdo"})
    void given_event_ids_should_evaluate_sdo_dmn(String eventId, List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @ParameterizedTest(name = "event id: {0}")
    @MethodSource({"scenarioProviderHearingNotice"})
    void given_event_ids_should_evaluate_hearingNotice_dmn(String eventId, List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @ParameterizedTest(name = "event id: {0}")
    @MethodSource({"scenarioProviderCaseNote"})
    void given_event_ids_should_evaluate_reviewOrder_dmn(String eventId, List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @ParameterizedTest(name = "event id: {0}")
    @MethodSource({"scenarioProviderToC"})
    void given_event_ids_should_evaluate_Toc_dmn(String eventId, List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @ParameterizedTest(name = "event id: {0}")
    @MethodSource({"scenarioProviderTDoRR"})
    void given_event_ids_should_evaluate_decOnReq_dmn(String eventId, List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @ParameterizedTest(name = "event id: {0}")
    @MethodSource({"scenarioProviderUpTD"})
    void given_event_ids_should_evaluate_UploadTransDoc_dmn(String eventId, List<Map<String, String>> expectation) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @ParameterizedTest(name = "event id: {0}")
    @MethodSource({"scenarioProviderReferJudge"})
    void given_event_ids_should_evaluate_ReferJudge_dmn(String eventId, List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @ParameterizedTest(name = "event id: {0}")
    @MethodSource({"scenarioValidateDiscontinuation"})
    void given_event_ids_should_evaluate_Discontinuance_dmn(String eventId, List<Map<String, String>> expectation) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @ParameterizedTest(name = "event id: {0}")
    @MethodSource({"scenarioHelpWithFeeEvent"})
    void given_event_ids_should_evaluate_hwf_dmn(String eventId, List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @ParameterizedTest(name = "event id: {0}")
    @MethodSource({"bundlefailedAmendandRestich"})
    void given_event_ids_should_evaluate_bundle_restitch_dmn(String eventId, List<Map<String, String>> expectation) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @ParameterizedTest(name = "event id: {0}")
    @MethodSource({"manageStay"})
    void given_event_ids_should_manage_stay_dmn(String eventId, List<Map<String, String>> expectation) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @ParameterizedTest(name = "event id: {0}")
    @MethodSource({"confirmListingScenarioProvider"})
    void given_event_ids_should_confirm_listing_dmn(String eventId, List<Map<String, String>> expectation) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @ParameterizedTest(name = "event id: {0}")
    @MethodSource({"scenarioRespondToQuery"})
    void given_event_ids_should_trigger_expected_config(String eventId, List<Map<String, String>> expectation) {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {

        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getRules().size(), is(78));
    }
}
