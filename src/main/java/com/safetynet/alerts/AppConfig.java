package com.safetynet.alerts;

import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Application configuration class.
 *
 * @author Thierry Schreiner
 */
@Configuration
class AppConfig {

    /**
     * Method needed by httptrace actuator.
     * @return InMemoryHttpTraceRepository()
     */
    @Bean
    protected HttpTraceRepository htttpTraceRepository() {
        return new InMemoryHttpTraceRepository();
    }

}
