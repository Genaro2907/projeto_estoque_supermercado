package gabrieldev.com.supermercado_estoque.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gabrieldev.com.supermercado_estoque.controllers.exceptions.ResourceNotFoundException;
import gabrieldev.com.supermercado_estoque.model.Department;
import gabrieldev.com.supermercado_estoque.model.Product;
import gabrieldev.com.supermercado_estoque.model.DTO.DepartmentDTO;
import gabrieldev.com.supermercado_estoque.model.DTO.ProductDTO;
import gabrieldev.com.supermercado_estoque.repository.DepartmentRepository;
import gabrieldev.com.supermercado_estoque.repository.ProductRepository;

@Service
public class DepartmentService {
    
    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private ProductRepository productRepository;

    private DepartmentDTO convertToDTO(Department department) {
        DepartmentDTO dto = new DepartmentDTO(department.getId(), department.getSector());
        
        if (department.getProducts() != null) {
            List<ProductDTO> productDTOs = department.getProducts().stream()
                .map(product -> new ProductDTO(
                    product.getId(), 
                    product.getName(), 
                    product.getDescription(), 
                    product.getQuantity(), 
                    product.getEntryDate(), 
                    product.getDepartment().getId()
                ))
                .collect(Collectors.toList());
            dto.setProducts(productDTOs);
        }
        
        return dto;
    }

    private Department convertToEntity(DepartmentDTO dto) {
        Department department = new Department(dto.getId(), dto.getSector());
        return department;
    }

    public List<DepartmentDTO> findAll() {
        return departmentRepository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public DepartmentDTO findById(Long id) {
        return departmentRepository.findById(id)
            .map(this::convertToDTO)
            .orElseThrow(() -> new ResourceNotFoundException("Departamento não encontrado com ID: " + id));
    }
    
    @Transactional
    public DepartmentDTO create(DepartmentDTO departmentDTO) {
        Department department = convertToEntity(departmentDTO);
        
        Department savedDepartment = departmentRepository.save(department);
        
        if (departmentDTO.getProducts() != null && !departmentDTO.getProducts().isEmpty()) {
            List<Product> products = departmentDTO.getProducts().stream()
                .map(productDTO -> {
                    Product product = new Product();
                    product.setName(productDTO.getName());
                    product.setDescription(productDTO.getDescription());
                    product.setQuantity(productDTO.getQuantity());
                    product.setEntryDate(productDTO.getEntryDate());
                    product.setDepartment(savedDepartment);
                    return product;
                })
                .collect(Collectors.toList());
            
            productRepository.saveAll(products);
            
            savedDepartment.setProducts(products);
        }
        
        return convertToDTO(savedDepartment);
    }

    @Transactional
    public DepartmentDTO update(Long id, DepartmentDTO departmentDTO) {
        Department existingDepartment = departmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Departamento não encontrado com ID: " + id));
        
        existingDepartment.setSector(departmentDTO.getSector());
        
        Department updatedDepartment = departmentRepository.save(existingDepartment);
        return convertToDTO(updatedDepartment);
    }
    
    @Transactional
    public void delete(Long id) {
        Department department = departmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Departamento não encontrado com ID: " + id));
        departmentRepository.delete(department);
    }

    public DepartmentDTO findDepartmentWithProducts(Long id) {
        Department department = departmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Departamento não encontrado com ID: " + id));
        
        department.getProducts().size(); 
        
        return convertToDTO(department);
    }
}