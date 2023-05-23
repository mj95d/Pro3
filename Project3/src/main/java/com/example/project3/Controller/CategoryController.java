package com.example.project3.Controller;

import com.example.springbootproject.ApiMessage.ApiMessage;
import com.example.springbootproject.Model.Category;
import com.example.springbootproject.Services.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
@RestController
@RequestMapping("/api/v1/category")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAll();
        return ResponseEntity.ok(categories);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addCategory(@Valid @RequestBody Category category, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(getAllErrors(errors));
        }
        if (categoryService.checkId(Integer.parseInt(category.getId())) != null) {
            return ResponseEntity.badRequest().body(new ApiMessage(categoryService.checkId(Integer.parseInt(category.getId()))));
        }

        categoryService.add(category);
        return ResponseEntity.ok(new ApiMessage("Success"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCategory(@PathVariable int id, @Valid @RequestBody Category category, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(getAllErrors(errors));
        }

        if (!categoryService.update(id, category)) {
            return ResponseEntity.badRequest().body(new ApiMessage("this id does not exist"));
        }

        return ResponseEntity.ok(new ApiMessage("Success"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteCategory(@PathVariable int id) {
        if (categoryService.delete(id)) {
            return ResponseEntity.ok(new ApiMessage("Success"));
        }
        return ResponseEntity.badRequest().body(new ApiMessage("BadRequest"));
    }

    private List<String> getAllErrors(Errors errors) {
        List<String> allErrors = new ArrayList<>();
        errors.getAllErrors().forEach(error -> allErrors.add(error.getDefaultMessage()));
        return allErrors;
    }
}
