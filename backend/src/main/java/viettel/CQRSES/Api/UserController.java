package viettel.CQRSES.Api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.bytebuddy.utility.RandomString;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import viettel.CQRSES.Domain.Contracts.Services.IUserService;
import viettel.CQRSES.Domain.Entities.User;
import viettel.CQRSES.Events.IEventListener;

@RestController
@RequestMapping(path="/api/users")
public class UserController {
    private final IUserService userService;
    private Flux<Iterable<User>> bridgeAllData;
    private final ObjectMapper mapper;
    public UserController(IUserService userService, ObjectMapper mapper) {
        this.userService = userService;
        this.mapper = mapper;
        bridgeAllData = createBridge().publish().autoConnect().cache(10).log();
    }
    @GetMapping(path="/all")
    public Mono<Iterable<User>> getListUsers() {
        return Mono.just(userService.getAll());
    }


    @CrossOrigin
    @SendTo("/topic/messages")
    public Flux<String> stream() {
        return this.bridgeAllData.map(event -> {
            try {
                return mapper.writeValueAsString(event);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return null;
        });
    }
    @GetMapping(path="/add")
    public Mono<User> addUsers() {
        User user = new User();
        user.setUserName(RandomString.make(10));
        return Mono.just(userService.handleInsert(user));
    }
    @GetMapping(path="/delete/{id}")
    public Mono<Boolean> delete(@PathVariable("id") String id) {
        return Mono.just(userService.handleDelete(id));
    }
    private Flux<Iterable<User>> createBridge() {
        Flux<Iterable<User>> bridge = Flux.create(sink -> {
            userService.register(new IEventListener<Iterable<User>>() {

                @Override
                public void processComplete() {
                    sink.complete();
                }

                @Override
                public void onData(Iterable<User> data) {
                    sink.next(data);
                }
            });
        });
        return bridge;
    }
}
