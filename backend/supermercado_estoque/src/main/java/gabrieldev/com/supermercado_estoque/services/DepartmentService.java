package gabrieldev.com.supermercado_estoque.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gabrieldev.com.supermercado_estoque.controllers.DepartmentController;
import gabrieldev.com.supermercado_estoque.controllers.exceptions.BusinessException;
import gabrieldev.com.supermercado_estoque.controllers.exceptions.ResourceNotFoundException;
import gabrieldev.com.supermercado_estoque.model.Department;
import gabrieldev.com.supermercado_estoque.model.Product;
import gabrieldev.com.supermercado_estoque.model.DTO.DepartmentDTO;
import gabrieldev.com.supermercado_estoque.model.DTO.SimpleDepartmentDTO;
import gabrieldev.com.supermercado_estoque.repository.DepartmentRepository;
import gabrieldev.com.supermercado_estoque.repository.ProductRepository;
import gabrieldev.com.supermercado_estoque.services.mapper.DepartmentMapper;
import gabrieldev.com.supermercado_estoque.util.DepartmentValidator;

@Service
public class DepartmentService {

    private static final Logger logger = LoggerFactory.getLogger(DepartmentService.class);

    @Autowired
    private DepartmentRepository departmentRepository;
    
    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private DepartmentValidator departmentValidator;

    public CollectionModel<EntityModel<DepartmentDTO>> findAll() {
        logger.info("Finding all departments");
        try {
            List<EntityModel<DepartmentDTO>> departments = departmentRepository.findAll()
                .stream()
                .map(department -> {
                    DepartmentDTO dto = departmentMapper.SimpleProductoDTO(department);
                    return EntityModel.of(dto,
                        linkTo(methodOn(DepartmentController.class).findById(department.getId())).withSelfRel());
                })
                .collect(Collectors.toList());

            if (departments.isEmpty()) {
                logger.warn("No departments found");
            }

            return CollectionModel.of(departments,
                linkTo(methodOn(DepartmentController.class).findAll()).withSelfRel());
        } catch (Exception e) {
            logger.error("Error while finding all departments", e);
            throw new BusinessException("Error while retrieving departments: " + e.getMessage());
        }
    }

    public DepartmentDTO findById(Long id) {
        logger.info("Finding department with id: {}", id);
        
        if (id == null) {
            throw new BusinessException("Department ID cannot be null");
        }

        var department = departmentRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Department", "id", id));
        
        var dto = departmentMapper.toDTO(department);
        dto.add(linkTo(methodOn(DepartmentController.class).findById(id)).withSelfRel());
        
        return dto;
    }

    @Transactional
    public DepartmentDTO create(DepartmentDTO departmentDTO) {
        logger.info("Creating new department");
        
        departmentValidator.validateDepartmentDTO(departmentDTO);

        try {
            Department department = departmentMapper.toEntity(departmentDTO);
            Department savedDepartment = departmentRepository.save(department);

            if (departmentDTO.getProducts() != null && !departmentDTO.getProducts().isEmpty()) {
                List<Product> products = departmentDTO.getProducts().stream()
                    .map(productDTO -> {
                    	departmentValidator.validateProductDTO(productDTO);
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

            var dto = departmentMapper.toDTO(savedDepartment);
            dto.add(linkTo(methodOn(DepartmentController.class).findById(dto.getKey())).withSelfRel());
            return dto;
        } catch (Exception e) {
            logger.error("Error while creating department", e);
            throw new BusinessException("Error while creating department: " + e.getMessage());
        }
    }

    @Transactional
    public DepartmentDTO update(Long id, DepartmentDTO departmentDTO) {
        logger.info("Updating department with id: {}", id);
        
        if (id == null) {
            throw new BusinessException("Department ID cannot be null");
        }

        departmentValidator.validateDepartmentDTO(departmentDTO);

        try {
            Department existingDepartment = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", id));

            Department departmentWithSameSector = departmentRepository.findBySector(departmentDTO.getSector());
            if (departmentWithSameSector != null && !departmentWithSameSector.getId().equals(id)) {
                throw new BusinessException("Sector already exists in another department");
            }

            existingDepartment.setSector(departmentDTO.getSector());

            Department updatedDepartment = departmentRepository.save(existingDepartment);
            var dto = departmentMapper.toDTO(updatedDepartment);
            dto.add(linkTo(methodOn(DepartmentController.class).findById(dto.getKey())).withSelfRel());
            return dto;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error while updating department", e);
            throw new BusinessException("Error while updating department: " + e.getMessage());
        }
    }

    @Transactional
    public void delete(Long id) {
        logger.info("Deleting department with id: {}", id);
        
        if (id == null) {
            throw new BusinessException("Department ID cannot be null");
        }

        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", id));

        if (productRepository.countByDepartmentId(id) > 0) {
            throw new BusinessException("Cannot delete department with associated products");
        }

        departmentRepository.delete(department);
    }

    public List<EntityModel<SimpleDepartmentDTO>> findDepartmentsWithoutProducts() {
        logger.info("Finding departments without products");
        try {
            return departmentRepository.findAll().stream()
                .filter(department -> department.getProducts() == null || department.getProducts().isEmpty())
                .map(department -> {
                    SimpleDepartmentDTO dto = departmentMapper.toSimpleDepartmentDTO(department);
                    return EntityModel.of(dto,
                        linkTo(methodOn(DepartmentController.class).findById(department.getId())).withSelfRel());
                })
                .collect(Collectors.toList());
        } catch (Exception e) {
            logger.error("Error while finding departments without products", e);
            throw new BusinessException("Error while retrieving departments without products: " + e.getMessage());
        }
    }
    
}