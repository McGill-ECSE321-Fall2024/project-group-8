package ca.mcgill.ecse321.gamemanager.integration;

import ca.mcgill.ecse321.gamemanager.dto.EmployeeRequestDto;
import ca.mcgill.ecse321.gamemanager.dto.EmployeeResponseDto;
import ca.mcgill.ecse321.gamemanager.repository.EmployeeRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// Define the order to conduct the tests
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
// Tell the springBoot to store everything I modified?
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EmployeeIntegrationTests {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private EmployeeRepository employeeRepository;

    @BeforeAll
    @AfterAll
    public void cleanUp() {
        employeeRepository.deleteAll();
    }

    public final String VALID_NAME = "Doe";
    public final String VALID_NEW_NAME = "John";
    public final String VALID_EMAIL = "doe@example.com";
    public final String VALID_PASSWORD = "123456789";
    public final String VALID_NEW_PASSWORD = "987654321";



    @Test
    @Order(1)
    public void testAndCreateValidEmployee() {
        EmployeeRequestDto requestDto = new EmployeeRequestDto(VALID_NAME, VALID_EMAIL, VALID_PASSWORD);

        ResponseEntity<EmployeeResponseDto> response = client.postForEntity("/api/employees", requestDto, EmployeeResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        EmployeeResponseDto responseBody = response.getBody();
        assertEquals(VALID_NAME, responseBody.getName());
        assertEquals(VALID_EMAIL, responseBody.getEmail());
    }

    @Test
    @Order(2)
    public void testAndUpdateValidEmployee() {
        EmployeeRequestDto request = new EmployeeRequestDto(VALID_NEW_NAME, VALID_EMAIL, VALID_NEW_PASSWORD);
        String url = "/api/employees/" + VALID_EMAIL;

        ResponseEntity<EmployeeResponseDto> response = client.exchange(
                url,
                HttpMethod.PUT,
                new HttpEntity<>(request),
                EmployeeResponseDto.class
        );

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        EmployeeResponseDto createdEmployee = response.getBody();
        assertEquals(VALID_NEW_NAME, createdEmployee.getName());
    }

    @Test
    @Order(3)
    public void testAndDeleteValidEmployee() {

        String url = "/api/employees/" + VALID_EMAIL;
        client.delete(url);
        ResponseEntity<String> response = client.getForEntity(url,String.class);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }


}
