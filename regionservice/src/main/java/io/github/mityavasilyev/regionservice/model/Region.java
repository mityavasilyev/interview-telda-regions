package io.github.mityavasilyev.regionservice.model;

import lombok.Data;


@Data
public class Region {

    // Needed to avoid string duplication during the process of mapping
    public final static String COLUMN_REGION_NAME = "REGION_NAME";
    public final static String COLUMN_REGION_SHORT_NAME = "REGION_SHORT_NAME";
    public final static String FIELD_REGION_NAME = "regionName";
    public final static String FIELD_REGION_SHORT_NAME = "regionShortName";

    private Long id;
    private String regionName;
    private String regionShortName;


}
