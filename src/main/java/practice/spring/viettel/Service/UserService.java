/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practice.spring.viettel.Service;

import org.springframework.stereotype.Service;
import practice.spring.viettel.Contract.RepoRead.IUserReadRepo;
import practice.spring.viettel.Contract.RepoWrite.IUserWriteRepo;
import practice.spring.viettel.Contract.Service.IUserService;
import practice.spring.viettel.Entity.User;


/**
 *
 * @author Admin
 */
@Service
public class UserService implements IUserService {
    public final IUserReadRepo readRepo;
    public final IUserWriteRepo writeRepo;

    public UserService(IUserReadRepo readRepo, IUserWriteRepo writeRepo) {
        this.readRepo = readRepo;
        this.writeRepo = writeRepo;
    }

    @Override
    public Iterable<User> getListUser() {
        return readRepo.findAll();
    }
    @Override
    public boolean addUser() {
        User save = new User();
        save.setUserName("lfjaslfja");
        writeRepo.save(save);
        return true;
    }
}
