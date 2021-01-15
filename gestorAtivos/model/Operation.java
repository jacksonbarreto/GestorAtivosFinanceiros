package model;

import java.io.Serializable;


public enum Operation implements Serializable {

    LOGIN(1L, "LOGING"), LOGOUT(2L, "LOGOUT"), CHANGED_PASSWORD(3L, "CHANGED PASSWORD"),
    CHANGED_USERNAME(4L, "CHANGED USERNAME"), CREATED_USER(5L, "CREATED USER"),
    CHANGED_USER_TYPE(6L, "CHANGED USER TYPE"), ADDED_DEPOSIT(7L, "ADDED A DEPOSIT"),
    ADDED_INVESTMENT_FUND(8L, "ADDED INVESTMENT FUND"), ADDED_PROPERTY(9L, "ADDED PROPERTY");


    private final Long id;
    private final String operation;

    Operation(Long id, String operation) {
        this.id = id;
        this.operation = operation;
    }

    public Long getId() {
        return id;
    }

    public String getOperation() {
        return operation;
    }
}
