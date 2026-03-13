package com.baarsch_bytes.studentRegDemo.controller;

import com.baarsch_bytes.studentRegDemo.model.Course;
import com.baarsch_bytes.studentRegDemo.repository.CourseRepository;
import com.baarsch_bytes.studentRegDemo.repository.StudentRepository;
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
    public List<Course> getAllCourses(){
        return repository.findAll();
    }

    @PostMapping
    public Course addCourse(@RequestBody Course course){
        return repository.save(course);
    }

}
