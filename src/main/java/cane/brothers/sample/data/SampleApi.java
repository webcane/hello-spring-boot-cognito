package cane.brothers.sample.data;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@Validated
@SuppressWarnings("unused")
@Tag(name = "sample", description = "Endpoints for registered samples")
interface SampleApi {

    @Operation(summary = "Filter samples by sample_key.", method = "GET", responses = {
            @ApiResponse(responseCode = "200", description = "Sample results",
                    content = @Content(schema = @Schema(implementation = Sample.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(hidden = true))),
            @ApiResponse(responseCode = "404", description = "No sample found for the given sample_key",
                    content = @Content(schema = @Schema(hidden = true)))})
    ResponseEntity<Sample> get(@Parameter(example = "123", required = true)
                               @PathVariable("sample_key") @NotNull Long sampleKey);

    @Operation(summary = "Store a sample.", method = "POST", responses = {
            @ApiResponse(responseCode = "200", description = "Sample was putted",
                    content = @Content(schema = @Schema(implementation = Sample.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input",
                    content = @Content(schema = @Schema(hidden = true)))})
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<Sample> save(@RequestBody(content = @Content(schema = @Schema(implementation = SampleRequest.class)))
                                @NotNull @Valid SampleRequest request);
}
