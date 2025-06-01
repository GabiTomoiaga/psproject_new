package com.example.perfumeservicemaven.Controller;

import com.example.perfumeservicemaven.Domain.Users;
import com.example.perfumeservicemaven.Domain.UsersType;
import com.example.perfumeservicemaven.Service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<Users> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/{name}")
    public Users getUserByName(@PathVariable String name) {
        return userService.getUserByName(name);
    }

    @GetMapping("/role")
    public List<Users> getByRole(@RequestParam UsersType role) {
        return userService.getUsersByRole(role);
    }

    @PostMapping
    public boolean addUser(@RequestBody Users user) {
        return userService.addUser(user);
    }

    @PutMapping
    public boolean updateUser(@RequestBody Users user) {
        return userService.updateUser(user);
    }

    @DeleteMapping
    public boolean deleteUser(@RequestBody Users user) {
        return userService.deleteUser(user);
    }
}
