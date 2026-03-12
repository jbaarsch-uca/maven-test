package com.baarsch_bytes.studentRegDemo.controller;
import com.baarsch_bytes.studentRegDemo.model.Student;
import com.baarsch_bytes.studentRegDemo.repository.StudentRepository;
import com.baarsch_bytes.studentRegDemo.validation.StudentValidator;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentRepository repository;
    private StudentValidator validator;

    public StudentController(StudentRepository repository) {
        this.repository = repository;
        validator = new StudentValidator();
    }

    // GET all students
    @GetMapping
    public List<Student> getAll() {
        return repository.findAll();
    }

    // POST a new student (The target for Postman testing)
    @PostMapping
    //public ResponseEntity<Student> create(@RequestBody Student student) {
    public ResponseEntity<String> create(@Valid @RequestBody Student student) {
        // Lab Logic: You could add validation here to throw an error
        // if GPA > 4.0, giving students a negative test case.

        /*
        // Attempt to do manual validation
        if (validator.isValid(student)) {
            repository.save(student);
            return ResponseEntity.ok(student.getName() + " added successfully");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("student failed validation");
*/

        // Spring boot validation takes care of that for you!
        repository.save(student);
        return ResponseEntity.ok(student.getName() + " added successfully");
    }

    // Update
    @PutMapping
    public ResponseEntity<String> update(@Valid @RequestBody Student student) {
        repository.save(student);
        return ResponseEntity.ok(student.getName() + " updated successfully");
    }

    // DELETE a student
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}