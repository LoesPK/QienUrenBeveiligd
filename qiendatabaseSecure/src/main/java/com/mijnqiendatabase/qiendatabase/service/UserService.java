package com.mijnqiendatabase.qiendatabase.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.mijnqiendatabase.qiendatabase.config.SimpleSecurityController;
import com.mijnqiendatabase.qiendatabase.domain.User;
import com.mijnqiendatabase.qiendatabase.exception.UserNotFoundException;
import com.mijnqiendatabase.qiendatabase.repository.UserRepository;


@Controller
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final SimpleSecurityController securityController;
    private final PasswordEncoder encoder;

    @Autowired
    public UserService(UserRepository userRepository, SimpleSecurityController securityController, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.securityController = securityController;
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
            if (savedUser != null) {
                //Adding user to simpleSecurityController to let the user to be able to use his/her account
                securityController.add(savedUser.getUsername(), savedUser.getPassword(), savedUser.getRole());
            }
            return savedUser;
        }
    }
    
    public User updateUser(User user, String newPassword) {

        final String encodedPassword = encoder.encode(newPassword);

		//save it!!!
        user.setPassword(encodedPassword);
        this.userRepository.save(user);

        // update the inMemoryUserManager
        this.securityController.updatePassword(user.getPassword(), encodedPassword);
    	
    	return user;
    }

    public String deleteUser(String username) {
        if (isUserExists(username)) {
            userRepository.deleteById(
                    userRepository
                            .findByUsername(username)
                            .getId());
            securityController.remove(username);
            return "User deleted [username: " + username + "]";
        } else {
            return "Delete user request can not proceed because of non existing user [username: " + username + "]";
        }
    }

    //Init user is for adding all the users on the database to simpleSecurityController when application restarts
    public void initUsers() {
        System.out.println("<----- User Initialization Started ----->");
        for (User u : getAllUsers()) {
        	System.out.println(u.getPassword());
            securityController.add(u.getUsername(), u.getPassword(), u.getRole());
        }
        System.out.println("<----- User Initialization Finished ----->");
    }

    public boolean isUserExists(String username) {
        return securityController.userExists(username);
    }

}