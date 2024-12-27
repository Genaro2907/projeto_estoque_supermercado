package gabrieldev.com.supermercado_estoque.services.mapper;

import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import gabrieldev.com.supermercado_estoque.model.Department;
import gabrieldev.com.supermercado_estoque.model.Product;
import gabrieldev.com.supermercado_estoque.model.DTO.DepartmentDTO;
import gabrieldev.com.supermercado_estoque.model.DTO.ProductDTO;
import gabrieldev.com.supermercado_estoque.model.DTO.SimpleDepartmentDTO;
import gabrieldev.com.supermercado_estoque.model.DTO.SimpleProductDTO;

@Component
public class DepartmentMapper {
    
    public DepartmentDTO toDTO(Department department) {
        DepartmentDTO dto = new DepartmentDTO(department.getId(), department.getSector());
        
        if (department.getProducts() != null) {
            dto.setProducts(department.getProducts().stream()
                .map(this::toProductDTO)
                .collect(Collectors.toList()));
        }
        
        return dto;
    }
    public DepartmentDTO SimpleProductoDTO(Department department) {
        DepartmentDTO dto = new DepartmentDTO(department.getId(), department.getSector());
        
        if (department.getProducts() != null) {
            dto.setSimpleProducts(department.getProducts().stream()
                .map(this::toSimpleProductDTO)
                .collect(Collectors.toList()));
        }
        
        return dto;
    }
    
    public SimpleDepartmentDTO toSimpleDepartmentDTO(Department department) {
        if (department == null) {
            return null;
        }
        SimpleDepartmentDTO dto = new SimpleDepartmentDTO();
        dto.setKey(department.getId());
        dto.setSector(department.getSector());

        return dto;
    }
    public Department toEntity(DepartmentDTO dto) {
        return new Department(dto.getKey(), dto.getSector());
    }
    
    private ProductDTO toProductDTO(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.setKey(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setQuantity(product.getQuantity());
        dto.setEntryDate(product.getEntryDate());
        
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setKey(product.getDepartment().getId());
        departmentDTO.setSector(product.getDepartment().getSector());
        dto.setDepartmentID(departmentDTO);
        
        return dto;
    }
    
    
    private SimpleProductDTO toSimpleProductDTO(Product product) {
    	SimpleProductDTO dto = new SimpleProductDTO();
        dto.setKey(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setQuantity(product.getQuantity());
        dto.setEntryDate(product.getEntryDate());
        
        return dto;
    }
}
