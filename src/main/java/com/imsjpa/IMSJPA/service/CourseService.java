package com.imsjpa.IMSJPA.service;

import com.imsjpa.IMSJPA.model.Course;
import com.imsjpa.IMSJPA.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    CourseRepository courseRepository;

    public List<Course> getAllCourses(){
        try {
            return courseRepository.findAll();
        } catch (Exception e) {
            // Handle the exception
            e.printStackTrace();
            return null; // or throw a custom exception
        }
    }
    public Optional<Course> getSpecificCourse(int id) throws Exception {
        try {
            return courseRepository.findById(id);
        } catch (Exception e) {
            throw new Exception("Error finding course with id " + id, e);
        }
    }

    public Course registerCourse(Course courseToRegister) {
        try {
            return courseRepository.save(courseToRegister);
        } catch (DataAccessException ex) {
            throw new RuntimeException("Error registering course: " + ex.getMessage(), ex);
        }
    }
    public Optional<Course> updateParticularCourse(int id, Course UpToDateCourse) {
        try {
            Optional<Course> optionalCourse = courseRepository.findById(id);
            optionalCourse.ifPresent((course) -> {
                course.name = UpToDateCourse.name;
                course.description = UpToDateCourse.description;
                courseRepository.save(course);
            });
            return optionalCourse;
        } catch (Exception e) {
            // handle the exception here
            e.printStackTrace();
            return Optional.empty();
        }
    }

    public void dropSpecificCourse(int id) {
        try {
            courseRepository.deleteById(id);
        } catch (Exception e) {
            // Handle the exception in a way that makes sense for your application.
            // For example, you could log the error, display a user-friendly message, etc.
            System.out.println("An error occurred while trying to delete the course with ID " + id + ": " + e.getMessage());
        }
    }


}
