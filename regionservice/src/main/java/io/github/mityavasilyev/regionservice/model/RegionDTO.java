package io.github.mityavasilyev.regionservice.model;

public record RegionDTO(Long regionCode, String regionName, String regionShortName) {

    /**
     * Parses provided entity to an DTO object.
     * Replaces name fields with empty string if null.
     * Assigns 0 region code if not provided
     *
     * @param entity entity for parsing
     * @return parsed DTO object
     */
    public static RegionDTO entityToDTO(Region entity) {
        return new RegionDTO(
                entity.getRegionCode() != null ? entity.getRegionCode() : 0L,
                entity.getRegionName() != null ? entity.getRegionName() : "",
                entity.getRegionShortName() != null ? entity.getRegionShortName() : ""
        );
    }
}
