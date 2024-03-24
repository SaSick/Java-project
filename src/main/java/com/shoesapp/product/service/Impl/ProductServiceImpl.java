package com.shoesapp.product.service.impl;

import com.shoesapp.category.entity.Category;
import com.shoesapp.category.repostirory.CategoryRepository;
import com.shoesapp.exception.APIException;
import com.shoesapp.exception.ResourceNotFoundException;
import com.shoesapp.product.dto.ProductDTO;
import com.shoesapp.product.entity.Product;
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
import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final FileService fileService;
    private final CategoryRepository categoryRepository;


    private final ModelMapper mapper;

    @Value("${project.image}")
    private String path;

    public Page<Product> getAllProducts(PageRequest pageRequest) {
        return productRepository.findAll(pageRequest);
    }


    public ProductDTO addProduct(ProductDTO productDTO, Long categoryId) {
        Product product = mapper.map(productDTO, Product.class);
        Category category = categoryRepository.findById(categoryId)
                        .orElseThrow(() -> new ResourceNotFoundException("Product with Id " + categoryId + " not found!"));

        boolean isProductNotPresent = true;

        List<Product> products = category.getProducts();
        for (Product value : products) {
            if (value.getProductName().equals(product.getProductName()) &&
                    value.getDescription().equals(product.getDescription())) {
                isProductNotPresent = false;
                break;

            }
        }

        if(isProductNotPresent){
            product.setImage("default.png");
            product.setCategory(category);

            double specialPrice = product.getPrice() - ((product.getDiscount() * 0.01) * product.getPrice());
            product.setSpecialPrice(specialPrice);

            productRepository.save(product);
            return mapper.map(product, ProductDTO.class);

        } else {
            throw new APIException("Product already exists!");
        }


    }

    public String deleteProduct(Long productId) {
        Product product = findById(productId);
        productRepository.delete(product);
        return "Product with ID: " + productId + " deleted successfully!";
    }


    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        Product productFromDB = findById(productId);

        if (productFromDB == null) {
            throw new APIException("Product not found with productId: " + productId);
        }

        productFromDB.setImage(productDTO.getImage());
        productFromDB.setProductId(productId);
        productFromDB.setCategory(productFromDB.getCategory());

        double specialPrice = productFromDB.getPrice() - ((productFromDB.getDiscount() * 0.01) * productFromDB.getPrice());
        productFromDB.setSpecialPrice(specialPrice);

        Product savedProduct = productRepository.save(productFromDB);


        return mapper.map(savedProduct, ProductDTO.class);


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
