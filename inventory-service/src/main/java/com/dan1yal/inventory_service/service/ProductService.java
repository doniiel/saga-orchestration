package com.dan1yal.inventory_service.service;

import com.dan1yal.inventory_service.dto.ProductDto;
import com.dan1yal.inventory_service.request.ProductCreateRequest;

import java.util.List;

public interface ProductService {
    void reserveProduct(String productId, int quantity);
    List<ProductDto> getProducts();
    ProductDto createProduct(ProductCreateRequest request);
}
