package mission.models;

public class CreateUserRequest {

    private String name;
    private String job;

    public CreateUserRequest(String name, String job) {
        this.name = name;
        this.job = job;
    }

}