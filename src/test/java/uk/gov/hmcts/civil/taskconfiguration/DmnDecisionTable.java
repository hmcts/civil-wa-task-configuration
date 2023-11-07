package uk.gov.hmcts.civil.taskconfiguration;

import com.fasterxml.jackson.annotation.JsonValue;

public enum DmnDecisionTable {

    WA_TASK_COMPLETION_CIVIL_DAMAGES("wa-task-completion-civil-civil",
                                     "wa-task-completion-civil-civil.dmn"
    ),
    WA_TASK_PERMISSIONS_CIVIL_DAMAGES(
        "wa-task-permissions-civil-civil",
        "wa-task-permissions-civil-civil.dmn"
    ),
    WA_TASK_CONFIGURATION_CIVIL_DAMAGES(
        "wa-task-configuration-civil-civil",
        "wa-task-configuration-civil-civil.dmn"
    ),
    WA_TASK_CANCELLATION_CIVIL_DAMAGES(
        "wa-task-cancellation-civil-civil",
        "wa-task-cancellation-civil-civil.dmn"
    ),
    WA_TASK_INITIATION_CIVIL_DAMAGES(
        "wa-task-initiation-civil-civil",
        "wa-task-initiation-civil-civil.dmn"
    ),
    WA_TASK_TYPES_CIVIL_DAMAGES("wa-task-types-civil-civil",
                                           "wa-task-types-civil-civil.dmn"
    ),
    WA_TASK_CONFIGURATION_CIVIL_GENERALAPPLICATION(
            "wa-task-configuration-civil-generalapplication",
            "wa-task-configuration-civil-generalapplication.dmn"
    ),
    WA_TASK_INITIATION_CIVIL_GENERALAPPLICATION(
        "wa-task-initiation-civil-generalapplication",
        "wa-task-initiation-civil-generalapplication.dmn"
    ),
    WA_TASK_CANCELLATION_CIVIL_GENERALAPPLICATION(
        "wa-task-cancellation-civil-generalapplication",
            "wa-task-cancellation-civil-generalapplication.dmn"
    ),
    WA_TASK_COMPLETION_CIVIL_GENERALAPPLICATION("wa-task-completion-civil-generalapplication",
                                                "wa-task-completion-civil-generalapplication.dmn"
    ),
    WA_TASK_PERMISSIONS_CIVIL_GENERALAPPLICATION("wa-task-permissions-civil-generalapplication",
                                                 "wa-task-permissions-civil-generalapplication.dmn"
    ),
    WA_TASK_TYPES_CIVIL_GENERALAPPLICATION("wa-task-types-civil-generalapplication",
                                                 "wa-task-types-civil-generalapplication.dmn"
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
