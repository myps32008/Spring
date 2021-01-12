/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viettel.CQRSES.Events.Kafka.Consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.kafka.annotation.KafkaListener;
import viettel.CQRSES.Domain.Entities.User;

import java.util.HashMap;

public class UserConsumer {
    private final HashMap<String, User> store;

    public UserConsumer(HashMap<String, User> store) {
        this.store = store;
    }


}
