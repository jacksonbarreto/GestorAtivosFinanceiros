package model;

import javafx.util.StringConverter;

import java.io.Serializable;
import java.util.Arrays;


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

    @Override
    public String toString() {
        return userType;
    }
    public static class UserTypeStringConverter extends StringConverter<UserType> {
        @Override
        public String toString (UserType object) {
            if (object == null) {
                return "no data";
            }
            return object.getUserType();
        }
        @Override
        public UserType fromString (String name) {
            return Arrays.stream(UserType.values())
                        .filter (userType->userType.userType.equalsIgnoreCase(name))
                    .findFirst().get();
        }
    }
}
