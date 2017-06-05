package org.nick.utils.transmissionrenamer;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    private final TransmissionService transmissionService;

    Controller(TransmissionService transmissionService) {
        this.transmissionService = transmissionService;
    }

    @GetMapping
    public void get() {
    }
}
