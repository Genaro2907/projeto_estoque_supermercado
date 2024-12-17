package gabrieldev.com.supermercado_estoque.model.DTO;

import java.io.Serializable;
import java.util.Date;

public class ProductDTO implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private String name;
	private String description;
	private Integer quantity;
	private Date entryDate;
	private DepartmentDTO departmentID;
	
	
	public ProductDTO() {
		
	}

	public ProductDTO(Long id, String name, String description, Integer quantity, Date entryDate, DepartmentDTO departmentID) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.quantity = quantity;
		this.entryDate = entryDate;
		this.departmentID = departmentID;
	}

	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
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


	public DepartmentDTO getDepartmentID() {
		return departmentID;
	}


	public void setDepartmentID(DepartmentDTO departmentID) {
		this.departmentID = departmentID;
	}
	
	
	
	
	
}
