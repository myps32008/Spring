package practice.spring.viettel.Contract.RepoRead;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import practice.spring.viettel.Entity.User;


public interface IUserReadRepo extends JpaRepository<User, Integer> {
}
