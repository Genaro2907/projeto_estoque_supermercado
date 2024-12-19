package gabrieldev.com.supermercado_estoque.services;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gabrieldev.com.supermercado_estoque.controllers.DepartmentController;
import gabrieldev.com.supermercado_estoque.controllers.exceptions.ResourceNotFoundException;
import gabrieldev.com.supermercado_estoque.model.Department;
import gabrieldev.com.supermercado_estoque.model.Product;
import gabrieldev.com.supermercado_estoque.model.DTO.DepartmentDTO;
import gabrieldev.com.supermercado_estoque.model.DTO.SimpleDepartmentDTO;
import gabrieldev.com.supermercado_estoque.repository.DepartmentRepository;
import gabrieldev.com.supermercado_estoque.repository.ProductRepository;
import gabrieldev.com.supermercado_estoque.services.mapper.DepartmentMapper;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentRepository departmentRepository;
	
	@Autowired
	private DepartmentMapper departmentMapper;

	@Autowired
	private ProductRepository productRepository;
	
	private Logger logger = Logger.getLogger(DepartmentService.class.getName());


	public CollectionModel<EntityModel<DepartmentDTO>> findAll() {
	    logger.info("Finding all departments!");
	    List<EntityModel<DepartmentDTO>> departments = departmentRepository.findAll()
	        .stream()
	        .map(department -> {
	            DepartmentDTO dto = departmentMapper.SimpleProducttoDTO(department);
	            return EntityModel.of(dto,
	                linkTo(methodOn(DepartmentController.class).findById(department.getId())).withSelfRel());
	        })
	        .collect(Collectors.toList());

	    return CollectionModel.of(departments,
	        linkTo(methodOn(DepartmentController.class).findAll()).withSelfRel());
	}

	public DepartmentDTO findById(Long id) {
	    logger.info("Finding one department!");
	    
	    var dto = departmentRepository.findById(id)
	        .map(departmentMapper::toDTO)
	        .orElseThrow(() -> new ResourceNotFoundException("Departamento não encontrado com ID: " + id));
	    
	    dto.add(linkTo(methodOn(DepartmentController.class).findById(id)).withSelfRel());
	    return dto;
	}

	@Transactional
	public DepartmentDTO create(DepartmentDTO departmentDTO) {
		
		logger.info("Creating one person!");
		Department department = departmentMapper.toEntity(departmentDTO);
		Department savedDepartment = departmentRepository.save(department);

		if (departmentDTO.getProducts() != null && !departmentDTO.getProducts().isEmpty()) {
			List<Product> products = departmentDTO.getProducts().stream().map(productDTO -> {
				Product product = new Product();
				product.setName(productDTO.getName());
				product.setDescription(productDTO.getDescription());
				product.setQuantity(productDTO.getQuantity());
				product.setEntryDate(productDTO.getEntryDate());
				product.setDepartment(savedDepartment);
				return product;
			}).collect(Collectors.toList());

			productRepository.saveAll(products);

			savedDepartment.setProducts(products);
		}

		var dto = departmentMapper.toDTO(savedDepartment);
		dto.add(linkTo(methodOn(DepartmentController.class).findById(dto.getKey())).withSelfRel());
		return dto;
	}

	@Transactional
	public DepartmentDTO update(Long id, DepartmentDTO departmentDTO) {
		
		logger.info("Updating one department!");
		Department existingDepartment = departmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Departamento não encontrado com ID: " + id));

		existingDepartment.setSector(departmentDTO.getSector());

		Department updatedDepartment = departmentRepository.save(existingDepartment);
		var dto = departmentMapper.toDTO(updatedDepartment);
		dto.add(linkTo(methodOn(DepartmentController.class).findById(dto.getKey())).withSelfRel());
		return dto;
	}

	@Transactional
	public void delete(Long id) {
		
		logger.info("Deleting one department!");
		Department department = departmentRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Departamento não encontrado com ID: " + id));
		departmentRepository.delete(department);
	}

	public List<EntityModel<SimpleDepartmentDTO>> findDepartmentsWithoutProducts() {
	    logger.info("Finding all departments without Products!");
	    return departmentRepository.findAll().stream()
	        .map(department -> {
	            SimpleDepartmentDTO dto = new SimpleDepartmentDTO(department.getId(), department.getSector());
	            return EntityModel.of(dto,
	                linkTo(methodOn(DepartmentController.class).findById(department.getId())).withSelfRel());
	        })
	        .collect(Collectors.toList());
	}
}