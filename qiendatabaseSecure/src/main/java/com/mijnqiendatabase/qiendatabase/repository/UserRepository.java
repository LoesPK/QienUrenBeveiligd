package com.mijnqiendatabase.qiendatabase.repository;

import org.springframework.data.repository.CrudRepository;

import com.mijnqiendatabase.qiendatabase.domain.User;

public interface UserRepository extends CrudRepository<User, Long>{
	User findByUsername(String username);
	User findByEmail(String email);

    @Override
    void delete(User user);

}

