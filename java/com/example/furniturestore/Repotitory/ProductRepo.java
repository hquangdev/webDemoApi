package com.example.furniturestore.Repotitory;

import com.example.furniturestore.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepo extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);

    //tim theo ten va giá
    List<Product> findByNameContainingAndPriceBetween(String name, double minPrice, double maxPrice);
}
