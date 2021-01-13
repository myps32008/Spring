/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viettel.CQRSES.Events.Kafka.Consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.receiver.ReceiverOffset;
import reactor.kafka.receiver.ReceiverOptions;
import reactor.kafka.receiver.ReceiverRecord;
import viettel.CQRSES.Domain.Entities.User;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
@Configuration
public class UserConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserConsumer.class);
    @Value(value = "${message.topic.name}")
    private String topicName;
    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;
    private final ReceiverOptions<String, String> receiverOptions;
    public UserConsumer() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.CLIENT_ID_CONFIG, "sample-consumer");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "USER_CONSUMER");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
//        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        receiverOptions = ReceiverOptions.create(props);
    }
    @Bean
    public Flux<ReceiverRecord<String, String>> consumerUserMessages() {
        ReceiverOptions<String, String> options = receiverOptions.subscription(Collections.singleton(topicName))
                .addAssignListener(partitions -> LOGGER.debug("onPartitionsAssigned {}", partitions))
                .addRevokeListener(partitions -> LOGGER.debug("onPartitionsRevoked {}", partitions));
        return KafkaReceiver.create(options).receive();
    }

}
