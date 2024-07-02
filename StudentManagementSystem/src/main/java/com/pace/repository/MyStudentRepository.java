package com.pace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.pace.entity.MyStudentList;

@Repository
public interface MyStudentRepository extends JpaRepository<MyStudentList,Integer>{

}
