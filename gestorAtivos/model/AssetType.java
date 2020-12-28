package model;

public enum AssetType {
    FOUND(1), DEPOSIT(2), PROPERTY(3);
    private final int type;

    AssetType(int type) {
        this.type = type;
    }

    public int getType() {
        return type;
    }
}
