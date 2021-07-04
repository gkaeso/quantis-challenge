package quantis.app.employee.exception;

public class InvalidEmployeeDTOException extends Exception {

    public InvalidEmployeeDTOException() {
        super("Could not create employee. Missing fields!");
    }

}
