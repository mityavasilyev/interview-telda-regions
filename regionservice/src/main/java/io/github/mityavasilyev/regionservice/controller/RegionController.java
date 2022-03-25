package io.github.mityavasilyev.regionservice.controller;

import io.github.mityavasilyev.regionservice.model.Region;
import io.github.mityavasilyev.regionservice.service.RegionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/")
public class RegionController {

    private final RegionService service;

    @GetMapping("regions")
    public List<Region> getRegions(@RequestParam(value = "search", required = false) String searchString) {
        log.info("New request: [/api/v1/regions]");
        return (searchString == null)
                ? service.getAllRegions()
                : service.getAllRegionsByNameContaining(searchString);
    }

    @GetMapping("region/id/{id}")
    public Region getRegionById(@PathVariable Long id) {
        log.info("New request: [/api/v1/region/id/{}]", id);
        return service.getRegionById(id);
    }

}
