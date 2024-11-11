package com.example.furniturestore.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class CartService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final HashOperations<String, String, Object> hashOperations;

    @Autowired
    public CartService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
    }

    // Thêm sản phẩm vào giỏ hàng
    public void addToCart(String userId, String productId, Map<String, Object> item) {
        // Sử dụng "default" cho giỏ hàng mặc định
        String key = "cart:default"; // Giỏ hàng mặc định
        if (hashOperations.hasKey(key, productId)) {
            Map<String, Object> existingItem = (Map<String, Object>) hashOperations.get(key, productId);
            int currentQuantity = (int) existingItem.get("quantity");
            item.put("quantity", currentQuantity + 1);
        } else {
            item.put("quantity", 1);
        }
        hashOperations.put(key, productId, item);

        //thời giann hêt hạn cache cart
        redisTemplate.expire(key, 1, TimeUnit.MINUTES);
    }

    // Lấy giỏ hàng của người dùng
    public Map<String, Object> getCart() {
        // Sử dụng "default" cho giỏ hàng mặc định
        String key = "cart:default";
        return hashOperations.entries(key);
    }

}
