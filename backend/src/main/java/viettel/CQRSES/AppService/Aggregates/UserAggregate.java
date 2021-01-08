/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viettel.CQRSES.AppService.Aggregates;

import org.springframework.stereotype.Service;
import viettel.CQRSES.Domain.Contracts.Aggregates.IUserAggregate;
import viettel.CQRSES.Domain.Contracts.RepoMaster.IUserRepoMaster;

@Service
public class UserAggregate implements IUserAggregate {
    private final IUserRepoMaster repoMaster;

    public UserAggregate(IUserRepoMaster repoMaster) {
        this.repoMaster = repoMaster;
    }
}
