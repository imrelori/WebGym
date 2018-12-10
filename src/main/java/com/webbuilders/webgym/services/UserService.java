package com.webbuilders.webgym.services;

import com.webbuilders.webgym.domain.User;

import java.util.List;

public interface UserService {

    /**
     * Get every {@link com.webbuilders.webgym.domain.User}
     * from the database
     *
     * @return every user
     */
    List<User> getUsers();

    /**
     * Get {@link com.webbuilders.webgym.domain.User}
     * by it's id
     *
     * @param id
     * @return the found user
     */
    User getUserByID(Long id);

    User findByEmail(String email);

    User findByConfirmationToken(String confirmationToken);

    void saveUser(User user);

}
