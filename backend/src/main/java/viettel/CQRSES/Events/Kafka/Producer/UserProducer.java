/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viettel.CQRSES.Events.Kafka.Producer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import viettel.CQRSES.Domain.Contracts.Producers.IUserProducer;
import viettel.CQRSES.Domain.Entities.User;
import viettel.CQRSES.Events.BaseEvent;
@Service
public class UserProducer implements IUserProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserProducer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;
    @Value(value = "${message.topic.name}")
    private String topicName;

    public UserProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }
    @Override
    public void sendMessage(BaseEvent eventUser) {
        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(topicName, eventUser.getId(), toJson(eventUser.getValue()));

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                LOGGER.info("Success: " + result.getRecordMetadata());
            }

            @Override
            public void onFailure(Throwable ex) {
                LOGGER.info("Failure: " + ex.getMessage() + ex.getStackTrace());
            }
        });
    }
    private String toJson(Object object){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return null;
    }
}
