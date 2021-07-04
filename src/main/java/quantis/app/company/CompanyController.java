package quantis.app.company;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import quantis.app.company.exception.CompanyNotFoundException;
import quantis.app.company.exception.IllegalCompanySectorException;
import quantis.app.company.exception.InvalidCompanyDTOException;
import quantis.app.employee.exception.InvalidEmployeeDTOException;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/company")
public class CompanyController {

    private final CompanyService service;

    public CompanyController(CompanyService service) {
        this.service = service;
    }

    @GetMapping("/")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    List<CompanyDTO> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody CompanyDTO get(@PathVariable Long id) throws CompanyNotFoundException {
        return service.get(id);
    }

    @GetMapping("/sector/{name}")
    @ResponseStatus(HttpStatus.OK)
    List<CompanyDTO> getBySector(@PathVariable String name) {
        return service.getBySector(name);
    }

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody CompanyDTO create(@RequestBody CompanyDTO companyDTO) throws InvalidCompanyDTOException {
        return service.create(companyDTO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody CompanyDTO update(@PathVariable Long id, @RequestBody CompanyDTO companyDTO) {
        return service.update(id, companyDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody CompanyDTO remove(@PathVariable Long id) {
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

    @ExceptionHandler(IllegalCompanySectorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<String> handleEntityNotFoundException(IllegalCompanySectorException exc) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exc.getMessage());
    }

}
