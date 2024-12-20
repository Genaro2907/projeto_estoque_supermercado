package gabrieldev.com.supermercado_estoque.util;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import gabrieldev.com.supermercado_estoque.controllers.exceptions.BusinessException;
import gabrieldev.com.supermercado_estoque.model.DTO.ProductDTO;

@Component
public class ProductValidator {

    public void validateProductDTO(ProductDTO productDTO) {
        if (productDTO == null) {
            throw new BusinessException("Product data cannot be null");
        }

        if (!StringUtils.hasText(productDTO.getName())) {
            throw new BusinessException("Product name cannot be empty");
        }

        if (productDTO.getQuantity() == null || productDTO.getQuantity() < 0) {
            throw new BusinessException("Product quantity must be greater than or equal to 0");
        }

        if (productDTO.getEntryDate() == null) {
            throw new BusinessException("Product entry date cannot be null");
        }

        if (productDTO.getDepartmentID() == null || productDTO.getDepartmentID().getKey() == null) {
            throw new BusinessException("Department ID cannot be null");
        }
    }

    public void validateUpdate(ProductDTO productDTO, Long id) {
        if (id == null) {
            throw new BusinessException("Product ID cannot be null");
        }

        validateProductDTO(productDTO);
    }

    public void validateId(Long id) {
        if (id == null) {
            throw new BusinessException("Product ID cannot be null");
        }
    }
}