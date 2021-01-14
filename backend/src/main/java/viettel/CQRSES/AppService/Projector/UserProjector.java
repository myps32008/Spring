package viettel.CQRSES.AppService.Projector;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import viettel.CQRSES.Domain.Contracts.Projectors.IUserProjector;
import viettel.CQRSES.Domain.Contracts.RepoSlave.IUserRepoSlave;
import viettel.CQRSES.Domain.Entities.User;
import viettel.CQRSES.Events.BaseEvent;
import viettel.CQRSES.Events.IEventListener;
import viettel.CQRSES.Events.UserManagementCommand;

import java.util.*;

@Service
public class UserProjector implements IUserProjector {

    private final IUserRepoSlave userRepoSlave;
    private Map<String, User> temp = new HashMap<>();
    private static final Logger LOGGER = LoggerFactory.getLogger(UserProjector.class);
    private IEventListener listener;
    private final ObjectMapper mapper;
    public UserProjector(IUserRepoSlave userRepoSlave, ObjectMapper mapper) {
        this.userRepoSlave = userRepoSlave;
        this.mapper = mapper;
    }
    public void register(IEventListener listener){
        this.listener = listener;
    }
    @Override
    public Optional<User> findById(int id) {
        User user = temp.get(id);
        if (user != null) {
            return Optional.of(user);
        }
        return userRepoSlave.findById(id);
    }

    @Override
    public Iterable<User> getAll() {
        if (!temp.isEmpty()) {
            return temp.values();
        }
        return userRepoSlave.findAll();
    }
    public void onEvent(Iterable<User> event) {
        if (listener != null) {
            listener.onData(event);
        }
    }

    public void onComplete() {
        if (listener != null) {
            listener.processComplete();
        }
    }

    @KafkaListener(topics = "${message.topic.name}", groupId = "USER_SERVICE", containerFactory = "eventUser_CD_KLCF")
    private void listenerInsert(BaseEvent message) {
        try {
            switch (UserManagementCommand.valueOf(message.getId())){
                case INSERT_USER:
                    User user = mapper.convertValue(message.getValue(), User.class) ;
                    temp.put(String.valueOf(user.getId()), user);
                    break;
                case DELETE_USER:
                    temp.remove(message.getValue());
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            LOGGER.info(e.getMessage() + e.getStackTrace());
        }
        onEvent(getAll());
    }
}
