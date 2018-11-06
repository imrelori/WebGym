package com.webbuilders.webgym.services;

import com.webbuilders.webgym.domain.Product;
import com.webbuilders.webgym.domain.User;
import com.webbuilders.webgym.repositories.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    UserServiceImpl userService;
    User user;
    Long userId = 1L;

    @Mock
    UserRepository userRepository;

    @Before
    public void setUp() throws Exception {

        MockitoAnnotations.initMocks(this);

        userService = new UserServiceImpl(userRepository);
        user = new User();
    }

    @Test
    public void getUsers() {

        List<User> userData = new ArrayList<>();
        userData.add(user);

        when(userService.getUsers()).thenReturn(userData);

        List<User> users = userService.getUsers();

        assertEquals(users.size(), 1);
        assertTrue(users.contains(user));

        verify(userRepository, times(1)).findAll();
    }

    @Test
    public void getUsersByID() throws Exception {

        user.setId(userId);
        Optional<User> userOptional = Optional.of(user)
                .filter(e -> e.getId().equals(userId));

        when(userService.getUserByID(userId)).thenReturn(userOptional.get());
        User result = userService.getUserByID(userId);
        assertEquals(result, user);

        verify(userRepository, times(1)).findById(userId);
    }

    @Test(expected = RuntimeException.class)
    public void getUsersByIDNoUser() throws Exception {

        user.setId(2L);
        Optional<User> userOptional = Optional.of(user)
                .filter(e -> e.getId().equals(userId));

        when(userService.getUserByID(userId)).thenReturn(userOptional.get());
        userService.getUserByID(userId);

        verify(userRepository, times(1)).findById(userId);
    }
}
