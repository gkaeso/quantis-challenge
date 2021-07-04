package quantis.app.employee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query(value = "SELECT * FROM employee e WHERE e.company_id = :companyId", nativeQuery = true)
    List<Employee> findByCompany(Long companyId);

}
