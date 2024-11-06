package com.example.furniturestore.Service;

import com.example.furniturestore.Entity.Order;
import com.example.furniturestore.Entity.OrderItem;
import com.example.furniturestore.Repotitory.OrderItemRepository;
import com.example.furniturestore.Repotitory.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Transactional
    public Order createOrder(Order order, List<OrderItem> orderItems) {
        // Lưu thông tin đơn hàng
        Order savedOrder = orderRepository.save(order);

        // Lưu các sản phẩm vào đơn hàng
        for (OrderItem item : orderItems) {
            item.setOrder(savedOrder);
            orderItemRepository.save(item);
        }

        return savedOrder;
    }


    // Lấy danh sách tất cả hóa đơn
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    // Lấy chi tiết hóa đơn theo ID
    public Optional<Order> getOrderById(Long orderId) {
        return orderRepository.findById(orderId);
    }

//    // Lấy tất cả các sản phẩm trong đơn hàng
//    public List<OrderItem> getOrderItemsByOrderId(Long orderId) {
//        return orderItemRepository.findAllByOrderId(orderId);
//    }
}

