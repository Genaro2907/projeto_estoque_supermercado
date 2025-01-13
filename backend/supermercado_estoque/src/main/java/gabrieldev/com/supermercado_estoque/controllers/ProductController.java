package gabrieldev.com.supermercado_estoque.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
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

import gabrieldev.com.supermercado_estoque.model.DTO.ProductDTO;
import gabrieldev.com.supermercado_estoque.model.DTO.SimpleProductDTO;
import gabrieldev.com.supermercado_estoque.services.ProductService;
import gabrieldev.com.supermercado_estoque.util.MediaType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/product")
@Tag(name = "Product", description = "Endpoints for Managing product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping(value = "/{id}", 
    		produces = {
    				MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Find a Product", description = "Find a Product", tags = {"Product"}, 
    responses = {
    		@ApiResponse(description = "Success", responseCode = "200", content = {
    					@Content(schema = @Schema(implementation = ProductDTO.class))}),
    		@ApiResponse(description = "No Content", responseCode = "204", content = @Content),
    		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
    		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
    		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
    		@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),})
    public ResponseEntity<ProductDTO> findById(@PathVariable Long id) {
        ProductDTO productDTO = productService.findById(id);
        return ResponseEntity.ok(productDTO);
    }

    @GetMapping(produces = {
    		MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Finds all Products", description = "Finds all Products", tags = {"Product"}, 
    responses = {
    		@ApiResponse(description = "Success", responseCode = "200", content = {
    					@Content(
    							mediaType = "application/json",
    							array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class)))}),
    		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
    		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
    		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
    		@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),})
    public ResponseEntity<List<SimpleProductDTO>> findAll() {
        List<SimpleProductDTO> products = productService.findAll();
        return ResponseEntity.ok(products);
    }

    @PostMapping(consumes  = {
    		MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}, 
    			produces = {
    					MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Adds new Product", description = "Adds new Product", tags = {"Product"}, 
    responses = {
    		@ApiResponse(description = "Created", responseCode = "200", content = {
    					@Content(schema = @Schema(implementation = ProductDTO.class))}),
    		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
    		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
    		@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),})
    public ResponseEntity<ProductDTO> create(@RequestBody ProductDTO productDTO) {
        ProductDTO createdProduct = productService.create(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @PutMapping(value = "/{id}", consumes  = {
    		MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML}, 
    			produces = {
    					MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Updates a Product", description = "Updates a Product", tags = {"Product"}, 
    responses = {
    		@ApiResponse(description = "Updated", responseCode = "200", content = {
    					@Content(schema = @Schema(implementation = ProductDTO.class))}),
    		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
    		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
    		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
    		@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),})
    public ResponseEntity<ProductDTO> update(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        ProductDTO updatedProduct = productService.update(id, productDTO);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Deletes a Product", description = "Deletes a Product", tags = {"Product"}, 
    responses = {
    		@ApiResponse(description = "No content", responseCode = "204", content = {@Content}),
    		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
    		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
    		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
    		@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),})
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(value = "/department/{departmentId}", produces = {
			MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML, MediaType.APPLICATION_YML})
    @Operation(summary = "Get all Products from a Department", description = "Get all Products from a Department", tags = {"Product"}, 
    responses = {
    		@ApiResponse(description = "Success", responseCode = "200", content = {
    					@Content(
    							mediaType = "application/json",
    							array = @ArraySchema(schema = @Schema(implementation = ProductDTO.class)))}),
    		@ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
    		@ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
    		@ApiResponse(description = "Not Found", responseCode = "404", content = @Content),
    		@ApiResponse(description = "Internal Error", responseCode = "500", content = @Content),})
    public ResponseEntity<CollectionModel<EntityModel<ProductDTO>>> findByDepartment(@PathVariable Long departmentId) {
        CollectionModel<EntityModel<ProductDTO>> products = (CollectionModel<EntityModel<ProductDTO>>) productService.findByDepartment(departmentId);
        return ResponseEntity.ok(products);
    }
}