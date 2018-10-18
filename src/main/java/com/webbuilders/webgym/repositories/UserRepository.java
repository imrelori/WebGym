package com.webbuilders.webgym.repositories;

import com.webbuilders.webgym.domain.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
}
