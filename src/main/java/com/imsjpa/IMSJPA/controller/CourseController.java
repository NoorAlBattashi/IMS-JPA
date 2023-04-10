package com.imsjpa.IMSJPA.controller;

import com.imsjpa.IMSJPA.model.Course;
import com.imsjpa.IMSJPA.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping(path = "/api/course")
public class CourseController {
    @Autowired
    CourseService courseService;

    @GetMapping
    public List<Course> getAllCourses() {
        try {
            return courseService.getAllCourses();
        } catch (Exception e) {
            // Throw a custom exception or re-throw the original one
            throw new RuntimeException("Failed to retrieve courses. Please try again later.");
        }
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Course> getSpecificCourse(@PathVariable int id) {
        try {
            Optional<Course> course = courseService.getSpecificCourse(id);
            if (course.isPresent()) {
                return ResponseEntity.ok().body(course.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping
    public Course createCourse(@RequestBody Course regCourse) {
        try {
            return courseService.registerCourse(regCourse);
        } catch (Exception e) {
            // Log the exception or handle it in some other way
            // For example, you could return a specific error message or code to the client
            throw new RuntimeException("Failed to create course: " + e.getMessage(), e);
        }
    }


    @PutMapping(path = "/{id}")
    public ResponseEntity<Optional<Course>> updateCourse(@PathVariable int id, @RequestBody Course upToDateCourse) {
        try {
            Optional<Course> updatedCourse = courseService.updateParticularCourse(id, upToDateCourse);
            return ResponseEntity.ok(updatedCourse);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }


    @DeleteMapping(path = "/{id}")
    public void deleteSpecificCourse(@PathVariable int id) {
        try {
            courseService.dropSpecificCourse(id);
        } catch (NoSuchElementException e) {
             // This exception is thrown if the specified course ID does not exist
            // Handle the exception as appropriate, for example:
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found", e);
        } catch (Exception e) {
            // This is a catch-all exception handler
            // Handle any other exceptions as appropriate, for example:
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong", e);
        }
    }
}
