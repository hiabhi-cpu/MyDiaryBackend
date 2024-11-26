package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.DiaryClass;
import com.example.demo.service.DiaryService;

@RestController
@RequestMapping("diary")
@CrossOrigin("*")
public class DiaryController {

	
	@Autowired
	DiaryService diaryService;
	
//	@GetMapping
//	public ResponseEntity<List<DiaryClass>> getAll(){
//		return new ResponseEntity<>(diaryService.getAll(),HttpStatus.OK);
//	}
	
	@GetMapping("all/{uid}")
	public ResponseEntity<List<DiaryClass>> getPerUserId(@PathVariable int uid ){
		//get all diary entry for a particular user id
		
		return new ResponseEntity<List<DiaryClass>>(diaryService.getPeruserId(uid),HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<DiaryClass> getDiaryPerId(@PathVariable int id){
		return new ResponseEntity<DiaryClass>(diaryService.getDiaryPerId(id),HttpStatus.OK);
	}
	
	@PostMapping("{uid}")
	public ResponseEntity<DiaryClass> addEntry(@RequestBody @Validated DiaryClass diary,@PathVariable int uid){
		return new ResponseEntity<DiaryClass>(diaryService.addEntry(diary,uid),HttpStatus.OK);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<DiaryClass> putEntry(@PathVariable int id,@RequestBody @Validated DiaryClass diary){
		return new ResponseEntity<DiaryClass>(diaryService.putEntry(id,diary),HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteEntry(@PathVariable int id){
		return new ResponseEntity<>(diaryService.deleteEntry(id),HttpStatus.OK);
	}
	
}
