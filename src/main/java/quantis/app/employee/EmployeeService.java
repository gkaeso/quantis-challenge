package quantis.app.employee;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import quantis.app.employee.exception.EmployeeNotFoundException;
import quantis.app.employee.exception.InvalidEmployeeDTOException;
import quantis.app.utils.DTOValidator;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository repository;

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    private final ModelMapper modelMapper = new ModelMapper();

    public List<EmployeeDTO> getAll() {
        List<Employee> employees = repository.findAll();

        List<EmployeeDTO> employeesDTO = Collections.emptyList();
        if (!employees.isEmpty()) {
            employeesDTO = employees
                    .stream()
                    .map(emp -> modelMapper.map(emp, EmployeeDTO.class))
                    .collect(Collectors.toList());
        }

        return employeesDTO;
    }

    public EmployeeDTO get(Long id) throws EmployeeNotFoundException {
        Employee employee = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    public EmployeeDTO create(EmployeeDTO employeeDTO) throws InvalidEmployeeDTOException {
        if (!DTOValidator.isValid(employeeDTO)) {
            throw new InvalidEmployeeDTOException();
        }
        Employee employee = modelMapper.map(employeeDTO, Employee.class);
        repository.save(employee);
        return modelMapper.map(employee, EmployeeDTO.class);
    }

    public EmployeeDTO update(Long id, EmployeeDTO employeeDTO) throws EmployeeNotFoundException {
        Employee employee = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));

        if (employeeDTO.getFirstName() != null && !employeeDTO.getFirstName().isEmpty()) {
            employee.setFirstName(employeeDTO.getFirstName());
        }
        if (employeeDTO.getLastName() != null && !employeeDTO.getLastName().isEmpty()) {
            employee.setLastName(employeeDTO.getLastName());
        }
        if (employeeDTO.getEmail() != null && !employeeDTO.getEmail().isEmpty()) {
            employee.setEmail(employeeDTO.getEmail());
        }
        repository.save(employee);

        return modelMapper.map(employee, EmployeeDTO.class);
    }

    public EmployeeDTO remove(Long id) throws EmployeeNotFoundException {
        Employee employee = repository.findById(id).orElseThrow(() -> new EmployeeNotFoundException(id));
        repository.delete(employee);
        return modelMapper.map(employee, EmployeeDTO.class);
    }

}
