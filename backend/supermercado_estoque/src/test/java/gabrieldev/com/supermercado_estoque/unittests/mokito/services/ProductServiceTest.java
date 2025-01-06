package gabrieldev.com.supermercado_estoque.unittests.mokito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import gabrieldev.com.supermercado_estoque.model.Department;
import gabrieldev.com.supermercado_estoque.model.Product;
import gabrieldev.com.supermercado_estoque.model.DTO.ProductDTO;
import gabrieldev.com.supermercado_estoque.repository.DepartmentRepository;
import gabrieldev.com.supermercado_estoque.repository.ProductRepository;
import gabrieldev.com.supermercado_estoque.services.ProductService;
import gabrieldev.com.supermercado_estoque.services.mapper.ProductMapper;
import gabrieldev.com.supermercado_estoque.unittests.mocks.MockProduct;
import gabrieldev.com.supermercado_estoque.util.ProductValidator;

class ProductServiceTest {

	MockProduct input;

	@InjectMocks
	private ProductService service;

	
	@Mock
	private ProductMapper productMapper;
	
	@Mock
    ProductRepository repository;
	
	@Mock
    DepartmentRepository departmentrepository;
    
    @Mock
    private ProductValidator ProductValidator;


	@BeforeEach
	void setUp() throws Exception {
		
		input = new MockProduct();
        MockitoAnnotations.openMocks(this);
	}
	
	
	@Test
	void testFindById() {
		Product product = input.mockEntity(1);
        product.setId(1L);

        ProductDTO productDTO = input.mockDTO(1);
        productDTO.setKey(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(product));
        when(productMapper.parseObject(product, ProductDTO.class)).thenReturn(productDTO);
        
        var result = service.findById(1L);

        assertNotNull(result, "Result should not be null");
        assertNotNull(result.getKey(), "Key should not be null");
        assertEquals(1L, result.getKey(), "Key should match");
        assertEquals("Name Test1", result.getName());
        assertEquals("Description Test1", result.getDescription());
        assertEquals(1, result.getQuantity());

        assertNotNull(result.getDepartmentID());
        assertTrue(result.toString().contains("links: [</api/product/1>;rel=\"self\"]"),
                "Self-link should be present");
	}
	@Test
	void testCreate() {
	    Product product = input.mockEntity(1);
	    Product persisted = product;
	    persisted.setId(1L);
	    ProductDTO productDTO = input.mockDTO(1);
	    productDTO.setKey(1L);
	    
	    Department department = new Department();
	    department.setId(1L);
	    department.setSector("Sector Test1");

	    when(departmentrepository.findById(1L)).thenReturn(Optional.of(department));

	    when(productMapper.toEntity(productDTO)).thenReturn(persisted);
	    when(repository.save(product)).thenReturn(persisted);
	    when(productMapper.toDTO(persisted)).thenReturn(productDTO);
	    
	    var result = service.create(productDTO);

	    assertNotNull(result, "Result should not be null");
	    assertNotNull(result.getKey(), "Key should not be null");
	    assertEquals(1L, result.getKey(), "Key should match");
	    assertEquals("Name Test1", result.getName());
	    assertEquals("Description Test1", result.getDescription());
	    assertEquals(1, result.getQuantity());
	    assertNotNull(result.getDepartmentID());
	    assertTrue(result.toString().contains("links: [</api/product/1>;rel=\"self\"]"),
	            "Self-link should be present");
	}
	@Test
	void testUpdate() {
	    Product product = input.mockEntity(1);
	    product.setId(1L);
	    Product persisted = product;
	    persisted.setId(1L);
	    
	    ProductDTO productDTO = input.mockDTO(1);
	    productDTO.setKey(1L);
	    
	    Department department = new Department();
	    department.setId(1L);
	    department.setSector("Sector Test1");

	    when(departmentrepository.findById(1L)).thenReturn(Optional.of(department));

	    when(repository.findById(1L)).thenReturn(Optional.of(persisted));
	    when(repository.save(product)).thenReturn(persisted);
	    when(productMapper.toDTO(persisted)).thenReturn(productDTO);
	    
	    var result = service.update(1L, productDTO);

	    assertNotNull(result, "Result should not be null");
	    assertNotNull(result.getKey(), "Key should not be null");
	    assertEquals(1L, result.getKey(), "Key should match");
	    assertEquals("Name Test1", result.getName());
	    assertEquals("Description Test1", result.getDescription());
	    assertEquals(1, result.getQuantity());
	    assertNotNull(result.getDepartmentID());
	    assertTrue(result.toString().contains("links: [</api/product/1>;rel=\"self\"]"),
	            "Self-link should be present");
		
	}
	@Test
	void testDelete() {
		Product product = input.mockEntity(1);
		product.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(product));
		
        service.delete(1L);
        
        verify(repository, times(1)).delete(product);
	}
	

	/*
	@Test
	void testFindAll() {
		fail("Not yet implemented");
	}
	
	@Test
	void testFindByDepartment() {
		fail("Not yet implemented");
	}
	*/
}
