package com.example.furniturestore.Service;

import com.example.furniturestore.Entity.Category;
import com.example.furniturestore.Entity.Product;
import com.example.furniturestore.Repotitory.CategoryRepo;
import com.example.furniturestore.Repotitory.ProductRepo;
import com.example.furniturestore.dto.ReqRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class ProductService {

    private final String uploadDir = "F:/File_web/FurnitureStore/src/main/resources/static/images/product/" ;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CategoryRepo categoryRepo;


    public void addProduct(String name, String title, Double price, int quantity, String details, MultipartFile image, Long categoryId) throws IOException {

        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(()->new RuntimeException("Không có danh mục"));

        if(productRepo.findByName(name).isPresent()){
            throw new RuntimeException("Tên sanr phâmr đã tồn tại");
        }

        Product product = new Product();
        product.setName(name);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setTitle(title);
        product.setDetails(details);
        product.setCategory(category);

        String imageFileName = saveFile(image);
        product.setImage(imageFileName);

        productRepo.save(product);
    }

    //Xử lí thêm ảnh
    public String saveFile(MultipartFile file) throws IOException {
        String originalFileName = file.getOriginalFilename();
        String filePath = Paths.get(uploadDir, originalFileName).toString();
        Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);

        // Trả về tên file gốc
        return originalFileName;
    }

    // Sửa sản phẩm
    public void editProduct(Long id, String name, String title, Double price, int quantity, String details, MultipartFile image, Long categoryId) throws IOException {
        // Tìm danh mục theo ID
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Không có danh mục"));

        // Kiểm tra xem tên sản phẩm đã tồn tại chưa
        if (productRepo.findByName(name).isPresent()) {
            throw new RuntimeException("Tên sản phẩm đã tồn tại");
        }

        // Tìm sản phẩm theo ID
        Product product = productRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sản phẩm"));

        // Xóa ảnh cũ nếu có
        if (product.getImage() != null && !product.getImage().isEmpty()) {
            delelteFile(product.getImage());
        }

        // Lưu ảnh mới vào thư mục chỉ nếu có ảnh mới
        if (image != null && !image.isEmpty()) {
            String newImageName = saveFile(image);
            product.setImage(newImageName); // Cập nhật ảnh mới vào sản phẩm
        }

        // Cập nhật thông tin sản phẩm
        product.setName(name);
        product.setTitle(title);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setDetails(details);

        product.setCategory(category);

        productRepo.save(product);
    }


    //xoa sanr pham
    public void delelteProduct(Long id){
        Product productId = productRepo.findById(id)
                .orElseThrow(()->new RuntimeException("Không tim thấy sản phẩm"));

        delelteFile(productId.getImage());
        productRepo.delete(productId);

    }

    //xoa anh trong thu muc
    public String delelteFile(String fileName) {
        File deleteFile = new File(uploadDir + fileName);

        if (deleteFile.exists() && deleteFile.delete()) {
            return "Tệp đã được xóa thành công: " + fileName;
        }
        return deleteFile.exists() ? "Không thể xóa tệp: " + fileName : "Không tìm thấy tệp: " + fileName;
    }

    //tim va loc san pham

    public ReqRes searchProducts(String name, double minPrice, double maxPrice) {
        List<Product> products = productRepo.findByNameContainingAndPriceBetween(name, minPrice, maxPrice);

        ReqRes response = new ReqRes();

        if (products.isEmpty()) {
            response.setStatusCode(404);
            response.setMessage("Không tìm thấy sản phẩm nào khớp với tên và giá đã chọn.");
        } else {
            response.setStatusCode(200);
            response.setMessage("Tìm thấy " + products.size() + " sản phẩm.");
            response.setData(products);
        }

        return response;
    }

}
