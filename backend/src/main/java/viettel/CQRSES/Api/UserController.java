package viettel.CQRSES.Api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import viettel.CQRSES.Domain.Contracts.Services.IUserService;
import viettel.CQRSES.Domain.Entities.User;

@RestController
@RequestMapping(path="/api/users")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }
    @GetMapping(path="/all")
    public Iterable<User> getListUsers() {
        return null;
    }
    @GetMapping(path="/add")
    public boolean addUsers() {
        return true;
    }
}
