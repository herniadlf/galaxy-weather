package application.persistance.repository;


import application.persistance.GalaxyDayTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GalaxyDayRepository extends JpaRepository<GalaxyDayTable, Long> {

    List<GalaxyDayTable> findAllByGalaxyWeather(String galaxyWeather);

}
