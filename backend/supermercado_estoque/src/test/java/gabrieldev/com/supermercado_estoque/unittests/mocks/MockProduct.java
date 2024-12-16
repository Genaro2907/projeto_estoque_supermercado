package gabrieldev.com.supermercado_estoque.unittests.mocks;

import java.util.ArrayList;
import java.util.List;

import gabrieldev.com.supermercado_estoque.model.Department;
import gabrieldev.com.supermercado_estoque.model.Product;
import gabrieldev.com.supermercado_estoque.model.DTO.ProductDTO;

public class MockProduct {

    public Product mockEntity() {
        return mockEntity(0);
    }

    public ProductDTO mockDTO() {
        return mockDTO(0);
    }

    public List<Product> mockEntityList() {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            products.add(mockEntity(i));
        }
        return products;
    }

    public List<ProductDTO> mockDTOList() {
        List<ProductDTO> products = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            products.add(mockDTO(i));
        }
        return products;
    }

    public Product mockEntity(Integer number) {
        Product product = new Product();
        Department department = new Department();
        department.setId(number.longValue()); 
        department.setSector("Sector Test" + number);

        product.setName("Name Test" + number);
        product.setDescription("Description Test" + number);
        product.setQuantity(number);
        product.setId(number.longValue());
        product.setDepartment(department); 
        return product;
    }

    public ProductDTO mockDTO(Integer number) {
        ProductDTO product = new ProductDTO();
        product.setName("Name Test" + number);
        product.setDescription("Description Test" + number);
        product.setQuantity(number);
        product.setId(number.longValue());
        if (number == 0) {
            product.setDepartmentID(0L);
        } else {
            product.setDepartmentID(number.longValue());
        }
        return product;
    }
}
