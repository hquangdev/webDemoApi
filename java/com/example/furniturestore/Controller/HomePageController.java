package com.example.furniturestore.Controller;


import com.example.furniturestore.Entity.Category;
import com.example.furniturestore.Entity.Product;
import com.example.furniturestore.Repotitory.CategoryRepo;
import com.example.furniturestore.Repotitory.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/home")
@RestController
public class HomePageController {

    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private CategoryRepo categoryRepo;

    @GetMapping("/content")
    public ResponseEntity<Object> listContent(){
        List<Product> products = productRepo.findAll();
        List<Category> categories = categoryRepo.findAll();

        // Tạo một Map để chứa phản hồi
        Map<String, Object> response = new HashMap<>();
        response.put("products", products);
        response.put("categories", categories);

        // Trả về phản hồi chứa cả sản phẩm và danh mục
        return ResponseEntity.ok(response);
    }
}
