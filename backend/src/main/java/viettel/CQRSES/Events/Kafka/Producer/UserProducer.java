/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viettel.CQRSES.Events.Kafka.Producer;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderOptions;
import reactor.kafka.sender.SenderRecord;
import reactor.kafka.sender.SenderResult;
import viettel.CQRSES.Domain.Contracts.Producers.IUserProducer;
import viettel.CQRSES.Domain.Entities.User;
import viettel.CQRSES.Events.BaseEvent;
import viettel.CQRSES.Events.BaseProducer;

import java.util.HashMap;
import java.util.Map;
@Service
public class UserProducer extends BaseProducer implements IUserProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserProducer.class);

    @Value(value = "${message.topic.name}")
    private String topicName;
    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;
    private final KafkaSender<String, String> sender;
    public UserProducer() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ProducerConfig.CLIENT_ID_CONFIG, topicName);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        SenderOptions<String, String> senderOptions = SenderOptions.create(props);
        sender = KafkaSender.create(senderOptions);
    }
    @Override
    public void sendMessage(BaseEvent eventUser) {
        String payload = toJson(eventUser.getValue());
        sender.send(Mono.just(SenderRecord.create(
                new ProducerRecord<>(topicName, eventUser.getId(), payload), 1))).subscribe();
    }
}
