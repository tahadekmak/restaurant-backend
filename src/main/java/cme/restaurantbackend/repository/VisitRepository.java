package cme.restaurantbackend.repository;

import cme.restaurantbackend.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    @Query("select new cme.restaurantbackend.model.Visit(v.id, v.person, v.restaurant, v.date) from Visit v join v.restaurant r  where v.person.id = :personId order by v.id")
    List<Visit> findByPersonId(@Param("personId") Long personId);
}
