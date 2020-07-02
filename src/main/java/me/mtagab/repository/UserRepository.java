package me.mtagab.repository;

import me.mtagab.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
  Optional<User> findByUsername(String username);
  List<User> findAllByOrderByFirstnameAsc();
  Boolean existsByUsername(String username);
  Boolean existsByEmail(String email);
}
