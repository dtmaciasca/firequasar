package com.mercadolibre.firequasar.jpa;

import com.mercadolibre.firequasar.model.Position;
import com.mercadolibre.firequasar.model.Satellite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SatelliteRepository extends JpaRepository<Satellite, Long> {

    Satellite findByName(String name);

    @Query("SELECT s.position FROM Satellite s WHERE s.name in (?1)")
    List<Position> findPositionByName(String[] name);

    class NotFoundSatelliteException extends RuntimeException {
        public NotFoundSatelliteException(String error) {
            super(error);
        }
    }
}
