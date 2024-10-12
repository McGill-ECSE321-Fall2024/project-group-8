package ca.mcgill.ecse321.gamemanager.repository;


import ca.mcgill.ecse321.gamemanager.model.Employee;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepo;

    @BeforeEach
    @AfterEach
    public void clearDataBase(){
        employeeRepo.deleteAll();
    }

    @Test
    public void testCreateAndReadOwner() {
        String name = "EmployeeName";
        String password = "EmployeePassword";
        String email = "EmployeeEmail";

        Employee testEmployee = new Employee(password, name, email);

        //testEmployee = employeeRepo.save(testEmployee);
        employeeRepo.save(testEmployee);
        Employee employeeFromDB = employeeRepo.findEmployeeByEmail(email);

        assertNotNull(employeeFromDB);
        assertEquals(name, employeeFromDB.getName());
        assertEquals(password, employeeFromDB.getPassword());
        assertEquals(email, employeeFromDB.getEmail());
    }
}