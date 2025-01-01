package gabrieldev.com.supermercado_estoque.model.DTO;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.github.dozermapper.core.Mapping;

@JsonPropertyOrder({ "id", "name", "description", "quantity", "entryDate", "departmentID"})
public class SimpleProductDTO  extends RepresentationModel<SimpleProductDTO> implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("id")
	@Mapping("id")
	private Long key;
	private String name;
	private String description;
	private Integer quantity;
	private Date entryDate;
	private SimpleDepartmentDTO departmentID;
	
	
	public SimpleProductDTO() {
		
	}

	public SimpleProductDTO(Long key, String name, String description, Integer quantity, Date entryDate,
			SimpleDepartmentDTO departmentID) {
		super();
		this.key = key;
		this.name = name;
		this.description = description;
		this.quantity = quantity;
		this.entryDate = entryDate;
		this.departmentID = departmentID;
	}



	public Long getKey() {
		return key;
	}

	public void setKey(Long key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Integer getQuantity() {
		return quantity;
	}


	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}


	public Date getEntryDate() {
		return entryDate;
	}


	public void setEntryDate(Date entryDate) {
		this.entryDate = entryDate;
	}
	
	public SimpleDepartmentDTO getDepartmentID() {
		return departmentID;
	}

	public void setDepartmentID(SimpleDepartmentDTO departmentID) {
		this.departmentID = departmentID;
	}

	@Override
	public int hashCode() {
		return Objects.hash(departmentID, description, entryDate, key, name, quantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleProductDTO other = (SimpleProductDTO) obj;
		return Objects.equals(departmentID, other.departmentID) && Objects.equals(description, other.description)
				&& Objects.equals(entryDate, other.entryDate) && Objects.equals(key, other.key)
				&& Objects.equals(name, other.name) && Objects.equals(quantity, other.quantity);
	}

	

	
}
