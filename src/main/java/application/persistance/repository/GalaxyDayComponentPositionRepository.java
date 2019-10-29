package application.persistance.repository;


import application.persistance.GalaxyDayComponentPositionPK;
import application.persistance.GalaxyDayComponentPositionTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GalaxyDayComponentPositionRepository extends JpaRepository<GalaxyDayComponentPositionTable, GalaxyDayComponentPositionPK> {

}
