package ca.mcgill.ecse321.gamemanager.dto;

public class EmployeeDto {
    private String name;
    private String email;

    public EmployeeDto() {}

    public EmployeeDto(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
