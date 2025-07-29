package com.E_commerce_Microservices.wallet_service.service;

import com.E_commerce_Microservices.wallet_service.entity.Users;
import com.E_commerce_Microservices.wallet_service.jwt.JwtUtil;
import com.E_commerce_Microservices.wallet_service.repositort.UserRepository;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    public Users getUser(Long id) {
        return  userRepository.findById(id).orElseThrow(()-> new RuntimeException("user not found"));
    }
    public Users getUser(String email) {
      return  userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("email not found"));
    }
    public String addUser(String name,String email,String password) {
        Optional<Users> checkUser = userRepository.findByEmail(email);
        if (checkUser.isPresent()) {
            throw new RuntimeException("user already exists");
        }
        Users user = new Users();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);
        return "User added successfully";
    }
    public String updateUser(String name,String email,String password) {
        Users checkUser = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("email not found"));
        if (checkUser.getEmail().equals(email)) {
            throw new RuntimeException("email already in use");
        }
        if (checkUser.getPassword().equals(password)) {
            throw new RuntimeException("password already in use");

        }
        if (checkUser.getName().equals(name)) {
            throw new RuntimeException("name already in use");
        }
        Users user = new Users();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);
        userRepository.save(user);
        return "User updated successfully";
    }
    public String deleteUser(String email) {
        Users CheckUser = userRepository.findByEmail(email).orElseThrow(()-> new RuntimeException("email not found"));
        userRepository.delete(CheckUser);
        return "User deleted successfully";
    }
    public String deleteUser(Long id) {
        Users CheckUser = userRepository.findById(id).orElseThrow(()-> new RuntimeException("email not found"));
        userRepository.delete(CheckUser);
        return "User deleted successfully";
    }
    public List<Users> getAllUsers() {
       return userRepository.findAll();

    }

    public ResponseEntity<String> registerUser(Users user) {
        Optional<Users> checkUser = userRepository.findByEmail(user.getEmail());
        if (checkUser.isPresent()) {
            throw new RuntimeException("user already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
         userRepository.save(user);
        String token = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(token);

    }

    public ResponseEntity<String> loginUser(String email, String rawPassword) {
        Users user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(rawPassword, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        String token = jwtUtil.generateToken(user.getEmail());
        return ResponseEntity.ok(token);
    }
}
