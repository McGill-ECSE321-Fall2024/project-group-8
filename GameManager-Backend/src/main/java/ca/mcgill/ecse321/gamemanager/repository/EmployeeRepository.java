package ca.mcgill.ecse321.gamemanager.repository;

import org.springframework.data.repository.CrudRepository;

import ca.mcgill.ecse321.gamemanager.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, String> {
    public Employee findEmployeeByEmail(String email);
}