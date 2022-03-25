package io.github.mityavasilyev.regionservice.service;

import io.github.mityavasilyev.regionservice.exception.RegionNotFoundException;
import io.github.mityavasilyev.regionservice.model.Region;

import java.util.List;

public interface RegionService {

    List<Region> getAllRegions();

    List<Region> getAllRegionsByNameContaining(String stringToSearchFor);

    Region getRegionById(Long id) throws RegionNotFoundException;

    Region getRegionByName(String regionName) throws RegionNotFoundException;

    Region getRegionByShortName(String regionShortName) throws RegionNotFoundException;

    boolean addRegion(Region region);

    boolean deleteRegion(Long id);

    boolean updateRegion(Long id, Region region);
}
