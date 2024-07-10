package uk.gov.hmcts.civil.taskconfiguration.dmn;

import com.microsoft.applicationinsights.web.dependencies.apachecommons.io.IOUtils;
import org.camunda.bpm.dmn.engine.DmnDecision;
import org.camunda.bpm.dmn.engine.DmnDecisionResult;
import org.camunda.bpm.dmn.engine.DmnDecisionResultEntries;
import org.camunda.bpm.dmn.engine.DmnEngine;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTable;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class TranslateWhenSdoTest {

    private static DmnEngine dmnEngine;
    private static final Map<DmnDecisionTable, DmnDecision> decisionMap = new HashMap<>();

    @BeforeAll
    static void prepareEngine() {
        dmnEngine = DmnEngineConfiguration
            .createDefaultDmnEngineConfiguration()
            .buildEngine();

        // Parse decision
        ClassLoader contextClassLoader = Thread.currentThread().getContextClassLoader();

        for (DmnDecisionTable table : EnumSet.of(
            DmnDecisionTable.WA_TASK_CANCELLATION_CIVIL_DAMAGES,
            DmnDecisionTable.WA_TASK_COMPLETION_CIVIL_DAMAGES,
            DmnDecisionTable.WA_TASK_INITIATION_CIVIL_DAMAGES,
            DmnDecisionTable.WA_TASK_CONFIGURATION_CIVIL_DAMAGES,
            DmnDecisionTable.WA_TASK_PERMISSIONS_CIVIL_DAMAGES
        )) {
            InputStream inputStream = contextClassLoader.getResourceAsStream(table.getFileName());
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
            DmnDecision decision = dmnEngine.parseDecision(table.getKey(), inputStream);
            decisionMap.put(table, decision);
        }
    }

    /**
     * Tests that the event creates the task in initiation, that the permissions and the configuration happen
     * as expected and that UPLOAD_TRANSLATED_DOCUMENT event completes the task.
     *
     * @param eventThatCreates name of the event that should create the task
     * @param expectedTaskId   id of the task that should be created
     */
    @ParameterizedTest
    @CsvSource({"CREATE_SDO, createSdoWelshRequest",
        "DECISION_ON_RECONSIDERATION_REQUEST, reconsiderationSdoWelshRequest"})
    void whenCreateSdo_thenTranslate(String eventThatCreates, String expectedTaskId) {
        String expectedProcessCategory = "requestTranslation";

        Map<String, Object> caseData = Map.of(
            "featureToggleWA", "CUI_CP",
            "claimantBilingualLanguagePreference", "BOTH",
            "applicant1", Map.of(
                "partyName", "Applicant 1"
            )
        );
        eventCreatesUploadTranslatedTask(eventThatCreates, expectedProcessCategory, expectedTaskId, caseData);
        taskConfiguredAsExpected(expectedTaskId, caseData);
        taskPermissionsAsExpected(expectedTaskId, caseData);
        uploadTranslatedEventCompletesTask(expectedTaskId);
    }

    private static void uploadTranslatedEventCompletesTask(String expectedTaskId) {
        Map<String, Object> variables = Map.of("eventId", "UPLOAD_TRANSLATED_DOCUMENT");
        DmnDecisionResult completion = dmnEngine.evaluateDecision(
            decisionMap.get(DmnDecisionTable.WA_TASK_COMPLETION_CIVIL_DAMAGES),
            variables
        );
        Assertions.assertTrue(completion.stream()
                                  .anyMatch(r ->
                                                r.get("taskType").equals(expectedTaskId)
                                                    && r.get("completionMode").equals("Auto")
                                  ));
    }

    private static void taskPermissionsAsExpected(String expectedTaskId, Map<String, Object> caseData) {
        Map<String, Object> variables = Map.of(
            "case", "any",
            "caseData", caseData,
            "taskAttributes", Map.of("taskType", expectedTaskId)
        );
        DmnDecisionResult permissions = dmnEngine.evaluateDecision(
            decisionMap.get(DmnDecisionTable.WA_TASK_PERMISSIONS_CIVIL_DAMAGES),
            variables
        );
        Assertions.assertTrue(permissions.stream().anyMatch(
            r -> r.get("name").equals("hearing-centre-admin")
        ));
        Assertions.assertTrue(permissions.stream().anyMatch(
            r -> r.get("name").equals("hearing-centre-admin-team-leader")
        ));
    }

    private static void taskConfiguredAsExpected(String expectedTaskId, Map<String, Object> caseData) {
        Map<String, Object> variables = Map.of(
            "caseData", caseData,
            "taskAttributes", Map.of(
                "taskType", expectedTaskId
            )
        );
        DmnDecisionResult configuration = dmnEngine.evaluateDecision(
            decisionMap.get(DmnDecisionTable.WA_TASK_CONFIGURATION_CIVIL_DAMAGES),
            variables
        );
        Assertions.assertTrue(
            configuration.stream()
                .anyMatch(r ->
                              r.get("name").equals("description")
                                  && ((String) r.get("value"))
                                  .contains(
                                      "/cases/case-details/${[CASE_REFERENCE]}/trigger/UPLOAD_TRANSLATED_DOCUMENT/"
                                          + "UPLOAD_TRANSLATED_DOCUMENTUploadTranslatedDocument")
                )
        );
        Assertions.assertTrue(configuration.stream().anyMatch(r -> r.get("name").equals("workType")));
        Assertions.assertTrue(configuration.stream().anyMatch(r -> r.get("name").equals("roleCategory")));
        Assertions.assertTrue(configuration.stream().anyMatch(r -> r.get("name").equals("dueDateIntervalDays")));
    }

    private static void eventCreatesUploadTranslatedTask(
        String eventThatCreates, String expectedProcessCategory, String expectedTaskId, Map<String, Object> caseData) {
        Map<String, Object> variables = Map.of(
            "eventId", eventThatCreates,
            "postEventState", "any",
            "additionalData", Map.of(
                "Data", caseData
            )
        );
        DmnDecisionResultEntries initiation = dmnEngine.evaluateDecision(
            decisionMap.get(DmnDecisionTable.WA_TASK_INITIATION_CIVIL_DAMAGES),
            variables
        ).get(0);
        Assertions.assertEquals(initiation.get("processCategories"), expectedProcessCategory);
        Assertions.assertEquals(initiation.get("taskId"), expectedTaskId);
    }
}
