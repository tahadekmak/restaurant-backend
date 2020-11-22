package cme.restaurantbackend.repository;

import cme.restaurantbackend.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @Query("select new cme.restaurantbackend.model.Restaurant(r.id, r.name, r.averageCost, r.address, r.phoneNumber, r.image, r.category) from Restaurant r  where lower(r.name) like %:restaurantName% order by r.name")
    List<Restaurant> findByName(@Param("restaurantName") String restaurantName);

    @Query("select new cme.restaurantbackend.model.Restaurant(r.id, r.name, r.averageCost, r.address, r.phoneNumber, r.image, r.category) from Restaurant r join r.category c  where c.id = :categoryID order by r.name")
    List<Restaurant> findByCategoryId(@Param("categoryID") Long categoryID);
}