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
            @Result(property = FIELD_REGION_SHORT_NAME, column = COLUMN_REGION_SHORT_NAME)
    })
    List<Region> findAll();


    @Select("select * from region" +
            " where ( LOWER(" + COLUMN_REGION_NAME + ") like LOWER( CONCAT('%', #{search}, '%') ) )" +
            " or ( LOWER(" + COLUMN_REGION_SHORT_NAME + ") like LOWER( CONCAT('%', #{search}, '%') ) )" +
            " order by " + COLUMN_REGION_NAME)
    @Results({
            @Result(property = FIELD_REGION_NAME, column = COLUMN_REGION_NAME),
            @Result(property = FIELD_REGION_SHORT_NAME, column = COLUMN_REGION_SHORT_NAME)
    })
    List<Region> findAllContaining(@Param("search") String stringToSearchFor);


    @Select("select * from region where id = #{id}")
    @Results({
            @Result(property = FIELD_REGION_NAME, column = COLUMN_REGION_NAME),
            @Result(property = FIELD_REGION_SHORT_NAME, column = COLUMN_REGION_SHORT_NAME)
    })
    Optional<Region> findById(@Param("id") Long id);


    @Select("select * from region where " + COLUMN_REGION_NAME + " = #{name}")
    @Results({
            @Result(property = FIELD_REGION_NAME, column = COLUMN_REGION_NAME),
            @Result(property = FIELD_REGION_SHORT_NAME, column = COLUMN_REGION_SHORT_NAME)
    })
    Optional<Region> findByName(@Param("name") String name);


    @Select("select * from region where " + COLUMN_REGION_SHORT_NAME + " = #{shortName}")
    @Results({
            @Result(property = FIELD_REGION_NAME, column = COLUMN_REGION_NAME),
            @Result(property = FIELD_REGION_SHORT_NAME, column = COLUMN_REGION_SHORT_NAME)
    })
    Optional<Region> findByShortName(@Param("shortName") String shortName);

    @Insert("insert into region (" + COLUMN_REGION_NAME + ", " + COLUMN_REGION_SHORT_NAME + ")" +
            " values(#{regionName}, #{regionShortName})")
    Integer addRegion(Region region);

    @Delete("delete from region WHERE id = #{id}")
    boolean deleteRegion(@Param("id") Long id);

    @Update("UPDATE region SET " + COLUMN_REGION_NAME + " = #{regionName}," +
            " " + COLUMN_REGION_SHORT_NAME + " = #{regionShortName}" +
            " WHERE id = #{id}")
    boolean updateRegion(Region region);


}
