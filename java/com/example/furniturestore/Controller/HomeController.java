package com.example.furniturestore.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HomeController {
//
//    @Autowired
//    private ProductRepo productRepo;
    @GetMapping("/home/login")
    public String listAlUsers() {
        return "/views/admin/login";
    }

    @GetMapping("/home/dashboard")
    public String home() {
        return "/views/admin/layout";
    }

    @GetMapping("/home/product/add")
    public String product(Model model) {
      model.addAttribute("contentAdmin");
        return "list";
    }

    @GetMapping("/home")
    public String homePage(Model model) {
        model.addAttribute("content", "/views/client/content/content");
        return "/views/client/layout";
    }

    @GetMapping("/home/cart")
    public String cart(Model model) {
        model.addAttribute("content", "/views/client/content/cart");
        return "/views/client/layout";
    }
}
