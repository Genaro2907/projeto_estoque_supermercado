package gabrieldev.com.supermercado_estoque.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gabrieldev.com.supermercado_estoque.model.Department;
import gabrieldev.com.supermercado_estoque.repository.DepartmentRepository;

@Service
public class DepartmentService {
	
	@Autowired
	DepartmentRepository repository;
	
	public List<Department> findAll() {
		return repository.findAll();
	}
	
	public Department findById(Long id) {
		return repository.findById(id).orElseThrow(() -> new RuntimeException("Department not found with ID: " + id));
	}
	
	public Department create(Department department) {
	    if (department.getProducts() != null) {
	        department.getProducts().forEach(product -> product.setDepartment(department));
	    }
	    return repository.save(department);
	}
	
}
