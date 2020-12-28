package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Operation {
    private final String operation;
    private final User user;
    private final LocalDateTime timeStamp;

    public Operation(String operation, User user) {
        this.operation = operation;
        this.user = user;
        this.timeStamp = LocalDateTime.now();
    }
    public Operation(Operation operation){
        this.operation = operation.getOperation();
        this.user = operation.getUser();
        this.timeStamp = operation.getTimeStamp();
    }

    public String getOperation() {
        return operation;
    }

    public User getUser() {
        return user;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    @Override
    public String toString() {
        return this.timeStamp.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")) + " User: " + this.user.getUsername() +
                "\t" + this.operation;
    }
}
