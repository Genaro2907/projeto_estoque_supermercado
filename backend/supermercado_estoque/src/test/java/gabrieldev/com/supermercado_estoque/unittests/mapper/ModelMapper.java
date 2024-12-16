package gabrieldev.com.supermercado_estoque.unittests.mapper;

import java.util.ArrayList;
import java.util.List;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import gabrieldev.com.supermercado_estoque.model.Department;
import gabrieldev.com.supermercado_estoque.model.Product;
import gabrieldev.com.supermercado_estoque.model.DTO.ProductDTO;

public class ModelMapper {
	
	private static  Mapper mapper = DozerBeanMapperBuilder.buildDefault();
	
	
	public static <O, D> D parseObject(O origin, Class<D> destination) {
	    D destinationObject = mapper.map(origin, destination);

	    if (origin instanceof ProductDTO && destinationObject instanceof Product) {
	        ProductDTO productDTO = (ProductDTO) origin;
	        Product product = (Product) destinationObject;

	        if (productDTO.getDepartmentID() != null) {
	            Department department = new Department();
	            department.setId(productDTO.getDepartmentID());
	            product.setDepartment(department);
	        }
	    }

	    return destinationObject;
	}

	public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
	    List<D> destinationObjects = new ArrayList<>();
	    for (O o : origin) {
	        D destinationObject = mapper.map(o, destination);

	        if (o instanceof ProductDTO && destinationObject instanceof Product) {
	            ProductDTO productDTO = (ProductDTO) o;
	            Product product = (Product) destinationObject;

	            if (productDTO.getDepartmentID() != null) {
	                Department department = new Department();
	                department.setId(productDTO.getDepartmentID());
	                product.setDepartment(department);
	            }
	        }

	        destinationObjects.add(destinationObject);
	    }
	    return destinationObjects;
	}
}
