package cane.brothers.sample.data;

import java.util.UUID;

import lombok.Builder;

/**
 * SampleEntity
 *
 * @param sampleId
 * @param sampleKey
 * @param status
 */
@Builder
record Sample(UUID sampleId, Long sampleKey, String status) {
}
