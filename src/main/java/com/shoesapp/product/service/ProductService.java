package com.shoesapp.product.service;

import com.shoesapp.product.dto.ProductDTO;
import com.shoesapp.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ProductService {

    Page<Product> findAll(PageRequest of);

    ProductDTO createProduct(ProductDTO productDTO);

    String deleteProduct(Long productId);

//    String updateProduct(Long productId);

}
