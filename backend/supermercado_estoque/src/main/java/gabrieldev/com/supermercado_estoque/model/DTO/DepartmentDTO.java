package gabrieldev.com.supermercado_estoque.model.DTO;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;

@JsonPropertyOrder({ "id", "sector", "products"})
public class DepartmentDTO extends RepresentationModel<DepartmentDTO> implements Serializable {
    private static final long serialVersionUID = 1L;
    
    
    @JsonProperty("id")
    @Mapping("id")
    private Long key;
    private String sector;
    private List<ProductDTO> products = new ArrayList<>();
    private List<SimpleProductDTO> simpleProducts = new ArrayList<>();

    public DepartmentDTO() {
    }
    
    public DepartmentDTO(Long key) {
        this.key = key;
    }
    public DepartmentDTO(Long key, String sector) {
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

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }
    public List<SimpleProductDTO> getSimpleProducts() {
        return simpleProducts;
    }
    
    public void setSimpleProducts(List<SimpleProductDTO> simpleProducts) {
        this.simpleProducts = simpleProducts;
    }

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + Objects.hash(key, products, sector);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DepartmentDTO other = (DepartmentDTO) obj;
		return Objects.equals(key, other.key) && Objects.equals(products, other.products)
				&& Objects.equals(sector, other.sector);
	}
    

}