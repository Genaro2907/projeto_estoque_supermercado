package gabrieldev.com.supermercado_estoque.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "product")
public class Product implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "description", nullable = false)
	private String description;
	private Integer quantity;
	private Date entryDate;
	
	@ManyToOne
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;
	
	public Product() {
	}
	
	public Product(Long id, String name, String description, Integer quantity, Date entryDate, Department department) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.quantity = quantity;
		this.entryDate = entryDate;
		this.department = department;
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

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		if (this.department != null) {
	        this.department.getProducts().remove(this);
	    }
	    this.department = department;
	    if (department != null) {
	        department.getProducts().add(this);
	    }
	}

	@Override
	public int hashCode() {
		return Objects.hash(department, description, entryDate, id, name, quantity);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Product other = (Product) obj;
		return Objects.equals(department, other.department) && Objects.equals(description, other.description)
				&& Objects.equals(entryDate, other.entryDate) && Objects.equals(id, other.id)
				&& Objects.equals(name, other.name) && Objects.equals(quantity, other.quantity);
	}
	
	
	
	
}
