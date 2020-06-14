package com.example.demo.student;

import java.util.Arrays;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("management/api/v1/students")
public class StudentManagementController {

	private static final List<Student> STUDENTS = Arrays.asList(
			new Student (1, "Abdul Kather"),
			new Student (2, "jack"),
			new Student (3, "Rose")
			);
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ADMINTRAINEE')")
	public List<Student> getStudents() {
		return STUDENTS;
	}
	
	@PostMapping
//	@PreAuthorize("hasAuthority('student:write')")
	@PreAuthorize("hasAuthority('course:write')")
	public void registerStudent(@RequestBody Student student) {
		System.out.println("Registering Student: "+student);
	}
	
	@DeleteMapping(path = "{studentId}")
//	@PreAuthorize("hasAuthority('student:write')")
	@PreAuthorize("hasAuthority('course:write')")
	public void deleteStudent(@PathVariable("studentId") Integer studentId) {
		System.out.println("Deleting Student: "+studentId);
	}
	
	@PutMapping (path="{studentId}")
//	@PreAuthorize("hasAuthority('student:write')")
	@PreAuthorize("hasAuthority('course:write')")
	public void updateStudent(@PathVariable("studentId") Integer studentId,@RequestBody Student student) {
		System.out.print("Updating Sudent");
		System.out.println(String.format("%s %s", studentId, student ));
	}
}
