package model;

import javax.persistence.*;
import java.io.Serializable;


public enum Operation implements Serializable {

    LOGIN(1L, "LOGING"), LOGOUT(2L, "LOGOUT"), CHANGED_PASSWORD(3L, "CHANGED PASSWORD"),
    CHANGED_USERNAME(4L, "CHANGED USERNAME");


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
