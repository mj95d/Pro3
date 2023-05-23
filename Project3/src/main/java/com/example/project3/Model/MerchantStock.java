package com.example.project3.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MerchantStock {

    @NotEmpty(message = "ID must not be empty")
    @Size(min = 3, message = "ID must be at least 3 characters long")
    private String id;

    @NotEmpty(message = "Product ID must not be empty")
    @Size(min = 3, message = "Product ID must be at least 3 characters long")
    private String productId;

    @NotEmpty(message = "Merchant ID must not be empty")
    @Size(min = 3, message = "Merchant ID must be at least 3 characters long")
    private String merchantId;

    @NotEmpty(message = "Stock must not be empty")
    @Size(min = 10, message = "Stock must be at least 10 characters long")
    private String stock;

}
