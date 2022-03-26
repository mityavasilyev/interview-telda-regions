package io.github.mityavasilyev.regionservice.mapper;

import io.github.mityavasilyev.regionservice.model.Region;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Some basic unit tests to ensure that
 * everything is working accordingly. Double-checking queries basically
 */
@ExtendWith(SpringExtension.class)
@MybatisTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD) // Needed for db purging before each test
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class RegionMapperTest {

    private final Region testRegion = Region.builder()
            .regionName("TestRegion")
            .regionShortName("TO")
            .regionCode(1L)
            .build();

    @Autowired
    private RegionMapper mapper;

    @Test
    void findAll() {
        List<Region> regions = mapper.findAll();
        assertTrue(regions.isEmpty());

        mapper.addRegion(testRegion);
        regions = mapper.findAll();
        assertFalse(regions.isEmpty());
    }

    @Test
    void findAllContaining() {
        mapper.addRegion(testRegion);
        mapper.addRegion(Region.builder()
                .regionName("Should not be found")
                .regionShortName("SNBF")
                .regionCode(2L)
                .build());
        List<Region> regions = mapper.findAllContaining("te");
        assertEquals(1, regions.size());
    }

    @Test
    void findById() {
        mapper.addRegion(testRegion);
        Optional<Region> response = mapper.findById(1L);
        Region retrieved = response.orElse(null);
        assertNotNull(retrieved);
        assertEquals(testRegion.getRegionName(), retrieved.getRegionName());
        assertEquals(testRegion.getRegionShortName(), retrieved.getRegionShortName());
        assertEquals(testRegion.getRegionCode(), retrieved.getRegionCode());

    }

    @Test
    void findByName() {// TODO: 26.03.2022 Implement case sensitive tests
        mapper.addRegion(testRegion);
        Optional<Region> response = mapper.findByName(testRegion.getRegionName());
        Region retrieved = response.orElse(null);
        assertNotNull(retrieved);
        assertEquals(testRegion.getRegionName(), retrieved.getRegionName());
        assertEquals(testRegion.getRegionShortName(), retrieved.getRegionShortName());
        assertEquals(testRegion.getRegionCode(), retrieved.getRegionCode());
    }

    @Test
    void findByShortName() {
        mapper.addRegion(testRegion);
        Optional<Region> response = mapper.findByShortName(testRegion.getRegionShortName());
        Region retrieved = response.orElse(null);
        assertNotNull(retrieved);
        assertEquals(testRegion.getRegionName(), retrieved.getRegionName());
        assertEquals(testRegion.getRegionShortName(), retrieved.getRegionShortName());
        assertEquals(testRegion.getRegionCode(), retrieved.getRegionCode());
    }

    @Test
    void findByCode() {
        mapper.addRegion(testRegion);
        Optional<Region> response = mapper.findByCode(testRegion.getRegionCode());
        Region retrieved = response.orElse(null);
        assertNotNull(retrieved);
        assertEquals(testRegion.getRegionName(), retrieved.getRegionName());
        assertEquals(testRegion.getRegionShortName(), retrieved.getRegionShortName());
        assertEquals(testRegion.getRegionCode(), retrieved.getRegionCode());
    }

    @Test
    void addRegion() {
        assertTrue(mapper.findAll().isEmpty());
        mapper.addRegion(testRegion);
        assertFalse(mapper.findAll().isEmpty());
    }

    @Test
    void deleteRegion() {
        assertTrue(mapper.findAll().isEmpty());
        mapper.addRegion(testRegion);
        assertFalse(mapper.findAll().isEmpty());
        mapper.deleteRegion(testRegion.getRegionCode());
        assertTrue(mapper.findAll().isEmpty());
    }

    @Test
    void updateRegion() {
        mapper.addRegion(testRegion);

        Optional<Region> response = mapper.findById(1L);
        Region region = response.orElse(null);
        assertNotNull(region);
        region.setRegionName("New name");
        mapper.updateRegion(region);

        response = mapper.findById(1L);
        Region updatedRegion = response.orElse(null);
        assertNotNull(updatedRegion);
        assertEquals(region.getRegionName(), updatedRegion.getRegionName());
        assertEquals(region.getRegionShortName(), updatedRegion.getRegionShortName());
        assertEquals(region.getRegionCode(), updatedRegion.getRegionCode());

    }

    @Test
    void updateRegion_partialUpdate() {
        mapper.addRegion(testRegion);

        Region region = Region.builder()
                .regionCode(1L)
                .regionShortName("HEY")
                .build();
        assertTrue(mapper.updateRegion(region));

        Optional<Region> response = mapper.findById(1L);
        Region updatedRegion = response.orElse(null);
        assertNotNull(updatedRegion);
        assertEquals(testRegion.getRegionName(), updatedRegion.getRegionName());
        assertEquals(region.getRegionShortName(), updatedRegion.getRegionShortName());
        assertEquals(testRegion.getRegionCode(), updatedRegion.getRegionCode());

    }
}