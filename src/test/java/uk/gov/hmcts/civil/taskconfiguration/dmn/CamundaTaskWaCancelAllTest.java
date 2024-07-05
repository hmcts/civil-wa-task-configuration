package uk.gov.hmcts.civil.taskconfiguration.dmn;

import com.microsoft.applicationinsights.web.dependencies.apachecommons.io.IOUtils;
import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableRuleImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.util.StringUtils;
import uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTableBaseUnitTest;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTable.WA_TASK_CANCELLATION_CIVIL_DAMAGES;
import static uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTable.WA_TASK_INITIATION_CIVIL_DAMAGES;

class CamundaTaskWaCancelAllTest {

    private static DmnEngine dmnEngine;
    private static DmnDecision cancelDecision;
    private static DmnDecision initiateDecision;

    @BeforeAll
    static void setUp() {
        dmnEngine = DmnEngineConfiguration
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
     */
    @Test
    void caseProceedsInCaseMan_cancelAllTasks() {
        int initiateProcessCategoryIndex = 3;
        int cancelEventIndex = 1;
        int cancelActionIndex = 0;
        int cancelProcessCategoryIndex = 3;
        String eventName = "\"CASE_PROCEEDS_IN_CASEMAN\"";
        String cancelActionName = "\"Cancel\"";

        Set<String> categoryIdentifiers = getProcessIdentifiers(initiateProcessCategoryIndex);

        categoryIdentifiers.removeAll(getCancelledProcessIdentifiers(cancelEventIndex,
                                                                     cancelActionIndex,
                                                                     cancelProcessCategoryIndex,
                                                                     eventName,
                                                                     cancelActionName));

        Assertions.assertTrue(categoryIdentifiers.isEmpty());
    }

    private static Set<String> getCancelledProcessIdentifiers(int cancelEventIndex,
                                                              int cancelActionIndex,
                                                              int cancelProcessCategoryIndex,
                                                              String eventName,
                                                              String cancelActionName) {
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

    private static Set<String> getProcessIdentifiers(int initiateProcessCategoryIndex) {
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
