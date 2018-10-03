package com.example.demo.user;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class UserServiceTest {
    @Mock
    private UserRepository repository;

    @Spy
    @InjectMocks
    private UserService service;

    private User user;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
        user = new User();
        user.setUsername("username");
        user.setName("name");
        user.setEmail("email");
    }

    @Test
    public void testCreateUser() throws Exception {
        service.createUser(user);

        verify(repository, times(1)).save(user);
    }

    @Test(expected = BadRequestException.class)
    public void testCreateUserShouldThrowErrorIfUsernameExists() throws Exception {
        when(repository.findById(any())).thenReturn(Optional.of(user));
        service.createUser(user);
    }

    @Test
    public void testGetUser() throws Exception {
        when(repository.findById(any())).thenReturn(Optional.of(user));
        User returnedUser = service.getUser(user.getUsername());

        verify(repository, times(1)).findById(user.getUsername());
        assertEquals(user, returnedUser);
    }

    @Test(expected = NotFoundException.class)
    public void testGetUserShouldThrowErrorIfUsernameNotExists() throws Exception {
        User returnedUser = service.getUser(user.getUsername());
    }

    @Test
    public void testDeleteUser() throws Exception {
        when(repository.findById(any())).thenReturn(Optional.of(user));
        service.deleteUser(user.getUsername());

        verify(repository, times(1)).deleteById(user.getUsername());
    }

    @Test(expected = NotFoundException.class)
    public void testDeleteUserShouldThrowErrorIfUsernameNotExists() throws Exception {
        service.deleteUser(user.getUsername());
    }

    @Test
    public void testUpdateUser() throws Exception {
        when(repository.findById(any())).thenReturn(Optional.of(user));
        User updatedUser = new User();
        updatedUser.setUsername(user.getUsername());
        updatedUser.setName("updated name");
        updatedUser.setEmail(user.getEmail());
        service.updateUser(user.getUsername(), updatedUser);

        verify(repository, times(1)).findById(user.getUsername());
        verify(repository, times(1)).save(updatedUser);
    }

    @Test(expected = NotFoundException.class)
    public void testUpdateUserShouldThrowErrorIfUsernameNotExists() throws Exception {
        User updatedUser = new User();
        updatedUser.setUsername(user.getUsername());
        updatedUser.setName("updated name");
        updatedUser.setEmail(user.getEmail());
        service.updateUser(user.getUsername(), updatedUser);
    }

    @Test(expected = BadRequestException.class)
    public void testUpdateUserShouldThrowErrorIfUpdatingAnotherUser() throws Exception {
        when(repository.findById(any())).thenReturn(Optional.of(user));
        User updatedUser = new User();
        updatedUser.setUsername("another username");
        updatedUser.setName("updated name");
        updatedUser.setEmail(user.getEmail());
        service.updateUser(user.getUsername(), updatedUser);
    }
}
