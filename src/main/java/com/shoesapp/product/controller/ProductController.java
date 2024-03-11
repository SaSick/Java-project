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

@RestController
@RequiredArgsConstructor
@RequestMapping("api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ProductResponse<Product> findAll(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int size
    )
    {
        Page <Product> pagedResponse = productService.findAll(PageRequest.of(page, size));

        ProductResponse<Product> productResponse = new ProductResponse<>();

        productResponse.setData(pagedResponse.getContent());
        productResponse.setTotal(pagedResponse.getTotalElements());

        return productResponse;
    }

    @PostMapping
    public ResponseEntity<ProductDTO> createProduct(
        @RequestBody ProductDTO productDTO
    ){
        ProductDTO createdProduct = productService.createProduct(productDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDTO> deleteProduct(
            @PathVariable("id") Long productId
    ){
        productService.deleteProduct(productId);
        return ResponseEntity.ok().build();
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<ProductDTO> updateProduct(
//            @PathVariable("id") Long productId
//
//    ){
//        productService.updateProduct(productId);
//        return ResponseEntity.ok().build();
//    }
}
