package gabrieldev.com.supermercado_estoque.model.DTO;

import java.io.Serializable;
import java.util.Objects;

public class SimpleDepartmentDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String sector;

    public SimpleDepartmentDTO() {
    }

    public SimpleDepartmentDTO(Long id, String sector) {
        this.id = id;
        this.sector = sector;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

	@Override
	public int hashCode() {
		return Objects.hash(id, sector);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleDepartmentDTO other = (SimpleDepartmentDTO) obj;
		return Objects.equals(id, other.id) && Objects.equals(sector, other.sector);
	}

}