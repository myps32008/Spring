/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viettel.CQRSES.Domain.Contracts.Aggregates;


import viettel.CQRSES.Domain.Entities.User;

public interface IUserAggregate {
    User handleCreateUser(User user);
    boolean handleDelete(String id);
}
