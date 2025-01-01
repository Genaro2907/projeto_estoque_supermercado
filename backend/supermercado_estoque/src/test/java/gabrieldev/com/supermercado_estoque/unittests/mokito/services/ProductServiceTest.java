package gabrieldev.com.supermercado_estoque.unittests.mokito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
	void testFindAll() {
		fail("Not yet implemented");
	}

	@Test
	void testFindById() {
		Product product = input.mockEntity(1);
        product.setId(1L);

        ProductDTO productDTO = input.mockDTO(1);
        productDTO.setKey(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(product));
        //when(productMapper.convertToDTO(product)).thenReturn(productDTO);
        
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
		fail("Not yet implemented");
	}

	@Test
	void testUpdate() {
		fail("Not yet implemented");
	}

	@Test
	void testDelete() {
		fail("Not yet implemented");
	}

	@Test
	void testFindByDepartment() {
		fail("Not yet implemented");
	}

}
