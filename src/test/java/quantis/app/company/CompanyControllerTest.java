package quantis.app.company;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import quantis.app.company.exception.CompanyNotFoundException;

import static org.junit.Assert.assertNotNull;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CompanyControllerTest {

    private MockMvc mockMvc;
    private CompanyService service;
    private CompanyDTO dto;

    @Before
    public void setupMock() {
        service = mock(CompanyService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new CompanyController(service)).build();
    }

    @Before
    public void setup() {
        dto = new CompanyDTO();
        dto.setName("Quantis");
        dto.setDescription("A Swiss company");
        dto.setSector(CompanySector.ICT);
    }

    @Test
    public void testMock() {
        assertNotNull(service);
        assertNotNull(mockMvc);
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get("/company/"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGet() throws Exception {
        when(service.get(anyLong())).thenReturn(dto);
        mockMvc.perform(get("/company/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetCompanyDoesNotExist() throws Exception {
        when(service.get(anyLong())).thenThrow(CompanyNotFoundException.class);
        mockMvc.perform(get("/company/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreate() throws Exception {
        when(service.create(any(CompanyDTO.class))).thenReturn(dto);
        mockMvc.perform(post("/company/add").contentType(MediaType.APPLICATION_JSON_VALUE).content((new Gson()).toJson(dto)))
                .andExpect(status().isCreated());
    }
/*
    @Test
    public void testCreateInvalidObject() throws Exception {
        when(service.create(any(CompanyDTO.class))).thenThrow(InvalidCompanyDTOException.class);
        mockMvc.perform(post("/company/add").contentType(MediaType.APPLICATION_JSON_VALUE).content((new Gson()).toJson(dto)))
                .andExpect(status().isBadRequest());
    }
*/
    @Test
    public void testUpdate() throws Exception {
        when(service.update(anyLong(), any(CompanyDTO.class))).thenReturn(dto);
        mockMvc.perform(put("/company/1").contentType(MediaType.APPLICATION_JSON_VALUE).content((new Gson()).toJson(dto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateCompanyDoesNotExist() throws Exception {
        when(service.update(anyLong(), any(CompanyDTO.class))).thenThrow(CompanyNotFoundException.class);
        mockMvc.perform(put("/company/0").contentType(MediaType.APPLICATION_JSON_VALUE).content((new Gson()).toJson(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testRemove() throws Exception {
        when(service.remove(anyLong())).thenReturn(dto);
        mockMvc.perform(delete("/company/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testRemoveCompanyDoesNotExist() throws Exception {
        when(service.remove(anyLong())).thenThrow(CompanyNotFoundException.class);
        mockMvc.perform(delete("/company/0").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
