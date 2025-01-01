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
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import gabrieldev.com.supermercado_estoque.controllers.exceptions.BusinessException;
import gabrieldev.com.supermercado_estoque.model.Department;
import gabrieldev.com.supermercado_estoque.model.DTO.DepartmentDTO;
import gabrieldev.com.supermercado_estoque.repository.DepartmentRepository;
import gabrieldev.com.supermercado_estoque.repository.ProductRepository;
import gabrieldev.com.supermercado_estoque.services.DepartmentService;
import gabrieldev.com.supermercado_estoque.services.mapper.DepartmentMapper;
import gabrieldev.com.supermercado_estoque.unittests.mocks.MockDepartment;
import gabrieldev.com.supermercado_estoque.util.DepartmentValidator;

@TestInstance(Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class DepartmentServiceTest {

    MockDepartment input;

    @InjectMocks
    private DepartmentService service;

    @Mock
    private DepartmentMapper departmentMapper;

    @Mock
    DepartmentRepository repository;

    @Mock
    ProductRepository productrepository;

    @Mock
    private DepartmentValidator departmentValidator;

    @BeforeEach
    void setUpMocks() throws Exception {
        input = new MockDepartment();
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        Department department = input.mockEntity(1);
        department.setId(1L);
        DepartmentDTO departmentDTO = input.mockDTO(1);
        departmentDTO.setKey(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(department));
        when(departmentMapper.parseObject(department, DepartmentDTO.class)).thenReturn(departmentDTO);

        var result = service.findById(1L);

        assertNotNull(result, "Result should not be null");
        assertNotNull(result.getKey(), "Key should not be null");
        assertEquals(1L, result.getKey(), "Key should match");
        assertEquals("Some Sector 1", result.getSector(), "Sector should match");

        assertNotNull(result.getProducts(), "Products should not be null");
        assertEquals(5, result.getProducts().size(), "There should be 5 products");
        assertEquals("Description Test0", result.getProducts().get(0).getDescription(),
                "First product description should match");
        assertEquals(0, result.getProducts().get(0).getQuantity(), "First product quantity should match");

        assertTrue(result.toString().contains("links: [</api/department/1>;rel=\"self\"]"),
                "Self-link should be present");
    }

    @Test
    void testCreate() {
        Department department = input.mockEntity(1);

        Department persisted = department;
        persisted.setId(1L);

        DepartmentDTO departmentDTO = input.mockDTO(1);
        departmentDTO.setKey(1L);

        when(departmentMapper.toEntity(departmentDTO)).thenReturn(persisted);
        when(repository.save(department)).thenReturn(persisted);
        when(departmentMapper.toDTO(persisted)).thenReturn(departmentDTO);

        var result = service.create(departmentDTO);

        assertNotNull(result, "Result should not be null");
        assertNotNull(result.getKey(), "Key should not be null");
        assertEquals(1L, result.getKey(), "Key should match");
        assertEquals("Some Sector 1", result.getSector(), "Sector should match");

        assertNotNull(result.getProducts(), "Products should not be null");
        assertEquals(5, result.getProducts().size(), "There should be 5 products");
        assertEquals("Description Test0", result.getProducts().get(0).getDescription(),
                "First product description should match");
        assertEquals(0, result.getProducts().get(0).getQuantity(), "First product quantity should match");

        assertTrue(result.toString().contains("links: [</api/department/1>;rel=\"self\"]"),
                "Self-link should be present");
    }
    @Test
    void testCreateWithNullDepartment() {
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
        Department department = input.mockEntity(1);
        department.setId(1L);

        Department persisted = department;
        persisted.setId(1L);

        DepartmentDTO departmentDTO = input.mockDTO(1);
        departmentDTO.setKey(1L);

        when(repository.findById(1L)).thenReturn(Optional.of(department));
        when(repository.save(department)).thenReturn(persisted);
        when(departmentMapper.toDTO(persisted)).thenReturn(departmentDTO);

        var result = service.update(1L, departmentDTO);

        assertNotNull(result, "Result should not be null");
        assertNotNull(result.getKey(), "Key should not be null");
        assertEquals(1L, result.getKey(), "Key should match");
        assertEquals("Some Sector 1", result.getSector(), "Sector should match");

        assertNotNull(result.getProducts(), "Products should not be null");
        assertEquals(5, result.getProducts().size(), "There should be 5 products");
        assertEquals("Description Test0", result.getProducts().get(0).getDescription(),
                "First product description should match");
        assertEquals(0, result.getProducts().get(0).getQuantity(), "First product quantity should match");

        assertTrue(result.toString().contains("links: [</api/department/1>;rel=\"self\"]"),
                "Self-link should be present");
    }
    @Test
    void testUpdateWithNullDepartment() {
    	Exception exception = assertThrows(BusinessException.class, () -> {
    		service.update(null, null);
    	});
    	String expectedMessage = "Department ID cannot be null";
    	String actualMessage = exception.getMessage();
    	
    	assertTrue(actualMessage.contains(expectedMessage));
    }


    @Test
    void testDelete() {
        Department department = input.mockEntity(1);
        department.setId(1L);

        when(productrepository.countByDepartmentId(1L)).thenReturn(0L);
        when(repository.findById(1L)).thenReturn(Optional.of(department));

        service.delete(1L);

        verify(repository, times(1)).delete(department);
    }
    
    @Test
    void testFindAll() {
        List<Department> mockList = input.mockEntityList();
        when(repository.findAll()).thenReturn(mockList);
        var department = service.findAll();
        assertNotNull(department);
        assertEquals(14, department.size());
        
        var departmentOne = department.get(1);
        assertNotNull(departmentOne);
        assertNotNull(departmentOne.getKey());
        assertNotNull(departmentOne.getLinks()); 
        assertTrue(departmentOne.toString().contains("</api/department/1>;rel=\"self\""));
        assertEquals("Some Sector 1", departmentOne.getSector());
        assertNotNull(departmentOne.getProducts());
        
        var departmentFour = department.get(4);
        assertNotNull(departmentFour);
        assertNotNull(departmentFour.getKey());
        assertNotNull(departmentFour.getLinks()); 
        assertTrue(departmentFour.toString().contains("</api/department/4>;rel=\"self\""));
        assertEquals("Some Sector 4", departmentFour.getSector());
        assertNotNull(departmentFour.getProducts());
        
        

    }

	  @Test void testFindDepartmentsWithoutProducts() {
		  
	  }
	

}
