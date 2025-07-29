package com.E_commerce_Microservices.wallet_service.controller;

import com.E_commerce_Microservices.wallet_service.entity.Users;
import com.E_commerce_Microservices.wallet_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/create/{name}/{email}/{password}")
    public String addUser(@PathVariable String name, @PathVariable String email, @PathVariable String password) {
       return userService.addUser(name, email, password);
    }
    @GetMapping("/allUsers")
    public List<Users> getAllUsers() {
        return userService.getAllUsers();
    }
    @GetMapping("/id/{id}")
    public Users getUser(@PathVariable Long id) {
       return userService.getUser(id);
    }
    @GetMapping("/email/{email}")
    public Users getUser(@PathVariable String email) {
        return userService.getUser(email);
    }
    @PutMapping("/update/{name}/{email}/{password}")
    public String updateUser(@PathVariable String name, @PathVariable String email, @PathVariable String password){
        return userService.updateUser(name, email, password);
    }
    @DeleteMapping("/delete/{email}")
    public String deleteUser(@PathVariable String email) {
        return userService.deleteUser(email);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        return userService.deleteUser(id);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody Users user) {
        return userService.registerUser(user);

    }
    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");
        return  userService.loginUser(email, password);

    }

}
