package com.example.project3.Controller;


import com.example.springbootproject.ApiMessage.ApiMessage;
import com.example.springbootproject.Model.Product;
import com.example.springbootproject.Services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = productService.getAll();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@Valid @RequestBody Product product, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(getAllErrors(errors));
        }

        if (productService.checkId(Integer.parseInt(product.getId())) != null) {
            return ResponseEntity.badRequest().body(new ApiMessage(productService.checkId(Integer.parseInt(product.getId()))));
        }

        if (!productService.getCategory(product)) {
            return ResponseEntity.badRequest().body(new ApiMessage("Category not found"));
        }

        productService.add(product);
        return ResponseEntity.ok(new ApiMessage("Success"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable int id, @Valid @RequestBody Product product, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(getAllErrors(errors));
        }

        if (!productService.getCategory(product)) {
            return ResponseEntity.badRequest().body(new ApiMessage("Category not found"));
        }

        if (!productproductService.update(id, product)) {
            return ResponseEntity.badRequest().body(new ApiMessage("Product not found"));
        }

        return ResponseEntity.ok(new ApiMessage("Success"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        if (productService.delete(id)) {
            return ResponseEntity.ok(new ApiMessage("Success"));
        }
        return ResponseEntity.badRequest().body(new ApiMessage("Product not found"));
    }

    private List<String> getAllErrors(Errors errors) {
        List<String> allErrors = new ArrayList<>();
        errors.getAllErrors().forEach(error -> allErrors.add(error.getDefaultMessage()));
        return allErrors;
    }
}
