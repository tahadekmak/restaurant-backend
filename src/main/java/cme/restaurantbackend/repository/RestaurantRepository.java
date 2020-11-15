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

    @Query("select new cme.restaurantbackend.model.RestaurantData(r.id, r.name, r.averageCost, r.address, r.phoneNumber, r.image, c.name) from Restaurant r join r.category c  where lower(r.name) like %:restaurantName% order by r.name")
    List<RestaurantData> findByName(@Param("restaurantName") String restaurantName);

    @Query("select new cme.restaurantbackend.model.RestaurantData(r.id, r.name, r.averageCost, r.address, r.phoneNumber, r.image, c.name) from Restaurant r join r.category c  where c.id = :categoryID order by r.name")
    List<RestaurantData> findByCategoryId(@Param("categoryID") Long categoryID);
}