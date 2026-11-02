package org.example;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();

    }
    public Restaurant getRestaurantById(int id) {
        return restaurantRepository.findById(id).orElse(null);

    }
    public Restaurant createRestaurant(Restaurant restaurant) {
        if (restaurant.getName() == null || restaurant.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Restaurant name cannot be empty.");

        } else if (restaurant.getRating() < 0.0 || restaurant.getRating() > 5.0) {
            throw new IllegalArgumentException("Rating must be between 0.0 and 5.0");

        } else if (restaurant.getMinimumOrderAmount() < 0) {
            throw new IllegalArgumentException("Minimum order amount cannot be negative");
        }
        return restaurantRepository.save(restaurant);
    }
    public Restaurant updateRestaurant(int id, Restaurant updateRestaurant) {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(id);

        if (optionalRestaurant.isPresent()) {
            Restaurant existingRestaurant = optionalRestaurant.get();

            existingRestaurant.setName(updateRestaurant.getName());
            existingRestaurant.setCuisineType(updateRestaurant.getCuisineType());
            existingRestaurant.setAddress(updateRestaurant.getAddress());
            existingRestaurant.setMinimumOrderAmount(updateRestaurant.getMinimumOrderAmount());
            existingRestaurant.setRating(updateRestaurant.getRating());

            return restaurantRepository.save(existingRestaurant);
        }
        return null;
    }
    public boolean deleteRestaurant(int id) {
        if (restaurantRepository.existsById(id)) {
            restaurantRepository.deleteById(id);
            return true;
        }
        return false;
    }
    public List<Restaurant> searchByCuisine(String cuisine) {
        return restaurantRepository.findByCuisineTypeIgnoreCase(cuisine);
    }
}
