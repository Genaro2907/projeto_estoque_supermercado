package gabrieldev.com.supermercado_estoque.model.DTO;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DepartmentDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String sector;
    private List<ProductDTO> products = new ArrayList<>();

    public DepartmentDTO() {
    }
    
    public DepartmentDTO(Long id) {
        this.id = id;
    }
    public DepartmentDTO(Long id, String sector) {
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

    public List<ProductDTO> getProducts() {
        return products;
    }

    public void setProducts(List<ProductDTO> products) {
        this.products = products;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, sector);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        DepartmentDTO other = (DepartmentDTO) obj;
        return Objects.equals(id, other.id) && Objects.equals(sector, other.sector);
    }
}