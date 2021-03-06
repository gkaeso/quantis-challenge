package quantis.app.company;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import quantis.app.company.exception.CompanyNotFoundException;
import quantis.app.company.exception.IllegalCompanySectorException;
import quantis.app.company.exception.InvalidCompanyDTOException;
import quantis.app.utils.DTOValidator;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompanyService {

    private final CompanyRepository repository;

    public CompanyService(CompanyRepository repository) {
        this.repository = repository;
    }

    private final ModelMapper modelMapper = new ModelMapper();

    public List<CompanyDTO> getAll() {
        List<CompanyDTO> companiesDto = Collections.emptyList();

        List<Company> companies = repository.findAll();
        if (!companies.isEmpty()) {
            companiesDto = companies
                    .stream()
                    .map(company -> modelMapper.map(company, CompanyDTO.class))
                    .collect(Collectors.toList());
            companiesDto.forEach(dto -> dto.setEmployeeNb(repository.countEmployeesByCompanyId(dto.getId())));
        }

        return companiesDto;
    }

    public CompanyDTO get(Long id) throws CompanyNotFoundException {
        Company company = repository.findById(id).orElseThrow(() -> new CompanyNotFoundException(id));

        CompanyDTO dto = modelMapper.map(company, CompanyDTO.class);
        dto.setEmployeeNb(repository.countEmployeesByCompanyId(company.getId()));

        return dto;
    }

    public List<CompanyDTO> getBySector(String name) throws IllegalCompanySectorException {
        List<CompanyDTO> companiesDto = Collections.emptyList();

        CompanySector sector;
        try {
            sector = CompanySector.valueOf(name.toUpperCase());
        } catch (IllegalArgumentException exc) {
            throw new IllegalCompanySectorException(name);
        }

        List<Company> companies = repository.findBySector(sector);
        if (!companies.isEmpty()) {
            companiesDto = companies
                    .stream()
                    .map(company -> modelMapper.map(company, CompanyDTO.class))
                    .collect(Collectors.toList());
            companiesDto.forEach(dto -> dto.setEmployeeNb(repository.countEmployeesByCompanyId(dto.getId())));
        }

        return companiesDto;
    }

    public CompanyDTO create(CompanyDTO companyDto) throws InvalidCompanyDTOException {
        if (!DTOValidator.isValid(companyDto)) {
            throw new InvalidCompanyDTOException();
        }
        Company company = modelMapper.map(companyDto, Company.class);

        repository.save(company);

        return modelMapper.map(company, CompanyDTO.class);
    }

    public CompanyDTO update(Long id, CompanyDTO companyDto) throws CompanyNotFoundException {
        Company company = repository.findById(id).orElseThrow(() -> new CompanyNotFoundException(id));

        if (companyDto.getName() != null && !companyDto.getName().isEmpty()) {
            company.setName(companyDto.getName());
        }
        if (companyDto.getDescription() != null && !companyDto.getDescription().isEmpty()) {
            company.setDescription(companyDto.getDescription());
        }
        if (companyDto.getSector() != null) {
            company.setSector(CompanySector.valueOf(companyDto.getSector().name()));
        }
        repository.save(company);

        return modelMapper.map(company, CompanyDTO.class);
    }

    public CompanyDTO remove(Long id) throws CompanyNotFoundException {
        Company company = repository.findById(id).orElseThrow(() -> new CompanyNotFoundException(id));
        repository.delete(company);
        return modelMapper.map(company, CompanyDTO.class);
    }

}
