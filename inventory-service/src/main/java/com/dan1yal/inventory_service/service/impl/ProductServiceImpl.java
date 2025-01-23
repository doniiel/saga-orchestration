package com.dan1yal.inventory_service.service.impl;

import com.dan1yal.inventory_service.dto.ProductDto;
import com.dan1yal.inventory_service.exc.ProductInsufficientQuantityException;
import com.dan1yal.inventory_service.mapper.ProductMapper;
import com.dan1yal.inventory_service.model.Product;
import com.dan1yal.inventory_service.repository.ProductRepository;
import com.dan1yal.inventory_service.request.ProductCreateRequest;
import com.dan1yal.inventory_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;
    private final ProductMapper productMapper;

    @Override
    public void reserveProduct(String productId, int quantity) {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product with id " + productId + " not found"));
        if (product.getQuantity() < quantity) {
            throw new ProductInsufficientQuantityException(productId, quantity);
        }
        product.setQuantity(product.getQuantity() - quantity);
        repository.save(product);
    }

    @Override
    public boolean cancelReservation(String productId, int quantity) {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Product with id " + productId + " not found"));
        product.setQuantity(product.getQuantity() + quantity);
        repository.save(product);
        return true;
    }

    @Override
    public List<ProductDto> getProducts() {
        List<Product> products = repository.findAll();
        return productMapper.toProductDtoList(products);
    }

    @Override
    public ProductDto createProduct(ProductCreateRequest request) {
        Product product = productMapper.toProduct(request);
        Product savedProduct = repository.save(product);
        return productMapper.toProductDto(savedProduct);
    }
}
