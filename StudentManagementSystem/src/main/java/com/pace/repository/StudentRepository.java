package com.pace.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.pace.entity.Student;

@Repository
public interface StudentRepository extends JpaRepository<Student,Integer>  {

}
