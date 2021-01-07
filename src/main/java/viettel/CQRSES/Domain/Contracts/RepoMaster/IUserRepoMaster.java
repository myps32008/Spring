/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viettel.CQRSES.Domain.Contracts.RepoMaster;

import org.springframework.data.jpa.repository.JpaRepository;
import viettel.CQRSES.Domain.Entities.User;

public interface IUserRepoMaster extends JpaRepository<User, Integer> {
    
}
