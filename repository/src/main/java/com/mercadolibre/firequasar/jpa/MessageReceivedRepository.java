package com.mercadolibre.firequasar.jpa;

import com.mercadolibre.firequasar.model.MessageReceived;
import com.mercadolibre.firequasar.model.Satellite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageReceivedRepository extends JpaRepository<MessageReceived, Long> {

    public MessageReceived findBySatellite(Satellite satellite);
}
