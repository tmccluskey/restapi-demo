package com.example.demo.user;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.NotFoundException;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService{
    @Autowired
    private UserRepository repository;

    public void createUser(User user) throws Exception{
        Optional<User> userInDatabase = repository.findById(user.getUsername());
        if(userInDatabase.isPresent()) {
            throw new BadRequestException();
        }
        repository.save(user);
    }

    public User getUser(String username) throws Exception{
        return repository.findById(username)
            .orElseThrow(() -> new NotFoundException());
    }

    public void deleteUser(String username) throws Exception{
        repository.findById(username)
                .orElseThrow(() -> new NotFoundException());
        repository.deleteById(username);
    }

    public void updateUser(String username, User user) throws Exception{
        User userInDatabase = repository.findById(username)
                .orElseThrow(() -> new NotFoundException());

        if(!userInDatabase.getUsername().equals(user.getUsername())) {
            throw new BadRequestException();
        }
        repository.save(user);
    }
}
