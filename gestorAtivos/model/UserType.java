package model;

import java.io.Serializable;


public enum UserType implements Serializable {
    SIMPLE(1L, "SIMPLE"), MANAGER(2L, "MANAGER"), ROOT(3L, "ROOT");


    private final Long id;
    private final String userType;

    UserType(Long id, String userType) {
        this.id = id;
        this.userType = userType;
    }

    public Long getId() {
        return id;
    }

    public String getUserType() {
        return userType;
    }

}
