/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practice.spring.viettel.API;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import practice.spring.viettel.Contract.Service.IUserService;
import practice.spring.viettel.Entity.User;


import java.util.Iterator;

/**
 *
 * @author Admin
 */
@Controller
@RequestMapping(path="/api/users")
public class UserController {
    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }
    @GetMapping(path="/all")
    public @ResponseBody Iterable<User> getListUsers() {
        return userService.getListUser();
    }
    @GetMapping(path="/add")
    public @ResponseBody boolean addUsers() {
        userService.addUser();
        return true;
    }
}
