package org.example;

import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class MenuItemService {

    private final MenuItemRepository menuItemRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuItemService(MenuItemRepository menuItemRepository, RestaurantRepository restaurantRepository) {
        this.menuItemRepository = menuItemRepository;
        this.restaurantRepository = restaurantRepository;

    }
    public MenuItem createMenuItem(MenuItemRequest request) {

        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId()).orElseThrow(() ->
                new IllegalArgumentException("Restaurant with ID " + request.getRestaurantId() + " not found!"));

        if (request.getName() == null || request.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Item name cannot be empty!");
        }
        if (request.getPrice() <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0!");
        }
        MenuItem item = new MenuItem();
        item.setRestaurant(restaurant);
        item.setName(request.getName());
        item.setCategory(request.getCategory());
        item.setPrice(request.getPrice());
        item.setAvailable(request.isAvailable());

        return menuItemRepository.save(item);

    }
    public List<MenuItem> getMenuByRestaurant(int restaurantId) {
        return menuItemRepository.findByRestaurantId(restaurantId);

    }
    public List<MenuItem> getItemsByCategory(String category) {
        return  menuItemRepository.findByCategoryIgnoreCase(category);

    }
    public boolean deleteMenuItem(int id) {
        if (menuItemRepository.existsById(id)) {
            menuItemRepository.deleteById(id);
            return true;
        }
        return false;

    }
}
