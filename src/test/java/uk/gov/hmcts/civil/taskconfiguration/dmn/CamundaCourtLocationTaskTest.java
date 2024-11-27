package uk.gov.hmcts.civil.taskconfiguration.dmn;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTable;
import uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTableBaseUnitTest;

import java.io.FileReader;
import java.io.Reader;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

class CamundaCourtLocationTaskTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = DmnDecisionTable.WA_COURT_LOCATION_FOR_TASKS;
    }

    @Test
    void verify_DMN_outputs_from_CSV() throws Exception {
        String csvFilePath = "src/main/resources/courtLocationData/CourtLocationForTasksCSV.csv";

        Reader reader = new FileReader(csvFilePath);
        Iterable<CSVRecord> records = CSVFormat.EXCEL.builder()
            .setHeader("CourtName", "caseManagementLocation", "claimTrack", "CMC", "CCMC", "PTR", "Trial")
            .setSkipHeaderRecord(true)
            .build()
            .parse(reader);

        records.forEach(record -> {
            String courtName = record.get("CourtName");
            String caseManagementLocation = record.get("caseManagementLocation");
            String claimTrack = record.get("claimTrack");
            String expectedCMC = record.get("CMC");
            String expectedCCMC = record.get("CCMC");
            String expectedTrial = record.get("Trial");
            String expectedPTR = record.get("PTR");

            VariableMap inputVariables = new VariableMapImpl();
            inputVariables.putValue("caseManagementLocation", caseManagementLocation);
            inputVariables.putValue("claimTrack", claimTrack);

            DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
            Map<String, Object> resultMap = dmnDecisionTableResult.getSingleResult();

            try {
                assertThat(resultMap.get("CMC"), is(expectedCMC));
                assertThat(resultMap.get("CCMC"), is(expectedCCMC));
                assertThat(resultMap.get("Trial"), is(expectedTrial));
                assertThat(resultMap.get("PTR"), is(expectedPTR));
            } catch (Exception e) {
                System.err.printf("Test failed for CourtName: %s, caseManagementLocation; %s, claimTrack:%s. \n Expected: CMC: %s, CCMC: %s, Trial: %s, PTR: %s",
                                  courtName, caseManagementLocation, claimTrack, expectedCMC, expectedCCMC, expectedTrial, expectedPTR);
                throw e;
            }
        });
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getRules().size(), is(280));
    }

}
