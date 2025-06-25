package cane.brothers.sample.data;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
class SampleLoader {

    private final SampleService sampleService;

    @EventListener(ContextRefreshedEvent.class)
    public void preloadSamples() {
        var smpl = sampleService.save(new SampleRequest(123L, "active"));
        log.debug("Loaded sample {}", smpl);
    }
}
