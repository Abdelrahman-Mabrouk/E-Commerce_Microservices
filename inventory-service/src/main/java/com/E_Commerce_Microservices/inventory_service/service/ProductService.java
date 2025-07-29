package com.E_Commerce_Microservices.inventory_service.service;


import com.E_Commerce_Microservices.inventory_service.entity.Product;
import com.E_Commerce_Microservices.inventory_service.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepo;

    public Product create(Product product) {
        Optional<Product> existing = productRepo.findByName(product.getName());
        if (existing.isPresent()) {
            throw new RuntimeException("Product with name: " + product.getName() + " already exists");
        }
        Product newProduct = new Product(product.getName(), product.getDescription(),product.getPrice());
        return productRepo.save(newProduct);
    }

    public List<Product> getAll() {
        return productRepo.findAll();
    }

    public Product getById(Long id) {
        return productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + id));
    }

    public void delete(Long id) {
        productRepo.deleteById(id);
    }

    public Product update(Long id, Product updatedProduct) {
        Product product = getById(id);
        product.setName(updatedProduct.getName());
        product.setDescription(updatedProduct.getDescription());
        product.setPrice(updatedProduct.getPrice());
        return productRepo.save(product);
    }
    public double getProductPrice(Long id) {
        Product product = getById(id);
        return product.getPrice();
    }


}
