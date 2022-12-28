package com.personal.export.controller;

import com.personal.export.entity.Employee;
import com.personal.export.repository.CustomRepositoryImpl;
import com.personal.export.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@Slf4j
@RequestMapping(value = "/companyX")
public class FrontController {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    CustomRepositoryImpl customRepository;

    @GetMapping(value = "/")
    public String atHome(){
        System.out.println("At Home...");
        log.info("At Home...");
        return "At Home...";
    }

    @PostMapping(value = "/add")            //Create
    public Employee addEmployee(@RequestBody Employee employee){
        log.info("Added: " + employee);     //Log Files are Generated for Every Operation for Server Side
        return employeeRepository.save(employee);
    }

    @PutMapping(value = "/update")          //Update
    public String updateEmployee(@RequestBody Employee employee){
        String _id = employee.get_id();
        Employee oldData = employeeRepository.findById(_id).orElse(new Employee());
        if (oldData.get_id() == null){
            log.info("Employee doesn't work in this Organization");     //Log Files are Generated for Every Operation for Server Side
            System.out.println("Employee doesn't work in this Organization");       //We are printing to easily see in the Terminal
            return "Employee doesn't work in this Organization";
        }else {
            employeeRepository.save(employee);
            log.info("Updated: " + oldData + " to " + employee);
            System.out.println("Updated: " + oldData + " to " + employee);
            return "Updated: " + oldData + " to " + employee;
        }
    }

    @DeleteMapping(value = "/delete/{_id}")  //Delete
    public String deleteEmployee(@PathVariable String _id){
        Employee employee = employeeRepository.findById(_id).orElse(new Employee());

        if (employee.get_id() == null){
            log.info("Employee doesn't work in this Organization");
            System.out.println("Employee doesn't work in this Organization");
            return "Employee doesn't work in this Organization";
        }else {
            employeeRepository.delete(employee);
            log.info("Deleted: " + employee);
            System.out.println("Deleted: " + employee);
            return "Deleted: " + employee;
        }
    }

    @GetMapping("/all")     //Retrieve All OR Read All
    public List<Employee> allEmployees(){
        employeeRepository.findAll().forEach(System.out::println);      //2 for loops are useless
        employeeRepository.findAll().forEach(employee -> log.info("Resource: " + employee));        //To show I can work with Lambda Expressions I did separately
        return employeeRepository.findAll();
    }

    @GetMapping(value = "/greaterThan/{_id}")  //Retrieve all greater than a specific id
    public List<Employee> employeesGreaterThanId(@PathVariable String _id){
        List<Employee> employees = new ArrayList<>();
        for (Employee employee : employeeRepository.findAll()){
            if (Integer.parseInt(employee.get_id()) > Integer.parseInt(_id))
                employees.add(employee);
        }
        employees.forEach(employee -> {
            System.out.println(employee);
            log.info("Resource: " + employee);
        });
        return employees;
    }

    @GetMapping(value = "/fetch/{_id}")  //Retrieve or FETCH a specific ID
    public String fetchEmployee(@PathVariable String _id){
        Employee employee = employeeRepository.findById(_id).orElse(new Employee());

        if (employee.get_id() == null){
            log.info("Employee doesn't work in this Organization");
            System.out.println("Employee doesn't work in this Organization");
            return "Employee doesn't work in this Organization";
        }else {
            log.info("Resource: " + employee);
            System.out.println(employee);
            return "Resource: " + employee;
        }
    }

    @GetMapping(value = "/search/{text}")       //Search by a Specific Text in Role or Tech Stack
    public List<Employee> searchByText(@PathVariable String text){
        customRepository.searchByText(text).forEach(employee -> {       //The result will be Sorted
            log.info("Resource: " + employee);
            System.out.println(employee);
        });
        return customRepository.searchByText(text);
    }
}
