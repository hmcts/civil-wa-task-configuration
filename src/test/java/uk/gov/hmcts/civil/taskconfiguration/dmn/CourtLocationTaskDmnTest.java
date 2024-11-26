package uk.gov.hmcts.civil.taskconfiguration.dmn;

import org.camunda.bpm.dmn.engine.DmnDecisionTableResult;
import org.camunda.bpm.dmn.engine.DmnEngineConfiguration;
import org.camunda.bpm.dmn.engine.impl.DmnDecisionTableImpl;
import org.camunda.bpm.engine.variable.VariableMap;
import org.camunda.bpm.engine.variable.impl.VariableMapImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTable;
import uk.gov.hmcts.civil.taskconfiguration.DmnDecisionTableBaseUnitTest;

import java.io.FileReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

class CourtLocationTaskDmnTest extends DmnDecisionTableBaseUnitTest {

    @BeforeAll
    public static void initialization() {
        CURRENT_DMN_DECISION_TABLE = DmnDecisionTable.WA_COURT_LOCATION_FOR_TASKS;
    }

    @Test
    void verify_ABERYSTWYTH_COUNTY_Locations_MULTI() {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseManagementLocation", CourtLocations.ABERYSTWYTH_COUNTY.getEpimm());
        inputVariables.putValue("claimTrack", "Multi");

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        List<Map<String, Object>> taskLocationCourtList = dmnDecisionTableResult.getResultList();

        dmnEngine = DmnEngineConfiguration
            .createDefaultDmnEngineConfiguration()
            .buildEngine();

        decision = parseDecision(dmnEngine, CURRENT_DMN_DECISION_TABLE);

        System.out.println("TEST" + decision);

        assertThat(taskLocationCourtList.get(0).get("CMC"), is(CourtLocations.ABERYSTWYTH_COUNTY.getEpimm()));
        assertThat(taskLocationCourtList.get(0).get("CCMC"), is(CourtLocations.ABERYSTWYTH_COUNTY.getEpimm()));
        assertThat(taskLocationCourtList.get(0).get("Trial"), is(CourtLocations.SWANSEA_COUNTY.getEpimm()));
        assertThat(taskLocationCourtList.get(0).get("PTR"), is(CourtLocations.SWANSEA_COUNTY.getEpimm()));
    }

    @Test
    void verify_ABERYSTWYTH_COUNTY_Locations_INTERMEDIATE() {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseManagementLocation", CourtLocations.ABERYSTWYTH_COUNTY.getEpimm());
        inputVariables.putValue("claimTrack", "Intermediate");

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        List<Map<String, Object>> taskLocationCourtList = dmnDecisionTableResult.getResultList();

        assertThat(taskLocationCourtList.get(0).get("CMC"), is(CourtLocations.ABERYSTWYTH_COUNTY.getEpimm()));
        assertThat(taskLocationCourtList.get(0).get("CCMC"), is(CourtLocations.ABERYSTWYTH_COUNTY.getEpimm()));
        assertThat(taskLocationCourtList.get(0).get("Trial"), is(CourtLocations.SWANSEA_COUNTY.getEpimm()));
        assertThat(taskLocationCourtList.get(0).get("PTR"), is(CourtLocations.SWANSEA_COUNTY.getEpimm()));
    }

    @Test
    void verify_ALDERSHOT_COUNTY_Locations_MULTI() {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseManagementLocation", CourtLocations.ALDERSHOT_COUNTY.getEpimm());
        inputVariables.putValue("claimTrack", "Multi");

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        List<Map<String, Object>> taskLocationCourtList = dmnDecisionTableResult.getResultList();

        assertThat(taskLocationCourtList.get(0).get("CMC"), is(CourtLocations.ALDERSHOT_COUNTY.getEpimm()));
        assertThat(taskLocationCourtList.get(0).get("CCMC"), is(CourtLocations.ALDERSHOT_COUNTY.getEpimm()));
        assertThat(taskLocationCourtList.get(0).get("Trial"), is(CourtLocations.SOUTHAMPTON_COUNTY.getEpimm()));
        assertThat(taskLocationCourtList.get(0).get("PTR"), is(CourtLocations.SOUTHAMPTON_COUNTY.getEpimm()));
    }

    @Test
    void verify_ALDERSHOT_COUNTY_Locations_INTERMEDIATE() {
        VariableMap inputVariables = new VariableMapImpl();
        inputVariables.putValue("caseManagementLocation", CourtLocations.ALDERSHOT_COUNTY.getEpimm());
        inputVariables.putValue("claimTrack", "Intermediate");

        DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
        List<Map<String, Object>> taskLocationCourtList = dmnDecisionTableResult.getResultList();

        assertThat(taskLocationCourtList.get(0).get("CMC"), is(CourtLocations.ALDERSHOT_COUNTY.getEpimm()));
        assertThat(taskLocationCourtList.get(0).get("CCMC"), is(CourtLocations.ALDERSHOT_COUNTY.getEpimm()));
        assertThat(taskLocationCourtList.get(0).get("Trial"), is(CourtLocations.SOUTHAMPTON_COUNTY.getEpimm()));
        assertThat(taskLocationCourtList.get(0).get("PTR"), is(CourtLocations.SOUTHAMPTON_COUNTY.getEpimm()));
    }

    @Test
    void if_this_test_fails_needs_updating_with_your_changes() {
        //The purpose of this test is to prevent adding new rows without being tested
        DmnDecisionTableImpl logic = (DmnDecisionTableImpl) decision.getDecisionLogic();
        assertThat(logic.getRules().size(), is(280));
    }


    @Test
    void verify_DMN_outputs_from_CSV() throws Exception {
        String csvFilePath = "src/test/java/uk/gov/hmcts/civil/taskconfiguration/dmn/testcsv.csv";

        try (Reader reader = new FileReader(csvFilePath)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withHeader("caseManagementLocation", "claimTrack", "CMC", "CCMC", "PTR", "Trial")
                .withSkipHeaderRecord()
                .parse(reader);

            for (CSVRecord record : records) {

                String caseManagementLocation = record.get("caseManagementLocation");
                String claimTrack = record.get("claimTrack");
                String expectedCMC = record.get("CMC");
                String expectedCCMC = record.get("CCMC");
                String expectedTrial = record.get("Trial");
                String expectedPTR = record.get("PTR");

                var a = String.format("test %s %s %s %s %s %s",
                                      caseManagementLocation,
                                      claimTrack,
                                      expectedCMC,
                                      expectedCCMC,
                                      expectedTrial,
                                      expectedPTR);
                System.out.println(a);

                VariableMap inputVariables = new VariableMapImpl();
                inputVariables.putValue("caseManagementLocation", caseManagementLocation);
                inputVariables.putValue("claimTrack", claimTrack);

                DmnDecisionTableResult dmnDecisionTableResult = evaluateDmnTable(inputVariables);
                Map<String, Object> resultMap = dmnDecisionTableResult.getSingleResult();

                assertThat(resultMap.get("CMC"), is(expectedCMC));
                assertThat(resultMap.get("CCMC"), is(expectedCCMC));
                assertThat(resultMap.get("Trial"), is(expectedTrial));
                assertThat(resultMap.get("PTR"), is(expectedPTR));
            }
        }
    }

}
