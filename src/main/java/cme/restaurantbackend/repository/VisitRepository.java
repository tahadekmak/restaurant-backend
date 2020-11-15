package cme.restaurantbackend.repository;

import cme.restaurantbackend.model.Visit;
import cme.restaurantbackend.model.VisitData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Long> {

    @Query("select new cme.restaurantbackend.model.VisitData(v.id, r.name, v.date) from Restaurant r join r.visits v  where v.person.id = :personId order by v.id")
    List<VisitData> findByPersonId(@Param("personId") Long personId);
}
