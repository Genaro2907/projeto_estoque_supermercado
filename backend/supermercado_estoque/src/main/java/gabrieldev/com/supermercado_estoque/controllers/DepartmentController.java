package gabrieldev.com.supermercado_estoque.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gabrieldev.com.supermercado_estoque.model.DTO.DepartmentDTO;
import gabrieldev.com.supermercado_estoque.model.DTO.SimpleDepartmentDTO;
import gabrieldev.com.supermercado_estoque.services.DepartmentService;
import gabrieldev.com.supermercado_estoque.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/department")
@Tag(name = "Department", description = "Endpoints for Managing department")
public class DepartmentController {

    @Autowired
    private DepartmentService service;

    @GetMapping(value = "/{id}", 
    		produces = {
    				MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Find a Department", description = "Find a Department", tags = {"Department"}, 
    responses = {
    		@ApiResponse(description = "Success", responseCode = "200", content = {
    					@Content(schema = @Schema(implementation = DepartmentDTO.class))}),
    		@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
    		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
    		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
    		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
    		@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),})
    public ResponseEntity<DepartmentDTO> findById(@PathVariable Long id) {
        DepartmentDTO departmentDTO = service.findById(id);
        return ResponseEntity.ok(departmentDTO);
    }

    @GetMapping(produces = {
    		MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Finds all Departments", description = "Finds all Departments", tags = {"Department"}, 
    responses = {
    		@ApiResponse(description = "Success", responseCode = "200", content = {
    					@Content(
    							mediaType = "application/json",
    							array = @ArraySchema(schema = @Schema(implementation = DepartmentDTO.class)))}),
    		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
    		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
    		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
    		@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),})
    public ResponseEntity<List<DepartmentDTO>> findAll() {
        List<DepartmentDTO> departments = service.findAll();
        return ResponseEntity.ok(departments);
    }

    @PostMapping(consumes  = {
    		MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}, 
    			produces = {
    					MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Adds new Department", description = "Adds new Department", tags = {"Department"}, 
    responses = {
    		@ApiResponse(description = "Created", responseCode = "200", content = {
    					@Content(schema = @Schema(implementation = DepartmentDTO.class))}),
    		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
    		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
    		@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),})
    public ResponseEntity<DepartmentDTO> create(@RequestBody DepartmentDTO departmentDTO) {
        DepartmentDTO createdDepartment = service.create(departmentDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdDepartment);
    }

    @PutMapping(value = "/{id}", consumes  = {
    		MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}, 
    			produces = {
    					MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Updates a Department", description = "Updates a Department", tags = {"Department"}, 
    responses = {
    		@ApiResponse(description = "Updated", responseCode = "200", content = {
    					@Content(schema = @Schema(implementation = DepartmentDTO.class))}),
    		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
    		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
    		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
    		@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),})
    public ResponseEntity<DepartmentDTO> update(@PathVariable Long id, @RequestBody DepartmentDTO departmentDTO) {
        DepartmentDTO updatedDepartment = service.update(id, departmentDTO);
        return ResponseEntity.ok(updatedDepartment);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a Department", description = "Deletes a Department", tags = {"Department"}, 
    responses = {
    		@ApiResponse(description = "No content", responseCode = "204", content = {@Content}),
    		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
    		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
    		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
    		@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping(value = "/sector", 
    			produces = {
    					MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Finds all Departments Without Products", description = "Finds all Departments Without Products", tags = {"Department"}, 
    responses = {
    		@ApiResponse(description = "Success", responseCode = "200", content = {
    					@Content(
    							mediaType = "application/json",
    							array = @ArraySchema(schema = @Schema(implementation = DepartmentDTO.class)))}),
    		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
    		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
    		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
    		@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),})
    public ResponseEntity<List<SimpleDepartmentDTO>> findDepartmentsWithoutProducts() {
        List<SimpleDepartmentDTO>departments = service.findDepartmentsWithoutProducts();
        return ResponseEntity.ok(departments);
    }
}
