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
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("Name Test0", output.getName());
        assertEquals("Description Test0", output.getDescription());
        assertEquals(0, output.getQuantity());
        assertEquals(0, output.getDepartmentID());
    }

    @Test
    public void parseEntityListToDTOListTest() {
        List<ProductDTO> outputList = ModelMapper.parseListObjects(inputObject.mockEntityList(), ProductDTO.class);
        ProductDTO outputZero = outputList.get(0);
        
        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("Name Test0", outputZero.getName());
        assertEquals("Description Test0", outputZero.getDescription());
        assertEquals(0, outputZero.getQuantity());
        assertEquals(0, outputZero.getDepartmentID());
        
        ProductDTO outputDois = outputList.get(2);
        
        assertEquals(Long.valueOf(2L), outputDois.getId());
        assertEquals("Name Test2", outputDois.getName());
        assertEquals("Description Test2", outputDois.getDescription());
        assertEquals(2, outputDois.getQuantity());
        assertEquals(2, outputDois.getDepartmentID());
        
        ProductDTO outputQuatro = outputList.get(4);
        
        assertEquals(Long.valueOf(4L), outputQuatro.getId());
        assertEquals("Name Test4", outputQuatro.getName());
        assertEquals("Description Test4", outputQuatro.getDescription());
        assertEquals(4, outputQuatro.getQuantity());
        assertEquals(4, outputQuatro.getDepartmentID());
    }

    @Test
    public void parseDTOToEntityTest() {
        Product output = ModelMapper.parseObject(inputObject.mockDTO(), Product.class);
        assertEquals(Long.valueOf(0L), output.getId());
        assertEquals("Name Test0", output.getName());
        assertEquals("Description Test0", output.getDescription());
        assertNotNull(output.getDepartment());
        assertEquals(Long.valueOf(0L), output.getDepartment().getId());
    }

    @Test
    public void parserDTOListToEntityListTest() {
        List<Product> outputList = ModelMapper.parseListObjects(inputObject.mockDTOList(), Product.class);
        Product outputZero = outputList.get(0);
        
        assertEquals(Long.valueOf(0L), outputZero.getId());
        assertEquals("Name Test0", outputZero.getName());
        assertEquals("Description Test0", outputZero.getDescription());
        assertNotNull(outputZero.getDepartment());
        assertEquals(Long.valueOf(0L), outputZero.getDepartment().getId());
        
        Product outputDois = outputList.get(2);
        
        assertEquals(Long.valueOf(2L), outputDois.getId());
        assertEquals("Name Test2", outputDois.getName());
        assertEquals("Description Test2", outputDois.getDescription());
        assertNotNull(outputZero.getDepartment());
        assertEquals(Long.valueOf(0L), outputZero.getDepartment().getId());
        
        Product outputQuatro = outputList.get(4);
        
        assertEquals(Long.valueOf(4L), outputQuatro.getId());
        assertEquals("Name Test4", outputQuatro.getName());
        assertEquals("Description Test4", outputQuatro.getDescription());
        assertNotNull(outputZero.getDepartment());
        assertEquals(Long.valueOf(0L), outputZero.getDepartment().getId());;
    }
}
