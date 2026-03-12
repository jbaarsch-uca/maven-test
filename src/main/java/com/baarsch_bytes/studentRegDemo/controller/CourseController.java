package com.baarsch_bytes.studentRegDemo.controller;

import com.baarsch_bytes.studentRegDemo.model.Course;
import com.baarsch_bytes.studentRegDemo.model.Student;
import com.baarsch_bytes.studentRegDemo.repository.CourseRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseRepository repository;

    public CourseController(CourseRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Course> getCourses() {
        return repository.findAll();
    }

    @PostMapping
    //public ResponseEntity<Student> create(@RequestBody Student student) {
    public ResponseEntity<String> create(@Valid @RequestBody Course course) {
        repository.save(course);
        return ResponseEntity.ok(course.getName() + " added successfully");
    }



}
