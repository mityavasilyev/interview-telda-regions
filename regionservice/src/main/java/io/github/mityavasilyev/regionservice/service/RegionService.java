package io.github.mityavasilyev.regionservice.service;

import io.github.mityavasilyev.regionservice.exception.RegionNotFoundException;
import io.github.mityavasilyev.regionservice.model.Region;
import io.github.mityavasilyev.regionservice.model.RegionDTO;

import java.util.List;

public interface RegionService {

    List<RegionDTO> getAllRegions();

    List<RegionDTO> getAllRegionsByNameContaining(String stringToSearchFor) throws IllegalArgumentException;

    RegionDTO getRegionById(Long id) throws RegionNotFoundException, IllegalArgumentException;

    RegionDTO getRegionByName(String regionName) throws RegionNotFoundException, IllegalArgumentException;

    RegionDTO getRegionByShortName(String regionShortName) throws RegionNotFoundException, IllegalArgumentException;

    boolean addRegion(Region region) throws IllegalArgumentException;

    boolean deleteRegion(Long id) throws RegionNotFoundException, IllegalArgumentException;

    boolean updateRegion(Region region) throws RegionNotFoundException, IllegalArgumentException;
}
