package uk.gov.hmcts.reform.iataskconfiguration;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DmnDecisionTable {
    WA_TASK_ALLOWED_DAYS_IA_ASYLUM("wa-task-allowed-days-ia-asylum", "wa-task-allowed-days-ia-asylum.dmn"),
    WA_TASK_CANCELLATION_IA_ASYLUM("wa-task-cancellation-ia-asylum", "wa-task-cancellation-ia-asylum.dmn"),
    WA_TASK_COMPLETION_IA_ASYLUM("wa-task-completion-ia-asylum", "wa-task-completion-ia-asylum.dmn"),
    WA_TASK_CONFIGURATION_IA_ASYLUM("wa-task-configuration-ia-asylum", "wa-task-configuration-ia-asylum.dmn"),
    WA_TASK_INITIATION_IA_ASYLUM("wa-task-initiation-ia-asylum", "wa-task-initiation-ia-asylum.dmn"),
    WA_TASK_PERMISSIONS_IA_ASYLUM("wa-task-permissions-ia-asylum", "wa-task-permissions-ia-asylum.dmn");

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
