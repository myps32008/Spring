/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viettel.CQRSES.Domain.Contracts.RepoSlave;

import org.springframework.data.jpa.repository.JpaRepository;
import viettel.CQRSES.Domain.Entities.User;

public interface IUserRepoSlave extends JpaRepository<User,Integer> {
    
}
