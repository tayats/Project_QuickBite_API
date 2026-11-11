package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

    @InjectMocks
    private OrderService orderService;

    @Test
    public void shouldApplyFreeDeliveryWhenSubtotalIs30OrMore() {
        // 1. ARRANGE: Create a fake restaurant with a $15 minimum
        Restaurant fakeRestaurant = new Restaurant(1, "Burger Palace", "Fast Food", "Kadikoy", 15.0, 4.8);

        // Tell the mock: "When findById(1) is called, pretend you found fakeRestaurant!"
        when(restaurantRepository.findById(1)).thenReturn(Optional.of(fakeRestaurant));

        // Tell orderRepository: "When save() is called, just return whatever order was passed into it"
        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Create an incoming request for $45.00
        OrderRequest request = new OrderRequest(1, "Alice", "Moda Cad.", "3x Burgers", 45.0);

        // 2. ACT: Place the order
        Order createdOrder = orderService.placeOrder(request);

        // 3. ASSERT: Verify the business rules!
        assertNotNull(createdOrder);
        assertEquals(0.0, createdOrder.getDeliveryFee(), "Delivery fee should be $0.00 for orders $30+");
        assertEquals(45.0, createdOrder.getTotalAmount(), "Total should equal subtotal when delivery is free");
        assertEquals("PREPARING", createdOrder.getStatus());

    }

    @Test
    public void shouldApplyStandardDeliveryFeeWhenSubtotalIsUnder30() {

        Restaurant fakeRestaurant = new Restaurant(2, "Pizza Palace", "Fast Food", "Beyoglu", 15.0, 4.7);

        when(restaurantRepository.findById(2)).thenReturn(Optional.of(fakeRestaurant));

        when(orderRepository.save(any(Order.class))).thenAnswer(invocation -> invocation.getArgument(0));

        OrderRequest request = new OrderRequest(2, "Jack", "Istiklal Cad.", "2x Pizza", 24.0);

        Order createdOrder = orderService.placeOrder(request);

        assertNotNull(createdOrder);
        assertEquals(3.50, createdOrder.getDeliveryFee(), "Delivery fee should be $3.50 for under $30");
        assertEquals(27.50, createdOrder.getTotalAmount(), "Total should be subtotal + $3.50 delivery fee");
        assertEquals("PREPARING", createdOrder.getStatus());

    }

    @Test
    public void shouldThrowExceptionWhenSubtotalIsBelowMinimumOrderAmount() {
        // 1. ARRANGE: Restaurant has minimum of $15.0
        Restaurant fakeRestaurant = new Restaurant(1, "Burger Palace", "Fast Food", "Kadikoy", 15.0, 4.8);
        when(restaurantRepository.findById(1)).thenReturn(Optional.of(fakeRestaurant));

        // Subtotal is $10.00 (below $15 minimum!)
        OrderRequest request = new OrderRequest(1, "Charlie", "Moda Cad.", "Fries", 10.0);

        // 2. ACT & ASSERT: Verify that placeOrder throws IllegalArgumentException!
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            orderService.placeOrder(request);
        });

        // Optional: verify the error message contains the expected text
        assertTrue(exception.getMessage().contains("below"));

    }
}