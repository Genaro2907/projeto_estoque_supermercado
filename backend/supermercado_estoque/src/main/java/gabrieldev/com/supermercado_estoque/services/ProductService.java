package gabrieldev.com.supermercado_estoque.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gabrieldev.com.supermercado_estoque.model.Product;
import gabrieldev.com.supermercado_estoque.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	ProductRepository repository;
	
	public Product findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new RuntimeException("Product not found with ID: " + id));
	}
	public List<Product> findAll() {
		return repository.findAll();
	}
	public Product create(Product product) {
	    if (product.getDepartment() == null || product.getDepartment().getId() == null) {
	        throw new RuntimeException("Department ID is required!");
	    }
	    return repository.save(product);
	}
	public Product update(Long id, Product updatedProduct) {
	    Product existingProduct = findById(id);
	    existingProduct.setName(updatedProduct.getName());
	    existingProduct.setDescription(updatedProduct.getDescription());
	    existingProduct.setQuantity(updatedProduct.getQuantity());
	    existingProduct.setEntryDate(updatedProduct.getEntryDate());
	    existingProduct.setDepartment(updatedProduct.getDepartment());
	    return repository.save(existingProduct);
	}
	
	public void delete(Long id) {
	    Product product = findById(id);
	    repository.delete(product);
	}
	public List<Product> findByDepartment(Long departmentId) {
	    return repository.findByDepartmentId(departmentId);
	
}
		
}
