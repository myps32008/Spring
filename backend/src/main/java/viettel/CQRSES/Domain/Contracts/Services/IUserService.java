package viettel.CQRSES.Domain.Contracts.Services;

import viettel.CQRSES.Domain.Entities.User;
import viettel.CQRSES.Events.IEventListener;

import java.util.Optional;

public interface IUserService {
    User handleInsert (User user);
    boolean handleDelete(String id);
    Iterable<User> getAll();
    Optional<User> findById (int id);
    void register(IEventListener listener);
}
