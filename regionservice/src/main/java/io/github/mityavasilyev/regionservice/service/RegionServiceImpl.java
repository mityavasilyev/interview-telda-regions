package io.github.mityavasilyev.regionservice.service;

import io.github.mityavasilyev.regionservice.exception.RegionNotFoundException;
import io.github.mityavasilyev.regionservice.mapper.RegionMapper;
import io.github.mityavasilyev.regionservice.model.Region;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class RegionServiceImpl implements RegionService {

    private final RegionMapper mapper;

    @Override
    public List<Region> getAllRegions() {
        return mapper.findAll();
    }

    @Override
    public List<Region> getAllRegionsByNameContaining(String stringToSearchFor) {
        log.info("Getting all regions containing: {}", stringToSearchFor);
        return mapper.findAllContaining(stringToSearchFor);
    }

    @Override
    public Region getRegionById(Long id) throws RegionNotFoundException {
        return mapper.findById(id)
                .orElseThrow(() -> {
                    throw new RegionNotFoundException("No region with such id: " + id);
                });
    }

    @Override
    public Region getRegionByName(String regionName) throws RegionNotFoundException {
        return null;
    }

    @Override
    public Region getRegionByShortName(String regionShortName) throws RegionNotFoundException {
        return null;
    }


    @Override
    public boolean addRegion(Region region) {
        return true;
    }

    @Override
    public boolean deleteRegion(Long id) {
        return true;
    }

    @Override
    public boolean updateRegion(Long id, Region region) {
        return true;
    }
}
