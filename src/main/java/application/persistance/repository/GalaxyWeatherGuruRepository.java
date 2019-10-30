package application.persistance.repository;


import application.persistance.GalaxyWeatherGuruTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface GalaxyWeatherGuruRepository extends JpaRepository<GalaxyWeatherGuruTable, Long> {
}
