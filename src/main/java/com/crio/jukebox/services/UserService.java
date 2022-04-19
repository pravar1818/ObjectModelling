package com.crio.jukebox.services;

import com.crio.jukebox.repositories.IUserRepository;


import java.util.Optional;

import com.crio.jukebox.entities.User;

public class UserService implements IUserService {

    IUserRepository userRepository;

    public UserService(IUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(String name) {
        // TODO Auto-generated method stub
        User user = new User(name);
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUser(String id){
        return userRepository.findById(id);
    }

   
    
}