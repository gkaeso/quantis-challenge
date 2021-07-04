package quantis.app.employee;

import org.junit.Before;
import org.junit.Test;

import org.modelmapper.ModelMapper;
import quantis.app.company.exception.CompanyNotFoundException;
import quantis.app.employee.exception.EmployeeNotFoundException;
import quantis.app.employee.exception.InvalidEmployeeDTOException;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class EmployeeServiceTest {

    private EmployeeService service;
    private EmployeeRepository repository;

    private EmployeeDTO dto;
    private Employee emp;
    private List<EmployeeDTO> dtoList;
    private List<Employee> empList;

    private final ModelMapper modelMapper = new ModelMapper();

    @Before
    public void setupMock() {
        repository = mock(EmployeeRepository.class);
        service = new EmployeeService(repository);
    }

    @Before
    public void setup() {
        emp = new Employee();
        emp.setId(1L);
        emp.setFirstName("John");;
        emp.setLastName("Lennon");
        emp.setEmail("jlennon@gmail.com");

        dto = modelMapper.map(emp, EmployeeDTO.class);

        dtoList = List.of(dto);
        empList = List.of(emp);
    }


    @Test
    public void testMock() {
        assertNotNull(repository);
    }

    @Test
    public void testService() {
        assertNotNull(service);
    }

    @Test
    public void testGetAll() {
        when(repository.findAll()).thenReturn(empList);
        List<EmployeeDTO> dtos = service.getAll();
        assertNotNull(dtos);
        assertFalse(dtos.isEmpty());
        assertEquals(dtos.size(), dtoList.size());
        assertEquals(dtoList.get(0), dtos.get(0));
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testGet() {
        when(repository.findById(anyLong())).thenReturn(java.util.Optional.of(emp));
        EmployeeDTO retrievedEmployee = service.get(emp.getId());
        assertNotNull(retrievedEmployee);
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    public void testGetEmployeeDoesNotExist() {
        when(repository.findById(anyLong())).thenThrow(EmployeeNotFoundException.class);
        assertThrows(EmployeeNotFoundException.class, () -> {
            service.get(-1L);
        });
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    public void testGetByCompany() {
        when(repository.findByCompany(anyLong())).thenReturn(empList);
        List<EmployeeDTO> dtos = service.getByCompany(1L);
        assertNotNull(dtos);
        assertFalse(dtos.isEmpty());
        verify(repository, times(1)).findByCompany(anyLong());
    }

    @Test
    public void testGetByCompanyDoesNotExist() {
        when(repository.findByCompany(anyLong())).thenThrow(CompanyNotFoundException.class);
        assertThrows(CompanyNotFoundException.class, () -> {
            service.getByCompany(-1L);
        });
        verify(repository, times(1)).findByCompany(anyLong());
    }

    @Test
    public void testCreate() {
        try {
            when(repository.save(any(Employee.class))).thenReturn(emp);
            EmployeeDTO createdEmployee = service.create(dto);
            assertNotNull(createdEmployee);
            assertEquals(createdEmployee, dto);
            verify(repository, times(1)).save(any(Employee.class));
        } catch (InvalidEmployeeDTOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateInvalidObject() {
        dto.setFirstName(null);
        assertThrows(InvalidEmployeeDTOException.class, () -> {
            service.create(dto);
        });
        verify(repository, times(0)).save(any(Employee.class));
    }

    @Test
    public void testUpdate() {
        EmployeeDTO modifiedDto = modelMapper.map(emp, EmployeeDTO.class);
        modifiedDto.setFirstName("Magnus");

        when(repository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(emp));
        when(repository.save(any(Employee.class))).thenReturn(emp);

        assertNotEquals(modifiedDto.getFirstName(), dto.getFirstName());
        EmployeeDTO newDto = service.update(modifiedDto.getId(), modifiedDto);
        assertNotNull(newDto);
        assertEquals(newDto, modifiedDto);
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).save(any(Employee.class));
    }

    @Test
    public void testUpdateEmployeeDoesNotExist() {
        when(repository.findById(anyLong())).thenThrow(EmployeeNotFoundException.class);
        assertThrows(EmployeeNotFoundException.class, () -> {
            service.update(dto.getId(), dto);
        });
        verify(repository, times(1)).findById(anyLong());
    }

}
