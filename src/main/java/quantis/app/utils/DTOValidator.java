package quantis.app.utils;

import quantis.app.company.CompanyDTO;
import quantis.app.employee.EmployeeDTO;

public class DTOValidator {

    public static boolean isValid(EmployeeDTO emp) {
        return emp.getFirstName() != null && !emp.getFirstName().isEmpty() &&
                emp.getLastName() != null && !emp.getLastName().isEmpty() &&
                emp.getEmail() != null && !emp.getEmail().isEmpty();
    }

    public static boolean isValid(CompanyDTO company) {
        return company.getName() != null && !company.getName().isEmpty() &&
                company.getSector() != null;
    }
}
