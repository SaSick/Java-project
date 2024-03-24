package com.shoesapp.product.service;

import com.shoesapp.product.dto.ProductDTO;
import com.shoesapp.product.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ProductService {

    Page<Product> getAllProducts(PageRequest of);

    ProductDTO addProduct(ProductDTO productDTO, Long productId);

    String deleteProduct(Long productId);

    ProductDTO updateProduct(Long productId, ProductDTO productDTO);

    ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException;



}
