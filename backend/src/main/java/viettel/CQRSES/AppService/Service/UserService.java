package viettel.CQRSES.AppService.Service;

import org.springframework.stereotype.Service;
import viettel.CQRSES.Domain.Contracts.Aggregates.IUserAggregate;
import viettel.CQRSES.Domain.Contracts.Projectors.IUserProjector;
import viettel.CQRSES.Domain.Contracts.Services.IUserService;
import viettel.CQRSES.Domain.Entities.User;

@Service
public class UserService implements IUserService {
    private final IUserAggregate userAggregate;
    private final IUserProjector userProjector;
    public UserService(IUserAggregate userAggregate, IUserProjector userProjector) {
        this.userAggregate = userAggregate;
        this.userProjector = userProjector;
    }
    public User handleInsert(User user){
        return userAggregate.handleCreateUser(user);
    }
    public boolean handleDelete(int id) {
        return true;
    }
}
