package com.dan1yal.inventory_service.mapper;

import com.dan1yal.inventory_service.dto.ProductDto;
import com.dan1yal.inventory_service.model.Product;
import com.dan1yal.inventory_service.request.ProductCreateRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toProductDto(Product product);
    Product toProduct(ProductCreateRequest productCreateRequest);
    List<ProductDto> toProductDtoList(List<Product> products);
}
