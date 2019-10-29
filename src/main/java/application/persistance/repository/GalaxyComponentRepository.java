package application.persistance.repository;


import application.persistance.GalaxyComponentTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GalaxyComponentRepository extends JpaRepository<GalaxyComponentTable, Long> {

}
