package gabrieldev.com.supermercado_estoque.services.mapper;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import gabrieldev.com.supermercado_estoque.model.Department;
import gabrieldev.com.supermercado_estoque.model.Product;
import gabrieldev.com.supermercado_estoque.model.DTO.ProductDTO;
import gabrieldev.com.supermercado_estoque.model.DTO.SimpleDepartmentDTO;
import gabrieldev.com.supermercado_estoque.model.DTO.SimpleProductDTO;

@Component
public class ProductMapper {
	
private static  Mapper mapper = DozerBeanMapperBuilder.buildDefault();
	
public static <O, D> D parseObject(O origin, Class<D> destination) {
    D destinationObject = mapper.map(origin, destination);

    if (origin instanceof ProductDTO && destinationObject instanceof Product) {
        ProductDTO productDTO = (ProductDTO) origin;
        Product product = (Product) destinationObject;

        if (productDTO.getDepartmentID() != null) {
            Department department = new Department();
            department.setId(productDTO.getDepartmentID().getKey() != null 
                ? productDTO.getDepartmentID().getKey() 
                : 0L);
            department.setSector(productDTO.getDepartmentID().getSector());
            product.setDepartment(department);
        } else {
            Department department = new Department();
            department.setId(0L);
            department.setSector("Setor Padrão");
            product.setDepartment(department);
        }
    }

    return destinationObject;
}
 

public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
    List<D> destinationObjects = new ArrayList<>();

    for (O o : origin) {
        D destinationObject = mapper.map(o, destination);

        if (o instanceof Product && destinationObject instanceof SimpleProductDTO) {
            Product product = (Product) o;
            SimpleProductDTO productDTO = (SimpleProductDTO) destinationObject;

            if (product.getDepartment() != null) {
                SimpleDepartmentDTO departmentDTO = new SimpleDepartmentDTO();
                departmentDTO.setKey(
                    product.getDepartment().getId() != null 
                    ? product.getDepartment().getId() 
                    : 0L
                );
                departmentDTO.setSector(product.getDepartment().getSector());
                productDTO.setDepartmentID(departmentDTO);
            } else {
            	SimpleDepartmentDTO departmentDTO = new SimpleDepartmentDTO ();
                departmentDTO.setKey(0L);
                departmentDTO.setSector("Setor Padrão");
                productDTO.setDepartmentID(departmentDTO);
            }
        }

        destinationObjects.add(destinationObject);
    }

    return destinationObjects;
}

}
