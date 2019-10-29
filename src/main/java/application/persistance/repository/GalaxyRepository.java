package application.persistance.repository;


import application.persistance.GalaxyTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GalaxyRepository extends JpaRepository<GalaxyTable, Long> {
}
