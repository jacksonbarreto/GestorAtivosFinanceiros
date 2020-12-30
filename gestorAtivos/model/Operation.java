package model;

public enum Operation {

    LOGIN(1, "LOGING"), LOGOUT(2, "LOGOUT"), CHANGED_PASSWORD(3, "CHANGED PASSWORD"),
    CHANGED_USERNAME(4, "CHANGED USERNAME");

    private final int id;
    private final String operation;

    Operation(int id, String operation) {
        this.id = id;
        this.operation = operation;
    }
}
