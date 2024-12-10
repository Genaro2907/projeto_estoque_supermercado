package gabrieldev.com.supermercado_estoque.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gabrieldev.com.supermercado_estoque.model.Department;
import gabrieldev.com.supermercado_estoque.model.Product;
import gabrieldev.com.supermercado_estoque.repository.DepartmentRepository;
import gabrieldev.com.supermercado_estoque.repository.ProductRepository;

@Service
public class ProductService {
	
	 @Autowired
	 private ProductRepository productRepository;
	
	@Autowired
    private DepartmentRepository departmentRepository;
	
	public Product findById(Long id) {
		return productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
	}
	public List<Product> findAll() {
		return productRepository.findAll();
	}
	public Product create(Product product) {
        if (product.getDepartment() == null || product.getDepartment().getId() == null) {
            throw new RuntimeException("Department ID is required to create a product!");
        }

        Department department = departmentRepository.findById(product.getDepartment().getId())
                .orElseThrow(() -> new RuntimeException("Department not found with ID: " + product.getDepartment().getId()));

        product.setDepartment(department);

        return productRepository.save(product);
    }
	public Product update(Long id, Product updatedProduct) {
	    Product existingProduct = findById(id);
	    existingProduct.setName(updatedProduct.getName());
	    existingProduct.setDescription(updatedProduct.getDescription());
	    existingProduct.setQuantity(updatedProduct.getQuantity());
	    existingProduct.setEntryDate(updatedProduct.getEntryDate());
	    existingProduct.setDepartment(updatedProduct.getDepartment());
	    return productRepository.save(existingProduct);
	}
	
	public void delete(Long id) {
	    Product product = findById(id);
	    productRepository.delete(product);
	}
	public List<Product> findByDepartment(Long departmentId) {
	    return productRepository.findByDepartmentId(departmentId);
	
}
		
}
