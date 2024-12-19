package gabrieldev.com.supermercado_estoque.model.DTO;

import java.io.Serializable;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;


@JsonPropertyOrder({ "id", "sector"})
public class SimpleDepartmentDTO extends RepresentationModel<SimpleDepartmentDTO> implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @JsonProperty("id")
    @Mapping("id")
    private Long key;
    private String sector;

    public SimpleDepartmentDTO() {
    }

    public SimpleDepartmentDTO(Long key, String sector) {
        this.key = key;
        this.sector = sector;
    }

    public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getSector() {
        return sector;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

	@Override
	public int hashCode() {
		return Objects.hash(key, sector);
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
		return Objects.equals(key, other.key) && Objects.equals(sector, other.sector);
	}

	

}