package model;

public enum UserType {
    SIMPLE(1), MANAGER(2), ROOT(3);
    private final int type;

    UserType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
