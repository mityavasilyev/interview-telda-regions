package io.github.mityavasilyev.regionservice.mapper;

import io.github.mityavasilyev.regionservice.model.Region;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

import static io.github.mityavasilyev.regionservice.model.Region.*;

@Mapper
public interface RegionMapper {

    @Select("select * from region order by REGION_NAME")
    @Results({
            @Result(property = FIELD_REGION_NAME, column = COLUMN_REGION_NAME),
            @Result(property = FIELD_REGION_SHORT_NAME, column = COLUMN_REGION_SHORT_NAME),
            @Result(property = FIELD_REGION_CODE, column = COLUMN_REGION_CODE)
    })
    List<Region> findAll();


    @Select("select * from region" +
            " where ( LOWER(" + COLUMN_REGION_NAME + ") like LOWER( CONCAT('%', #{search}, '%') ) )" +
            " or ( LOWER(" + COLUMN_REGION_SHORT_NAME + ") like LOWER( CONCAT('%', #{search}, '%') ) )" +
            " order by " + COLUMN_REGION_NAME)
    @Results({
            @Result(property = FIELD_REGION_NAME, column = COLUMN_REGION_NAME),
            @Result(property = FIELD_REGION_SHORT_NAME, column = COLUMN_REGION_SHORT_NAME),
            @Result(property = FIELD_REGION_CODE, column = COLUMN_REGION_CODE)
    })
    List<Region> findAllContaining(@Param("search") String stringToSearchFor);


    @Select("select * from region where id = #{id}")
    @Results({
            @Result(property = FIELD_REGION_NAME, column = COLUMN_REGION_NAME),
            @Result(property = FIELD_REGION_SHORT_NAME, column = COLUMN_REGION_SHORT_NAME),
            @Result(property = FIELD_REGION_CODE, column = COLUMN_REGION_CODE)
    })
    Optional<Region> findById(@Param("id") Long id);


    @Select("select * from region where " + COLUMN_REGION_NAME + " = #{name}")
    @Results({
            @Result(property = FIELD_REGION_NAME, column = COLUMN_REGION_NAME),
            @Result(property = FIELD_REGION_SHORT_NAME, column = COLUMN_REGION_SHORT_NAME),
            @Result(property = FIELD_REGION_CODE, column = COLUMN_REGION_CODE)
    })
    Optional<Region> findByName(@Param("name") String name);


    @Select("select * from region where " + COLUMN_REGION_SHORT_NAME + " = #{shortName}")
    @Results({
            @Result(property = FIELD_REGION_NAME, column = COLUMN_REGION_NAME),
            @Result(property = FIELD_REGION_SHORT_NAME, column = COLUMN_REGION_SHORT_NAME),
            @Result(property = FIELD_REGION_CODE, column = COLUMN_REGION_CODE)
    })
    Optional<Region> findByShortName(@Param("shortName") String shortName);

    @Select("select * from region where " + COLUMN_REGION_CODE + " = #{code}")
    @Results({
            @Result(property = FIELD_REGION_NAME, column = COLUMN_REGION_NAME),
            @Result(property = FIELD_REGION_SHORT_NAME, column = COLUMN_REGION_SHORT_NAME),
            @Result(property = FIELD_REGION_CODE, column = COLUMN_REGION_CODE)
    })
    Optional<Region> findByCode(@Param("code") Long code);


    @Options(useGeneratedKeys = true)
    @Insert("insert into region (" + COLUMN_REGION_NAME + ", " + COLUMN_REGION_SHORT_NAME + ", " + COLUMN_REGION_CODE + ")" +
            " values(#{regionName}, #{regionShortName}, #{regionCode})")
    Integer addRegion(Region region);

    @Delete("delete from region WHERE " + COLUMN_REGION_CODE + " = #{regionCode}")
    boolean deleteRegion(@Param("regionCode") Long regionCode);


    @Update({"<script>",
            "update region",
            "   <set>",
            "       <if test='regionName != null'>REGION_NAME=#{regionName},</if>",
            "       <if test='regionShortName != null'>REGION_SHORT_NAME=#{regionShortName},</if>",
            "   </set>",
            "where REGION_CODE=#{regionCode}",
            "</script>"})
    boolean updateRegion(Region region);


}
