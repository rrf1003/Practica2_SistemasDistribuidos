package com.sistemasdistr.basico.service;

import com.sistemasdistr.basico.model.User;
import com.sistemasdistr.basico.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserService {

    @Autowired
    public UserRepository repo;

    @Autowired
    private KeyService keyservice;

    public User register(String username, String password) throws Exception{

        Map<String, String> keys = keyservice.generateKeyPair();

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setPublickey(keys.get("public").getBytes());
        user.setPrivateKey(keys.get("private").getBytes());

        return repo.save(user);
    }
}
