package com.personal.export.repository;

import com.personal.export.entity.Employee;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomRepository {
    List<Employee> searchByText(String skill);
}
