package gabrieldev.com.supermercado_estoque.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import gabrieldev.com.supermercado_estoque.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{

}
