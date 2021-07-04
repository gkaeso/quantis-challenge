package quantis.app.employee;

import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Test;

import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import quantis.app.company.exception.CompanyNotFoundException;
import quantis.app.employee.exception.EmployeeNotFoundException;
import quantis.app.employee.exception.InvalidEmployeeDTOException;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EmployeeControllerTest {

    private MockMvc mockMvc;
    private EmployeeService service;
    private EmployeeDTO dto;
    private List<EmployeeDTO> dtoList;

    @Before
    public void setupMock() {
        service = mock(EmployeeService.class);
        mockMvc = MockMvcBuilders.standaloneSetup(new EmployeeController(service)).build();
    }

    @Before
    public void setup() {
        dto = new EmployeeDTO();
        dto.setId(1L);
        dto.setFirstName("John");;
        dto.setLastName("Lennon");
        dto.setEmail("jlennon@gmail.com");

        dtoList = List.of(dto);
    }

    @Test
    public void testMock() {
        assertNotNull(service);
        assertNotNull(mockMvc);
    }

    @Test
    public void testGetAll() throws Exception {
        mockMvc.perform(get("/employee/"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGet() throws Exception {
        when(service.get(anyLong())).thenReturn(dto);
        mockMvc.perform(get("/employee/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetByCompany() throws Exception {
        when(service.getByCompany(anyLong())).thenReturn(dtoList);
        mockMvc.perform(get("/employee/company/1"))
                .andExpect(status().isOk());
        verify(service, times(1)).getByCompany(anyLong());
    }

    @Test
    public void testGetByCompanyDoesNotExist() throws Exception {
        when(service.getByCompany(anyLong())).thenThrow(CompanyNotFoundException.class);
        mockMvc.perform(get("/employee/company/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetEmployeeDoesNotExist() throws Exception {
        when(service.get(anyLong())).thenThrow(EmployeeNotFoundException.class);
        mockMvc.perform(get("/employee/0"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreate() throws Exception {
        when(service.create(any(EmployeeDTO.class))).thenReturn(dto);
        mockMvc.perform(post("/employee/add").contentType(MediaType.APPLICATION_JSON_VALUE).content((new Gson()).toJson(dto)))
                .andExpect(status().isCreated());
    }

    @Test
    public void testCreateInvalidObject() throws Exception {
        when(service.create(any(EmployeeDTO.class))).thenThrow(InvalidEmployeeDTOException.class);
        mockMvc.perform(post("/employee/add").contentType(MediaType.APPLICATION_JSON_VALUE).content((new Gson()).toJson(dto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdate() throws Exception {
        when(service.update(anyLong(), any(EmployeeDTO.class))).thenReturn(dto);
        mockMvc.perform(put("/employee/1").contentType(MediaType.APPLICATION_JSON_VALUE).content((new Gson()).toJson(dto)))
                .andExpect(status().isOk());
    }

    @Test
    public void testUpdateEmployeeDoesNotExist() throws Exception {
        when(service.update(anyLong(), any(EmployeeDTO.class))).thenThrow(EmployeeNotFoundException.class);
        mockMvc.perform(put("/employee/0").contentType(MediaType.APPLICATION_JSON_VALUE).content((new Gson()).toJson(dto)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testRemove() throws Exception {
        when(service.remove(anyLong())).thenReturn(dto);
        mockMvc.perform(delete("/employee/1").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testRemoveEmployeeDoesNotExist() throws Exception {
        when(service.remove(anyLong())).thenThrow(EmployeeNotFoundException.class);
        mockMvc.perform(delete("/employee/0").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}
