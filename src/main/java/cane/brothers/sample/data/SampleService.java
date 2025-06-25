package cane.brothers.sample.data;

import java.util.Optional;

interface SampleService {

    Optional<Sample> get(Long sampleKey);


    Sample save(SampleRequest request);

}
