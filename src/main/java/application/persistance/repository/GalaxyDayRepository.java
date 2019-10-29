package application.persistance.repository;


import application.persistance.GalaxyDayPK;
import application.persistance.GalaxyDayTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GalaxyDayRepository extends JpaRepository<GalaxyDayTable, GalaxyDayPK> {

}
