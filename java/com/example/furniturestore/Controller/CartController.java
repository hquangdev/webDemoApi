package com.example.furniturestore.Controller;

import com.example.furniturestore.Entity.Product;
import com.example.furniturestore.Repotitory.ProductRepo;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/home")
@RestController
public class CartController {

    @Autowired
    private ProductRepo productRepo; // Service này sẽ gọi API lấy thông tin sản phẩm

    @Autowired
    private HttpSession httpSession;

    // API để thêm sản phẩm vào giỏ hàng
    @PostMapping("/addcart")
    public ResponseEntity<String> addToCart(@RequestBody Map<String, String> request) {
        String productId = request.get("productId");

        // Lấy sản phẩm từ repo
        Product product = productRepo.findById(Long.valueOf(productId)).orElse(null);

        if (product == null) {
            return ResponseEntity.badRequest().body("Sản phẩm không tồn tại.");
        }

        // Lưu vào giỏ hàng
        Map<String, Object> cart = (Map<String, Object>) httpSession.getAttribute("CART");

        if (cart == null) {
            cart = new HashMap<>();
        }

        // Kiểm tra sản phẩm có trong giỏ hàng chưa
        if (cart.containsKey(productId)) {
            Map<String, Object> item = (Map<String, Object>) cart.get(productId);
            int quantity = (int) item.get("quantity");
            item.put("quantity", quantity + 1); // Tăng số lượng
        } else {
            Map<String, Object> item = new HashMap<>();
            item.put("name", product.getName());
            item.put("price", product.getPrice());
            item.put("image", product.getImage());
            item.put("quantity", 1);
            cart.put(productId, item); // Thêm sản phẩm mới vào giỏ hàng
        }

        // Lưu lại giỏ hàng vào session
        httpSession.setAttribute("CART", cart);

        return ResponseEntity.ok("Sản phẩm đã được thêm vào giỏ hàng");
    }

    // API để lấy giỏ hàng
    @GetMapping("/cart/list")
    public ResponseEntity<Map<String, Object>> getCart() {
        Map<String, Object> cart = (Map<String, Object>) httpSession.getAttribute("CART");
        System.out.println("Cart data: " + cart); // In dữ liệu giỏ hàng ra console để kiểm tra
        return ResponseEntity.ok(cart);
    }

}
