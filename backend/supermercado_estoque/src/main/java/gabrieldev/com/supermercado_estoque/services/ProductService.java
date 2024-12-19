package gabrieldev.com.supermercado_estoque.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import gabrieldev.com.supermercado_estoque.controllers.exceptions.ResourceNotFoundException;
import gabrieldev.com.supermercado_estoque.model.Department;
import gabrieldev.com.supermercado_estoque.model.Product;
import gabrieldev.com.supermercado_estoque.model.DTO.DepartmentDTO;
import gabrieldev.com.supermercado_estoque.model.DTO.ProductDTO;
import gabrieldev.com.supermercado_estoque.repository.DepartmentRepository;
import gabrieldev.com.supermercado_estoque.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DepartmentRepository departmentRepository;

    public ProductDTO findById(Long id) {
        return productRepository.findById(id)
            .map(this::convertToDTO)
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));
    }

    public List<ProductDTO> findAll() {
        return productRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    public ProductDTO create(ProductDTO productDTO) {
        Department department = departmentRepository.findById(productDTO.getDepartmentID().getKey())
            .orElseThrow(() -> new ResourceNotFoundException("Departamento não encontrado"));

        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setQuantity(productDTO.getQuantity());
        product.setEntryDate(productDTO.getEntryDate());
        product.setDepartment(department);

        Product savedProduct = productRepository.save(product);
        return convertToDTO(savedProduct);
    }

    public ProductDTO update(Long id, ProductDTO productDTO) {
        Product existingProduct = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));

        Department department = departmentRepository.findById(productDTO.getDepartmentID().getKey())
            .orElseThrow(() -> new ResourceNotFoundException("Departamento não encontrado"));

        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setQuantity(productDTO.getQuantity());
        existingProduct.setEntryDate(productDTO.getEntryDate());
        existingProduct.setDepartment(department);

        Product updatedProduct = productRepository.save(existingProduct);
        return convertToDTO(updatedProduct);
    }

    public void delete(Long id) {
        Product product = productRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Produto não encontrado com ID: " + id));
        productRepository.delete(product);
    }

    public List<ProductDTO> findByDepartment(Long departmentId) {
        List<Product> products = productRepository.findByDepartmentId(departmentId);
        return products.stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    private ProductDTO convertToDTO(Product product) {
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
