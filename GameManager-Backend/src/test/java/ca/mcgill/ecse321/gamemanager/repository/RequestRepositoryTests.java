package ca.mcgill.ecse321.gamemanager.repository;

import ca.mcgill.ecse321.gamemanager.model.Employee;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RequestRepositoryTests {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private RequestRepository requestRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        requestRepository.deleteAll();
        employeeRepository.deleteAll();
    }

    @Test
    public void testCreateAndReadRequest() {
        String employeePassword = "employeePassword";
        String employeeName = "employeeName";
        String employeeEmail = "employeeEmail";

        Employee testEmployee = new Employee(employeePassword, employeeName, employeeEmail);




    }


}
