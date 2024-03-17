package com.shoesapp.product.service.Impl;

import com.shoesapp.product.dto.ProductDTO;
import com.shoesapp.product.entity.Product;
import com.shoesapp.exception.ResourceNotFoundException;
import com.shoesapp.product.file_service.FileService;
import com.shoesapp.product.repository.ProductRepository;
import com.shoesapp.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final FileService fileService;

    private final ModelMapper mapper;

    @Value("${project.image}")
    private String path;

    public Page<Product> getAllProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest);
    }


    public ProductDTO addProduct(ProductDTO productDTO) {
        Product product = mapper.map(productDTO, Product.class);
        productRepository.save(product);
        return mapper.map(product, ProductDTO.class);
    }

    public String deleteProduct(Long productId) {
        Product product = findById(productId);
        productRepository.delete(product);
        return "Product with ID: " + productId + " deleted successfully!";
    }


    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product productFromDB = findById(productId);
        productFromDB.setProductId(productId);
        productFromDB.setProductName(productDTO.getProductName());
        productFromDB.setImage(productDTO.getImage());
        productFromDB.setDescription(productDTO.getDescription());
        productFromDB.setQuantity(productDTO.getQuantity());
        productFromDB.setDiscount(productDTO.getDiscount());
        productFromDB.setPrice(productDTO.getPrice());
        productFromDB.setSpecialPrice(productDTO.getSpecialPrice());
        productRepository.save(productFromDB);
        return mapper.map(productFromDB, ProductDTO.class);
    }


    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        Product productFromDB = findById(productId);
        String filename = fileService.uploadImage(path, image);
        productFromDB.setImage(filename);
        productRepository.save(productFromDB);
        return mapper.map(productFromDB, ProductDTO.class);
    }

    private Product findById(Long productId){
        return productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + productId));
    }

}
