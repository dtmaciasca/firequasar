package com.mercadolibre.firequasar.service.test.unitary;

import com.mercadolibre.firequasar.jpa.SatelliteRepository;
import com.mercadolibre.firequasar.model.PositionMother;
import com.mercadolibre.firequasar.model.Satellite;
import com.mercadolibre.firequasar.model.SatelliteMother;
import com.mercadolibre.firequasar.service.SatelliteService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class SatelliteServiceTest {

    @Mock
    SatelliteRepository satelliteRepository;

    SatelliteService satelliteService;

    @BeforeEach
    public void beforeEach(){
        satelliteService = new SatelliteService(satelliteRepository);
    }

    @Nested
    @DisplayName("Pruebas unitarias para encontrar posicion por nombre")
    class FindPositionByName {

        String[] names;
        double[][] positions;

        @Test
        void findPositionByNameSuccess(){

            givenNames();
            giveFindPositionByNameSuccess();
            whenFindPositionByName();
            thenPositionsAreCorrect();

        }

        private void thenPositionsAreCorrect() {
            Assertions.assertNotNull(positions);
            Assertions.assertEquals(3, positions.length);
            Assertions.assertArrayEquals(new double[][]{{-500,-200},{100,-100},{500,100}}, positions);
        }

        private void whenFindPositionByName() {
            positions = satelliteService.findPositionByName(names);
        }

        private void givenNames(){
            names = SatelliteMother.all().stream().map(Satellite::getName).toArray(String[]::new);
        }

        private void giveFindPositionByNameSuccess(){
            given(satelliteRepository.findPositionByName(names))
                    .willReturn(PositionMother.allPositionSatellites());
        }
    }

    @Nested
    @DisplayName("Pruebas unitarias para encontrar satelite por nombre")
    class FindByName {

        String name;
        Satellite satellite;

        @Test
        void findByNameSuccess(){

            givenName();
            giveFindByNameSuccess();
            whenFindByName();
            thenSatelliteIsCorrect();

        }

        @Test
        void findByNameWhenNotFound(){

            givenNameSatelliteNotExist();
            giveFindByNameSatelliteIsNull();
            thenShowNotFoundSatelliteException();

        }

        @Test
        void findByNameWhenFailed(){

            givenNameSatelliteNotExist();
            giveFindByNameSatelliteFailed();
            thenShowNotFoundSatelliteException();

        }

        private void thenSatelliteIsCorrect() {
            Assertions.assertNotNull(satellite);
            Assertions.assertEquals("kenobi", satellite.getName());
        }

        private void whenFindByName() {
            satellite = satelliteService.findByName(name);
        }

        private void givenName(){
            name = "kenobi";
        }

        private void givenNameSatelliteNotExist(){
            name = "satellite";
        }

        private void giveFindByNameSuccess(){
            given(satelliteRepository.findByName(name))
                    .willReturn(SatelliteMother.satelliteKenobi());
        }

        private void giveFindByNameSatelliteIsNull(){
            given(satelliteRepository.findByName(name))
                    .willReturn(null);
        }

        private void giveFindByNameSatelliteFailed(){
            given(satelliteRepository.findByName(name))
                    .willThrow(SatelliteRepository.NotFoundSatelliteException.class);
        }

        private void thenShowNotFoundSatelliteException(){
            Assertions.assertThrows(SatelliteRepository.NotFoundSatelliteException.class, this::whenFindByName);
        }
    }
}
