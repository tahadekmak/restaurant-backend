package cme.restaurantbackend.repository;

import cme.restaurantbackend.dto.VisitBrief;
import cme.restaurantbackend.model.Visit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    @Query("select new cme.restaurantbackend.dto.VisitBrief(v.id, v.restaurant.name, v.date) from Visit v join v.restaurant r  where v.person.id = :personId order by v.id")
    List<VisitBrief> findByPersonId(@Param("personId") Long personId);
}
