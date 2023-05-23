package com.example.project3.Controller;


import com.example.springbootproject.ApiMessage.ApiMessage;
import com.example.springbootproject.Model.MerchantStock;
import com.example.springbootproject.Services.MerchantStockService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/merchantStock")
@RequiredArgsConstructor
public class MerchantStockController {

    private final MerchantStockService merchantStockService;

    @GetMapping("/all")
    public ResponseEntity<List<MerchantStock>> getAllMerchantStocks() {
        List<MerchantStock> merchantStocks = merchantStockService.getAll();
        return ResponseEntity.ok(merchantStocks);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMerchantStock(@Valid @RequestBody MerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(getAllErrors(errors));
        }

        if (merchantStockService.checkId(Integer.parseInt(merchantStock.getId())) != null) {
            return ResponseEntity.badRequest().body(new ApiMessage(merchantStockService.checkId(Integer.parseInt(merchantStock.getId()))));
        }

        if (merchantStockService.getProduct(merchantStock) == null) {
            return ResponseEntity.badRequest().body(new ApiMessage("Product not found"));
        }

        if (merchantStockService.getMerchant(merchantStock) == null) {
            return ResponseEntity.badRequest().body(new ApiMessage("Merchant not found"));
        }

        merchantStockService.add(merchantStock);
        return ResponseEntity.ok(new ApiMessage("Success"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchantStock(@PathVariable int id, @Valid @RequestBodyMerchantStock merchantStock, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(getAllErrors(errors));
        }

        if (!merchantStockService.update(id, merchantStock)) {
            return ResponseEntity.badRequest().body(new ApiMessage("Merchant stock not found"));
        }

        if (merchantStockService.getProduct(merchantStock) == null) {
            return ResponseEntity.badRequest().body(new ApiMessage("Product not found"));
        }

        if (merchantStockService.getMerchant(merchantStock) == null) {
            return ResponseEntity.badRequest().body(new ApiMessage("Merchant not found"));
        }

        return ResponseEntity.ok(new ApiMessage("Success"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteMerchantStock(@PathVariable int id) {
        if (merchantStockService.delete(id)) {
            return ResponseEntity.ok(new ApiMessage("Success"));
        }
        return ResponseEntity.badRequest().body(new ApiMessage("Merchant stock not found"));
    }

    private List<String> getAllErrors(Errors errors) {
        List<String> allErrors = new ArrayList<>();
        errors.getAllErrors().forEach(error -> allErrors.add(error.getDefaultMessage()));
        return allErrors;
    }
}
