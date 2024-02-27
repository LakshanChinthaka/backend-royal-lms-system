package com.chinthaka.backendroyallmssystem.statics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Getter;
import org.springframework.stereotype.Component;


@Getter
@Component
public class EndpointAccessStatistics {

    private final MeterRegistry registry;
    private final Counter endpointAccessCounter;

    public EndpointAccessStatistics(MeterRegistry registry) {
        this.registry = registry;

        // Create a single counter for endpoint access
        this.endpointAccessCounter = Counter.builder("endpoint.access.counter")
                .description("Counts accesses to different endpoints")
                .tags("region", "us-east")
                .register(registry);
    }

    public void incrementEndpointAccessCounter(String endpoint, String exception, String method, String status) {
        endpointAccessCounter.increment();
    }

}
