package uk.gov.hmcts.civil.taskconfiguration.dmn;

import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableRuleImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.StringUtils;
import uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTableBaseUnitTest;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTable.WA_TASK_CANCELLATION_CIVIL_DAMAGES;
import static uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTable.WA_TASK_INITIATION_CIVIL_DAMAGES;

class CamundaTaskWaCancelAllTest {

    private static final int initiateProcessCategoryIndex = 3;
    private static final int cancelEventIndex = 1;
    private static final int cancelActionIndex = 0;
    private static final int cancelProcessCategoryIndex = 3;
    private static final String cancelActionName = "\"Cancel\"";

    private static final List<String> categoriesExcludedFromCancellation = List.of(
        "qmResponseTasks",
        "queryResponseTasks",
        "queryManagement_queryID"
    );

    private static DmnDecision cancelDecision;
    private static DmnDecision initiateDecision;

    @BeforeAll
    static void setUp() {
        DmnEngine dmnEngine = DmnEngineConfiguration
            .createDefaultDmnEngineConfiguration()
            .buildEngine();

        initiateDecision = DmnDecisionTableBaseUnitTest.parseDecision(
            dmnEngine,
            WA_TASK_INITIATION_CIVIL_DAMAGES
        );
        cancelDecision = DmnDecisionTableBaseUnitTest.parseDecision(
            dmnEngine,
            WA_TASK_CANCELLATION_CIVIL_DAMAGES
        );
    }


    /**
     * When a case proceeds in caseman, all outstanding tasks need to be cancelled.
     * Please add a row in cancellation DMN if you have a new entry of a process identifier in Initiation DMN
     */
    @Test
    void caseProceedsInCaseMan_cancelAllTasks() {
        assertEventCancels(
            "\"CASE_PROCEEDS_IN_CASEMAN\"",
            Collections.emptySet(),
            categoriesExcludedFromCancellation
        );
    }

    /**
     * When a case proceeds in heritage system , all outstanding tasks need to be cancelled.
     * Please add a row in cancellation DMN if you have a new entry of a process identifier in Initiation DMN
     */
    @Test
    void caseProceedsInHeritageSystem_cancelAllTasks() {
        assertEventCancels(
            "\"PROCEEDS_IN_HERITAGE_SYSTEM\"",
            Collections.emptySet(),
            categoriesExcludedFromCancellation
        );;
    }

    /**
     * Asserts that eventName cancels all outstanding tasks in a claim,
     * except for the process ids within noNeedToCancel.
     *
     * @param eventName      the event name
     * @param noNeedToCancel process ids that don't require cancellation by eventName
     */
    private void assertEventCancels(String eventName, Collection<String> noNeedToCancel, List<String> excludedCategories) {
        Set<String> categoryIdentifiers = getProcessIdentifiers();
        // if there are process identifiers that the event should not cancel, they are here
        Optional.ofNullable(noNeedToCancel).ifPresent(categoryIdentifiers::removeAll);

        categoryIdentifiers.removeAll(getCancelledProcessIdentifiers(eventName));

        categoryIdentifiers = categoryIdentifiers.stream()
            .filter(categoryId -> excludedCategories.stream().noneMatch(categoryId::contains))
            .collect(Collectors.toSet());

        Assertions.assertTrue(categoryIdentifiers.isEmpty());
    }

    /**
     * Cancellation dmn states several tasks that should be cancelled by certain events.
     * This method considers that we should always check for individual process identifiers, because leaving that
     * field to empty cancels everything. Should we do that, then the creator of a new process identifier
     * has no indication that the identifier is going to be cancelled by eventName.
     *
     * @param eventName name of a ccd-definition event
     * @return set of process identifiers cancelled by the event
     */
    private static Set<String> getCancelledProcessIdentifiers(String eventName) {
        Set<String> cancelled = new HashSet<>();
        DmnDecisionTableImpl cancelLogic = (DmnDecisionTableImpl) cancelDecision.getDecisionLogic();
        Assertions.assertEquals("Event", cancelLogic.getInputs()
            .get(cancelEventIndex).getName());
        Assertions.assertEquals("Action", cancelLogic.getOutputs()
            .get(cancelActionIndex).getName());
        Assertions.assertEquals("Process Categories Identifiers", cancelLogic.getOutputs()
            .get(cancelProcessCategoryIndex).getName());
        for (DmnDecisionTableRuleImpl rule : cancelLogic.getRules()) {
            if (eventName.equals(rule.getConditions().get(cancelEventIndex).getExpression())
                && cancelActionName.equals(rule.getConclusions().get(cancelActionIndex).getExpression())
                && StringUtils.isNotBlank(rule.getConclusions().get(cancelProcessCategoryIndex).getExpression())
                && !rule.getConclusions().get(cancelProcessCategoryIndex).getExpression().contains(",")) {
                cancelled.add(rule.getConclusions().get(cancelProcessCategoryIndex)
                                  .getExpression().replaceAll("\"", ""));
            }
        }
        return cancelled;
    }

    /**
     * Gets the process identifiers used by the initiation dmn.
     *
     * @return different process ids in the initiation dmn.
     */
    private static Set<String> getProcessIdentifiers() {
        DmnDecisionTableImpl initiateLogic = (DmnDecisionTableImpl) initiateDecision.getDecisionLogic();
        Assertions.assertEquals("Process Categories Identifiers", initiateLogic.getOutputs()
            .get(initiateProcessCategoryIndex).getName());
        Set<String> categoryIdentifiers = new HashSet<>();
        for (DmnDecisionTableRuleImpl rule : initiateLogic.getRules()) {
            Optional.ofNullable(rule.getConclusions().get(initiateProcessCategoryIndex)
                                    .getExpression())
                .map(e -> e.replaceAll("\"", "").split("\\s*,\\s*"))
                .ifPresent(ea -> categoryIdentifiers.addAll(Arrays.asList(ea)));
        }
        return categoryIdentifiers;
    }
}
