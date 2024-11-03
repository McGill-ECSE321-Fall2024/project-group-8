package ca.mcgill.ecse321.gamemanager.repository;

import ca.mcgill.ecse321.gamemanager.model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class RequestRepositoryTests {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    @AfterEach
    public void clearDatabase() {
        requestRepository.deleteAll();
        gameRepository.deleteAll();
        employeeRepository.deleteAll();
        categoryRepository.deleteAll();
    }

    @Test
    public void testCreateAndLoadRequest() {
        // Initialize Category
        Category category = new Category("RPG", "Role-playing Game");
        category = categoryRepository.save(category);

        // Initialize Game
        Game game = new Game("Test Game", "An awesome RPG", "RPG", 49.99, 3, category, Game.GameStatus.InStock);
        game = gameRepository.save(game);

        // Initialize Employee
        Employee employee = new Employee("employee123","Test Employee","employee@test.com");
        employee = employeeRepository.save(employee);

        // Initialize Request
        Request request = new Request();
        request.setRequestType(Request.RequestType.Addition);
        request.setRequestStatus(Request.RequestStatus.Pending);
        request.setGame(game); // Ensuring game is associated
        request.setEmployee(employee); // Ensuring employee is associated

        // Save the request
        request = requestRepository.save(request);

        // Fetch the request from the database
        Request requestFromDb = requestRepository.findById(request.getRequestId()).orElse(null);

        assertNotNull(requestFromDb);
        assertEquals(Request.RequestType.Addition, requestFromDb.getRequestType());
        assertEquals(Request.RequestStatus.Pending, requestFromDb.getRequestStatus());
        assertNotNull(requestFromDb.getGame()); // Check if Game is associated
        assertNotNull(requestFromDb.getEmployee()); // Check if Employee is associated
        assertEquals(game.getGameId(), requestFromDb.getGame().getGameId());
        assertEquals(employee.getEmail(), requestFromDb.getEmployee().getEmail());
    }
}