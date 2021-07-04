package quantis.app.employee;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import quantis.app.employee.exception.EmployeeNotFoundException;
import quantis.app.employee.exception.InvalidEmployeeDTOException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

    private final EmployeeService service;

    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody List<EmployeeDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody EmployeeDTO get(@PathVariable Long id) throws EmployeeNotFoundException {
        return service.get(id);
    }
    @GetMapping("/company/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody List<EmployeeDTO> getByCompany(@PathVariable Long id) {
        return service.getByCompany(id);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody EmployeeDTO create(@RequestBody EmployeeDTO employeeDTO) throws InvalidEmployeeDTOException {
        return service.create(employeeDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody EmployeeDTO update(@PathVariable Long id, @RequestBody EmployeeDTO employeeDTO) {
        return service.update(id, employeeDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody EmployeeDTO remove(@PathVariable Long id) {
        return service.remove(id);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException exc) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exc.getMessage());
    }

    @ExceptionHandler(InvalidEmployeeDTOException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleEntityNotFoundException(InvalidEmployeeDTOException exc) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exc.getMessage());
    }

}
