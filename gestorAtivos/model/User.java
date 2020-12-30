package model;

import java.util.ArrayList;

public class User {
    private String username;
    private String password;
    private UserType userType;
    private ArrayList<FinancialAsset> financialAssets;
    private ArrayList<Log> logs;

    public User(String username, String password, UserType userType) {
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.financialAssets = new ArrayList<>();
        this.logs = new ArrayList<>();
    }

    public User(String username, String password, UserType userType, ArrayList<FinancialAsset> financialAssets, ArrayList<Log> logs) {
        this.username = username;
        this.password = password;
        this.userType = userType;
        this.financialAssets = financialAssets;
        this.logs = logs;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
