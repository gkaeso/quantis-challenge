package quantis.app.company.exception;

public class IllegalCompanySectorException extends IllegalArgumentException {

    public IllegalCompanySectorException(String name) {
        super(String.format("Input company sector '%s' is invalid!", name));
    }
}
