package uk.gov.hmcts.civil.taskconfiguration;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DmnDecisionTable {

    WA_TASK_COMPLETION_CIVIL_DAMAGES("wa-task-completion-CIVIL-CIVIL",
                                     "wa-task-completion-CIVIL-CIVIL.dmn"
    ),
    WA_TASK_PERMISSIONS_CIVIL_DAMAGES(
        "wa-task-permissions-CIVIL-CIVIL",
        "wa-task-permissions-CIVIL-CIVIL.dmn"
    ),
    WA_TASK_CONFIGURATION_CIVIL_DAMAGES(
        "wa-task-configuration-CIVIL-CIVIL",
        "wa-task-configuration-CIVIL-CIVIL.dmn"
    ),
    WA_TASK_CANCELLATION_CIVIL_DAMAGES(
        "wa-task-cancellation-CIVIL-CIVIL",
            "wa-task-cancellation-CIVIL-CIVIL.dmn"
    ),
    WA_TASK_INITIATION_CIVIL_DAMAGES(
        "wa-task-initiation-CIVIL-CIVIL",
        "wa-task-initiation-CIVIL-CIVIL.dmn"
    ),
    WA_TASK_INITIATION_CIVIL_GENERALAPPLICATION(
        "wa-task-initiation-CIVIL-GENERALAPPPLICATION",
            "wa-task-initiation-CIVIL-GENERALAPPPLICATION.dmn"
    ),
    WA_TASK_CANCELLATION_CIVIL_GENERALAPPLICATION(
        "wa-task-cancellation-CIVIL-GENERALAPPLICATION",
        "wa-task-cancellation-CIVIL-GENERALAPPLICATION.dmn"
    );

    @JsonValue
    private final String key;
    private final String fileName;

    DmnDecisionTable(String key, String fileName) {
        this.key = key;
        this.fileName = fileName;
    }

    public String getKey() {
        return key;
    }

    public String getFileName() {
        return fileName;
    }
}
