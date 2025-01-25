package com.dan1yal.orderservice;

import com.dan1yal.orderservice.dto.OrderDto;
import com.dan1yal.orderservice.mapper.OrderMapper;
import com.dan1yal.orderservice.model.Order;
import com.dan1yal.orderservice.repository.OrderRepository;
import com.dan1yal.orderservice.request.OrderRequest;
import com.dan1yal.orderservice.service.OrderHistoryService;
import com.dan1yal.orderservice.service.impl.OrderServiceImpl;
import com.example.demo.events.order.OrderCreatedEvent;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;


@TestInstance(TestInstance.Lifecycle.PER_CLASS) // default
public class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private OrderMapper orderMapper;
    @Mock
    private OrderHistoryService orderHistoryService;
    @Mock
    private KafkaTemplate<String, Object> kafkaTemplate;

    @BeforeAll
    void init() {
        System.out.println("Starting Testing");
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @ParameterizedTest
    @MethodSource("provideOrderData")
    void createOrderSuccess(OrderRequest request, Order order, Order savedOrder, OrderDto orderDto, String expectedOrderId) {
        // Arrange
        when(orderMapper.toOrder(request)).thenReturn(order);
        when(orderRepository.save(order)).thenReturn(savedOrder);  // Ensure savedOrder is initialized properly
        when(orderMapper.toOrderDto(savedOrder)).thenReturn(orderDto);

        // Act
        OrderDto result = orderService.createOrder(request);

        // Assert
        assertThat(result)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(orderDto);

        verify(orderRepository, times(1)).save(order);
        verify(orderHistoryService, times(1)).createOrderHistory(expectedOrderId);
        ArgumentCaptor<OrderCreatedEvent> eventCaptor = ArgumentCaptor.forClass(OrderCreatedEvent.class);
        verify(kafkaTemplate, times(1)).send(any(), eventCaptor.capture());

        OrderCreatedEvent capturedEvent = eventCaptor.getValue();
        assertThat(capturedEvent.getOrderId()).isEqualTo(expectedOrderId);
    }

    static Stream<Arguments> provideOrderData() {
        return Stream.of(
                Arguments.of(
                        new OrderRequest("product1", "user1", 2, new BigDecimal("100.0")),
                        new Order("1", "product1", "user1", 2, new BigDecimal("100.0")),
                        new Order("1", "product1", "user1", 2, new BigDecimal("100.0")),
                        new OrderDto("1", "product1", "user1", 2, new BigDecimal("100.0")),
                        "1"
                ),
                Arguments.of(
                        new OrderRequest("product2", "user2", 3, new BigDecimal("200.0")),
                        new Order("2", "product2", "user2", 3, new BigDecimal("200.0")),
                        new Order("2", "product2", "user2", 3, new BigDecimal("200.0")),
                        new OrderDto("2", "product2", "user2", 3, new BigDecimal("200.0")),
                        "2"
                )
        );
    }

    @Test
    @DisplayName("Should return order by ID if exists")
    void getOrderByIdFound() {
        // Arrange
        String orderId = "1";
        Order order = new Order(orderId, "product1", "user1", 2, new BigDecimal("100.0"));
        OrderDto orderDto = new OrderDto(orderId, "product1", "user1", 2, new BigDecimal("100.0"));

        when(orderRepository.existsById(orderId)).thenReturn(true);
        when(orderRepository.findById(orderId)).thenReturn(Optional.of(order));
        when(orderMapper.toOrderDto(order)).thenReturn(orderDto);

        // Act
        OrderDto result = orderService.getOrderById(orderId);

        // Assert
        assertThat(result)
                .isNotNull()
                .isEqualTo(orderDto);
    }

    @Test
    @DisplayName("Should return null if order ID does not exist")
    void getOrderByIdNotFound() {
        // Arrange
        String orderId = "1";
        when(orderRepository.existsById(orderId)).thenReturn(false);

        // Act
        OrderDto result = orderService.getOrderById(orderId);

        // Assert
        assertThat(result).isNull();
    }

    @ParameterizedTest
    @ValueSource(strings = {"1", "2", "3"})
    @DisplayName("Should delete order if exists")
    void deleteOrderSuccess(String orderId) {
        // Arrange
        when(orderRepository.existsById(orderId)).thenReturn(true);

        // Act
        orderService.deleteOrder(orderId);

        // Assert
        verify(orderRepository, times(1)).deleteById(orderId);
    }

    @Test
    @DisplayName("Should cancel order successfully")
    void cancelOrderSuccess() {
        // Arrange
        String orderId = "1";

        // Act
        orderService.cancelOrder(orderId);

        // Assert
        verify(orderHistoryService, times(1)).updateOrderStatus(orderId, "CANCELED");
    }

    @Test
    @DisplayName("Should complete order successfully")
    void completeOrderSuccess() {
        // Arrange
        String orderId = "1";

        // Act
        orderService.completeOrder(orderId);

        // Assert
        verify(orderHistoryService, times(1)).updateOrderStatus(orderId, "COMPLETED");
    }

    @Nested
    @DisplayName("Tests for getAllOrders method")
    class GetAllOrdersTests {

        @Test
        @DisplayName("Should return all orders")
        void getAllOrdersSuccess() {
            // Arrange
            Order order = new Order("1", "product1", "user1", 2, new BigDecimal("100.0"));
            List<Order> mockOrders = List.of(order);
            when(orderRepository.findAll()).thenReturn(mockOrders);


            // Act
            List<OrderDto> orders= orderService.getAllOrders();

            // Assert
            assertEquals(1, orders.size());
            assertEquals("1", orders.get(0).getOrderId());
        }
    }
    @AfterEach
    void tearDown() {

    }

    @AfterAll
    void end() {

    }
}
