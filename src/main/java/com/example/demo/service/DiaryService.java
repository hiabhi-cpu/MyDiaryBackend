package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;

import com.example.demo.model.DiaryClass;
import com.example.demo.model.UserClass;
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

	public DiaryClass putEntry(int uid, int did, DiaryClass diary) {
		// TODO Auto-generated method stub
		DiaryClass temp=diaryRepo.findById(did).orElseThrow(()->new ErrorResponseException(HttpStatus.NOT_FOUND));
		if(temp.getUid().getUid()!=uid) {
			throw new ErrorResponseException(HttpStatus.UNAUTHORIZED);
		}
		temp.setDdate(diary.getDdate());
		temp.setData(diary.getData());
		return diaryRepo.save(temp);
	}

	public String deleteEntry(int uid, int did) {
		// TODO Auto-generated method stub
		DiaryClass temp=diaryRepo.findById(did).orElseThrow(()->new ErrorResponseException(HttpStatus.NOT_FOUND));
		if(temp.getUid().getUid()!=uid) {
			throw new ErrorResponseException(HttpStatus.UNAUTHORIZED);
		}
		diaryRepo.deleteById(did);
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

	public DiaryClass getDiaryPerId(int uid, int did) {
		// TODO Auto-generated method stub
		DiaryClass temp= diaryRepo.findById(did).orElseThrow(()->new ErrorResponseException(HttpStatus.BAD_REQUEST));
		if(temp.getUid().getUid()!=uid) {
			throw new ErrorResponseException(HttpStatus.UNAUTHORIZED);
		}
		return temp;
	}
}
