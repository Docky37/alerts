package com.safetynet.alerts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.safetynet.alerts" })
public class AlertsApplication {

    /**
     * Main method, the entry point of the application.
     *
     * @param args
     */
    public static void main(final String[] args) {
        SpringApplication.run(AlertsApplication.class, args);
    }

    /**
     * Empty class constructor.
     */
    protected AlertsApplication() {
    }
}
