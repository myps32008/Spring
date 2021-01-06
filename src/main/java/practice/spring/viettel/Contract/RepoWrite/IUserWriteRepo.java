/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practice.spring.viettel.Contract.RepoWrite;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import practice.spring.viettel.Entity.User;


public interface IUserWriteRepo extends JpaRepository<User, Integer> {
    
}
