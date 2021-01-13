package viettel.CQRSES.Api;

import net.bytebuddy.utility.RandomString;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import viettel.CQRSES.Domain.Contracts.Services.IUserService;
import viettel.CQRSES.Domain.Entities.User;
import viettel.CQRSES.Events.IEventListener;

import java.util.concurrent.Executors;

@RestController
@RequestMapping(path="/api/users")
public class UserController {
    private final IUserService userService;
    private Flux<Iterable<User>> bridgeAllData;
    public UserController(IUserService userService) {
        this.userService = userService;
        bridgeAllData = createBridge().publish().autoConnect().cache(10).log();
    }
    @GetMapping(path="/all")
    public Mono<Iterable<User>> getListUsers() {
        return Mono.just(userService.getAll());
    }
    @GetMapping(path="/add")
    public Mono<User> addUsers() {
        User user = new User();
        user.setUserName(RandomString.make(10));
        return Mono.just(userService.handleInsert(user));
    }
    private Flux<Iterable<User>> createBridge() {
        Flux<Iterable<User>> bridge = Flux.create(sink -> { // (2)
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
