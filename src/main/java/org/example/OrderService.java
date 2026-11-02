package org.example;

import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class OrderService {

    private final OrderRepository orderRepository;
    private final RestaurantRepository restaurantRepository;

    public OrderService(OrderRepository orderRepository, RestaurantRepository restaurantRepository) {
        this.orderRepository = orderRepository;
        this.restaurantRepository = restaurantRepository;

    }
    public Order placeOrder(OrderRequest request) {

        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId()).orElseThrow(() ->
                new IllegalArgumentException("Restaurant with ID " + request.getRestaurantId() + " not found!"));

        if (request.getSubtotal() < restaurant.getMinimumOrderAmount()) {
            throw new IllegalArgumentException("Order subtotal ($" + request.getSubtotal() + ") " +
                    "is below the restaurant's minimum of $" + restaurant.getMinimumOrderAmount());
        }
        double deliveryFee;
        if (request.getSubtotal() >= 30.0) {
            deliveryFee = 0.0;
        } else {
            deliveryFee = 3.50;
        }

        double totalAmount = request.getSubtotal() + deliveryFee;

        Order order = new Order();

        order.setRestaurant(restaurant);
        order.setCustomerName(request.getCustomerName());
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setItemsSummary(request.getItemsSummary());
        order.setSubtotal(request.getSubtotal());
        order.setDeliveryFee(deliveryFee);
        order.setTotalAmount(totalAmount);
        order.setStatus("PREPARING");

        return orderRepository.save(order);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order getOrderById(int id) {
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> getOrdersByRestaurant(int restaurantId) {
        return orderRepository.findByRestaurantId(restaurantId);
    }

    public List<Order> getOrdersByStatus(String status) {
        return orderRepository.findByStatusIgnoreCase(status);
    }

    public Order updateOrderStatus(int id, String newStatus) {
        Order existingOrder = orderRepository.findById(id).orElse(null);
        if (existingOrder != null) {
            existingOrder.setStatus(newStatus.toUpperCase());
            return orderRepository.save(existingOrder);
        }
        return null;
    }
}
