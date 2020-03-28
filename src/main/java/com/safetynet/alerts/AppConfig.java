package com.safetynet.alerts;

import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("actuator-endpoints")
public class AppConfig {

    @Bean
    public HttpTraceRepository htttpTraceRepository() {
        return new InMemoryHttpTraceRepository();
    }

}
