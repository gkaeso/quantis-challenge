package quantis.app.company.exception;

public class InvalidCompanyDTOException extends Exception {

    public InvalidCompanyDTOException() {
        super("Could not create company. Missing fields!");
    }

}
