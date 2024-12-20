package gabrieldev.com.supermercado_estoque.services.mapper;

import org.springframework.stereotype.Component;

import gabrieldev.com.supermercado_estoque.model.Product;
import gabrieldev.com.supermercado_estoque.model.DTO.DepartmentDTO;
import gabrieldev.com.supermercado_estoque.model.DTO.ProductDTO;

@Component
public class ProductMapper {
    
	public ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();

        productDTO.setKey(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setQuantity(product.getQuantity());
        productDTO.setEntryDate(product.getEntryDate());

        if (product.getDepartment() != null) {
            DepartmentDTO departmentDTO = new DepartmentDTO();
            departmentDTO.setKey(product.getDepartment().getId());
            departmentDTO.setSector(product.getDepartment().getSector());
            productDTO.setDepartmentID(departmentDTO);
        }

        return productDTO;
    }
}
