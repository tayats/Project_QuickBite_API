package org.example;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {

    List<MenuItem> findByRestaurantId(int restaurantId);

    List<MenuItem> findByCategoryIgnoreCase(String category);
}
