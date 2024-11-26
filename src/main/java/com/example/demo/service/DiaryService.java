package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import com.example.demo.model.DiaryClass;
import com.example.demo.repository.DiaryRepo;
import com.example.demo.repository.UserRepo;

@Service
public class DiaryService {
	@Autowired
	DiaryRepo diaryRepo;
	
	@Autowired
	private UserRepo userRepo;

	public List<DiaryClass> getAll() {
		// TODO Auto-generated method stub
		return diaryRepo.findAll();
	}

	public DiaryClass addEntry(DiaryClass diary, int uid) {
		// TODO Auto-generated method stub
		diary.setUid(userRepo.findById(uid).orElseThrow(()-> new ErrorResponseException(HttpStatus.NOT_FOUND)));
		return diaryRepo.save(diary);
	}

	public DiaryClass putEntry(int id, DiaryClass diary) {
		// TODO Auto-generated method stub
		DiaryClass temp=diaryRepo.findById(id).orElseThrow(()->new ErrorResponseException(HttpStatus.NOT_FOUND));
		temp.setDdate(diary.getDdate());
		temp.setData(diary.getData());
		return diaryRepo.save(temp);
	}

	public String deleteEntry(int id) {
		// TODO Auto-generated method stub
		DiaryClass temp=diaryRepo.findById(id).orElseThrow(()->new ErrorResponseException(HttpStatus.NOT_FOUND));
		diaryRepo.deleteById(id);
		return "Deleted";
	}
	
	private ProblemDetail getErrorMsg(String msg) {
		ProblemDetail pd=ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, msg);
		return pd;
	}

	public List<DiaryClass> getPeruserId(int uid) {
		// TODO Auto-generated method stub
		try {
			return userRepo.findById(uid).get().getDiaries();
		} catch (Exception e) {
			// TODO: handle exception
			throw new ErrorResponseException(HttpStatus.BAD_REQUEST);
		}
		
	}

	public DiaryClass getDiaryPerId(int id) {
		// TODO Auto-generated method stub
		return diaryRepo.findById(id).orElseThrow(()->new ErrorResponseException(HttpStatus.BAD_REQUEST));
	}
}
