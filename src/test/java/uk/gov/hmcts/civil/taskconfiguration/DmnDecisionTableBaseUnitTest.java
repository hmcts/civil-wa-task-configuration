package uk.gov.hmcts.civil.taskconfiguration;

import com.microsoft.applicationinsights.web.dependencies.apachecommons.io.IOUtils;
import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.camunda.bpm.dmn.engine.impl.DefaultDmnEngineConfiguration;
import org.junit.jupiter.api.BeforeEach;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.Map;

public abstract class DmnDecisionTableBaseUnitTest {

    protected static DmnDecisionTable CURRENT_DMN_DECISION_TABLE;
    protected DmnEngine dmnEngine;
    protected DmnDecision decision;

    public static DmnDecision parseDecision(DmnEngine dmnEngine, DmnDecisionTable decisionTable) {
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = contextClassLoader.getResourceAsStream(decisionTable.getFileName());
        StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(new InputStreamReader(inputStream), writer);
            // nbsp is not accepted by the default parser, but Camunda does not have any actual problem
            inputStream = new ByteArrayInputStream(
                writer.toString()
                    .replaceAll("&nbsp;", " ")
                    .getBytes());
        } catch (IOException e) {
            // can't allow
            throw new RuntimeException(e);
        }
        return dmnEngine.parseDecision(decisionTable.getKey(), inputStream);
    }

    @BeforeEach
    void setUp() {
        dmnEngine = DmnEngineConfiguration
            .createDefaultDmnEngineConfiguration()
            .buildEngine();

        decision = parseDecision(dmnEngine, CURRENT_DMN_DECISION_TABLE);
    }

    public DmnDecisionTableResult evaluateDmnTable(Map<String, Object> variables) {
        return dmnEngine.evaluateDecisionTable(decision, variables);
    }

    public static class DmnEntityConfiguration extends DefaultDmnEngineConfiguration {

        public DmnEntityConfiguration() {

        }
    }
}
