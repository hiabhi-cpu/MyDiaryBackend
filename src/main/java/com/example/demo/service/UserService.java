package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import com.example.demo.model.UserClass;
import com.example.demo.repository.UserRepo;

@Service
public class UserService {
	@Autowired
	private UserRepo userRepo;

	public UserClass adduser(UserClass user) {
		// TODO Auto-generated method stub
		if(userRepo.findByUsername(user.getUsername()).size()!=0) {
			throw new ErrorResponseException(HttpStatus.BAD_REQUEST);
		}
		return userRepo.save(user);
	}

	public List<UserClass> getAllUsers() {
		// TODO Auto-generated method stub
		return userRepo.findAll();
	}

	public UserClass getUserPerId(int uid) {
		// TODO Auto-generated method stub
		return userRepo.findById(uid).orElseThrow(()-> new ErrorResponseException(HttpStatus.BAD_REQUEST));
	}

	private ProblemDetail getErrorMsg(String msg) {
		ProblemDetail pd=ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, msg);
		pd.setTitle(msg);
		return pd;
	}


	public UserClass login(UserClass userClass) {
		// TODO Auto-generated method stub
		UserClass temp=userRepo.findByUsername(userClass.getUsername()).get(0);
		if(!userClass.getUserpassword().equals(temp.getUserpassword())) {
			throw new ErrorResponseException(HttpStatus.BAD_REQUEST);
		}
		return temp;
	}

	public UserClass updateUser(UserClass user) {
		// TODO Auto-generated method stub
		UserClass temp=userRepo.findById(user.getUid()).get();
		temp.setUserpassword(user.getUserpassword());
		return userRepo.save(temp);
	}

}
