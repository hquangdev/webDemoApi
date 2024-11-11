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

    // Phương thức tạo đơn hàng
    public Order createOrder(Order order, List<OrderItem> orderItems) {
        // Lưu đơn hàng vào cơ sở dữ liệu
        Order savedOrder = orderRepository.save(order);

        // Lưu các item trong đơn hàng
        for (OrderItem orderItem : orderItems) {
            orderItem.setOrder(savedOrder);
            orderItemRepository.save(orderItem);
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

