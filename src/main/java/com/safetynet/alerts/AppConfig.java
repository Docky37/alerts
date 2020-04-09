package com.safetynet.alerts;

import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * Application configuration class.
 *
 * @author Thierry Schreiner
 */
@Configuration
@Profile("actuator-endpoints")
class AppConfig {

    /**
     * Method needed by htmltrace actuator.
     * @return InMemoryHttpTraceRepository()
     */
    @Bean
    protected final HttpTraceRepository htttpTraceRepository() {
        return new InMemoryHttpTraceRepository();
    }

}
