package viettel.CQRSES.Api;

import net.bytebuddy.utility.RandomString;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;
import viettel.CQRSES.Domain.Contracts.Services.IUserService;
import viettel.CQRSES.Domain.Entities.User;

import java.util.concurrent.Executors;

@RestController
@RequestMapping(path="/api/users")
public class UserController {
    private final IUserService userService;
    public UserController(IUserService userService) {
        this.userService = userService;
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
}
