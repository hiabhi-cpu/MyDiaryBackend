package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.model.UserClass;

@Repository
public interface UserRepo extends JpaRepository<UserClass, Integer>{

	List<UserClass> findByUsername(String username);


}
