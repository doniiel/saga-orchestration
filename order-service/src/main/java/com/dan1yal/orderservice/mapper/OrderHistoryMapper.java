package com.dan1yal.orderservice.mapper;

import com.dan1yal.orderservice.dto.OrderHistoryDto;
import com.dan1yal.orderservice.model.OrderHistory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderHistoryMapper {
    OrderHistoryMapper INSTANCE = Mappers.getMapper(OrderHistoryMapper.class);

    OrderHistoryDto toDto(OrderHistory orderHistory);

    List<OrderHistoryDto> toDtoList(List<OrderHistory> orderHistories);
}
