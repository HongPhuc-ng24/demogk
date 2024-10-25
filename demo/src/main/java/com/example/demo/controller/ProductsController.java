package com.example.demo.controller;

import com.example.demo.model.Product;
import com.example.demo.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class ProductsController {

    @Autowired
    private ProductService productService;

    // Khởi tạo danh sách Product
    public ProductsController() {
    }

    // Trả về danh sách Product
    @GetMapping("/products")
    @ResponseBody
    public List<Product> getProductList() {
        return productService.findAll();
    }

    // Trả về một Product cụ thể theo ID
    @GetMapping("/products/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable("id") int productId) {
        return productService.findAll().stream()
                .filter(product -> product.getId() == productId)
                .findFirst()
                .map(product -> ResponseEntity.status(200).body(product))
                .orElse(ResponseEntity.status(404).body(null));
    }

    // Xóa sản phẩm theo ID
    @DeleteMapping("/products/{id}")
    @ResponseBody
    public List<Product> removeProductById(@PathVariable("id") int productId) {
        productService.delete(productId);
        return productService.findAll();
    }

    // Tạo mới một sản phẩm
    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        productService.save(product);
        return ResponseEntity.status(201).body(product);
    }

    // Cập nhật thông tin sản phẩm
    @PutMapping("/product/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") int productId, @RequestBody Product updateProduct) {
        Product product = productService.findById(productId);
        if (product != null) {
            product.setName(updateProduct.getName());
            product.setDescription(updateProduct.getDescription()); // Cập nhật mô tả
            product.setPrice(updateProduct.getPrice()); // Cập nhật giá
            product.setStock(updateProduct.getStock()); // Cập nhật số lượng tồn kho
            productService.save(product);
            return ResponseEntity.status(200).body(product);
        }
        return ResponseEntity.status(404).body(null);
    }
}
