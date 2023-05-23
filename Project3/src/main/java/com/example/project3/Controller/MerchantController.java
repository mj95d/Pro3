package com.example.project3.Controller;
import com.example.springbootproject.ApiMessage.ApiMessage;
import com.example.springbootproject.Model.Merchant;
import com.example.springbootproject.Services.MerchantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
@RestController
@RequestMapping("/api/v1/merchant")
@RequiredArgsConstructor
public class MerchantController {

    private final MerchantService merchantService;

    @GetMapping("/all")
    public ResponseEntity<List<Merchant>> getAllMerchants() {
        List<Merchant> merchants = merchantService.getAll();
        return ResponseEntity.ok(merchants);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addMerchant(@Valid @RequestBody Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(getAllErrors(errors));
        }
        if (merchantService.checkId(Integer.parseInt(merchant.getId())) != null) {
            return ResponseEntity.badRequest().body(new ApiMessage(merchantService.checkId(Integer.parseInt(merchant.getId()))));
        }

        merchantService.add(merchant);
        return ResponseEntity.ok(new ApiMessage("Success"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMerchant(@PathVariable int id, @Valid @RequestBody Merchant merchant, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(getAllErrors(errors));
        }

        if (!merchantService.update(id, merchant)) {
            return ResponseEntity.badRequest().body(new ApiMessage("this id does not exist"));
        }

        return ResponseEntity.ok(new ApiMessage("Success"));
    }

    @DeleteMapping("/delete/{id}")
public ResponseEntity<?> deleteMerchant(@PathVariable int id) {
        if (merchantService.delete(id)) {
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
