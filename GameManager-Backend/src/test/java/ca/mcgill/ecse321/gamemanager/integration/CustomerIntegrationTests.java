package ca.mcgill.ecse321.gamemanager.integration;


import ca.mcgill.ecse321.gamemanager.dto.CustomerResponseDto;


import ca.mcgill.ecse321.gamemanager.dto.CustomerRequestDto;
import ca.mcgill.ecse321.gamemanager.repository.CustomerRepository;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CustomerIntegrationTests {
    @Autowired
    private TestRestTemplate client;
    @Autowired
    private CustomerRepository repository;

    @BeforeAll
    @AfterAll
    public void cleanUp() {

        repository.deleteAll();

    }

    private final String aPassword = "12345678";
    private final String aUsername = "Ang";
    private final String aEmail = "ang@example.com";

    @Test
    @Order(1)

    public void testCreateValid(){
        //set
        CustomerRequestDto request = new CustomerRequestDto( aUsername,aEmail,aPassword);

        ResponseEntity<CustomerResponseDto> response = client.postForEntity("/customers", request, CustomerResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());


        CustomerResponseDto createdCustomerResponseDto = response.getBody();

        assertEquals(aUsername, createdCustomerResponseDto.getName());
        assertEquals(aEmail, createdCustomerResponseDto.getEmail());



    }
}
