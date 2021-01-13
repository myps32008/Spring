package viettel.CQRSES.AppService.Projector;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.kafka.receiver.ReceiverRecord;
import viettel.CQRSES.Domain.Contracts.Projectors.IUserProjector;
import viettel.CQRSES.Domain.Contracts.RepoSlave.IUserRepoSlave;
import viettel.CQRSES.Domain.Entities.User;
import viettel.CQRSES.Events.UserManagementCommand;

import java.util.*;

@Service
public class UserProjector implements IUserProjector {

    private final IUserRepoSlave userRepoSlave;
    private Map<String, User> temp = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(UserProjector.class);
    private final Flux<ReceiverRecord<String, String>> consumerUserMessages;

    public UserProjector(IUserRepoSlave userRepoSlave,
            Flux<ReceiverRecord<String, String>> consumerUserMessages) {
        this.userRepoSlave = userRepoSlave;
        this.consumerUserMessages = consumerUserMessages;
        consumerUserMessages.subscribe(record -> {
            doWork(record);
        });
    }

    public Optional<User> findById(int id) {
        User user = temp.get(id);
        if (user != null) {
            return Optional.of(user);
        }
        return userRepoSlave.findById(id);
    }

    public Iterable<User> getAll() {
        if (!temp.isEmpty()) {
            return temp.values();
        }
        return userRepoSlave.findAll();
    }

    private void doWork(ReceiverRecord<String, String> record) {
        ObjectMapper mapper = new ObjectMapper();
        if (record.key().equals(UserManagementCommand.INSERT_USER.toString())) {
            try {
                User user = mapper.readValue(record.value(), User.class);
                temp.put(String.valueOf(user.getId()), user);
            } catch (Exception e) {
                LOGGER.info(e.getMessage() + e.getStackTrace());
            }
        }
        if (record.key().equals(UserManagementCommand.DELETE_USER.toString())) {
            temp.remove(record.value());
        }
    }

//    @KafkaListener(topics = "${message.topic.name}", groupId = "USER_SERVICE", containerFactory = "userKLCF")
//    private void listenInsert(User user) {
//        ObjectMapper mapper = new ObjectMapper();
//        try {
////            User user = mapper.readValue(message, User.class);
//            temp.put(String.valueOf(user.getId()), user);
//        } catch (Exception e) {
//            LOGGER.info(e.getMessage() + e.getStackTrace());
//        }
//    }
//    @KafkaListener(topics = "${message.topic.name}", groupId = "USER_SERVICE", containerFactory = "filterUserDeleteKLCF")
//    private void listenDelete(String message) {
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            User user = mapper.readValue(message, User.class);
//            temp.remove(user.getId());
//        } catch (Exception e) {
//            LOGGER.info(e.getMessage() + e.getStackTrace());
//        }
//    }
}
