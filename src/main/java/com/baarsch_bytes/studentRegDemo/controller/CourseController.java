package com.baarsch_bytes.studentRegDemo.controller;

import com.baarsch_bytes.studentRegDemo.dto.CourseRequest;
import com.baarsch_bytes.studentRegDemo.dto.CourseResponse;
import com.baarsch_bytes.studentRegDemo.model.Course;
import com.baarsch_bytes.studentRegDemo.model.Student;
import com.baarsch_bytes.studentRegDemo.repository.CourseRepository;
import com.baarsch_bytes.studentRegDemo.repository.StudentRepository;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courses")
public class CourseController {
    private final CourseRepository repository;
    StudentRepository studentRepository;

    public CourseController(CourseRepository repository,
                            StudentRepository studentRepository) {
        this.repository = repository;
        this.studentRepository = studentRepository;
    }

    @GetMapping
    public List<CourseResponse> getCourses() {
        List<Course> courses = repository.findAll();
        return courses.stream()
                .map(course -> {
                    CourseResponse response = new CourseResponse();
                    response.setId(course.getId());
                    response.setName(course.getName());
                    response.setInstructor(course.getInstructor());
                    response.setRoom(course.getRoom());
                    response.setRoster(
                            course.getRoster().stream()
                                    .map(Student::getName)
                                    .collect(Collectors.toSet()));
                    return response;
                })
                .collect(Collectors.toList());
    }

    @PostMapping
    //public ResponseEntity<Student> create(@RequestBody Student student) {
    public ResponseEntity<String> create(@Valid @RequestBody CourseRequest request) {

        // translate between the dto from the frontend and the entity
        // ruled by the DB.
        Course course = new Course();
        course.setName(request.getName());
        course.setInstructor(request.getInstructor());
        course.setMaxSize(request.getMaxSize());
        course.setRoom(request.getRoom());
        if (request.getRoster() != null) {
            List<Student> studentsFromDb =
                    studentRepository.findAllById(request.getRoster());
            course.setRoster(new HashSet<Student>(studentsFromDb));
        }
        // add course to db
        repository.save(course);
        return ResponseEntity.ok(course.getName() + " added successfully");
    }



}
