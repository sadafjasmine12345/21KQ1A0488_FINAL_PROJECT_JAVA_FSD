package com.pace.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pace.entity.MyStudentList;
import com.pace.repository.MyStudentRepository;

@Service
public class MyStudentListService {
	
	@Autowired
	private MyStudentRepository mystudent;
	
	public void saveMyStudents(MyStudentList student) {
		mystudent.save(student);
	}
	
	public List<MyStudentList> getAllMyStudents(){
		return mystudent.findAll();
	}
	
	public void deleteById(int id) {
		mystudent.deleteById(id);
	}
}
