package ca.mcgill.ecse321.gamemanager.integration;

import ca.mcgill.ecse321.gamemanager.dto.*;
import ca.mcgill.ecse321.gamemanager.repository.OwnerRepository;
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
public class OwnerIntegrationTests {

    @Autowired
    private TestRestTemplate client;

    @Autowired
    private OwnerRepository ownerRepository;

    @BeforeAll
    @AfterAll
    public void cleanUp() {
        ownerRepository.deleteAll();
    }

    public final String VALID_NAME = "Doe";
    public final String VALID_NEW_NAME = "John";
    public final String VALID_EMAIL = "doe@example.com";
    public final String VALID_PASSWORD = "123456789";
    public final String VALID_NEW_PASSWORD = "987654321";



    @Test
    @Order(1)
    public void testAndCreateValidOwner() {
        OwnerRequestDto requestDto = new OwnerRequestDto(VALID_NAME, VALID_EMAIL, VALID_PASSWORD);

        ResponseEntity<OwnerResponseDto> response = client.postForEntity("/api/owners", requestDto, OwnerResponseDto.class);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        assertNotNull(response.getBody());
        OwnerResponseDto responseBody = response.getBody();
        assertEquals(VALID_NAME, responseBody.getName());
        assertEquals(VALID_EMAIL, responseBody.getEmail());
    }

    @Test
    @Order(2)
    public void testAndUpdateValidOwner() {
        OwnerRequestDto request = new OwnerRequestDto(VALID_NEW_NAME, VALID_EMAIL, VALID_NEW_PASSWORD);
        String url = "/api/owners/" + this.VALID_EMAIL;

        ResponseEntity<OwnerResponseDto> response = client.exchange(
                url,
                HttpMethod.PUT,
                new HttpEntity<>(request),
                OwnerResponseDto.class
        );
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        OwnerResponseDto createdOwner = response.getBody();
        assertEquals(VALID_NEW_NAME, createdOwner.getName());
    }

    @Test
    @Order(3)
    public void testAndDeleteValidOwner() {

        String url = "/api/owners/" + VALID_EMAIL;
        client.delete(url);
        ResponseEntity<String> response = client.getForEntity(url,String.class);
        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

    }


}
