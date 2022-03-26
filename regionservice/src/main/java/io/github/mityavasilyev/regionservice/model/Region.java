package io.github.mityavasilyev.regionservice.model;

import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Region {

    // Needed to avoid string duplication during the process of mapping. Move to utils if it gets bigger
    public final static String COLUMN_REGION_NAME = "REGION_NAME";
    public final static String COLUMN_REGION_SHORT_NAME = "REGION_SHORT_NAME";
    public final static String COLUMN_REGION_CODE = "REGION_CODE";
    public final static String FIELD_REGION_NAME = "regionName";
    public final static String FIELD_REGION_SHORT_NAME = "regionShortName";
    public final static String FIELD_REGION_CODE = "regionCode";

    private Long id;
    private String regionName;
    private String regionShortName;
    private Long regionCode;

}
