package com.example.furniturestore.Repotitory;

import com.example.furniturestore.Entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
