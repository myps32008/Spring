package viettel.CQRSES.Domain.Contracts.Services;

import viettel.CQRSES.Domain.Entities.User;

import java.util.Optional;

public interface IUserService {
    User handleInsert (User user);
    boolean handleDelete(int id);
    Iterable<User> getAll();
    Optional<User> findById (int id);
}
