package com.mijnqiendatabase.qiendatabase.service;

import com.mijnqiendatabase.qiendatabase.domain.User;
import com.mijnqiendatabase.qiendatabase.exception.UserNotFoundException;
import com.mijnqiendatabase.qiendatabase.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;


@Controller
@Transactional
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository,  PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserByUsername(String username) {
        User result = userRepository.findByUsername(username);
        if (result != null) {
            return result;
        } else {
            throw new UserNotFoundException("[username : " + username + "] - User not found");
        }
    }

    public User addUser(User user) {
        try {
            return getUserByUsername(user.getUsername());
//            return new User();
        } catch (UserNotFoundException e) {
        	user.setPassword(encoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);

            return savedUser;
        }
    }
    
    public User updateUser(User user, String newPassword) {

        final String encodedPassword = encoder.encode(newPassword);

		//save it!!!
        user.setPassword(encodedPassword);
        this.userRepository.save(user);


    	return user;
    }

    public String deleteUser(String username) {
        if (isUserExists(username)) {
            userRepository.deleteById(
                    userRepository
                            .findByUsername(username)
                            .getId());
            return "User deleted [username: " + username + "]";
        } else {
            return "Delete user request can not proceed because of non existing user [username: " + username + "]";
        }
    }

    public boolean isUserExists(String username) {
        return this.userRepository.findByUsername(username) != null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = this.userRepository.findByUsername(username);

        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());

        return userDetails;
    }
}