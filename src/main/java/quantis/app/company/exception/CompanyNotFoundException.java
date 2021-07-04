package quantis.app.company.exception;

import javax.persistence.EntityNotFoundException;

public class CompanyNotFoundException extends EntityNotFoundException {

    public CompanyNotFoundException(Long id) {
        super("Could not find company with id: " + id.toString());
    }

}
