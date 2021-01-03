package model;

import java.io.Serializable;


public enum AssetType implements Serializable {
    FOUND(1L, "FOUND"), DEPOSIT(2L, "DEPOSIT"), PROPERTY(3L, "PROPERTY");


    private final Long id;
    private final String assetType;

    AssetType(Long id, String assetType) {
        this.id = id;
        this.assetType = assetType;
    }


    public Long getId() {
        return id;
    }

    public String getAssetType() {
        return assetType;
    }
}
