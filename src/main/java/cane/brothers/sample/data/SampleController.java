package cane.brothers.sample.data;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/sample", produces = {"application/json"})
class SampleController implements SampleApi {

    private final SampleService svc;

    @GetMapping("/{sample_key}")
    public ResponseEntity<Sample> get(@PathVariable("sample_key") Long sampleKey) {
        return ResponseEntity.of(svc.get(sampleKey));
    }


    @PostMapping(consumes = {"application/json"})
    @PreAuthorize("hasRole(@environment.getProperty('app.roles.whitelist'))")
    public ResponseEntity<Sample> save(@RequestBody SampleRequest request) {
        return new ResponseEntity<>(svc.save(request), HttpStatus.CREATED);
    }
}
