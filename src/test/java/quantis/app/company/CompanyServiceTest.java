package quantis.app.company;

import org.junit.Before;
import org.junit.Test;

import org.modelmapper.ModelMapper;

import quantis.app.company.exception.CompanyNotFoundException;
import quantis.app.company.exception.InvalidCompanyDTOException;

import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class CompanyServiceTest {

    private CompanyService service;
    private CompanyRepository repository;

    private CompanyDTO dto;
    private Company company;
    private List<CompanyDTO> dtoList;
    private List<Company> companyList;

    private final ModelMapper modelMapper = new ModelMapper();

    @Before
    public void setupMock() {
        repository = mock(CompanyRepository.class);
        service = new CompanyService(repository);
    }

    @Before
    public void setup() {
        company = new Company();
        company.setId(1L);
        company.setName("Quantis");
        company.setDescription("A Swiss company");
        company.setSector(CompanySector.ICT);

        dto = modelMapper.map(company, CompanyDTO.class);

        dtoList = List.of(dto);
        companyList = List.of(company);
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
        when(repository.findAll()).thenReturn(companyList);
        List<CompanyDTO> dtos = service.getAll();
        assertNotNull(dtos);
        assertFalse(dtos.isEmpty());
        assertEquals(dtos.size(), dtoList.size());
        assertEquals(dtoList.get(0), dtos.get(0));
        verify(repository, times(1)).findAll();
    }

    @Test
    public void testGet() {
        when(repository.findById(anyLong())).thenReturn(java.util.Optional.of(company));
        CompanyDTO retrievedCompany = service.get(company.getId());
        assertNotNull(retrievedCompany);
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    public void testGetCompanyDoesNotExist() {
        when(repository.findById(anyLong())).thenThrow(CompanyNotFoundException.class);
        assertThrows(CompanyNotFoundException.class, () -> {
            service.get(-1L);
        });
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    public void testCreate() {
        try {
            when(repository.save(any(Company.class))).thenReturn(company);
            CompanyDTO createdCompany = service.create(dto);
            assertNotNull(createdCompany);
            assertEquals(createdCompany, dto);
            verify(repository, times(1)).save(any(Company.class));
        } catch (InvalidCompanyDTOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testCreateInvalidObject() {
        dto.setName(null);
        assertThrows(InvalidCompanyDTOException.class, () -> {
            service.create(dto);
        });
        verify(repository, times(0)).save(any(Company.class));
    }

    @Test
    public void testUpdate() {
        CompanyDTO modifiedDto = modelMapper.map(company, CompanyDTO.class);
        modifiedDto.setName("Google");

        when(repository.findById(anyLong())).thenReturn(java.util.Optional.ofNullable(company));
        when(repository.save(any(Company.class))).thenReturn(company);

        assertNotEquals(modifiedDto.getName(), dto.getName());
        CompanyDTO newDto = service.update(modifiedDto.getId(), modifiedDto);
        assertNotNull(newDto);
        assertEquals(newDto, modifiedDto);
        verify(repository, times(1)).findById(anyLong());
        verify(repository, times(1)).save(any(Company.class));
    }

    @Test
    public void testUpdateCompanyDoesNotExist() {
        when(repository.findById(anyLong())).thenThrow(CompanyNotFoundException.class);
        assertThrows(CompanyNotFoundException.class, () -> {
            service.update(dto.getId(), dto);
        });
        verify(repository, times(1)).findById(anyLong());
    }

}
