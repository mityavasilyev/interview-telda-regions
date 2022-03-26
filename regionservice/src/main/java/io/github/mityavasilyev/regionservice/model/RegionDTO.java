package io.github.mityavasilyev.regionservice.model;

public record RegionDTO(Long id, String regionName, String regionShortName) {

    /**
     * Parses provided entity to an DTO object.
     * Replaces name fields with empty string if null.
     * Assigns 0 ID if not provided
     *
     * @param entity entity for parsing
     * @return parsed DTO object
     */
    public static RegionDTO entityToDTO(Region entity) {
        return new RegionDTO(
                entity.getId() != null ? entity.getId() : 0L,
                entity.getRegionName() != null ? entity.getRegionName() : "",
                entity.getRegionShortName() != null ? entity.getRegionShortName() : ""
        );
    }
}
