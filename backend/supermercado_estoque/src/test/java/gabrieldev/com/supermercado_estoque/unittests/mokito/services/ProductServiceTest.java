package gabrieldev.com.supermercado_estoque.unittests.mokito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import gabrieldev.com.supermercado_estoque.controllers.exceptions.BusinessException;
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
	    void testCreateWithNulProducy() {
	    	Exception exception = assertThrows(BusinessException.class, () -> {
	    		service.create(null);
	    	});
	    	String expectedMessage = "It is not allowed to persist a null object!";
	    	String actualMessage = exception.getMessage();
	    	System.out.println(expectedMessage);
	    	
	    	assertTrue(actualMessage.contains(expectedMessage));
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
    void testUpdateWithNullProduct() {
    	Exception exception = assertThrows(BusinessException.class, () -> {
    		service.update(null, null);
    	});
    	String expectedMessage = "Product ID cannot be null";
    	String actualMessage = exception.getMessage();
    	
    	assertTrue(actualMessage.contains(expectedMessage));
    }
	@Test
	void testDelete() {
		Product product = input.mockEntity(1);
		product.setId(1L);
		
		when(repository.findById(1L)).thenReturn(Optional.of(product));
		
        service.delete(1L);
        
        verify(repository, times(1)).delete(product);
	}
	@Test
	void testFindAll() {
	List<Product> mockList = input.mockEntityList();
	when(repository.findAll()).thenReturn(mockList);
	
	var product = service.findAll();
	assertNotNull(product);
	assertEquals(14, product.size());
	
	
	var productOne = product.get(1);

    assertNotNull(productOne, "Result should not be null");
    assertNotNull(productOne.getKey(), "Key should not be null");
    assertEquals(1L, productOne.getKey(), "Key should match");
    assertEquals("Name Test1", productOne.getName());
    assertEquals("Description Test1", productOne.getDescription());
    assertEquals(1, productOne.getQuantity());

    assertNotNull(productOne.getDepartmentID());
    assertTrue(productOne.toString().contains("links: [</api/product/1>;rel=\"self\"]"),
            "Self-link should be present");
	
    var productFour = product.get(4);
    
    assertNotNull(productFour, "Result should not be null");
    assertNotNull(productFour.getKey(), "Key should not be null");
    assertEquals(4L, productFour.getKey(), "Key should match");
    assertEquals("Name Test4", productFour.getName());
    assertEquals("Description Test4", productFour.getDescription());
    assertEquals(4, productFour.getQuantity());
    
    assertNotNull(productFour.getDepartmentID());
    assertTrue(productFour.toString().contains("links: [</api/product/4>;rel=\"self\"]"),
    		"Self-link should be present");
    
    var productSix = product.get(6);
    
    assertNotNull(productSix, "Result should not be null");
    assertNotNull(productSix.getKey(), "Key should not be null");
    assertEquals(6L, productSix.getKey(), "Key should match");
    assertEquals("Name Test6", productSix.getName());
    assertEquals("Description Test6", productSix.getDescription());
    assertEquals(6, productSix.getQuantity());
    
    assertNotNull(productSix.getDepartmentID());
    assertTrue(productSix.toString().contains("links: [</api/product/6>;rel=\"self\"]"),
    		"Self-link should be present");
    
	
	
	
		
	}
}
