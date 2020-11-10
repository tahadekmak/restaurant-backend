package cme.restaurantbackend.repository;

import cme.restaurantbackend.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query(value = "SELECT * FROM restaurant WHERE restaurant_name like %:restaurantName%", nativeQuery = true)
    List<Restaurant> findByName(@Param("restaurantName") String restaurantName);

    @Query(value = "SELECT * FROM restaurant WHERE restaurant_category like %:restaurantCategory%", nativeQuery = true)
    List<Restaurant> findByCategory(@Param("restaurantCategory") String restaurantCategory);
}