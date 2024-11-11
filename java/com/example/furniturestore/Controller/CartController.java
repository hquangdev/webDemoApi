package com.example.furniturestore.Controller;

import com.example.furniturestore.Entity.Product;
import com.example.furniturestore.Repotitory.ProductRepo;

import com.example.furniturestore.Service.CartService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/home")
@RestController
public class CartController {

    private final CartService cartService;
    private final ProductRepo productRepo;

    @Autowired
    public CartController(CartService cartService, ProductRepo productRepo) {
        this.cartService = cartService;
        this.productRepo = productRepo;
    }

    // API để thêm sản phẩm vào giỏ hàng
    @PostMapping("/add-cart")
    public ResponseEntity<String> addToCart(@RequestBody Map<String, String> request) {
        String productId = request.get("productId");

        // Lấy sản phẩm từ repo
        Product product = productRepo.findById(Long.valueOf(productId))
                .orElseThrow(()-> new RuntimeException("Sản phẩm không tồn tại"));


        // Tạo item giỏ hàng
        Map<String, Object> item = new HashMap<>();
        item.put("name", product.getName());
        item.put("price", product.getPrice());
        item.put("image", product.getImage());

        // Lưu vào giỏ hàng trong Redis (sử dụng "default" cho giỏ hàng mặc định)
        cartService.addToCart("default", productId, item);

        return ResponseEntity.ok("Sản phẩm đã được thêm vào giỏ hàng" );
    }

    // API để lấy giỏ hàng
    @GetMapping("/cart/list")
    public ResponseEntity<Map<String, Object>> getCart() {
        Map<String, Object> cart = cartService.getCart();
        return ResponseEntity.ok(cart);
    }


}
