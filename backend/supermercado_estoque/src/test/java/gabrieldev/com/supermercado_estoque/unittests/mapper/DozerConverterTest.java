package gabrieldev.com.supermercado_estoque.unittests.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import gabrieldev.com.supermercado_estoque.model.Product;
import gabrieldev.com.supermercado_estoque.model.DTO.ProductDTO;
import gabrieldev.com.supermercado_estoque.unittests.mocks.MockDepartment;
import gabrieldev.com.supermercado_estoque.unittests.mocks.MockProduct;

public class DozerConverterTest {
    
    MockProduct inputObject;
    MockDepartment inputObject1;

    @BeforeEach
    public void setUp() {
        inputObject = new MockProduct();
        inputObject1 = new MockDepartment();
    }

    @Test
    public void parseEntityToDTOTest() {
        ProductDTO output = ModelMapper.parseObject(inputObject.mockEntity(), ProductDTO.class);
        assertEquals(Long.valueOf(0L), output.getKey());
        assertEquals("Name Test0", output.getName());
        assertEquals("Description Test0", output.getDescription());
        assertEquals(0, output.getQuantity());
        assertNotNull(output.getDepartmentID(), "DepartmentDTO não deveria ser nulo");
        assertEquals(Long.valueOf(0L), output.getDepartmentID().getKey(), "ID do departamento deve ser 0");
        }
        

    @Test
    public void parseEntityListToDTOListTest() {
        List<ProductDTO> outputList = ModelMapper.parseListObjects(inputObject.mockEntityList(), ProductDTO.class);
        ProductDTO outputZero = outputList.get(0);
        
        assertEquals(Long.valueOf(0L), outputZero.getKey());
        assertEquals("Name Test0", outputZero.getName());
        assertEquals("Description Test0", outputZero.getDescription());
        assertEquals(0, outputZero.getQuantity());
        assertNotNull(outputZero.getDepartmentID(), "DepartmentDTO não deveria ser nulo");
        assertEquals(Long.valueOf(0L), outputZero.getDepartmentID().getKey(), "ID do departamento deve ser 0");
        
        ProductDTO outputDois = outputList.get(2);
        
        assertEquals(Long.valueOf(2L), outputDois.getKey());
        assertEquals("Name Test2", outputDois.getName());
        assertEquals("Description Test2", outputDois.getDescription());
        assertEquals(2, outputDois.getQuantity());
        assertNotNull(outputDois.getDepartmentID(), "DepartmentDTO não deveria ser nulo");
        assertEquals(Long.valueOf(2L), outputDois.getDepartmentID().getKey(), "ID do departamento deve ser 2");
        
        
        ProductDTO outputQuatro = outputList.get(4);
        
        assertEquals(Long.valueOf(4L), outputQuatro.getKey());
        assertEquals("Name Test4", outputQuatro.getName());
        assertEquals("Description Test4", outputQuatro.getDescription());
        assertEquals(4, outputQuatro.getQuantity());
        assertNotNull(outputQuatro.getDepartmentID(), "DepartmentDTO não deveria ser nulo");
        assertEquals(Long.valueOf(4L), outputQuatro.getDepartmentID().getKey(), "ID do departamento deve ser 4");
    }
    @Test
    public void parseDTOToEntityTest() {
        ProductDTO dto = inputObject.mockDTO(0);
        Product output = ModelMapper.parseObject(dto, Product.class);

        assertNotNull(output);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("Name Test0", output.getName());
        assertEquals(Long.valueOf(0L), output.getDepartment().getId());
    }

    @Test
    public void parserDTOListToEntityListTest() {
        List<Product> outputList = ModelMapper.parseListObjects(inputObject.mockDTOList(), Product.class);
        
        Product outputZero = outputList.get(0);
        
        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("Name Test0", outputZero.getName());
        assertEquals("Description Test0", outputZero.getDescription());
        assertEquals(0, outputZero.getQuantity());
        assertEquals(Long.valueOf(0L), outputZero.getDepartment().getId());
        
        Product outputDois = outputList.get(2);
        assertEquals(Long.valueOf(2L), outputDois.getId());
        assertEquals("Name Test2", outputDois.getName());
        assertEquals("Description Test2", outputDois.getDescription());
        assertEquals(2, outputDois.getQuantity());
        assertEquals(Long.valueOf(0L), outputZero.getDepartment().getId());
        
        Product outputQuatro = outputList.get(4);
        assertEquals(Long.valueOf(4L), outputQuatro.getId());
        assertEquals("Name Test4", outputQuatro.getName());
        assertEquals("Description Test4", outputQuatro.getDescription());
        assertEquals(4, outputQuatro.getQuantity());
        assertEquals(Long.valueOf(0L), outputZero.getDepartment().getId());

    }
    
    
}
