package com.chinthaka.backendroyallmssystem.statics;


import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.trace.http.InMemoryHttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricsConfiguration {

    //Count total request
    @Bean
    public Counter apiRequestCounter(MeterRegistry meterRegistry) {
        return Counter.builder("api.requests")
                .description("Total number of API requests")
                .register(meterRegistry);
    }


    @Bean
    public Counter status200Counter(MeterRegistry meterRegistry) {
        return Counter.builder("http.status.200")
                .description("Total number of HTTP status 200 responses")
                .register(meterRegistry);
    }

    @Bean
    public Counter status400Counter(MeterRegistry meterRegistry) {
        return Counter.builder("http.status.400")
                .description("Total number of HTTP status 400 responses")
                .register(meterRegistry);
    }

    @Bean
    public Counter status404Counter(MeterRegistry meterRegistry) {
        return Counter.builder("http.status.404")
                .description("Total number of HTTP status 404 responses")
                .register(meterRegistry);
    }

    @Bean
    public Counter status500Counter(MeterRegistry meterRegistry) {
        return Counter.builder("http.status.500")
                .description("Total number of HTTP status 500 responses")
                .register(meterRegistry);
    }

    //All Http trace
    @Bean
    public HttpTraceRepository httpTraceRepository() {
        return new InMemoryHttpTraceRepository();
    }

}
