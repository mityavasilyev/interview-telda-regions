package io.github.mityavasilyev.regionservice.service;

import io.github.mityavasilyev.regionservice.exception.MissingRegionDataException;
import io.github.mityavasilyev.regionservice.exception.RegionNotFoundException;
import io.github.mityavasilyev.regionservice.mapper.RegionMapper;
import io.github.mityavasilyev.regionservice.model.Region;
import io.github.mityavasilyev.regionservice.model.RegionDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RegionServiceImplTest {

    private Region testRegion;

    @Mock
    private RegionMapper mapper;

    @InjectMocks
    private RegionServiceImpl service;

    private Map<Long, Region> dummyRepo;

    @BeforeEach
    void setUp() {
        testRegion = Region.builder()
                .regionCode(1L)
                .regionName("Test Region")
                .regionShortName("Short Name")
                .build();

        dummyRepo = new HashMap<>();
        dummyRepo.put(testRegion.getRegionCode(), testRegion);
    }

    // Testing getAllRegions()
    @Test
    void getAllRegions() {
        when(mapper.findAll())
                .thenReturn(new ArrayList<>(dummyRepo.values()));
        List<RegionDTO> regionDTOS = service.getAllRegions();

        assertEquals(dummyRepo.size(), regionDTOS.size());
    }

    // Testing getAllRegionsByNameContaining()
    @Test
    void getAllRegionsByNameContaining() {
        when(mapper.findAllContaining("re"))
                .thenReturn(new ArrayList<>(dummyRepo.values()));
        List<RegionDTO> regionDTOS = service.getAllRegionsByNameContaining("re");

        assertEquals(dummyRepo.size(), regionDTOS.size());
    }

    @Test
    void getAllRegionsByNameContaining_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class,
                () -> service.getAllRegionsByNameContaining(""));
    }

    // Testing getRegionByCode()
    @Test
    void getRegionByCode() {
        when(mapper.findByCode(Mockito.any()))
                .thenReturn(Optional.ofNullable(dummyRepo.get(1L)));
        RegionDTO regionDTO = service.getRegionByCode(1L);

        assertEquals(testRegion.getRegionName(), regionDTO.regionName());
        assertEquals(testRegion.getRegionShortName(), regionDTO.regionShortName());
    }

    @Test
    void getRegionByCode_throwsRegionNotFound() {
        when(mapper.findByCode(Mockito.any()))
                .thenReturn(Optional.empty());

        assertThrows(RegionNotFoundException.class,
                () -> service.getRegionByCode(1L));
    }

    @Test
    void getRegionByCode_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class,
                () -> service.getRegionByCode(-1L));
        assertThrows(IllegalArgumentException.class,
                () -> service.getRegionByCode(0L));
    }

    // Testing getRegionByName()
    @Test
    void getRegionByName() {
        when(mapper.findByName("Test Region"))
                .thenReturn(Optional.ofNullable(dummyRepo.get(1L)));
        RegionDTO regionDTO = service.getRegionByName("Test Region");

        assertEquals(testRegion.getRegionName(), regionDTO.regionName());
        assertEquals(testRegion.getRegionShortName(), regionDTO.regionShortName());
    }

    @Test
    void getRegionByName_throwsRegionNotFound() {
        when(mapper.findByName(Mockito.any()))
                .thenReturn(Optional.empty());

        assertThrows(RegionNotFoundException.class,
                () -> service.getRegionByName("Bruh"));
    }

    @Test
    void getRegionByName_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class,
                () -> service.getRegionByName(""));
        assertThrows(IllegalArgumentException.class,
                () -> service.getRegionByName(" "));
    }

    // Testing getRegionByShortName()
    @Test
    void getRegionByShortName() {
        when(mapper.findByShortName(testRegion.getRegionShortName()))
                .thenReturn(Optional.ofNullable(dummyRepo.get(1L)));
        RegionDTO regionDTO = service.getRegionByShortName(testRegion.getRegionShortName());

        assertEquals(testRegion.getRegionName(), regionDTO.regionName());
        assertEquals(testRegion.getRegionShortName(), regionDTO.regionShortName());
    }

    @Test
    void getRegionByShortName_throwsRegionNotFound() {
        when(mapper.findByShortName(Mockito.any()))
                .thenReturn(Optional.empty());

        assertThrows(RegionNotFoundException.class,
                () -> service.getRegionByShortName("Bruh"));
    }

    @Test
    void getRegionByShortName_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class,
                () -> service.getRegionByShortName(""));
        assertThrows(IllegalArgumentException.class,
                () -> service.getRegionByShortName(" "));
    }

    // Testing addRegion()
    @Test
    void addRegion() {
        Region newRegion = Region.builder()
                .regionCode(2L)
                .regionName("New Region")
                .regionShortName("NR")
                .build();
        when(mapper.addRegion(any()))
                .thenReturn(2);

        assertTrue(service.addRegion(newRegion));

        when(mapper.addRegion(any()))
                .thenThrow(new RuntimeException("DB Mock exception"));
        assertThrows(IllegalArgumentException.class,
                () -> service.addRegion(newRegion));
    }

    @Test
    void addRegion_throwsMissingRegionDataOnEmptyRegionCode() {
        assertThrows(MissingRegionDataException.class,
                () -> service.addRegion(Region.builder().regionName("Bruh Region").build()));
    }

    @Test
    void addRegion_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class,
                () -> service.addRegion(Region.builder().build()));
        assertThrows(IllegalArgumentException.class,
                () -> service.addRegion(Region.builder().regionName(" ").build()));
    }

    // Testing deleteRegion()
    @Test
    void deleteRegion() {
        when(mapper.deleteRegion(1L))
                .thenReturn(dummyRepo.remove(1L) != null);
        assertTrue(service.deleteRegion(1L));

        // Deleting non existing entity
        when(mapper.deleteRegion(2L))
                .thenReturn(dummyRepo.remove(2L) != null);
        assertThrows(RegionNotFoundException.class,
                () -> service.deleteRegion(2L));
    }

    @Test
    void deleteRegion_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class,
                () -> service.deleteRegion(-1L));
        assertThrows(IllegalArgumentException.class,
                () -> service.deleteRegion(0L));
    }

    // Testing updateRegion()
    @Test
    void updateRegion() {
        testRegion.setRegionName("New Name");
        when(mapper.updateRegion(any()))
                .thenReturn(dummyRepo.put(testRegion.getRegionCode(), testRegion) != null);

        assertTrue(service.updateRegion(testRegion));

        // Trying to update non existing entity
        dummyRepo.clear();
        when(mapper.updateRegion(any()))
                .thenReturn(dummyRepo.put(testRegion.getRegionCode(), testRegion) != null);
        assertThrows(RegionNotFoundException.class,
                () -> service.updateRegion(testRegion));

        when(mapper.updateRegion(any()))
                .thenThrow(new RuntimeException("Some trouble with saving to DB"));
        assertThrows(RegionNotFoundException.class,
                () -> service.updateRegion(testRegion));
    }

    @Test
    void updateRegion_throwsIllegalArgument() {
        assertThrows(IllegalArgumentException.class,
                () -> service.updateRegion(Region.builder().build()));

        assertThrows(IllegalArgumentException.class,
                () -> service.updateRegion(Region.builder().regionCode(1L).build()));

        assertThrows(IllegalArgumentException.class,
                () -> service.updateRegion(Region.builder().regionCode(1L).regionName(" ").build()));
    }

    @Test
    void processRegion() {
        Region newRegion = Region.builder()
                .regionCode(3L)
                .regionName("new region")
                .build();
        when(mapper.addRegion(any()))
                .thenReturn(2);
        service.addRegion(newRegion);

        verify(mapper).addRegion(argThat((Region processedRegion) ->
                processedRegion.getRegionName().equals("New Region") && processedRegion.getRegionShortName().equals("NR")
        ));
    }
}