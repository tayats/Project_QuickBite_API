package org.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RestaurantController.class)
public class RestaurantControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    public void shouldReturnAllRestaurantsWithStatus200() throws Exception {
        // 1. ARRANGE: Prepare sample restaurants
        Restaurant r1 = new Restaurant(1, "Burger Palace", "Fast Food", "Kadikoy", 15.0, 4.8);
        Restaurant r2 = new Restaurant(2, "Pizza Roma", "Italian", "Besiktas", 20.0, 4.9);
        List<Restaurant> mockList = Arrays.asList(r1, r2);

        when(restaurantService.getAllRestaurants()).thenReturn(mockList);

        // 2. ACT & 3. ASSERT: Simulate HTTP GET /api/restaurants
        mockMvc.perform(get("/api/restaurants"))
                .andExpect(status().isOk()) // Checks HTTP 200 OK
                .andExpect(jsonPath("$.size()").value(2)) // Checks array length is 2
                .andExpect(jsonPath("$[0].name").value("Burger Palace"))
                .andExpect(jsonPath("$[1].name").value("Pizza Roma"));
    }

    @Test
    public void shouldReturn404WhenRestaurantNotFound() throws Exception {
        // 1. ARRANGE: Service returns null when ID 999 is searched
        when(restaurantService.getRestaurantById(999)).thenReturn(null);

        // 2. ACT & 3. ASSERT: Expect HTTP 404 Not Found!
        mockMvc.perform(get("/api/restaurants/999"))
                .andExpect(status().isNotFound()); // Checks HTTP 404
    }
}