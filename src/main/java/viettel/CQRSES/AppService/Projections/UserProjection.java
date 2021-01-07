/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viettel.CQRSES.AppService.Projections;

import org.springframework.stereotype.Service;
import viettel.CQRSES.Domain.Contracts.Projections.IUserProjection;
import viettel.CQRSES.Domain.Contracts.RepoSlave.IUserRepoSlave;

@Service
public class UserProjection implements IUserProjection {
    private final IUserRepoSlave repoSlave;

    public UserProjection(IUserRepoSlave repoSlave) {
        this.repoSlave = repoSlave;
    }
}
