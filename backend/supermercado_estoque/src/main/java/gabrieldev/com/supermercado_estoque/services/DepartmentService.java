package gabrieldev.com.supermercado_estoque.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gabrieldev.com.supermercado_estoque.controllers.exceptions.ResourceNotFoundException;
import gabrieldev.com.supermercado_estoque.model.Department;
import gabrieldev.com.supermercado_estoque.model.DTO.DepartmentDTO;
import gabrieldev.com.supermercado_estoque.model.DTO.ProductDTO;
import gabrieldev.com.supermercado_estoque.repository.DepartmentRepository;

@Service
public class DepartmentService {
    
    @Autowired
    private DepartmentRepository repository;

    private Department convertToEntity(DepartmentDTO departmentDto) {
        Department department = new Department();
        department.setId(departmentDto.getId());
        department.setSector(departmentDto.getSector());
        return department;
    }
    
    public List<DepartmentDTO> findAll() {
        return repository.findAll().stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }
    
    public DepartmentDTO findById(Long id) {
        return repository.findById(id)
            .map(this::convertToDTO)
            .orElseThrow(() -> new ResourceNotFoundException("Departamento não encontrado com ID: " + id));
    }
    
    @Transactional
    public DepartmentDTO create(DepartmentDTO departmentDTO) {
        Department department = convertToEntity(departmentDTO);
        
        if (departmentDTO.getProducts() != null) {
            department.setProducts(departmentDTO.getProducts().stream()
                .map(productDTO -> {
                    
                    return null; 
                })
                .collect(Collectors.toList()));
        }
        
        Department savedDepartment = repository.save(department);
        return convertToDTO(savedDepartment);
    }

    @Transactional
    public DepartmentDTO update(Long id, DepartmentDTO departmentDTO) {
        Department existingDepartment = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Departamento não encontrado com ID: " + id));
        existingDepartment.setSector(departmentDTO.getSector());
        
        Department updatedDepartment = repository.save(existingDepartment);
        return convertToDTO(updatedDepartment);
    }
    
    @Transactional
    public void delete(Long id) {
        Department department = repository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Departamento não encontrado com ID: " + id));
        repository.delete(department);
    }
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
}