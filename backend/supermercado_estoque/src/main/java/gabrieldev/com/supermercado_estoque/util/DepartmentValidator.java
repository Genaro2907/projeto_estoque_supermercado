package gabrieldev.com.supermercado_estoque.util;

import org.springframework.stereotype.Component;

import gabrieldev.com.supermercado_estoque.controllers.exceptions.BusinessException;
import gabrieldev.com.supermercado_estoque.model.Department;
import gabrieldev.com.supermercado_estoque.model.DTO.DepartmentDTO;
import gabrieldev.com.supermercado_estoque.model.DTO.ProductDTO;
import gabrieldev.com.supermercado_estoque.repository.DepartmentRepository;

@Component
public class DepartmentValidator {
    
    private final DepartmentRepository departmentRepository;

    public DepartmentValidator(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public void validateDepartmentDTO(DepartmentDTO departmentDTO) {
        if (departmentDTO == null) {
            throw new BusinessException("Department data cannot be null");
        }
        if (departmentDTO.getSector() == null || departmentDTO.getSector().trim().isEmpty()) {
            throw new BusinessException("Department sector cannot be empty");
        }
    }

    public void validateDepartmentUpdate(Long id, String newSector) {
        Department departmentWithSameSector = departmentRepository.findBySector(newSector);
        if (departmentWithSameSector != null && !departmentWithSameSector.getId().equals(id)) {
            throw new BusinessException("Sector already exists in another department");
        }
    }

    public void validateProductDTO(ProductDTO productDTO) {
        if (productDTO == null) {
            throw new BusinessException("Product data cannot be null");
        }
        if (productDTO.getName() == null || productDTO.getName().trim().isEmpty()) {
            throw new BusinessException("Product name cannot be empty");
        }
        if (productDTO.getQuantity() == null || productDTO.getQuantity() < 0) {
            throw new BusinessException("Product quantity must be greater than or equal to 0");
        }
        if (productDTO.getEntryDate() == null) {
            throw new BusinessException("Product entry date cannot be null");
        }
    }

    public void validateDepartmentDeletion(Department department) {
        if (!department.getProducts().isEmpty()) {
            throw new BusinessException("Cannot delete department with associated products");
        }
    }
}