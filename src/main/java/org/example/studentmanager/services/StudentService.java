package org.example.studentmanager.services;

import org.example.studentmanager.model.Student;

import java.util.List;

public interface StudentService {

    public void save(Student student);
    public void remove(Student student);
    public List<Student> findAll();
    public List<Student> findById(String subString);

    List<Student> find(String subString);
}
