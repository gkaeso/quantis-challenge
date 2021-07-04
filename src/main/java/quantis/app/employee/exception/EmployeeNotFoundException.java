package quantis.app.employee.exception;

import javax.persistence.EntityNotFoundException;

public class EmployeeNotFoundException extends EntityNotFoundException {

    public EmployeeNotFoundException(Long id) {
        super("Could not find employee with id: " + id.toString());
    }

}
