package cme.restaurantbackend.repository;

import cme.restaurantbackend.model.Restaurant;
import cme.restaurantbackend.model.RestaurantData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query(value = "SELECT * FROM restaurant WHERE lower(restaurant_name) like %:restaurantName%", nativeQuery = true)
    List<Restaurant> findByName(@Param("restaurantName") String restaurantName);

    @Query("select new cme.restaurantbackend.model.RestaurantData(r.name, r.averageCost, r.address, r.phoneNumber, r.image, c.name) from Restaurant r join r.category c  where c.id = :categoryId order by r.name")
    List<RestaurantData> findByCategoryId(@Param("categoryId") Long categoryId);
}