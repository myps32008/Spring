/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practice.spring.viettel.Contract.Service;

import practice.spring.viettel.Entity.User;


/**
 *
 * @author Admin
 */

public interface IUserService {
    Iterable<User> getListUser();
    boolean addUser();
}
