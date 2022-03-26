package io.github.mityavasilyev.regionservice.service;

import io.github.mityavasilyev.regionservice.exception.RegionNotFoundException;
import io.github.mityavasilyev.regionservice.model.Region;
import io.github.mityavasilyev.regionservice.model.RegionDTO;

import java.util.List;

public interface RegionService {

    /**
     * Retrieves all regions
     *
     * @return List of all available regions
     */
    List<RegionDTO> getAllRegions();

    /**
     * Searches all regions for provided string
     *
     * @param stringToSearchFor String to search for in region names and short names
     * @return List of regions with matching names or short names
     * @throws IllegalArgumentException if string is empty or made out of spaces
     */
    List<RegionDTO> getAllRegionsByNameContaining(String stringToSearchFor) throws IllegalArgumentException;

    /**
     * Retrieves region with matching code
     *
     * @param code Region code to search for
     * @return Matching region
     * @throws RegionNotFoundException  If no region with such code was found
     * @throws IllegalArgumentException If code is less or equals to 0
     */
    RegionDTO getRegionByCode(Long code) throws RegionNotFoundException, IllegalArgumentException;

    /**
     * Retrieves region with matching name
     *
     * @param regionName Region Name to search for
     * @return Matching region
     * @throws RegionNotFoundException  If no region with such name was found
     * @throws IllegalArgumentException If name is empty or made out of spaces
     */
    RegionDTO getRegionByName(String regionName) throws RegionNotFoundException, IllegalArgumentException;

    /**
     * Retrieves region with matching short name
     *
     * @param regionShortName Region Short Name to search for
     * @return Matching region
     * @throws RegionNotFoundException  If no region with such short name was found
     * @throws IllegalArgumentException If short name is empty or made out of spaces
     */
    RegionDTO getRegionByShortName(String regionShortName) throws RegionNotFoundException, IllegalArgumentException;

    /**
     * Saves provided region to database
     *
     * @param region Region to save
     * @return If saved successfully
     * @throws IllegalArgumentException If region name is null or blank
     */
    boolean addRegion(Region region) throws IllegalArgumentException;

    /**
     * Deletes region with matching region code
     *
     * @param regionCode Code of the region to delete
     * @return If deleted successfully. False if no such region was found
     * @throws RegionNotFoundException  deprecated
     * @throws IllegalArgumentException If region code is less or equal to 0
     */
    boolean deleteRegion(Long regionCode) throws RegionNotFoundException, IllegalArgumentException;

    /**
     * Updates region in database with provided one.
     * Fetches region code from provided region, then updates one in
     * db that has the same region code with new data.
     * <p>
     * Updates only provided fields. Those that are null will be skipped
     *
     * @param region Region with code and new data
     * @return If updated successfully
     * @throws RegionNotFoundException  deprecated
     * @throws IllegalArgumentException If provided region has no region code or if Region name is null or blank
     */
    boolean updateRegion(Region region) throws RegionNotFoundException, IllegalArgumentException;
}
