package io.github.mityavasilyev.regionservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.mityavasilyev.regionservice.model.Region;
import io.github.mityavasilyev.regionservice.model.RegionDTO;
import io.github.mityavasilyev.regionservice.service.RegionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class RegionControllerTest {

    MockMvc mockMvc;
    @Mock
    private RegionService service;
    @InjectMocks
    private RegionController controller;
    private Region testRegion;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .standaloneSetup(controller)
                .build();

        testRegion = Region.builder()
                .regionCode(1L)
                .regionName("Test Region")
                .regionShortName("TR")
                .build();
    }

    @Test
    void getRegions() throws Exception {
        when(service.getAllRegions())
                .thenReturn(List.of(RegionDTO.entityToDTO(testRegion)));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/regions")
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.response.data.[0]").exists());
        verify(service, times(1))
                .getAllRegions();
    }

    @Test
    void getRegions_withQuery() throws Exception {
        when(service.getAllRegionsByNameContaining(anyString()))
                .thenReturn(List.of(
                        RegionDTO.entityToDTO(testRegion)
                ));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/regions?value=test")
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.response.data.[0]").exists());
        verify(service, times(1))
                .getAllRegionsByNameContaining(anyString());
    }

    @Test
    void getRegionByCode() throws Exception {
        when(service.getRegionByCode(testRegion.getRegionCode()))
                .thenReturn(RegionDTO.entityToDTO(testRegion));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/region/code/" + testRegion.getRegionCode())
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.response.data.regionCode")
                        .value(testRegion.getRegionCode()));
        verify(service, times(1))
                .getRegionByCode(anyLong());
    }

    @Test
    void getRegionByName() throws Exception {
        when(service.getRegionByName(testRegion.getRegionName()))
                .thenReturn(RegionDTO.entityToDTO(testRegion));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/region/name?value=" + testRegion.getRegionName())
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.response.data.regionName")
                        .value(testRegion.getRegionName()));
        verify(service, times(1))
                .getRegionByName(anyString());
    }

    @Test
    void getRegionByShortName() throws Exception {
        when(service.getRegionByShortName(testRegion.getRegionShortName()))
                .thenReturn(RegionDTO.entityToDTO(testRegion));
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/api/v1/region/shortname?value=" + testRegion.getRegionShortName())
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.response.data.regionShortName")
                        .value(testRegion.getRegionShortName()));
        verify(service, times(1))
                .getRegionByShortName(anyString());
    }

    @Test
    void saveNewRegion() throws Exception {
        when(service.addRegion(testRegion))
                .thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/region/save")
                        .content(new ObjectMapper().writeValueAsString(testRegion))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isCreated())
                .andExpect(jsonPath("$.response.data.regionShortName")
                        .value(testRegion.getRegionShortName()));
        verify(service, times(1))
                .addRegion(testRegion);
    }

    @Test
    void deleteRegion() throws Exception {
        when(service.deleteRegion(testRegion.getRegionCode()))
                .thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/region/code/" + testRegion.getRegionCode())
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.response.data")
                        .value(true));
        verify(service, times(1))
                .deleteRegion(testRegion.getRegionCode());
    }

    @Test
    void updateRegion() throws Exception {
        when(service.updateRegion(testRegion))
                .thenReturn(true);
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/region/code/" + testRegion.getRegionCode())
                        .content(new ObjectMapper().writeValueAsString(testRegion))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.response.data")
                        .value(true));
        verify(service, times(1))
                .updateRegion(testRegion);
    }
}