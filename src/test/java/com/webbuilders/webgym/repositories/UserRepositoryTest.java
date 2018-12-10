package com.webbuilders.webgym.repositories;

import com.webbuilders.webgym.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findByRole() {

        User user = null;

        assertNull(user);
    }

}