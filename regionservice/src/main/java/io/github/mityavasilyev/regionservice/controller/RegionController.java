package io.github.mityavasilyev.regionservice.controller;

import io.github.mityavasilyev.regionservice.model.Region;
import io.github.mityavasilyev.regionservice.model.RegionDTO;
import io.github.mityavasilyev.regionservice.service.RegionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class RegionController {

    private final RegionService service;

    @GetMapping("regions")
    public ResponseEntity<SuccessResponse<List<RegionDTO>>> getRegions(
            @RequestParam(value = "value", required = false) String value) {
        log.info("New request: [/api/v1/regions]");

        List<RegionDTO> regionDTOS = (value == null)
                ? service.getAllRegions()
                : service.getAllRegionsByNameContaining(value);

        return ResponseEntity.ok(new SuccessResponse<>(regionDTOS));
    }

    @GetMapping("region/code/{code}")
    public ResponseEntity<SuccessResponse<RegionDTO>> getRegionByCode(@PathVariable Long code) {
        log.info("New request: [/api/v1/region/code/{}]", code);

        RegionDTO regionDTO = service.getRegionByCode(code);

        return ResponseEntity.ok(new SuccessResponse<>(regionDTO));
    }

    @GetMapping("region/name")
    public ResponseEntity<SuccessResponse<RegionDTO>> getRegionByName(@RequestParam String value) {
        log.info("New request: [/api/v1/region/name?value={}]", value);

        RegionDTO regionDTO = service.getRegionByName(value);

        return ResponseEntity.ok(new SuccessResponse<>(regionDTO));
    }

    @GetMapping("region/shortname")
    public ResponseEntity<SuccessResponse<RegionDTO>> getRegionByShortName(@RequestParam String value) {
        log.info("New request: [/api/v1/region/shortname?value={}", value);

        RegionDTO regionDTO = service.getRegionByShortName(value);

        return ResponseEntity.ok(new SuccessResponse<>(regionDTO));
    }

    @PutMapping("region/save")
    public ResponseEntity<SuccessResponse<RegionDTO>> saveNewRegion(@RequestBody RegionDTO regionDTO) {
        log.info("New request: [/api/v1/region/save]");

        Region region = Region.builder()
                .regionCode(regionDTO.regionCode())
                .regionName(regionDTO.regionName())
                .regionShortName(regionDTO.regionShortName())
                .build();
        boolean response = service.addRegion(region);
        if (!response) throw new RuntimeException("Failed to save");
        // TODO: 26.03.2022 Add check for failed saving

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new SuccessResponse<>(RegionDTO.entityToDTO(region)));
    }

    @DeleteMapping("region/code/{code}")
    public ResponseEntity<SuccessResponse<Boolean>> deleteRegion(@PathVariable Long code) {
        log.info("New request: [/api/v1/region/code/{}] DELETE", code);

        boolean response = service.deleteRegion(code);

        return ResponseEntity.ok(new SuccessResponse<>(response));
    }

    @PutMapping("region/code/{code}")
    public ResponseEntity<SuccessResponse<Boolean>> updateRegion(
            @PathVariable Long code,
            @RequestBody RegionDTO regionDTO) {
        log.info("New request: [/api/v1/region/code/{}] PUT", code);

        Region region = Region.builder()
                .regionCode(code)
                .regionName(regionDTO.regionName())
                .regionShortName(regionDTO.regionShortName())
                .build();
        boolean response = service.updateRegion(region);

        return ResponseEntity.ok(new SuccessResponse<>(response));
    }


}
