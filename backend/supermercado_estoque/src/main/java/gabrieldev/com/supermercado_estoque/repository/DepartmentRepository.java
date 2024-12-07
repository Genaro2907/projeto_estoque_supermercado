package gabrieldev.com.supermercado_estoque.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import gabrieldev.com.supermercado_estoque.model.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long>{

}
