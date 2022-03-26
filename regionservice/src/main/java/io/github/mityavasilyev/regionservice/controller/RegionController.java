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

    /**
     * Retrieves all available regions sorted in alphabetical order. <br/>
     * <p>
     * Throws IllegalArgumentException if query is blank. In that case
     * response is handled by ExceptionController
     *
     * @param value optional query. If provided will search for matches
     *              in region names and short names.
     * @return Response entity with regions data
     */
    @GetMapping("regions")
    public ResponseEntity<SuccessResponse<List<RegionDTO>>> getRegions(
            @RequestParam(value = "value", required = false) String value) {
        log.info("New request: [/api/v1/regions]");

        List<RegionDTO> regionDTOS = (value == null)
                ? service.getAllRegions()
                : service.getAllRegionsByNameContaining(value);

        return ResponseEntity.ok(new SuccessResponse<>(regionDTOS));
    }

    /**
     * Retrieves region with matching region code <br/>
     * Throws IllegalArgumentException if code is less or equal to 0. <br/>
     * Throws RegionNotFoundException if region with such code not found. <br/>
     *
     * @param code to search for
     * @return Response Entity with matching region data
     */
    @GetMapping("region/code/{code}")
    public ResponseEntity<SuccessResponse<RegionDTO>> getRegionByCode(@PathVariable Long code) {
        log.info("New request: [/api/v1/region/code/{}]", code);

        RegionDTO regionDTO = service.getRegionByCode(code);

        return ResponseEntity.ok(new SuccessResponse<>(regionDTO));
    }

    /**
     * Retrieves region with matching region name <br/>
     * Throws IllegalArgumentException if name is blank. <br/>
     * Throws RegionNotFoundException if region with such name not found. <br/>
     *
     * @param value Name of the region to search for
     * @return Response Entity with matching region data
     */
    @GetMapping("region/name")
    public ResponseEntity<SuccessResponse<RegionDTO>> getRegionByName(@RequestParam String value) {
        log.info("New request: [/api/v1/region/name?value={}]", value);

        RegionDTO regionDTO = service.getRegionByName(value);

        return ResponseEntity.ok(new SuccessResponse<>(regionDTO));
    }

    /**
     * Retrieves region with matching region short name <br/>
     * Throws IllegalArgumentException if short name is blank. <br/>
     * Throws RegionNotFoundException if region with such short name not found. <br/>
     *
     * @param value Short name of the region to search for
     * @return Response Entity with matching region data
     */
    @GetMapping("region/shortname")
    public ResponseEntity<SuccessResponse<RegionDTO>> getRegionByShortName(@RequestParam String value) {
        log.info("New request: [/api/v1/region/shortname?value={}", value);

        RegionDTO regionDTO = service.getRegionByShortName(value);

        return ResponseEntity.ok(new SuccessResponse<>(regionDTO));
    }

    /**
     * Saves provided region.
     * Provided data is accepted as DTO and converted to Entity for security reasons. <br/>
     * Throws IllegalArgumentException if provided region's code is already taken or less than or equals to 0. <br/>
     * Throws MissingRegionDataException if provided region has no code (null)
     *
     * @param regionDTO Region data from request body that needs to be saved
     * @return Response entity with info whether saving was a success
     */
    @PutMapping("region/save")
    public ResponseEntity<SuccessResponse<RegionDTO>> saveNewRegion(@RequestBody RegionDTO regionDTO) {
        log.info("New request: [/api/v1/region/save]");

        Region region = Region.builder()
                .regionCode(regionDTO.regionCode())
                .regionName(regionDTO.regionName())
                .regionShortName(regionDTO.regionShortName())
                .build();
        boolean response = service.addRegion(region);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new SuccessResponse<>(RegionDTO.entityToDTO(region)));
    }

    /**
     * Deletes region with matching region code <br/>
     * Throws IllegalArgumentException if code is less or equal to 0. <br/>
     * Throws RegionNotFoundException if region with such code not found. <br/>
     *
     * @param code Region code of region that will be deleted
     * @return Response Entity with status of deletion
     */
    @DeleteMapping("region/code/{code}")
    public ResponseEntity<SuccessResponse<Boolean>> deleteRegion(@PathVariable Long code) {
        log.info("New request: [/api/v1/region/code/{}] DELETE", code);

        boolean response = service.deleteRegion(code);

        return ResponseEntity.ok(new SuccessResponse<>(response));
    }

    /**
     * Updates region with provided data (Only region code is necessary, though it is provided via url).
     * Region name or short name are optional and may not be present in the request body. <br/>
     * Provided data is accepted as DTO and converted to Entity for security reasons. <br/>
     * Throws IllegalArgumentException if provided data is invalid <br/>
     * Throws RegionNotFoundException if region with such code not found
     *
     * @param regionDTO Region data from request body that needs to be saved
     * @return Response entity with info whether saving was a success
     */
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
