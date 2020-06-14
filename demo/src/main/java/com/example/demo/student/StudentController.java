package com.example.demo.student;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/students")
public class StudentController {

	private static final List<Student> STUDENTS = Arrays.asList(
			new Student (1, "Abdul Kather"),
			new Student (2, "jack"),
			new Student (3, "Rose")
			);
	@GetMapping (path = "{studentId}")
	public Student getStudent(@PathVariable("studentId") Integer studentId) {
		
		return STUDENTS.stream()
				.filter(student -> studentId.equals(student.getId()))
				.findFirst()
				.orElseThrow(()->new IllegalStateException("Student  "+studentId+"  not found"));
				
	}
}
