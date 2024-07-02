package com.pace.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import com.pace.entity.Student;
import com.pace.entity.MyStudentList;
import com.pace.service.StudentService;
import com.pace.service.MyStudentListService;

import java.util.List;

@Controller
public class StudentController {

    @Autowired
    private StudentService service;

    @Autowired
    private MyStudentListService myStudentService;

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/student_register")
    public String studentRegister() {
        return "studentRegister";
    }

    @GetMapping("/available_students")
    public ModelAndView getAllStudent() {
        List<Student> list = service.getAllStudent();
        return new ModelAndView("studentList", "student", list);
    }

    @PostMapping("/save")
    public String addStudent(@ModelAttribute Student b) {
        service.save(b);
        return "redirect:/available_students";
    }
    

    @GetMapping("/my_students")
    public String getMyStudents(Model model) {
        List<MyStudentList> list = myStudentService.getAllMyStudents();
        model.addAttribute("student", list);
        return "myStudents";
    }

    @RequestMapping("/mylist/{id}")
    public String getMyList(@PathVariable("id") int id) {
        Student b = service.getStudentById(id);
        MyStudentList mb = new MyStudentList(b.getId(), b.getName(), b.getBranch(), b.getPercentage());
        myStudentService.saveMyStudents(mb);
        return "redirect:/my_students";
    }

    @RequestMapping("/editStudent/{id}")
    public String editStudent(@PathVariable("id") int id, Model model) {
        Student b = service.getStudentById(id);
        model.addAttribute("student", b);
        return "studentEdit";
    }

    @RequestMapping("/deleteStudent/{id}")
    public String deleteStudent(@PathVariable("id") int id) {
        service.deleteById(id);
        return "redirect:/available_students";
    }

    // Add RESTful endpoints

    @RestController
    @RequestMapping("/api/students")
    public static class StudentApiController {

        @Autowired
        private StudentService service;

        @GetMapping
        public ResponseEntity<List<Student>> getAllStudents() {
            return ResponseEntity.ok(service.getAllStudent());
        }

        @GetMapping("/{id}")
        public ResponseEntity<Student> getStudentById(@PathVariable int id) {
            Student student = service.getStudentById(id);
            if (student == null) {
                return ResponseEntity.notFound().build();
            }
            return ResponseEntity.ok(student);
        }

        @PostMapping
        public ResponseEntity<Student> createStudent(@RequestBody Student student) {
            service.save(student);
            return ResponseEntity.ok(student);
        }

        @PutMapping("/{id}")
        public ResponseEntity<Student> updateStudent(@PathVariable int id, @RequestBody Student studentDetails) {
            Student student = service.getStudentById(id);
            if (student == null) {
                return ResponseEntity.notFound().build();
            }
            student.setName(studentDetails.getName());
            student.setBranch(studentDetails.getBranch());
            student.setPercentage(studentDetails.getPercentage());
            service.save(student);
            return ResponseEntity.ok(student);
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> deleteStudent(@PathVariable int id) {
            service.deleteById(id);
            return ResponseEntity.noContent().build();
        }
    }
}

