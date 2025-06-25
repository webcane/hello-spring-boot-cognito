package cane.brothers.sample.data;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.stereotype.Service;

@Service
class SampleSvc implements SampleService {

    private static final Set<Sample> repo = new HashSet<>();

    @Override
    public Optional<Sample> get(Long sampleKey) {
        return repo.stream()
                .filter(sample -> sample.sampleKey().equals(sampleKey))
                .findFirst();
    }

    @Override
    public Sample save(SampleRequest request) {
        var study = Sample.builder().sampleId(UUID.randomUUID())
                .sampleKey(request.sampleKey())
                .status(request.status())
                .build();
        repo.add(study);
        return study;
    }
}
