package gabrieldev.com.supermercado_estoque.unittests.mocks;

import java.util.ArrayList;
import java.util.List;

import gabrieldev.com.supermercado_estoque.model.Department;
import gabrieldev.com.supermercado_estoque.model.Product;
import gabrieldev.com.supermercado_estoque.model.DTO.DepartmentDTO;
import gabrieldev.com.supermercado_estoque.model.DTO.ProductDTO;

public class MockDepartment {

    private final MockProduct mockProduct = new MockProduct();

    public Department mockEntity() {
        return mockEntity(0);
    }

    public DepartmentDTO mockDTO() {
        return mockDTO(0);
    }

    public List<Department> mockEntityList() {
        List<Department> departments = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            departments.add(mockEntity(i));
        }
        return departments;
    }

    public List<DepartmentDTO> mockDTOList() {
        List<DepartmentDTO> departments = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            departments.add(mockDTO(i));
        }
        return departments;
    }

    public Department mockEntity(Integer number) {
        Department department = new Department();
        department.setId(number.longValue());
        department.setSector("Some Sector " + number);

        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Product product = mockProduct.mockEntity(i);
            product.setDepartment(department); 
            products.add(product);
        }

        department.setProducts(products);
        return department;
    }

    public DepartmentDTO mockDTO(Integer number) {
        DepartmentDTO departmentDTO = new DepartmentDTO();
        departmentDTO.setKey(number.longValue());
        departmentDTO.setSector("Some Sector " + number);

        List<ProductDTO> productDTOs = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            ProductDTO productDTO = mockProduct.mockDTO(i);
            productDTO.setDepartmentID(departmentDTO); 
            productDTOs.add(productDTO);
        }

        departmentDTO.setProducts(productDTOs);
        return departmentDTO;
    }
}
