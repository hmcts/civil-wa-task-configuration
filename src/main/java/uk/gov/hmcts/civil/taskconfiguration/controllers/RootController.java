package uk.gov.hmcts.civil.taskconfiguration.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    @GetMapping("/")
    public String welcome() {
        return "Welcome to spring ia-task-configuration";
    }
}
