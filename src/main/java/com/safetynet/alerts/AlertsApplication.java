package com.safetynet.alerts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringBootApplication(scanBasePackages = { "com.safetynet.alerts" })
public class AlertsApplication {

    /**
     * Create a SLF4J/LOG4J LOGGER instance.
     */
    static final Logger LOGGER = LoggerFactory
            .getLogger(AlertsApplication.class);

    /**
     * Main method, the entry point of the application.
     *
     * @param args
     */
    public static void main(final String[] args) {
        SpringApplication.run(AlertsApplication.class, args);
        LOGGER.info("Alerts application is running...");
    }

    /**
     * Empty class constructor.
     */
    protected AlertsApplication() {
    }
}
