package com.imsjpa.IMSJPA.controller;

import com.imsjpa.IMSJPA.model.Student;
import com.imsjpa.IMSJPA.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/student")
public class StudentController {
    @Autowired
    private StudentService studentService;// The StudentService used for accessing student data

    /**
     * Endpoint for retrieving all students.
     *
     * @return List of all students
     */
    @GetMapping
    public ResponseEntity<List<Student>> getStudents() {
        try {
            List<Student> students = studentService.getAllStudents();
            return new ResponseEntity<>(students, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint for retrieving a specific student by their ID.
     *
     * @param id The ID of the desired student
     * @return The Student object associated with the given ID
     */
    @GetMapping(path = "/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable int id) {
        try {
            Optional<Student> student = studentService.getStudent(id);
            if (student.isPresent()) {
                return ResponseEntity.ok(student.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    /**
     * Endpoint for creating a new student.
     *
     * @param currStudent The Student object containing the information for the new student
     * @return The newly created Student object
     */
    @PostMapping
    public ResponseEntity<Student> createStudent(@Valid @RequestBody Student currStudent) {
        try {
            Student createdStudent = studentService.createStudent(currStudent);
            return new ResponseEntity<>(createdStudent, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Endpoint for updating an existing student.
     *
     * @param id          The ID of the student to be updated
     * @param currStudent The updated Student object
     * @return The updated Student object
     */
    @PutMapping(path = "/{id}")
    public ResponseEntity<Optional<Student>> updateStudent(@PathVariable(name = "id") int id, @RequestBody Student currStudent) {
        try {
            Optional<Student> updatedStudent = studentService.updateParticularStudent(id, currStudent);
            return ResponseEntity.ok(updatedStudent);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    /**
     * Endpoint for deleting an existing student.
     *
     * @param id The ID of the student to be deleted
     * @return The deleted Student object
     */
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<?> deleteStudent(@PathVariable(name = "id") int id) {
        try {
            studentService.deleteParticularStudent(id);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
