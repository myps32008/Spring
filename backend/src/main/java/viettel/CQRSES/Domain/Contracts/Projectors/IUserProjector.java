package viettel.CQRSES.Domain.Contracts.Projectors;

import viettel.CQRSES.Domain.Entities.User;

import java.util.Optional;

public interface IUserProjector {
    Iterable<User> getAll();
    Optional<User> findById(int id);
}
