package quantis.app.company;

import java.io.Serializable;
import java.util.Objects;

public class CompanyDTO implements Serializable {

    private Long id;

    private String name;

    private String description;

    private CompanySector sector;

    private Integer employeeNb;

    public CompanyDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CompanySector getSector() {
        return sector;
    }

    public void setSector(CompanySector sector) {
        this.sector = sector;
    }

    public Integer getEmployeeNb() {
        return employeeNb;
    }

    public void setEmployeeNb(Integer employeeNb) {
        this.employeeNb = employeeNb;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CompanyDTO that = (CompanyDTO) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(description, that.description) && sector == that.sector;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, sector);
    }

}
