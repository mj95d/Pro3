package com.example.project3.Controller;

import com.example.springbootproject.ApiMessage.ApiMessage;
import com.example.springbootproject.Model.User;
import com.example.springbootproject.Services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
@RestController
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAll();
        return ResponseEntity.ok(users);
    }

    @PostMapping("/add")
    public ResponseEntity<?> addUser(@Valid @RequestBody User user, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(getAllErrors(errors));
        }

        if (userService.checkId(Integer.parseInt(user.getId())) != null) {
            return ResponseEntity.badRequest().body(new ApiMessage("User already exists"));
        }

        userService.add(user);
        return ResponseEntity.ok(new ApiMessage("Success"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable int id, @Valid @RequestBody User user, Errors errors) {
        if (errors.hasErrors()) {
            return ResponseEntity.badRequest().body(getAllErrors(errors));
        }

        if (userService.update(id, user)) {
            return ResponseEntity.ok(new ApiMessage("Success"));
        }

        return ResponseEntity.badRequest().body(new ApiMessage("User not found"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable int id) {
        if (userService.delete(id)){
            return ResponseEntity.ok(new ApiMessage("Success"));
        }

        return ResponseEntity.badRequest().body(new ApiMessage("User not found"));
    }

    @PutMapping("/{userId}/product/{productId}/merchant/{merchantId}")
    public ResponseEntity<?> changeUser(@PathVariable int userId, @PathVariable int productId, @PathVariable int merchantId) {
        String result = userService.checks(userId, productId, merchantId);
        if (result.equals("Success")) {
            return ResponseEntity.ok(new ApiMessage("Success"));
        }

        return ResponseEntity.badRequest().body(new ApiMessage(result));
    }

    private List<String> getAllErrors(Errors errors) {
        return errors.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.toList());
    }
}
