package io.github.mityavasilyev.regionservice.service;

import io.github.mityavasilyev.regionservice.exception.MissingRegionDataException;
import io.github.mityavasilyev.regionservice.exception.RegionNotFoundException;
import io.github.mityavasilyev.regionservice.mapper.RegionMapper;
import io.github.mityavasilyev.regionservice.model.Region;
import io.github.mityavasilyev.regionservice.model.RegionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.WordUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Transactional
@CacheConfig(cacheNames = {"caffeineCache"})
@Service
public class RegionServiceImpl implements RegionService {

    private final RegionMapper mapper;

    private final CacheManager cacheManager;

    @Cacheable(value = "all_regions_cache")
    @Override
    public List<RegionDTO> getAllRegions() {

        log.info("Getting all regions");
        return mapper.findAll().stream()
                .map(RegionDTO::entityToDTO)
                .toList();
    }

    @Cacheable(value = "all_regions_cache")
    @Override
    public List<RegionDTO> getAllRegionsByNameContaining(String stringToSearchFor) throws IllegalArgumentException {

        if (stringToSearchFor.isBlank()) throw new IllegalArgumentException("Search request cannot be blank");
        log.info("Getting all regions containing: {}", stringToSearchFor);

        return mapper.findAllContaining(stringToSearchFor).stream()
                .map(RegionDTO::entityToDTO)
                .toList();
    }

    @Cacheable(value = "region_cache", key = "#code")
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

    @Cacheable(value = "region_cache", key = "#regionName")
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

    @Cacheable(value = "region_cache", key = "#regionShortName")
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

    @CacheEvict(value = "all_regions_cache", allEntries = true)
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

    @CacheEvict(value = "all_regions_cache", allEntries = true)
    @Override
    public boolean deleteRegion(Long regionCode) throws RegionNotFoundException, IllegalArgumentException {

        if (regionCode <= 0) throw new IllegalArgumentException("Region Code cannot be 0 or less than 0");
        log.info("Deleting region with id: {}", regionCode);

        // This block is needed for caching and possible future return of the entity that is marked for deletion
        Optional<Region> fromDatabase = mapper.findByCode(regionCode);
        if (fromDatabase.isEmpty())
            throw new RegionNotFoundException("No region with such code", regionCode.toString());

        boolean response = mapper.deleteRegion(regionCode);
        if (response) clearCacheForRegion(fromDatabase.get());  // Clearing cache for a region
        return response;
    }

    @CacheEvict(value = "all_regions_cache", allEntries = true)
    @Override
    public boolean updateRegion(Region region) throws RegionNotFoundException, IllegalArgumentException {

        if (region.getRegionCode() == null)
            throw new IllegalArgumentException("Region code cannot be empty");
        if (region.getRegionCode() <= 0)
            throw new IllegalArgumentException("Region code cannot be less or equal to 0");

        if (region.getRegionName() != null && region.getRegionName().isBlank())
            throw new IllegalArgumentException("Region name cannot be blank");

        // This block is needed for caching and possible future return of the entity that is marked for deletion
        Optional<Region> fromDatabase = mapper.findByCode(region.getRegionCode());
        if (fromDatabase.isEmpty())
            throw new RegionNotFoundException("No region with such code", region.getRegionCode().toString());

        log.info("Updating region {} with code: {}", region.getRegionName(), region.getRegionCode());

        region.setRegionName(WordUtils.capitalizeFully(region.getRegionName()));
        region.setRegionShortName(region.getRegionShortName().toUpperCase(Locale.ROOT));

        // Updating
        boolean response = mapper.updateRegion(region);
        if (!response) { // Just in case
            log.error("Failed to update region: {}", region);
            throw new RuntimeException("Failed to save to db");
        }

        clearCacheForRegion(fromDatabase.get());
        return true;
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

    /**
     * Evicts all related cache for provided region
     *
     * @param region Cache of which needs to be invalidated
     */
    private void clearCacheForRegion(Region region) {
        try {
            log.info("Clearing cache for region: {}", region);
            Cache regionCache = cacheManager.getCache("region_cache");
            assert regionCache != null;
            regionCache.evictIfPresent(region.getRegionCode());
            regionCache.evictIfPresent(region.getRegionName().toString());
            regionCache.evictIfPresent(region.getRegionShortName().toString());
        } catch (Exception exception) {
            log.warn("No cache to evict");
        }
    }

}
