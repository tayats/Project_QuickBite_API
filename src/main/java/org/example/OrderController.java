package org.example;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")

public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;

    }
    @PostMapping
    public ResponseEntity<Order> placeOrder (@RequestBody OrderRequest request) {
        Order order = orderService.placeOrder(request);

        return new ResponseEntity<>(order, HttpStatus.CREATED);

    }
    @GetMapping
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();

    }
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable int id) {
        Order order = orderService.getOrderById(id);
        if (order != null) {
            return ResponseEntity.ok(order);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/restaurant/{restaurantId}")
    public List<Order> getOrderByRestaurant(@PathVariable int restaurantId) {
        return orderService.getOrdersByRestaurant(restaurantId);

    }
    @GetMapping("/status")
    public List<Order> getOrderByStatus(@RequestParam String status) {
        return orderService.getOrdersByStatus(status);

    }
    @PutMapping("/{id}")
    public Order updateOrder(@PathVariable int id, @RequestParam String status) {
        return orderService.updateOrderStatus(id, status);

    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST); //400 Bad Request
    }
}
