package com.shoesapp.product.service.serviceImpl;

import com.shoesapp.product.dto.ProductDTO;
import com.shoesapp.product.entity.Product;
import com.shoesapp.product.exception.ResourceNotFoundException;
import com.shoesapp.product.repositories.ProductRepository;
import com.shoesapp.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Value("${project.image}")
    private String path;

    public Page<Product> findAll(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest);

    }


    public ProductDTO createProduct(ProductDTO productDTO) {
        Product product = convertToEntity(productDTO);
        productRepository.save(product);
        return convertToDTO(product);
    }

    public String deleteProduct(Long productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Resource not found"));
        productRepository.delete(product);
        return "Product with ID: " + productId + " deleted successfully!";

    }



    private Product convertToEntity(ProductDTO request){
        Product product = new Product();
        product.setProductName(request.getProductName());
        product.setImage(request.getImage());
        product.setDescription(request.getDescription());
        product.setQuantity(request.getQuantity());
        product.setDiscount(request.getDiscount());
        product.setPrice(request.getPrice());
        product.setSpecialPrice(request.getSpecialPrice());
        return product;
    }

    private ProductDTO convertToDTO(Product product){
        ProductDTO response = new ProductDTO();
        response.setProductName(product.getProductName());
        response.setImage(product.getImage());
        response.setDescription(product.getDescription());
        response.setQuantity(product.getQuantity());
        response.setDiscount(product.getDiscount());
        response.setPrice(product.getPrice());
        response.setSpecialPrice(response.getSpecialPrice());
        return response;
    }


}
