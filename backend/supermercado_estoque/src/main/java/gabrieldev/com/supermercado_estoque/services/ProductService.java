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

import gabrieldev.com.supermercado_estoque.controllers.ProductController;
import gabrieldev.com.supermercado_estoque.controllers.exceptions.BusinessException;
import gabrieldev.com.supermercado_estoque.controllers.exceptions.ResourceNotFoundException;
import gabrieldev.com.supermercado_estoque.model.Department;
import gabrieldev.com.supermercado_estoque.model.Product;
import gabrieldev.com.supermercado_estoque.model.DTO.ProductDTO;
import gabrieldev.com.supermercado_estoque.model.DTO.SimpleProductDTO;
import gabrieldev.com.supermercado_estoque.repository.DepartmentRepository;
import gabrieldev.com.supermercado_estoque.repository.ProductRepository;
import gabrieldev.com.supermercado_estoque.services.mapper.ProductMapper;
import gabrieldev.com.supermercado_estoque.util.ProductValidator;

@Service
public class ProductService {
    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    @Autowired
    private  ProductRepository productRepository;
    @Autowired
    private  DepartmentRepository departmentRepository;
    @Autowired
    private ProductValidator validator;
    @Autowired
    private ProductMapper productMapper;


    public List<SimpleProductDTO> findAll() {
        logger.info("Finding all products");
        try {
            var products = ProductMapper.parseListObjects(productRepository.findAll(), SimpleProductDTO.class);
            		
            if (products.isEmpty()) {
                logger.warn("No products found");
            }
            products
            .stream()
            .forEach(p -> p.add(linkTo(methodOn(ProductController.class).findById(p.getKey())).withSelfRel()));
  
            return products;
        } catch (Exception e) {
            logger.error("Error while finding all products", e);
            throw new BusinessException("Error while retrieving products: " + e.getMessage());
        }
    }

    public ProductDTO findById(Long id) {
        logger.info("Finding product with id: {}", id);
        validator.validateId(id);

        try {
            var entity = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
            var dto = productMapper.parseObject(entity, ProductDTO.class);
            dto.add(linkTo(methodOn(ProductController.class).findById(id)).withSelfRel());
            return dto;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error while finding product with id: {}", id, e);
            throw new BusinessException("Error while retrieving product: " + e.getMessage());
        }
    }

    @Transactional
    public ProductDTO create(ProductDTO productDTO) {
        logger.info("Creating new product");
        if(productDTO == null) throw new BusinessException("It is not allowed to persist a null object!");
        validator.validateProductDTO(productDTO);

        try {
            Department department = departmentRepository.findById(productDTO.getDepartmentID().getKey())
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", productDTO.getDepartmentID().getKey()));

            Product product = productMapper.toEntity(productDTO);
            product.setDepartment(department);

            Product savedProduct = productRepository.save(product);
            ProductDTO dto = productMapper.toDTO(savedProduct);

            if (dto == null) {
                throw new BusinessException("Error in mapping Product to ProductDTO: Mapping resulted in null");
            }

            dto.add(linkTo(methodOn(ProductController.class).findById(dto.getKey())).withSelfRel());
            return dto;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error while creating product", e);
            throw new BusinessException("Error while creating product: " + e.getMessage());
        }
    }


    @Transactional
    public ProductDTO update(Long id, ProductDTO productDTO) {
        logger.info("Updating product with id: {}", id);
        if (id == null) {
            throw new BusinessException("Product ID cannot be null");
        }
        validator.validateUpdate(productDTO, id);

        try {
            Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));

            Department department = departmentRepository.findById(productDTO.getDepartmentID().getKey())
                .orElseThrow(() -> new ResourceNotFoundException("Department", "id", productDTO.getDepartmentID().getKey()));

            existingProduct.setName(productDTO.getName());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setQuantity(productDTO.getQuantity());
            existingProduct.setEntryDate(productDTO.getEntryDate());
            existingProduct.setDepartment(department);

            Product updatedProduct = productRepository.save(existingProduct);
            ProductDTO dto = productMapper.toDTO(updatedProduct);

            if (dto == null) {
                throw new BusinessException("Error in mapping Product to ProductDTO: Mapping resulted in null");
            }

            dto.add(linkTo(methodOn(ProductController.class).findById(dto.getKey())).withSelfRel());
            return dto;
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error while updating product", e);
            throw new BusinessException("Error while updating product: " + e.getMessage());
        }
    }


    @Transactional
    public void delete(Long id) {
        logger.info("Deleting product with id: {}", id);
        validator.validateId(id);

        try {
            Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "id", id));
            
            productRepository.delete(product);
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error while deleting product", e);
            throw new BusinessException("Error while deleting product: " + e.getMessage());
        }
    }

    public CollectionModel<EntityModel<ProductDTO>> findByDepartment(Long departmentId) {
        logger.info("Finding products by department id: {}", departmentId);
        validator.validateId(departmentId);

        try {
            
            if (!departmentRepository.existsById(departmentId)) {
                throw new ResourceNotFoundException("Department", "id", departmentId);
            }

            List<EntityModel<ProductDTO>> products = productRepository.findByDepartmentId(departmentId).stream()
                .map(product -> {
                    ProductDTO dto = productMapper.parseObject(product, ProductDTO.class);
                    return EntityModel.of(dto,
                        linkTo(methodOn(ProductController.class).findById(dto.getKey())).withSelfRel());
                })
                .collect(Collectors.toList());

            if (products.isEmpty()) {
                logger.warn("No products found for department id: {}", departmentId);
            }

            return CollectionModel.of(products,
                linkTo(methodOn(ProductController.class).findByDepartment(departmentId)).withSelfRel());
        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error while finding products by department", e);
            throw new BusinessException("Error while retrieving products by department: " + e.getMessage());
        }
    }
}