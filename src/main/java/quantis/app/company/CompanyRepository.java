package quantis.app.company;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CompanyRepository extends JpaRepository<Company, Long> {

    List<Company> findBySector(CompanySector sector);

    @Query(value = "SELECT COUNT(*) FROM employee e WHERE e.company_id = :companyId", nativeQuery = true)
    Integer countEmployeesByCompanyId(Long companyId);

}
