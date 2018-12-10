package com.webbuilders.webgym.repositories;

import com.webbuilders.webgym.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByRole(String role);

    Optional<User> findByEmail(String email);

    Optional<User> findByConfirmationToken(String confirmationToken);

}
