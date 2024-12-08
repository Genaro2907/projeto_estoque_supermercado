package gabrieldev.com.supermercado_estoque.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gabrieldev.com.supermercado_estoque.model.Product;
import gabrieldev.com.supermercado_estoque.services.ProductService;


@RestController
@RequestMapping("/product")
public class ProductController {
	
	
	@Autowired
	private ProductService service;
	
	
	@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public Product findById(@PathVariable Long id) {
		return service.findById(id);
	}
	
	@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Product> findAll() {
		return service.findAll();
	}
	
	@PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Product create(@RequestBody Product product) {
		return service.create(product);
	}
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public Product update(@PathVariable Long id, @RequestBody Product product) {
	    return service.update(id, product);
	}
	@DeleteMapping("/{id}")
	public void delete(@PathVariable Long id) {
	    service.delete(id);
	}
	@GetMapping("/department/{departmentId}")
    public List<Product> findByDepartment(@PathVariable Long departmentId) {
        return service.findByDepartment(departmentId);
    }

}
