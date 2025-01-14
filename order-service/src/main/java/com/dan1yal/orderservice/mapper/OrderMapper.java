package com.dan1yal.orderservice.mapper;

import com.dan1yal.orderservice.dto.OrderDto;
import com.dan1yal.orderservice.model.Order;
import com.dan1yal.orderservice.request.OrderRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderMapper INSTANCE = Mappers.getMapper(OrderMapper.class);

    @Mapping(target = "orderId", ignore = true)
    Order toOrder(OrderRequest request);

    OrderDto toOrderDto(Order order);

    List<OrderDto> toOrderDtoList(List<Order> orders);

}
