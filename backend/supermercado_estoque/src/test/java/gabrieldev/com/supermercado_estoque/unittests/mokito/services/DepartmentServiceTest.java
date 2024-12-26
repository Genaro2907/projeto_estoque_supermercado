package gabrieldev.com.supermercado_estoque.unittests.mokito.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

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

import gabrieldev.com.supermercado_estoque.model.Department;
import gabrieldev.com.supermercado_estoque.model.DTO.DepartmentDTO;
import gabrieldev.com.supermercado_estoque.repository.DepartmentRepository;
import gabrieldev.com.supermercado_estoque.services.DepartmentService;
import gabrieldev.com.supermercado_estoque.services.mapper.DepartmentMapper;
import gabrieldev.com.supermercado_estoque.unittests.mocks.MockDepartment;

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
        when(departmentMapper.toDTO(department)).thenReturn(departmentDTO);

        var result = service.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getKey());
        System.out.println(result.toString());
        assertTrue(result.toString().contains("links: [</api/department/1>;rel=\"self\"]"));
        assertEquals("Some Sector 1", result.getSector());
        assertEquals("", result.getProducts());
        
    }


	/*@Test
	void testFindAll() {
		fail("Not yet implemented");
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
	void testFindDepartmentsWithoutProducts() {
		fail("Not yet implemented");
	}*/

}
