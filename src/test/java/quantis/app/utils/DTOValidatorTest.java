package quantis.app.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import quantis.app.employee.EmployeeDTO;

public class DTOValidatorTest {

    private EmployeeDTO dto;

    @Before
    public void setup() {
        dto = new EmployeeDTO();
        dto.setFirstName("First");
        dto.setLastName("Last");
        dto.setEmail("email@test.com");
    }

    @Test
    public void testEmployeeValid() {
        Assert.assertTrue(DTOValidator.isValid(dto));
    }

    @Test
    public void testEmployeeInvalidMissingFirstName() {
        dto.setFirstName("");
        Assert.assertFalse(DTOValidator.isValid(dto));
        dto.setFirstName(null);
        Assert.assertFalse(DTOValidator.isValid(dto));
    }

    @Test
    public void testEmployeeInvalidMissingLastName() {
        dto.setLastName("");
        Assert.assertFalse(DTOValidator.isValid(dto));
        dto.setLastName(null);
        Assert.assertFalse(DTOValidator.isValid(dto));
    }

    @Test
    public void testEmployeeInvalidMissingEmail() {
        dto.setEmail("");
        Assert.assertFalse(DTOValidator.isValid(dto));
        dto.setEmail(null);
        Assert.assertFalse(DTOValidator.isValid(dto));
    }

}
