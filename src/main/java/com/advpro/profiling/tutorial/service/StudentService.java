package com.advpro.profiling.tutorial.service;

import com.advpro.profiling.tutorial.model.Student;
import com.advpro.profiling.tutorial.model.StudentCourse;
import com.advpro.profiling.tutorial.repository.StudentCourseRepository;
import com.advpro.profiling.tutorial.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Comparator;


/**
 * @author muhammad.khadafi
 */
@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private StudentCourseRepository studentCourseRepository;

    public List<StudentCourse> getAllStudentsWithCourses() {
        // Optimization: we use a single query to fetch all StudentCourse records
        // instead of querying the database for each student ID in a loop
        return studentCourseRepository.findAll();
    }

    public Optional<Student> findStudentWithHighestGpa() {
        List<Student> students = studentRepository.findAll();
        // Optimization: Using parallelStream to utilize multiple CPU cores for searching
        return students.parallelStream()
                .max(Comparator.comparingDouble(Student::getGpa));
    }

    public String joinStudentNames() {
        List<Student> students = studentRepository.findAll();
        StringBuilder result = new StringBuilder(); // Optimization: StringBuilder is much faster in loops
        for (Student student : students) {
            result.append(student.getName()).append(", ");
        }
        return result.toString();
    }
}


