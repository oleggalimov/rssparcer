package org.oleggalimov.rssreader.endpoints;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheck {
    @GetMapping("healthcheck")
    public String getHealthStatus() {
        return "Ok";
    }
}
