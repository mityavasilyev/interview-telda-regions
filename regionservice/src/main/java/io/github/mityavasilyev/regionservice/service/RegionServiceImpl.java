package io.github.mityavasilyev.regionservice.service;

import io.github.mityavasilyev.regionservice.exception.MissingRegionDataException;
import io.github.mityavasilyev.regionservice.exception.RegionNotFoundException;
import io.github.mityavasilyev.regionservice.mapper.RegionMapper;
import io.github.mityavasilyev.regionservice.model.Region;
import io.github.mityavasilyev.regionservice.model.RegionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.WordUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class RegionServiceImpl implements RegionService {

    private final RegionMapper mapper;

    @Override
    public List<RegionDTO> getAllRegions() {

        log.info("Getting all regions");
        return mapper.findAll().stream()
                .map(RegionDTO::entityToDTO)
                .toList();
    }

    @Override
    public List<RegionDTO> getAllRegionsByNameContaining(String stringToSearchFor) throws IllegalArgumentException {

        if (stringToSearchFor.isBlank()) throw new IllegalArgumentException("Search request cannot be blank");
        log.info("Getting all regions containing: {}", stringToSearchFor);

        return mapper.findAllContaining(stringToSearchFor).stream()
                .map(RegionDTO::entityToDTO)
                .toList();
    }

    @Override
    public RegionDTO getRegionByCode(Long code) throws RegionNotFoundException, IllegalArgumentException {

        if (code <= 0) throw new IllegalArgumentException("Region code cannot be 0 or less than 0");
        log.info("Getting a region by code: {}", code);

        return mapper.findByCode(code)
                .map(RegionDTO::entityToDTO)
                .orElseThrow(() -> {
                    throw new RegionNotFoundException("No region with such code: " + code);
                });
    }

    @Override
    public RegionDTO getRegionByName(String regionName) throws RegionNotFoundException, IllegalArgumentException {

        if (regionName.isBlank()) throw new IllegalArgumentException("Region name cannot be blank");
        log.info("Getting a region by region name: {}", regionName);

        return mapper.findByName(regionName)
                .map(RegionDTO::entityToDTO)
                .orElseThrow(() -> {
                    throw new RegionNotFoundException("No region with such name: " + regionName);
                });
    }

    @Override
    public RegionDTO getRegionByShortName(String regionShortName) throws RegionNotFoundException, IllegalArgumentException {

        if (regionShortName.isBlank()) throw new IllegalArgumentException("Region name cannot be blank");
        log.info("Getting a region by region name: {}", regionShortName);

        return mapper.findByShortName(regionShortName)
                .map(RegionDTO::entityToDTO)
                .orElseThrow(() -> {
                    throw new RegionNotFoundException("No region with such name: " + regionShortName);
                });
    }

    @Override
    public boolean addRegion(Region region) throws IllegalArgumentException {
        // TODO: 26.03.2022 Add check if region code is already taken
        if (region.getRegionName() == null || region.getRegionName().isBlank())
            throw new IllegalArgumentException("Region name cannot be blank");
        if (region.getRegionCode() == null || region.getRegionCode() <= 0)
            throw new MissingRegionDataException("No valid region code provided");
        log.info("Adding a new region: {}-{}", region.getRegionCode(), region.getRegionName());

        try {
            mapper.addRegion(processRegion(region));
        } catch (RuntimeException exception) {
            // If failed to save to database
            log.error("Failed to save region [{}] to database", region.getRegionName());
            throw new IllegalArgumentException(
                    String.format("Region Code [%s] is already taken", region.getRegionCode()));
        }
        return true;
    }

    @Override
    public boolean deleteRegion(Long regionCode) throws RegionNotFoundException, IllegalArgumentException {

        if (regionCode <= 0) throw new IllegalArgumentException("Region Code cannot be 0 or less than 0");
        log.info("Deleting region with id: {}", regionCode);

        boolean response = mapper.deleteRegion(regionCode);
        if (!response) throw new RegionNotFoundException("No region with such code", regionCode.toString());
        return true;
    }

    @Override
    public boolean updateRegion(Region region) throws RegionNotFoundException, IllegalArgumentException {

        if (region.getRegionCode() == null)
            throw new IllegalArgumentException("Region code cannot be empty");
        if (region.getRegionCode() <= 0)
            throw new IllegalArgumentException("Region code cannot be less or equal to 0");

        if (region.getRegionName() == null || region.getRegionName().isBlank())
            throw new IllegalArgumentException("Region name cannot be blank");

        log.info("Updating region {} with code: {}", region.getRegionName(), region.getRegionCode());

        try {
            region.setRegionName(WordUtils.capitalizeFully(region.getRegionName()));
            boolean response = mapper.updateRegion(region);
            if (!response) throw new RuntimeException();
            return true;
        } catch (RuntimeException exception) {
            // If failed to save to database
            log.error("Failed to update region [{}]", region.getRegionName());
            throw new RegionNotFoundException(
                    String.format("No region with code [%s] was found", region.getRegionCode()), region.getRegionCode().toString());
        }
    }

    /**
     * Capitalizes region name and generates region short name
     * if necessary.
     *
     * @param region Region that needs to be processed
     * @return processed region
     */
    private Region processRegion(Region region) {

        // Generating region short name if not provided
        if (region.getRegionShortName() == null || region.getRegionShortName().isBlank()) {
            String generatedShortName = "";
            for (String s : region.getRegionName().split(" ")) {
                generatedShortName += s.charAt(0);    // Avoiding StringBuilder. There won't be that much of iterations
            }
            generatedShortName = generatedShortName.toUpperCase(Locale.ROOT);
            region.setRegionShortName(generatedShortName);
            log.info("Generated short name [{}] for a region [{}]", generatedShortName, region.getRegionName());
        }

        // leningradskaya oblast -> Leningradskaya Oblast
        region.setRegionName(WordUtils.capitalizeFully(region.getRegionName()));

        return region;
    }


}
