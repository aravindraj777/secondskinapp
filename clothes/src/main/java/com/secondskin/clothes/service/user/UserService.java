package com.secondskin.clothes.service.user;


import com.secondskin.clothes.Dto.CreateUserRequest;
import com.secondskin.clothes.entity.User;
import com.secondskin.clothes.repository.user.UserRepository;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Data
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private  PasswordEncoder encoder;
    int flag=0;

    public boolean createUser(CreateUserRequest createUserRequest ) {
        Optional<User> userByName= userRepository.findByUsername(createUserRequest.getUsername());
        Optional<User> userByEmail= userRepository.findByEmail(createUserRequest.getEmail());

        if(userByName.isPresent()) {
            this.flag=1;
            return false;

        }else if (userByEmail.isPresent()) {
            this.flag=1;
            return false;

        }else if(createUserRequest.getPassword().equals(createUserRequest.getConfirmPassword())) {
            User newUser= User.builder()
                    .username(createUserRequest.getUsername())
                    .email(createUserRequest.getEmail())
                    .password(encoder.encode(createUserRequest.getPassword()))
                    .roles("ROLE_USER")
                    .build();

            userRepository.save(newUser);
            return true;
        }

        return false;
    }


    public void upadateUser(Long id, User user) {

        Optional<User> user_db = userRepository.findById(id);
        User updateUser = user_db.get();

        updateUser.setUsername(user.getUsername());
        updateUser.setEmail(user.getEmail());
//        updateUser.setPassword(encoder.encode(user.getPassword()));


        userRepository.save(updateUser);
    }

}