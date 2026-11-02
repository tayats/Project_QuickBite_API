package org.example;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu-items")

public class MenuItemController {

    private final MenuItemService menuItemService;

    public MenuItemController(MenuItemService menuItemService) {
        this.menuItemService = menuItemService;

    }
    @PostMapping
    public ResponseEntity<MenuItem> createMenuItem(@RequestBody MenuItemRequest request) {
        MenuItem menuItem = menuItemService.createMenuItem(request);

        return new ResponseEntity<>(menuItem, HttpStatus.CREATED);

    }
    @GetMapping("/restaurant/{restaurantId}")
    public List<MenuItem> getMenuByRestaurant(@PathVariable int restaurantId) {
        return menuItemService.getMenuByRestaurant(restaurantId);

    }
    @GetMapping("/category")
    public List<MenuItem>  getItemsByCategory(@RequestParam String category) {
        return menuItemService.getItemsByCategory(category);

    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable int id) {
        boolean deleted = menuItemService.deleteMenuItem(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleBadRequest(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
