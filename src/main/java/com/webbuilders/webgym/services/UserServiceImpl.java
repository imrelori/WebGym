package com.webbuilders.webgym.services;

import com.webbuilders.webgym.domain.User;
import com.webbuilders.webgym.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<User> getUsers() {

        List<User> userList = new ArrayList<>();
        userRepository.findAll().iterator().forEachRemaining(userList::add);

        return userList;
    }

    @Override
    public User getUserByID(Long id) {

        Optional<User> userOptional = userRepository.findById(id);

        if (!userOptional.isPresent()) {
            throw new RuntimeException("Product not found with id:" + id);
        }

        return userOptional.get();
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> userOptional = userRepository.findByEmail(email);

        if (!userOptional.isPresent()) {
            throw new RuntimeException("There is no user with the email: " + email);
        }

        return userOptional.get();
    }

    @Override
    public User findByConfirmationToken(String confirmationToken) {
        Optional<User> userOptional = userRepository.findByConfirmationToken(confirmationToken);

        if (!userOptional.isPresent()) {
            throw new RuntimeException("There is no confirmationToken: " + confirmationToken);
        }

        return userOptional.get();
    }

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }
}
