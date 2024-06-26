package com.shoesapp.product.controller;

import com.shoesapp.product.dto.ProductDTO;
import com.shoesapp.product.dto.ProductResponse;
import com.shoesapp.product.entity.Product;
import com.shoesapp.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

    private final ProductService productService;

    @GetMapping("/products")
    public ProductResponse<ProductDTO> getAllProducts(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    ) {
        Page<ProductDTO> pagedResponse = productService.getAllProducts(PageRequest.of(page, size));

        ProductResponse<ProductDTO> productResponse = new ProductResponse<>();
        productResponse.setData(pagedResponse.getContent());
        productResponse.setTotal(pagedResponse.getTotalElements());

        return productResponse;
    }

    @PostMapping("/categories/{categoryId}/product")
    public ResponseEntity<ProductDTO> addProduct(
            @Valid @RequestBody ProductDTO productDTO, @PathVariable("categoryId") Long categoryId
    ){
        ProductDTO createdProduct = productService.addProduct(productDTO, categoryId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable("productId") Long productId
    ){
        String status = productService.deleteProduct(productId);
        return new ResponseEntity<>(status, HttpStatus.OK);
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable("productId") Long productId,
            @RequestBody ProductDTO productDTO

    ){
        ProductDTO updatedProduct = productService.updateProduct(productId, productDTO);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProduct);
    }

    @PutMapping("/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(
            @PathVariable("productId") Long productId,
            @RequestParam("image")MultipartFile image
            ) throws IOException {
        ProductDTO updatedProductImage = productService.updateProductImage(productId, image);
        return ResponseEntity.status(HttpStatus.OK).body(updatedProductImage);
    }
}
