/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viettel.CQRSES.AppService.Aggregates;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import viettel.CQRSES.Domain.Contracts.Aggregates.IUserAggregate;
import viettel.CQRSES.Domain.Contracts.Producers.IUserProducer;
import viettel.CQRSES.Domain.Contracts.RepoMaster.IUserRepoMaster;
import viettel.CQRSES.Domain.Entities.User;
import viettel.CQRSES.Events.BaseEvent;
import viettel.CQRSES.Events.UserManagementCommand;

@Service
public class UserAggregate implements IUserAggregate {
    private final IUserRepoMaster repoMaster;
    public IUserProducer userProducer;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserAggregate.class);
    public UserAggregate(IUserRepoMaster repoMaster,
                         IUserProducer userProducer) {
        this.repoMaster = repoMaster;
        this.userProducer = userProducer;
    }
    public User handleCreateUser(User user) {
        try {
            repoMaster.save(user);
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return null;
        }
        BaseEvent event = new BaseEvent();
        event.setId(UserManagementCommand.INSERT_USER.toString());
        event.setValue(user);
        userProducer.sendMessage(event);
        return user;
    }
    public boolean handleDelete(int id) {
        User user;
        try {
            user = repoMaster.getOne(id);
            repoMaster.deleteById(id);
        } catch (Exception ex) {
            LOGGER.info(ex.getMessage());
            return false;
        }
        if (user != null) {
            BaseEvent event = new BaseEvent();
            event.setId(UserManagementCommand.DELETE_USER.toString());
            event.setValue(String.valueOf(id));
            userProducer.sendMessage(event);
        }
        return true;
    }
}
