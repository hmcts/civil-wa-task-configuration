package uk.gov.hmcts.civil.taskconfiguration;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DmnDecisionTable {

    WA_TASK_COMPLETION_CIVIL_DAMAGES("wa-task-completion-civil-damages",
                                     "wa-task-completion-civil-damages.dmn"),
    WA_TASK_PERMISSIONS_CIVIL_DAMAGES(
        "wa-task-permissions-civil-damages",
        "wa-task-permissions-civil-damages.dmn"
    ),
    WA_TASK_CONFIGURATION_CIVIL_DAMAGES(
        "wa-task-configuration-civil-damages",
        "wa-task-configuration-civil-damages.dmn"
    ),
    WA_TASK_CANCELLATION_CIVIL_DAMAGES(
        "wa-task-cancellation-civil-damages",
        "wa-task-cancellation-civil-damages.dmn"
    ),
    WA_TASK_INITIATION_CIVIL_DAMAGES(
        "wa-task-initiation-civil-damages",
        "wa-task-initiation-civil-damages.dmn"
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
