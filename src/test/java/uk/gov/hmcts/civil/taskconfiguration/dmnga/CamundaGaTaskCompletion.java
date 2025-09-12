package uk.gov.hmcts.civil.taskconfiguration.dmnga;

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

public class CamundaGaTaskCompletion extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = DmnDecisionTable.WA_TASK_COMPLETION_CIVIL_GENERALAPPLICATION;
    }

    static Stream<Arguments> scenarioProvider() {

        return Stream.of(
            Arguments.of(
                "MAKE_DECISION",
                asList(
                    Map.of(
                        "taskType", "JudgeDecideOnApplication",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "LegalAdvisorDecideOnApplication",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "JudgeRevisitApplication",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "LegalAdvisorRevisitApplication",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "completionMode", "Auto"
                    ))),
                Arguments.of(
                    "HEARING_SCHEDULED_GA",
                    asList(
                        Map.of(
                            "taskType", "ScheduleApplicationHearing",
                            "completionMode", "Auto"
                        ),
                        Map.of(
                            "completionMode", "Auto"
                        ))),
            Arguments.of(
                "REFER_TO_JUDGE",
                asList(
                    Map.of(
                        "taskType", "LegalAdvisorDecideOnApplication",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "LegalAdvisorRevisitApplication",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "ReviewApplication",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "ReviewRevisitedApplication",
                        "completionMode", "Auto"
                    ))),
            Arguments.of(
                "REFER_TO_LEGAL_ADVISOR",
                asList(
                    Map.of(
                        "taskType", "ReviewApplication",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "ReviewRevisitedApplication",

                        "completionMode", "Auto"
                    ))),
            Arguments.of(
                "APPROVE_CONSENT_ORDER",
                asList(
                    Map.of(
                        "taskType", "ReviewApplication",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "ReviewRevisitedApplication",

                        "completionMode", "Auto"
                    ))),
            Arguments.of(
                "INITIATE_GENERAL_APPLICATION_AFTER_PAYMENT",
                asList(
                    Map.of(
                        "taskType", "HelpWithFeesApplicationFee",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "completionMode", "Auto"
                    ))),
            Arguments.of(
                "MODIFY_STATE_AFTER_ADDITIONAL_FEE_PAID",
                asList(
                    Map.of(
                        "taskType", "HelpWithFeesAdditionalApplicationFee",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "completionMode", "Auto"
                    ))),
            Arguments.of(
                "UPLOAD_TRANSLATED_DOCUMENT",
                asList(
                    Map.of(
                        "taskType", "applicationDocumentsWelshRequestAppSum",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "applicationDocumentsWelshRequestAppSumResponded",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "applicationDocumentsWelshRequestOrderMade",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "applicationDocumentsWelshRequestHearingOrder",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "applicationDocumentsWelshRequestRespondToMoreInfo",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "applicationDocumentsWelshRequestRespondToWrittenRep",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "applicationDocumentsWelshRequestAddlDoc",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "applicationDocumentsWelshRequestRespondToJudge",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "applicationDocumentsWelshRequestMoreInfo",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "applicationDocumentsWelshRequestWrittenResp",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "applicationDocumentsWelshRequestHearingSchedule",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "applicationDocumentsWelshRequestAppDismissed",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "applicationDocumentsWelshRequestJudgeDirection",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "applicationDocumentsWelshRequestFinalOrder",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "applicationDocumentsWelshRequestRespondToWrittenRepRespondent",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "applicationDocumentsWelshRequestRespondToMoreInfoRespondent",
                        "completionMode", "Auto"
                    ))),
            Arguments.of(
                "FEE_PAYMENT_OUTCOME_GA",
                asList(
                    Map.of(
                        "taskType", "HelpWithFeesApplicationFee",
                        "completionMode", "Auto"
                    ),
                    Map.of(
                        "taskType", "HelpWithFeesAdditionalApplicationFee",
                        "completionMode", "Auto"
                    )))
        );
    }

    @ParameterizedTest(name = "event id: {0}")
    @MethodSource("scenarioProvider")
    void given_event_ids_should_evaluate_dmn(String eventId, List<Map<String, String>> expectation) {

        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("eventId", eventId);
        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        MatcherAssert.assertThat(dmnDecisionTableResult.getResultList(), is(expectation));
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {

        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getRules().size(), is(28));

    }
}
