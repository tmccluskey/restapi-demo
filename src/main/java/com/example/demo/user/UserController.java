package com.example.demo.user;

import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    public void setUserService(UserService service) {
        this.service = service;
    }

    @PostMapping("")
    public void createUser(@RequestBody User user) throws Exception{
        service.createUser(user);
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable(name="username")String username) throws Exception{
        return service.getUser(username);
    }

    @DeleteMapping("/{username}")
    public void deleteUser(@PathVariable(name="username")String username) throws Exception{
        service.deleteUser(username);
    }

    @PutMapping("/{username}")
    public void updateEmployee(@RequestBody User user,
                               @PathVariable(name="username")String username) throws Exception{
        service.updateUser(username, user);

    }

}