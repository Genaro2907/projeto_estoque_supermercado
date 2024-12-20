package gabrieldev.com.supermercado_estoque.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import gabrieldev.com.supermercado_estoque.model.Department;
import gabrieldev.com.supermercado_estoque.model.DTO.DepartmentDTO;

public interface DepartmentRepository extends JpaRepository<Department, Long>{
	@Query("SELECT new gabrieldev.com.supermercado_estoque.model.DTO.DepartmentDTO(d.id, d.sector) FROM Department d")
	List<DepartmentDTO> findSimpleDepartments();
	
	Department findBySector(String sector);
}
