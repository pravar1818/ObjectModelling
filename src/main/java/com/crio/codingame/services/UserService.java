package com.crio.codingame.services;

import java.util.Collections;
import java.util.List;


import com.crio.codingame.dtos.UserRegistrationDto;
import com.crio.codingame.entities.Contest;
import com.crio.codingame.entities.ContestStatus;
import com.crio.codingame.entities.RegisterationStatus;
import com.crio.codingame.entities.ScoreOrder;
import com.crio.codingame.entities.User;
import com.crio.codingame.exceptions.ContestNotFoundException;
import com.crio.codingame.exceptions.InvalidOperationException;
import com.crio.codingame.exceptions.UserNotFoundException;
import com.crio.codingame.repositories.IContestRepository;
import com.crio.codingame.repositories.IUserRepository;

public class UserService implements IUserService {

    private final IUserRepository userRepository;
    private final IContestRepository contestRepository;
    Integer autoIncreament = 0; 

    public UserService(IUserRepository userRepository, IContestRepository contestRepository) {
        this.userRepository = userRepository;
        this.contestRepository = contestRepository;
    }
    // TODO: CRIO_TASK_MODULE_SERVICES
    // Create and store User into the repository.
    @Override
    public User create(String name) {
        autoIncreament++;
        String id = Integer.toString(autoIncreament);
        User user = new User(id,name,0);
        userRepository.save(user);
        return user;
    }

    // TODO: CRIO_TASK_MODULE_SERVICES
    // Get All Users in Ascending Order w.r.t scores if ScoreOrder ASC.
    // Or
    // Get All Users in Descending Order w.r.t scores if ScoreOrder DESC.

    @Override
    public List<User> getAllUserScoreOrderWise(ScoreOrder scoreOrder){
        List<User> userList = userRepository.findAll();

        if(scoreOrder == ScoreOrder.ASC){
            Collections.sort(userList, (u1, u2) -> {
                return u1.getScore() - u2.getScore(); 
            }); 
        }
        else{
            Collections.sort(userList, (u1, u2) -> {
                return u2.getScore() - u1.getScore(); 
            });  
        }
        return userList;
    }

    @Override
    public UserRegistrationDto attendContest(String contestId, String userName) throws ContestNotFoundException, UserNotFoundException, InvalidOperationException {
        Contest contest = contestRepository.findById(contestId).orElseThrow(() -> new ContestNotFoundException("Cannot Attend Contest. Contest for given id:"+contestId+" not found!"));
        User user = userRepository.findByName(userName).orElseThrow(() -> new UserNotFoundException("Cannot Attend Contest. User for given name:"+ userName+" not found!"));
        if(contest.getContestStatus().equals(ContestStatus.IN_PROGRESS)){
            throw new InvalidOperationException("Cannot Attend Contest. Contest for given id:"+contestId+" is in progress!");
        }
        if(contest.getContestStatus().equals(ContestStatus.ENDED)){
            throw new InvalidOperationException("Cannot Attend Contest. Contest for given id:"+contestId+" is ended!");
        }
        if(user.checkIfContestExists(contest)){
            throw new InvalidOperationException("Cannot Attend Contest. Contest for given id:"+contestId+" is already registered!");
        }
        user.addContest(contest);
        userRepository.save(user);
        return new UserRegistrationDto(contest.getName(), user.getName(),RegisterationStatus.REGISTERED);
    }

    // TODO: CRIO_TASK_MODULE_SERVICES
    // Withdraw the user from the contest
    // Hint :- Refer Unit Testcases withdrawContest method

    @Override
    public UserRegistrationDto withdrawContest(String contestId, String userName) throws ContestNotFoundException, UserNotFoundException, InvalidOperationException {
        
        Contest contest = contestRepository.findById(contestId).orElseThrow(() -> new ContestNotFoundException());
        User user = userRepository.findByName(userName).orElseThrow(() -> new UserNotFoundException());

        if(!user.checkIfContestExists(contest))
            throw new InvalidOperationException("Cannot Withdraw Contest. User for given contest id:"+contestId+" is not registered!");

        if(contest.getContestStatus() == ContestStatus.IN_PROGRESS)
            throw new InvalidOperationException("Cannot Withdraw Contest. Contest for given id:"+contestId+" is in progress!");
        
        if(contest.getContestStatus() == ContestStatus.ENDED)
            throw new InvalidOperationException("Cannot Withdraw Contest. Contest for given id:"+contestId+" is ended!");
        
        if(contest.getCreator().getName().equals(userName))
            throw new InvalidOperationException("Cannot Withdraw Contest. Contest Creator:"+userName+ "not allowed to withdraw contest!");

        user.deleteContest(contest);
        userRepository.save(user);
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto(contest.getName(), user.getName(), RegisterationStatus.NOT_REGISTERED);
        return userRegistrationDto;
    }
    
}
