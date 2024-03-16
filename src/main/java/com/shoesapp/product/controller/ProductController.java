package com.shoesapp.product.controller;

import com.shoesapp.product.dto.ProductDTO;
import com.shoesapp.product.dto.ProductResponse;
import com.shoesapp.product.entity.Product;
import com.shoesapp.product.service.ProductService;
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
    public ProductResponse<Product> getAllProducts(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    )
    {
        Page <Product> pagedResponse = productService.getAllProducts(PageRequest.of(page, size));

        ProductResponse<Product> productResponse = new ProductResponse<>();

        productResponse.setData(pagedResponse.getContent());
        productResponse.setTotal(pagedResponse.getTotalElements());

        return productResponse;
    }

    @PostMapping("/products")
    public ResponseEntity<ProductDTO> addProduct(
        @RequestBody ProductDTO productDTO
    ){
        ProductDTO createdProduct = productService.addProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @DeleteMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> deleteProduct(
            @PathVariable("productId") Long productId
    ){
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/products/{productId}")
    public ResponseEntity<ProductDTO> updateProduct(
            @PathVariable("productId") Long productId,
            @RequestBody ProductDTO productDTO

    ){
        productService.updateProduct(productId, productDTO);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/products/{productId}/image")
    public ResponseEntity<ProductDTO> updateProductImage(
            @PathVariable("productId") Long productId,
            @RequestParam("image")MultipartFile image
            ) throws IOException {
        productService.updateProductImage(productId, image);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
