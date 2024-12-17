package gabrieldev.com.supermercado_estoque.unittests.mapper;

import java.util.ArrayList;
import java.util.List;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.loader.api.BeanMappingBuilder;

import gabrieldev.com.supermercado_estoque.model.Department;
import gabrieldev.com.supermercado_estoque.model.Product;
import gabrieldev.com.supermercado_estoque.model.DTO.DepartmentDTO;
import gabrieldev.com.supermercado_estoque.model.DTO.ProductDTO;

public class ModelMapper {
    
    private static Mapper mapper;
    
    static {
        DozerBeanMapperBuilder builder = DozerBeanMapperBuilder.create();
        
        builder.withMappingBuilder(new BeanMappingBuilder() {
            @Override
            protected void configure() {
                mapping(Product.class, ProductDTO.class)
                    .fields("department", "departmentID");
            }
        });

        mapper = builder.build();
    }
    
    public static <O, D> D parseObject(O origin, Class<D> destination) {
        D destinationObject = mapper.map(origin, destination);

        if (origin instanceof ProductDTO && destinationObject instanceof Product) {
            ProductDTO productDTO = (ProductDTO) origin;
            Product product = (Product) destinationObject;

            if (productDTO.getDepartmentID() != null) {
                Department department = new Department();
                department.setId(productDTO.getDepartmentID().getId() != null 
                    ? productDTO.getDepartmentID().getId() 
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

            if (o instanceof Product && destinationObject instanceof ProductDTO) {
                Product product = (Product) o;
                ProductDTO productDTO = (ProductDTO) destinationObject;

                if (product.getDepartment() != null) {
                    DepartmentDTO departmentDTO = new DepartmentDTO();
                    departmentDTO.setId(
                        product.getDepartment().getId() != null 
                        ? product.getDepartment().getId() 
                        : 0L
                    );
                    departmentDTO.setSector(product.getDepartment().getSector());
                    productDTO.setDepartmentID(departmentDTO);
                } else {
                    DepartmentDTO departmentDTO = new DepartmentDTO();
                    departmentDTO.setId(0L);
                    departmentDTO.setSector("Setor Padrão");
                    productDTO.setDepartmentID(departmentDTO);
                }
            }

            destinationObjects.add(destinationObject);
        }

        return destinationObjects;
    }
    
    
}