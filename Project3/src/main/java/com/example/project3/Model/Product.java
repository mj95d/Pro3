package com.example.project3.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

mport lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    @NotEmpty(message = "ID must not be empty")
    @Size(min = 3, message = "ID must be at least 3 characters long")
    private String id;

    @NotEmpty(message = "Name must not be empty")
    @Size(min = 3, message = "Name must be at least 3 characters long")
    private String name;

    @NotEmpty(message = "Price must not be empty")
    @Positive(message = "Price must be a positive number")
    private String price;

    @NotEmpty(message = "Category ID must not be empty")
    @Size(min = 3, message = "Category ID must be at least 3 characters long")
    private String categoryID;

}
